package mvc.spring.restmvc.forms;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterForm {
    @NotNull
    @Length(min = 6, max = 40)
    @Email
    private String email;

    @NotNull
    @Size(min=1, max=50)
    private String password;

    @NotNull
    @Length(min = 2, max = 30)
    private String firstName;

    @NotNull
    @Length(min = 2, max = 30)
    private String lastName;

    private String imageUrl;
}
