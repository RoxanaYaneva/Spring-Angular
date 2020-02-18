package mvc.spring.restmvc.dto;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class InsertOrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long productId;
    private Integer quantity;

}