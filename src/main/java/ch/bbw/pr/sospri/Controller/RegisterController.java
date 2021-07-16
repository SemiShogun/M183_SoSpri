package ch.bbw.pr.sospri.Controller;

import ch.bbw.pr.sospri.captcha.ReCaptchaValidationService;
import ch.bbw.pr.sospri.member.Member;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.awt.*;
import java.net.URLEncoder;
import java.security.SecureRandom;

/**
 * RegisterController
 *
 * @author Jamie Lam
 * @version 25.05.2021
 */
@Controller
public class RegisterController {
    @Autowired
    private ReCaptchaValidationService validator;

    @Autowired
    MemberService memberservice;

    private static final Logger logger = LogManager.getLogger(MembersController.class);

    private static final String PEPPER = "DingDongChingChong";

    public static String QR_PREFIX =
            "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";

    public static String APP_NAME = "SoSpri";

    public static String getPepper() {
        return PEPPER;
    }

    @GetMapping("/get-register")
    public String getRequestRegistMembers(Model model) {
        model.addAttribute("registerMember", new RegisterMember());
        return "register";
    }

    @PostMapping("/get-register")
    public String postRequestRegistMembers(@Valid RegisterMember registerMember, BindingResult bindingResult, @RequestParam(name="g-recaptcha-response") String captcha, Model model) {

        if (validator.validateCaptcha(captcha)) {
            if (bindingResult.hasErrors()) {
//			model.addAttribute("registerMember", new RegisterMember());
                return "register";
            }

            if (!registerMember.getPassword().equals(registerMember.getConfirmation())) {
                model.addAttribute("registerMember", new RegisterMember());
                model.addAttribute("message", "Password and Password confirmation are not equal!");
                return "register";
            }

            if (memberservice.getByUserName(registerMember.getPrename().toLowerCase() + "." +
                    registerMember.getLastname().toLowerCase()) != null) {
                model.addAttribute("message", "Username " + registerMember.getPrename().toLowerCase() + "." +
                        registerMember.getLastname().toLowerCase() + " already exists");

                return "register";
            }

            Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder(PEPPER, 185000, 256);
            String encodedpw = encoder.encode(registerMember.getPassword());

            Member member = new Member();
            member.setAuthority("member");
            member.setPrename(registerMember.getPrename());
            member.setLastname(registerMember.getLastname());
            member.setPassword(encodedpw);
            member.setUsername(member.getPrename() + "." + member.getLastname());
            member.setEmail(registerMember.getEmail());
            member.setTfa(registerMember.isTfa());

            memberservice.add(member);

            if (member.isTfa()) {
                model.addAttribute("qr", generateQRUrl(member));
                return "qrcode";
            }

            model.addAttribute("username", member.getUsername());
            model.addAttribute("email", member.getEmail());
            return "registerconfirmed";

        } else {
            model.addAttribute("message", "Please verify Captcha");
            logger.error("invalid captcha");
        }
        return "register";
    }

    @PostMapping("/confirmation")
    public String confirmTfa(Model model, @RequestParam String code, @RequestParam String email, BindingResult bindingResult) {
        if (getTOTPCode(memberservice.getByUserName(email).getSecret()) == getTOTPCode(memberservice.getByUserName(email).getSecret())) {
            model.addAttribute("message", "2 Factor Authentication successfully confirmed");
        }
        return "/";
    }

    public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    public String generateQRUrl(Member member) {
        return QR_PREFIX + URLEncoder.encode(String.format(
            "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                APP_NAME, member.getEmail(), member.getSecret(), APP_NAME, "UTF-8"
        ));
    }
}