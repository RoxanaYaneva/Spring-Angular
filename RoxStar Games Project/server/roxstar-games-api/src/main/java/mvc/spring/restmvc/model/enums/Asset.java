package mvc.spring.restmvc.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Asset {

    USER(1, "USER"),
    GAME(2, "GAME");

    private Integer id;
    private String description;
}

