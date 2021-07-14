package ch.bbw.pr.sospri.config;

import ch.bbw.pr.sospri.Controller.RegisterController;
import ch.bbw.pr.sospri.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberservice;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(RegisterController.getPepper(), 185000, 256);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.memberservice);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/css/*", "/fragments/*", "/img/*", "/errors/*").permitAll()
                .antMatchers("/channel").hasAnyAuthority("admin","supervisor","member","ROLE_USER")
                .antMatchers("/get-register").permitAll()
                .antMatchers("/oauth-login").permitAll()
                .antMatchers("/get-members").hasAuthority("admin")
                .anyRequest().authenticated()
                .and().logout().permitAll()
                .and().httpBasic()
                .and().oauth2Login().loginPage("/oauth-login").defaultSuccessUrl("/", true).failureUrl("/errors/loginerror.html")
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/", true).failureUrl("/errors/loginerror.html")
                .and().exceptionHandling().accessDeniedPage("/errors/403.html")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).invalidSessionUrl("/login")
                .and().headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/actuator/**");
    }

}
