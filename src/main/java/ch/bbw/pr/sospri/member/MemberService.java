package ch.bbw.pr.sospri.member;

import ch.bbw.pr.sospri.mapper.UserDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * MemberService
 * 
 * @author Jamie Lam
 * @version 25.05.2021
 * 
 * https://www.baeldung.com/transaction-configuration-with-jpa-and-spring
 * https://reflectoring.io/spring-security-password-handli
 */
@Service
@Transactional
public class MemberService implements UserDetailsService {
	@Autowired
	private MemberRepository repository;
	
	public Iterable<Member> getAll(){
		return repository.findAll();
	}

	public void add(Member member) {
		repository.save(member);
	}

	public void update(Long id, Member member) {
		//save geht auch für update.
		repository.save(member);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	public Member getById(Long id) {
		Iterable<Member> memberitr = repository.findAll();
		
		for(Member member: memberitr){
			if (member.getId() == id) {
				return member;
			}
		}
		System.out.println("MemberService:getById(), id does not exist in repository: " + id);
		return null;
	}
	
	public Member getByUserName(String username) {
		Iterable<Member> memberitr = repository.findAll();
		
		for(Member member: memberitr){
			if (member.getUsername().equals(username)) {
				return member;
			}
		}
		System.out.println("MemberService:getByUserName(), username does not exist in repository: " + username);
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = getByUserName(username);
		return UserDetailsMapper.toUserDetails(member);
	}
}
