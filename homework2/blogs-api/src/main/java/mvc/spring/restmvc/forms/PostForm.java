package mvc.spring.restmvc.forms;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostForm {
    @NotNull
    private String title;

    @NotNull
    @Size(max=500, message = "Maximum 500 characters!")
    private String text;

    private String imageUrl;

    private List<String> tags = new ArrayList<>();
}
