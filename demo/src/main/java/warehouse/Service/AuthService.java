package warehouse.Service;


import org.springframework.http.ResponseEntity;
import warehouse.DTOs.LoginRequestDTO;
import warehouse.DTOs.LoginResponseDTO;
import warehouse.DTOs.SignupRequestDTO;
import warehouse.DTOs.SignupResponseDTO;
import warehouse.Entities.User;

public interface AuthService {

    ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO);

    User getUserById(Long userId);
}
