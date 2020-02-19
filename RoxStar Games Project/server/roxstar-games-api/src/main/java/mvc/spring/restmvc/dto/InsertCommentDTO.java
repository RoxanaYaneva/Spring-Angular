package mvc.spring.restmvc.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InsertCommentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String text;

    private Long productId;

    private Long userId;

}
