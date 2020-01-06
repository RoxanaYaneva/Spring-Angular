package mvc.spring.restmvc.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "permissions")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    public static final String OWN = "OWN";
    public static final String ALL = "ALL";
    public static final String READ = "READ";
    public static final String CREATE = "CREATE";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";

    @Id
    private String id;

    @NonNull
    @NotNull
    private String owner;

    @NonNull
    @NotNull
    private String asset;

    @NonNull
    @NotNull
    private String operation;

    @Override
    public String toString() {
        return this.getOwner() + "_" + this.getAsset() + "_" + this.getOperation();
    }
}