package mvc.spring.restmvc.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Operation {

    READ(1, "READ"),
    CREATE(2, "CREATE"),
    UPDATE(3, "UPDATE"),
    DELETE(4, "DELETE");

    private Integer id;
    private String description;
}
