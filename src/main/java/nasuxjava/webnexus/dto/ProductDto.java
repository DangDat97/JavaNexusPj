package nasuxjava.webnexus.dto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    private String detail;

    private Boolean status;

    private List<Long> categories;

    private Long distributor;

    private MultipartFile image;

    private List<MultipartFile> images;

    private List<String> lImageName;

    private String imageName;
}
