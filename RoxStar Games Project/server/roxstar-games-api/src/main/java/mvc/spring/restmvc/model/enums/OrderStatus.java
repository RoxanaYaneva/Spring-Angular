package mvc.spring.restmvc.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatus {

    PENDING, CLEARED, CANCELED, SHIPPED;

}
