package ch.bbw.pr.sospri.Controller;

import java.security.Principal;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.message.Message;
import ch.bbw.pr.sospri.message.MessageService;

/**
 * ChannelsController
 * @author Jamie Lam
 * @version 25.05.2021
 */
@Controller("/channel")
public class ChannelsController {
	@Autowired
	MessageService messageservice;
	@Autowired
	MemberService memberservice;

	@GetMapping("/channel")
	public String getRequestChannel(@RequestParam String channelName, Model model) {
		System.out.println("getRequestChannel");
		model.addAttribute("messages", messageservice.getChannelMessages(channelName));
		
		Message message = new Message();
		message.setContent("");
		System.out.println("message: " + message);
		model.addAttribute("message", message);
		return "channel";
	}

	@PostMapping("/add-message/{channelName}")
	public String postRequestChannel(@PathVariable(value="channelName") String channel, Model model,
									 @ModelAttribute @Valid Message message, BindingResult bindingResult,
									 Principal principal) {
		try {
			System.out.println("postRequestChannel(): message: " + message.toString());
			if(bindingResult.hasErrors()) {
				System.out.println("postRequestChannel(): has Error(s): " + bindingResult.getErrorCount());
				bindingResult
						.getFieldErrors()
						.stream()
						.forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));
				model.addAttribute("messages", messageservice.getAll());
				return "redirect:/channel?channelName=" + channel;
			}
			// Hack solange es kein authenticated member hat

			String name = principal.getName();
			Member tmpMember = memberservice.getByUserName(name.replaceAll(" ", ".").toLowerCase());
			message.setAuthor(tmpMember.getPrename() + " " + tmpMember.getLastname());
			message.setChannel(channel);
			message.setOrigin(new Date());
			System.out.println("message: " + message);
			messageservice.add(message);
			System.out.println("added message: " + message);

		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/channel?channelName=" + channel;

	}
}
