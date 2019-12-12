package mvc.spring.restmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "games")
@Data
@AllArgsConstructor
public class Game {

    @Id
    private String id;

    @NonNull
    @Length(min = 1, max = 60)
    private String title;

    @NonNull
    @Length(min = 2, max = 20)
    private String genre;

    @NonNull
    @Length(max = 200)
    private String description;

    @NonNull
    @Length(max = 30)
    private String studio;

    @NonNull
    @Length(max = 30)
    private String platform;

    @NonNull
    @Length(max = 20)
    private String type;

    private double price;

    private List<String> imageUrls = new ArrayList<>();

}
