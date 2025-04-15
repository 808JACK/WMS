package warehouse.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import warehouse.Entities.ROLE;

@Data
@Getter
@Setter

public class SignupRequestDTO {

    private String username;
    private String email;
    private String password;
    private ROLE role;
}
