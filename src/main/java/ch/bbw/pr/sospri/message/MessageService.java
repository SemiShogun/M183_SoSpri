package ch.bbw.pr.sospri.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

	public void add(Message message) {
		repository.save(message);
	}

	public void update(Long id, Message message) {
		//save geht auch für update.
		repository.save(message);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
