package ch.bbw.pr.sospri.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.Optional;

/**
 * MessageService
 * 
 * @author Jamie Lam
 * @version 25.05.2021
 */
@Service
public class MessageService {
	@Autowired
	private MessageRepository repository;
	
	public Iterable<Message> getAll(){
		return repository.findAll();
	}

	public Iterable<Message> getChannelMessages(String channel) {
		return repository.getChannelMessages(channel);
	}

	public byte[] getPhotoById(int id) {
		return repository.getPhotoById(id);
	}

	public Message findById(Long id) {
		return repository.findById(id).get();
	}

	public void add(Message message) {
		repository.save(message);
	}

	public void addChannelMessage(Message message) {
		repository.addChannelMessage(message);
	}

	public void update(Long id, Message message) {
		//save geht auch für update.
		repository.save(message);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
