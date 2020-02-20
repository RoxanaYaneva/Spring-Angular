package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import mvc.spring.restmvc.model.enums.OrderStatus;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "orders")
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "pk.order", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<OrderItem> orderItems = new HashSet<>();

    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime updated = LocalDateTime.now();

    public Double getTotal() {
        double total = 0d;
        for (OrderItem item : orderItems) {
            total += item.getSubTotal();
        }
        return total;
    }

    @Override
    public String toString() {
        Locale locale = new Locale("en", "US");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        DateFormat dt = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        StringBuilder builder = new StringBuilder();
        builder.append("\nOrder number: #");
        builder.append(getId());
        builder.append(", Created: ");
        builder.append(dt.format(getCreated()));
        builder.append(", Customer: ");
        builder.append(getUser().getEmail());
        builder.append("\nDetails:\n");
        for (OrderItem item : getOrderItems()) {
            builder.append(item);
            builder.append("\n");
        }
        builder.append("Total: ");
        builder.append(nf.format(getTotal()));
        return builder.toString();
    }

}
