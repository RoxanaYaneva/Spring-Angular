package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "comments")
@Data
public class Comment {

    @Id
    private String id;

    @NonNull
    @Length(max = 240)
    private String text;

    @NonNull
    @Length(min = 24, max = 24)
    private String gameId;

    @NonNull
    @Length(min = 24, max = 24)
    private String userId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime edited = LocalDateTime.now();
}
