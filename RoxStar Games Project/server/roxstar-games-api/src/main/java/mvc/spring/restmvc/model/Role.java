package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mvc.spring.restmvc.model.enums.UserProfile;

import javax.persistence.*;
import java.util.Collection;

@JsonPropertyOrder({"id", "name", "permissions"})
@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UserProfile userProfile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "permByRole",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    @JsonIgnore
    @ToString.Exclude
    private Collection<Permission> permissions;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    @ToString.Exclude
    private Collection<User> users;

    @JsonCreator
    @java.beans.ConstructorProperties({"name", "permissions"})
    public Role(UserProfile userProfile, Collection<Permission> permissions) {
        this.userProfile = userProfile;
        this.permissions = permissions;
    }
}
