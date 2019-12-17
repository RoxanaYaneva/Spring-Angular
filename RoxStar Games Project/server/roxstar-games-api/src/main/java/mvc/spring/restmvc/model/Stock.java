package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Document(collection = "stock")
@Data
@NoArgsConstructor
public class Stock {
    @Id
    private String id;

    @NonNull
    @Length(min = 24, max = 24)
    private String productId;

    @PositiveOrZero
    private Integer quantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated = LocalDateTime.now();

    public Stock(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
