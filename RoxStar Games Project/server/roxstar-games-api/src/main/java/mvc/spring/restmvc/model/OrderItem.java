package mvc.spring.restmvc.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.text.NumberFormat;
import java.util.Locale;

@Data
@Entity
@Table(name = "order_item")
@NoArgsConstructor
public class OrderItem {

    @EmbeddedId
    private OrderItemPK pk;

    //Percentage value, e.g.: 5% --> 0.05d
    private double discount;

    @PositiveOrZero
    private Integer quantity = 0;

    @Builder
    private OrderItem(Order order, Product product, double discount, int quantity) {
        pk = OrderItemPK.builder().order(order).product(product).build();
        this.discount = discount;
        this.quantity = quantity;
    }

    public Double getSubTotal() {
        return (getProduct().getPrice() * (1 - discount) * quantity);
    }

    // Add discount over the value already set
    public void addDiscount(double discount) {
        this.discount += discount;
    }

    public Product getProduct() {
        return pk.getProduct();
    }

    public void setProduct(Product product) {
        pk.setProduct(product);
    }

    @Override
    public String toString() {
        Locale locale = new Locale("en", "US");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        StringBuilder builder = new StringBuilder();
        builder.append(getProduct().getTitle());
        builder.append(", Quantity: ");
        builder.append(getQuantity());
        builder.append(", Unit price: ");
        builder.append(nf.format(getProduct().getPrice()));
        builder.append(", Unit discount: ");
        builder.append(nf.format(getDiscount() * getProduct().getPrice()));
        builder.append(", Subtotal: ");
        builder.append(nf.format(getSubTotal()));
        return builder.toString();
    }
}
