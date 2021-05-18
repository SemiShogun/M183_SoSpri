package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;
/**
 * RegisterController
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
	public String postRequestRegistMembers(RegisterMember registerMember, Model model) {
		System.out.println("postRequestRegistMembers: registerMember");
		System.out.println(registerMember);

		if (registerMember.getPassword() != registerMember.getConfirmation()) {
			model.addAttribute("registerMember", new RegisterMember());
			model.addAttribute("error", )
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