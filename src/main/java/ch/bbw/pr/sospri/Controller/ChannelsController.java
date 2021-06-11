package ch.bbw.pr.sospri.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Blob;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import org.springframework.web.multipart.MultipartFile;

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
	public String getRequestChannel(@RequestParam String channelName, Model model,
									HttpServletResponse response) {
		response.setContentType("image/jpeg");
		System.out.println("getRequestChannel");
		model.addAttribute("messages", messageservice.getChannelMessages(channelName));

		System.out.println(messageservice.getChannelMessages(channelName).toString());

		Message message = new Message();
		message.setContent("");
		System.out.println("message: " + message);
		model.addAttribute("message", message);
		return "channel";
	}

	@GetMapping(value = "/getPhoto/{id}")
	public void getPhoto(@PathVariable("id") String id, HttpServletResponse response) throws Exception {
		response.setContentType("image/jpeg");
		Message message = messageservice.findById(Long.parseLong(id));
		InputStream is = new ByteArrayInputStream(message.getPhoto());
		IOUtils.copy(is, response.getOutputStream());
	}

	@PostMapping("/add-message/{channelName}")
	public String postRequestChannel(@PathVariable(value="channelName") String channel, Model model,
									 @ModelAttribute @Valid Message message, BindingResult bindingResult,
									 @RequestParam("image") MultipartFile file, Principal principal) {
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

			System.out.println(file.getBytes());

			byte[] image = file.getBytes();

			message.setAuthor(tmpMember.getPrename() + " " + tmpMember.getLastname());
			message.setChannel(channel);
			message.setOrigin(new Date());
			message.setPhoto(message.getPhoto());
			message.setPhoto(image);
			System.out.println("message: " + message);
			messageservice.add(message);
			System.out.println("added message: " + message);

		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/channel?channelName=" + channel;

	}
}
