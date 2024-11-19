package nasuxjava.webnexus.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDto {

    private String name = "";

    private BigDecimal min_price = BigDecimal.valueOf(0);

    private BigDecimal max_price = BigDecimal.valueOf(100);

    private List<Long> categories;

    private Long distributor;

    private Boolean status = true;

    private int page = 0;
    private int size = 10;
}
