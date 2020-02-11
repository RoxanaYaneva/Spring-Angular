package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.beans.Transient;

@Data
@Entity
@NoArgsConstructor
public class OrderItem {

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private OrderItemKey pk;

    @ManyToOne
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer quantity;

    public OrderItem(Order order, Product product, Integer quantity) {
        pk = new OrderItemKey();
        pk.setOrderId(order.getId());
        pk.setProductId(product.getId());
        this.quantity = quantity;
    }

    @Transient
    public Product getProduct() {
        return product;
    }

    @Transient
    public Double getTotalPrice() {
        return getProduct().getPrice() * getQuantity();
    }
}
