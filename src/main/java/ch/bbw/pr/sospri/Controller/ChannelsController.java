package ch.bbw.pr.sospri.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

	private static final Logger logger = LogManager.getLogger(ChannelsController.class);

	@GetMapping("/channel")
	public String getRequestChannel(@RequestParam String channelName, Model model,
									HttpServletResponse response) {
		response.setContentType("image/jpeg");
		model.addAttribute("messages", messageservice.getChannelMessages(channelName));

		logger.warn("User opened channel " + messageservice.getChannelMessages(channelName).toString());

		Message message = new Message();
		message.setContent("");
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
									 @RequestParam("image") MultipartFile file, Principal principal, @AuthenticationPrincipal OAuth2User oAuth2User) {
		try {
			if(bindingResult.hasErrors()) {
				logger.error("postRequestChannel(): has Error(s): " + bindingResult.getErrorCount());
				bindingResult
						.getFieldErrors()
						.stream()
						.forEach(f -> logger.error(f.getField() + ": " + f.getDefaultMessage()));
				model.addAttribute("messages", messageservice.getAll());
				return "redirect:/channel?channelName=" + channel;
			}

			try {
				String name = oAuth2User.getAttribute("name").toString();
				byte[] image = file.getBytes();

				message.setOrigin(new Date());
				message.setChannel(channel);
				message.setAuthor(name);
				message.setPhoto(image);
				messageservice.add(message);
			} catch (Exception e) {
				String name = principal.getName();
				Member tmpMember = memberservice.getByUserName(name.replaceAll(" ", ".").toLowerCase());

				logger.warn("New message " + message);

				byte[] image = file.getBytes();

				message.setAuthor(tmpMember.getPrename() + " " + tmpMember.getLastname());
				message.setChannel(channel);
				message.setOrigin(new Date());
				message.setPhoto(message.getPhoto());
				message.setPhoto(image);
				messageservice.add(message);
			}

		} catch (Exception e) {
			logger.error("Exception detected: " + e);
		}
		return "redirect:/channel?channelName=" + channel;

	}
}
