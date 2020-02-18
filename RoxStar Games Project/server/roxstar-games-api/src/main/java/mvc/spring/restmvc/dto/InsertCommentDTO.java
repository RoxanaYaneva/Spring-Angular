package mvc.spring.restmvc.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InsertCommentDTO implements Serializable {

    private String text;

    private Long productId;

    private Long userId;

}
