package warehouse.AuthDTOs;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import warehouse.Entities.ROLE;

@Data
@Getter
@Setter

public class SignupResponseDTO {

    @Id
    private Long userId;
    private String username;
    private ROLE role;
}
