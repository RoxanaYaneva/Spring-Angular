package mvc.spring.restmvc.dto;

import java.io.Serializable;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class InsertOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String paymentType;

    private Set<InsertOrderItemDTO> orderItems;

}
