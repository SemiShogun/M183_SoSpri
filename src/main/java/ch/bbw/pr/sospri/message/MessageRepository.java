package ch.bbw.pr.sospri.message;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * MessageRepository
 * 
 * @author Jamie Lam
 * @version 25.05.2020
 */

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>{
	//Da wir eine embedded database verwenden, braucht es keine Conecction Information.

    @Query(value = "SELECT m FROM Message m WHERE m.channel = :channel")
    Iterable<Message> getChannelMessages(@Param("channel") String channel);

    @Modifying
    @Query(value = "INSERT INTO Message(id, author, channel, content, origin) VALUES :message", nativeQuery = true)
    @Transactional
    void addChannelMessage(@Param("message") Message message);
}
