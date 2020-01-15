package mvc.spring.blogsapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    @NonNull
    @NotNull(message = "firsName must not be empty")
    private String firstName;
    @NonNull
    @NotNull(message = "lastName must not be empty")
    private String lastName;
    @NonNull
    @NotNull(message = "email must not be empty")
    @Email(message = "email format not right")
    private String email;
    @NonNull
    @NotNull
    private String password;
    @DBRef
    private List<Role> roles = new ArrayList<>();
    private String imageUrl;

    private boolean active = true;

    @JsonCreator
    @java.beans.ConstructorProperties({"email", "password", "fname", "lname", "roles"})
    public User(@NotNull String email, String password, String fname, String lname, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.firstName = fname;
        this.lastName = lname;
        this.roles = roles;
    }
}
