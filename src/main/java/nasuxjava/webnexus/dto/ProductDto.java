package nasuxjava.webnexus.dto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @NotNull(message = "Product price cannot be blank")
    @DecimalMin(value = "0.0", inclusive = false, message = "Product price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "Product description cannot be blank")
    private String description;

    @NotBlank(message = "Product details cannot be left blank")
    private String detail;

    private Boolean status;

    @NotEmpty(message = "Product category cannot be empty")
    private List<Long> categories;

    @NotNull(message = "Distributor cannot be left blank")
    private Long distributor;

    @NotNull(message = "Product image cannot be blank")
    private MultipartFile image;

    private List<MultipartFile> images;

    private List<String> lImageName;

    private String imageName;
}
