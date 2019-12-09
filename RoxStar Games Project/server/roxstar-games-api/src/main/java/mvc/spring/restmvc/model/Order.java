package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.Identifiable;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
@Data
@AllArgsConstructor
public class Order implements Identifiable<String> {

    @Id
    private String id;

    @NonNull
    @NotNull
    private String status;

    @NonNull
    @Length(min = 24, max = 24)
    private String userId;

    @NonNull
    private List<Game> products = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated = LocalDateTime.now();

    @Override
    public String getId() {
        return id;
    }
}
