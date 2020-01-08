package mvc.spring.restmvc.forms;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginForm {
    @NotNull
    @Length(min = 6, max = 40)
    @Email
    private String email;

    @NotNull
    @Size(min=8, max=100)
    private String password;
}
