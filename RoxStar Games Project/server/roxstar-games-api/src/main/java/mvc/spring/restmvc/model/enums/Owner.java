package mvc.spring.restmvc.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Owner {

    OWN(1, "OWN"),
    ALL(2, "ALL");

    private Integer id;
    private String description;
}
