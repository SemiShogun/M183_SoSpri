package ch.bbw.pr.sospri.member;

import org.springframework.data.repository.CrudRepository;
/**
 * MemberRepository
 * 
 * @author Peter Rutschmann
 * @version 26.03.2020
 */
                                                       //Klasse, id-Typ
public interface MemberRepository extends CrudRepository<Member, Long>{
	//Da wir eine embedded database verwenden, braucht es keine Conecction Information.
}

