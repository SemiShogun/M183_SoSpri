package ch.bbw.pr.sospri.Controller;

import ch.bbw.pr.sospri.captcha.ReCaptchaValidationService;
import ch.bbw.pr.sospri.member.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

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

            memberservice.add(member);

            model.addAttribute("username", member.getUsername());
            return "registerconfirmed";

        } else {
            model.addAttribute("message", "Please verify Captcha");
            logger.error("invalid captcha");
        }
        return "register";
    }
}