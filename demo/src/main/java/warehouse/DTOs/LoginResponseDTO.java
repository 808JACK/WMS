package warehouse.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import warehouse.Entities.ROLE;

@Data
@Getter
@Setter
public class LoginResponseDTO {
    
    private Long userId;
    private String accessToken;
    private ROLE role;

    public LoginResponseDTO(Long userId, String accessToken, ROLE role) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.role = role;
    }

}
