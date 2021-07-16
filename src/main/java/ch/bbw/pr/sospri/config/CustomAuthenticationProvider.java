package ch.bbw.pr.sospri.config;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberRepository;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MemberRepository userRepository;

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        String verificationCode
                = ((CustomWebAuthenticationDetails) auth.getDetails())
                .getVerificationCode();
        Member user = userRepository.findByEmail(username);
        if ((user == null)) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (user.isTfa()) {
            Totp totp = new Totp(user.getSecret());
            if (!totp.verify(verificationCode)) {
                throw new BadCredentialsException("Invalid verification code");
            }
        }

        System.out.println(user.isTfa());
        System.out.println(user.getSecret());
        System.out.println("PASS PASS PASS");

        return new UsernamePasswordAuthenticationToken(
                user, password, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}