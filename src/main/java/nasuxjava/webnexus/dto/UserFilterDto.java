package nasuxjava.webnexus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterDto {
    private Long id;

    private String email;

    private String fullName;

    private String phone;

    private int page = 0;
    private int size = 10;

}