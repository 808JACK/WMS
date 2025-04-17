package warehouse.Service;


import org.springframework.http.ResponseEntity;
import warehouse.AuthDTOs.LoginRequestDTO;
import warehouse.AuthDTOs.LoginResponseDTO;
import warehouse.Entities.User;

public interface AuthService {

    ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO);

    User getUserById(Long userId);
}
