package ch.bbw.pr.sospri.message;

import org.springframework.data.repository.CrudRepository;
/**
 * MessageRepository
 * 
 * @author Jamie Lam
 * @version 25.05.2020
 */
                                                        //Klasse, id-Typ
public interface MessageRepository extends CrudRepository<Message, Long>{
	//Da wir eine embedded database verwenden, braucht es keine Conecction Information.
}

