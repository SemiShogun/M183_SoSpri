package ch.bbw.pr.sospri.mapper;

import ch.bbw.pr.sospri.config.MemberGrantedAuthority;
import ch.bbw.pr.sospri.member.Member;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class UserDetailsMapper {

    static public UserDetails toUserDetails(Member member) {
        User user = null;

        if (member != null) {
            Collection<MemberGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new MemberGrantedAuthority(member.getAuthority()));
            user = new User(member.getPrename() + " " + member.getLastname(), member.getPassword(), true, true, true, true, authorities);
        }
        return user;
   }
}
