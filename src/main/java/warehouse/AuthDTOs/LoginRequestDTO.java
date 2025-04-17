package warehouse.AuthDTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginRequestDTO {


    private String email;
    private String password;

}
