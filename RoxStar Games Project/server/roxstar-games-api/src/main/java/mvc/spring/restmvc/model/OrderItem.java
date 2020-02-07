package mvc.spring.restmvc.model;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;
import java.beans.Transient;

@Data
public class OrderItem {

    private Product game;

    @PositiveOrZero
    private Integer quantity;

    @Transient
    public Double getTotalPrice() {
        return getGame().getPrice() * getQuantity();
    }
}
