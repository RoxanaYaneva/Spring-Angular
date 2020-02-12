package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "text"})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 240)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference(value = "comment_product")
    @ToString.Exclude
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "comment_user")
//    @JsonBackReference(value = "user_comment")
    @ToString.Exclude
    private User user; // can take only certain fields with a DTO

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime edited;
}
