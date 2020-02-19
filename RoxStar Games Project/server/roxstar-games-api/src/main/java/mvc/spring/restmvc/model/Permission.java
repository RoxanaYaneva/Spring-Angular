package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import mvc.spring.restmvc.model.enums.Asset;
import mvc.spring.restmvc.model.enums.Operation;
import mvc.spring.restmvc.model.enums.Owner;

import javax.persistence.*;
import java.util.Collection;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Owner owner;

    private Asset asset;

    private Operation operation;

    @ManyToMany(mappedBy = "permissions")
    @ToString.Exclude
    @JsonIgnore
    private Collection<Role> roles;

    public Permission(Owner owner, Asset asset, Operation operation) {
        this.owner = owner;
        this.asset = asset;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return this.getOwner().getDescription() + "_" + this.getAsset().getDescription() + "_" + this.getOperation().getDescription();
    }
}
