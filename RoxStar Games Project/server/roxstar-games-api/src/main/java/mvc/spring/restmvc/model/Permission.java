package mvc.spring.restmvc.model;

import lombok.*;
import mvc.spring.restmvc.model.enums.Asset;
import mvc.spring.restmvc.model.enums.Operation;
import mvc.spring.restmvc.model.enums.Owner;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "permissions")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    @Id
    private String id;

    @NonNull
    @NotNull
    private Owner owner;

    @NonNull
    @NotNull
    private Asset asset;

    @NonNull
    @NotNull
    private Operation operation;

    @Override
    public String toString() {
        return this.getOwner().getDescription() + "_" + this.getAsset().getDescription() + "_" + this.getOperation().getDescription();
    }
}
