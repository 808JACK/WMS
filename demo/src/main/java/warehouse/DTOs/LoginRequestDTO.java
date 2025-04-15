package warehouse.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import warehouse.Entities.ROLE;

@Data
@Getter
@Setter
public class LoginRequestDTO {


    private String email;
    private String password;

}
