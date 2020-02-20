package mvc.spring.restmvc.model;

import lombok.*;
import mvc.spring.restmvc.model.enums.Asset;
import mvc.spring.restmvc.model.enums.Operation;
import mvc.spring.restmvc.model.enums.Owner;

import javax.persistence.*;

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
