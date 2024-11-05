package nasuxjava.webnexus.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotEmpty
    @Min(value = 8, message = "Password must be at least 8 characters")
    private String password;

    private String fullName;
    private String address;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b")
    private String phone;

    private MultipartFile image;

    private String imageName;
    @Size(max = 1000, message = "Notes must not exceed 1000 characters.")
    private String note;

    // @NotNull(message = "Creation date cannot be blank")
    // @Past(message = "The creation date must be a date in the past")
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    // private Date datecreate;

    // public String getDateCreate() {
    // return new SimpleDateFormat("dd/MM/yyyy").format(this.datecreate);

    // }

}
