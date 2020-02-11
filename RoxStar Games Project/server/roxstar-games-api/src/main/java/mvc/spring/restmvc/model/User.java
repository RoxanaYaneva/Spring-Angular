package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(value = {"authorities", "name", "password",
        "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
@JsonPropertyOrder({"id"})
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    private static final String DEFAULT_IMAGE = "https://cdn3.iconfinder.com/data/icons/vector-icons-6/96/256-512.png";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique=true)
    private String email;

    @Length(min = 8, max = 100)
    @JsonIgnore
    private String password;

    @Length(min = 2, max = 30)
    private String firstName;

    @Length(min = 2, max = 30)
    private String lastName;

    private String imageUrl = DEFAULT_IMAGE;

    @ManyToMany(fetch = FetchType.EAGER)
//    @Builder.Default
    @JoinTable(name = "rolesByUser",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
    private List<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JsonManagedReference(value = "comment_user")
//    @JsonManagedReference(value = "user_comment")
    @ToString.Exclude
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JsonManagedReference(value = "user_order")
    @JsonIgnore
    @ToString.Exclude
    private List<Order> orders;

    private boolean active = true;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registered = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private  LocalDateTime updated = LocalDateTime.now();

    public User(Long id,
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

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles.stream()
//                .flatMap(role ->
//                        Stream.concat(
//                                Stream.of(new SimpleGrantedAuthority(role.getName())),
//                                role.getPermissions().stream().map(perm -> new SimpleGrantedAuthority(perm.toString()))
//
//                        )
//                ).collect(Collectors.toSet());
//    }
//
//    @JsonIgnore
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return active;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return active;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return active;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return active;
//    }
}
