package ch.bbw.pr.sospri.Controller;

import ch.bbw.pr.sospri.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;

/**
 * RegisterController
 *
 * @author Peter Rutschmann
 * @version 26.03.2020
 */
@Controller
public class RegisterController {
    @Autowired
    MemberService memberservice;

    @GetMapping("/get-register")
    public String getRequestRegistMembers(Model model) {
        System.out.println("getRequestRegistMembers");
        model.addAttribute("registerMember", new RegisterMember());
        return "register";
    }

    @PostMapping("/get-register")
    public String postRequestRegistMembers(@Valid RegisterMember registerMember, BindingResult bindingResult, Model model) {
        System.out.println("postRequestRegistMembers: registerMember");
        System.out.println(registerMember);

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
            System.out.println("User already exists. Choose another first- or lastname.");
            model.addAttribute("message", "Username " + registerMember.getPrename().toLowerCase() + "." +
                    registerMember.getLastname().toLowerCase() + " already exists");

            return "register";
        }

        Member member = new Member();
        member.setAuthority("member");
        member.setPrename(registerMember.getPrename());
        member.setLastname(registerMember.getLastname());
        member.setPassword(registerMember.getPassword());
        member.setUsername(member.getPrename() + "." + member.getLastname());

        memberservice.add(member);

        model.addAttribute("username", member.getUsername());

        return "registerconfirmed";
    }
}