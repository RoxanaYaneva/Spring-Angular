package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NonNull;
import mvc.spring.restmvc.model.enums.UserProfile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Document(collection = "roles")
@JsonPropertyOrder({"id", "name", "permissions"})
@Data
public class Role {

    @Id
    private String id;

    @NotNull
    @NonNull
    private UserProfile userProfile;

    @JsonManagedReference
    private Collection<Permission> permissions = new ArrayList<>();

    @JsonCreator
    @java.beans.ConstructorProperties({"name", "permissions"})
    public Role(@NotNull UserProfile userProfile, Collection<Permission> permissions) {
        this.userProfile = userProfile;
        this.permissions = permissions;
    }
}
