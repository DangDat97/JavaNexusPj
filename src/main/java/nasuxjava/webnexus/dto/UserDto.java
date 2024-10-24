package nasuxjava.webnexus.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// import nasuxjava.webnexus.entity.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Please provide a valid email address")
    private String email;
    @Min(value = 8, message = "Password must be at least 8 characters")
    private String password;

    // private String role;

    // @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    // @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
    // inverseJoinColumns = @JoinColumn(name = "role_id"))
    // private List<Role> roles;

    @NotEmpty(message = "Full Name cannot be empty")
    private String fullName;
    // @NotEmpty(message = "Address cannot be empty")
    // private String address;
    // @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Phone number
    // is not valid")
    // @NotEmpty(message = "Phone cannot be empty")
    // private String phone;

    // @Size(max = 255, message = "Image path cannot exceed 255 characters")
    // private String image;
    // @Size(max = 1000, message = "Notes must not exceed 1000 characters.")
    // private String note;

    // @NotNull(message = "Creation date cannot be blank")
    // @Past(message = "The creation date must be a date in the past")
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    // private Date datecreate;

    // public String getDateCreate() {
    // return new SimpleDateFormat("dd/MM/yyyy").format(this.datecreate);

    // }

}
