package ch.bbw.pr.sospri.member;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * To regist a new Member
 *
 * @author Jamie Lam
 * @version 25.05.2021
 */
public class RegisterMember {

    @Size(min = 1, max = 20, message = "Your surname must be between 1 to 20 characters long.")
    private String prename;

    @Size(min = 1, max = 20, message = "Your lastname must be between 1 to 20 characters long.	")
    private String lastname;

    @Pattern(message = "Your password must contain atleast one capital letter and one number",
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
    private String password;

    @Pattern(message = "Your confirmation password must contain atleast one capital letter and one number",
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
    private String confirmation;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    @Override
    public String toString() {
        return "RegisterMember [prename=" + prename + ", lastname=" + lastname + ", password=" + password
                + ", confirmation=" + confirmation + "]";
    }
}
