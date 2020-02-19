package mvc.spring.restmvc.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatus {

    PENDING(1, "Pending"),
    CLEARED(2, "Cleared"),
    CANCELED(3, "Canceled"),
    SHIPPED(4, "Shipped");

    private Integer id;
    private String description;
}
