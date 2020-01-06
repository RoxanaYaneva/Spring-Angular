package mvc.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    public final static String STATUS_ACTIVE = "active";
    public final static String STATUS_INACTIVE = "inactive";

    @Id
    private String id;

    @NonNull
    @NotNull
    private String title;

    @NonNull
    @NotNull
    private String text;

    @NonNull
    @NotNull
    private String authorId;

    private List<String> tags = new ArrayList<>();

    private String imageUrl;

    private String status = STATUS_ACTIVE;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime published = LocalDateTime.now();
}