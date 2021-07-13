package ch.bbw.pr.sospri.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
/**
 * UsersController
 * @author Jamie Lam
 * @version 25.05.2021
 */
@Controller
public class MembersController {
	@Autowired
	MemberService memberservice;

	private static final Logger logger = LogManager.getLogger(MembersController.class);

	@GetMapping("/get-members")
	public String getRequestMembers(Model model) {
		model.addAttribute("members", memberservice.getAll());
		return "members";
	}
	
	@GetMapping("/edit-member")
	public String editMember(@RequestParam(name="id", required = true) long id, Model model) {
		Member member = memberservice.getById(id);
		model.addAttribute("member", member);
		logger.warn("editMember get: " + member);
		return "editmember";
	}

	@PostMapping("/edit-member")
	public String editMember(Member member, Model model) {
		Member value = memberservice.getById(member.getId());
		value.setAuthority(member.getAuthority());
		memberservice.update(member.getId(), value);
		logger.warn("editMember post: edit member" + member);
		logger.warn("editMember post: update member" + value);
		return "redirect:/get-members";
	}

	@GetMapping("/delete-member")
	public String deleteMember(@RequestParam(name="id", required = true) long id, Model model) {
		memberservice.deleteById(id);
		logger.warn("deleteMember: " + id);
		return "redirect:/get-members";
	}
}
