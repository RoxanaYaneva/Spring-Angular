package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(value = {"authorities", "name",
        "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
public class User {

    private static final String DEFAULT_IMAGE = "https://cdn3.iconfinder.com/data/icons/vector-icons-6/96/256-512.png";

    @Id
    private String id;

    @NotNull
    @NonNull
    @Length(min = 6, max = 40)
    @Email
    private String email;

    @NotNull
    @NonNull
    @Length(min = 8, max = 100)
    private String password;

    @NotNull
    @NonNull
    @Length(min = 2, max = 30)
    private String firstName;

    @NotNull
    @NonNull
    @Length(min = 2, max = 30)
    private String lastName;

    private String imageUrl = DEFAULT_IMAGE;

    @NonNull
    @JsonManagedReference
    private List<Role> roles = new ArrayList<>();

    private boolean active = true;

    public User(String id,
                @NotNull @Length(min = 6, max = 40) String email,
                @NotNull @Length(min = 8, max = 100) String password,
                @NotNull @Length(min = 2, max = 30) String fname,
                @NotNull @Length(min = 2, max = 30) String lname,
                List<Role> roles,
                boolean active) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = fname;
        this.lastName = lname;
        this.roles = roles;
        this.active = active;
    }

    @JsonCreator
    @java.beans.ConstructorProperties({"email", "password", "firstName", "lastName", "roles"})
    public User(@NotNull @Length(min = 6, max = 40) String email,
                @Length(min = 8, max = 100) String password,
                @Length(min = 2, max = 30) String fname,
                @Length(min = 2, max = 30) String lname,
                List<Role> roles) {
        this.email = email;
        this.password = password;
        this.firstName= fname;
        this.lastName = lname;
        this.roles = roles;
    }
}
