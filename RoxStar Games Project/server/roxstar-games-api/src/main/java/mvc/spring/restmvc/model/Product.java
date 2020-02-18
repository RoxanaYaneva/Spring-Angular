package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.omg.CORBA.ServiceDetailHelper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 1, max = 60)
    @Column(unique = true)
    private String title;

    @Length(min = 2, max = 20)
    private String genre;

    @Length(max = 700)
    private String description;

    @Length(max = 30)
    private String studio;

    @Length(max = 30)
    private String platform;

    @Length(max = 20)
    private String type;

    private double price;

    private boolean onSale;

    private String imageUrl;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime released = LocalDateTime.now();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnore
    @ToString.Exclude
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "pk.product")
    @Setter(value = AccessLevel.NONE)
    private List<OrderItem> orderItems;

}
