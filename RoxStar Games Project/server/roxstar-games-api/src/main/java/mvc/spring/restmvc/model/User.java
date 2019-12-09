package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.Identifiable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Document(collection = "users")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(value = {"authorities", "name",
        "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
public class User implements UserDetails, Identifiable<String> {

    @Id
    private String id;

    @NotNull
    @NonNull
    @Length(min = 6, max = 40)
    private String email;

    @NotNull
    @NonNull
    @Length(min = 8, max = 30)
    private String password;

    @NotNull
    @NonNull
    @Length(min = 2, max = 30)
    private String firstName;

    @NotNull
    @NonNull
    @Length(min = 2, max = 30)
    private String lastName;

    @NonNull
    private List<Role> roles = new ArrayList<>();

    private boolean active = true;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm::ss")
    private LocalDateTime registered = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm::ss")
    private  LocalDateTime updated = LocalDateTime.now();

    public User(String id,
                @NotNull @Length(min = 6, max = 40) String email,
                @NotNull @Length(min = 8, max = 30) String password,
                @NotNull @Length(min = 2, max = 30) String fname,
                @NotNull @Length(min = 2, max = 30) String lname,
                List<Role> roles,
                boolean active,
                LocalDateTime created,
                LocalDateTime updated) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = fname;
        this.lastName = lname;
        this.roles = roles;
        this.active = active;
        this.registered = created;
        this.updated = updated;
    }

    @JsonCreator
    @java.beans.ConstructorProperties({"email", "password", "firstName", "lastName", "roles"})
    public User(@NotNull @Length(min = 6, max = 40) String email,
                @Length(min = 8, max = 30) String password,
                @Length(min = 2, max = 30) String fname,
                @Length(min = 2, max = 30) String lname,
                List<Role> roles) {
        this.email = email;
        this.password = password;
        this.firstName= fname;
        this.lastName = lname;
        this.roles = roles;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role ->
                        Stream.concat(
                                Stream.of(new SimpleGrantedAuthority(role.getName())),
                                role.getPermissions().stream().map(perm -> new SimpleGrantedAuthority(perm.toString()))

                        )
                ).collect(Collectors.toSet());
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
