package mvc.spring.restmvc.model;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;
import java.beans.Transient;

@Data
public class OrderProduct {

    private Game game;

    @PositiveOrZero
    private Integer quantity;

    @Transient
    public Double getTotalPrice() {
        return getGame().getPrice() * getQuantity();
    }
}
