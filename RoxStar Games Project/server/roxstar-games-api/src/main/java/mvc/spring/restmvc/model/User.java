package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties(value = {"authorities", "name", "password",
        "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
@JsonPropertyOrder({"id"})
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
@Builder
public class User {

    private static final String DEFAULT_IMAGE = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique = true)
    private String email;

    @Length(min = 8, max = 100)
    @JsonIgnore
    private String password;

    @Length(min = 2, max = 30)
    private String firstName;

    @Length(min = 2, max = 30)
    private String lastName;

    @Builder.Default
    private String imageUrl = DEFAULT_IMAGE;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private List<Order> orders;

    @Builder.Default
    private boolean active = true;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime registered = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime updated = LocalDateTime.now();

    public User(Long id,
                @NotNull @Length(min = 6, max = 40) String email,
                @NotNull @Length(min = 8, max = 30) String password,
                @NotNull @Length(min = 2, max = 30) String fname,
                @NotNull @Length(min = 2, max = 30) String lname,
                Set<Role> roles,
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
                Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.firstName = fname;
        this.lastName = lname;
        this.roles = roles;
    }

}
