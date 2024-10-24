package nasuxjava.webnexus.entity;

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

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String role;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Column(name = "full_name")
    private String fullName;
    private String address;

    private String phone;

    private String image;
    private String note;

    // @NotNull(message = "Creation date cannot be blank")
    @Past(message = "The creation date must be a date in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datecreate;

    public String getDateCreate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(this.datecreate);

    }

}
