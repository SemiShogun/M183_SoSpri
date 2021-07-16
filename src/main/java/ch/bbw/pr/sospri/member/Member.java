package ch.bbw.pr.sospri.member;

import org.jboss.aerogear.security.otp.api.Base32;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * A member
 * 
 * @author Jamie Lam
 * @version 25.05.2021
 */
@Entity
@Table(name = "member")
public class Member {
	@Id
    @GeneratedValue(generator = "generatorMember", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generatorMember", initialValue=20)
	private Long id;
	
	@NotEmpty (message = "prename may not be empty" )
	@Size(min=2, max=512, message="Die Länge des Vornamens muss 2 bis 25 Zeichen sein.")
	private String prename;
	
	@NotEmpty (message = "lastname may not be empty" )
	@Size(min=2, max=20, message="Die Länge des Nachnamens 2 bis 25 Zeichen sein.")
	private String lastname;

	@NotEmpty(message = "Password can't be empty")
//	@Size(min=4, max=20, message="The length of the password has to be between 4 to 20 letters long.")
	private String password;
	private String username;
	private String authority;
	private String email;
	private boolean tfa;
	private String secret;

	public Member() {
		super();
		this.secret = Base32.random();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrename() {
		return prename;
	}

	public void setPrename(String prename) {
		this.prename = prename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public boolean isTfa() {
		return tfa;
	}

	public void setTfa(boolean tfa) {
		this.tfa = tfa;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	public String toString() {
		return "Member{" +
				"id=" + id +
				", prename='" + prename + '\'' +
				", lastname='" + lastname + '\'' +
				", password='" + password + '\'' +
				", username='" + username + '\'' +
				", authority='" + authority + '\'' +
				", email='" + email + '\'' +
				", tfa=" + tfa +
				", secret='" + secret + '\'' +
				'}';
	}
}
