package warehouse.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import warehouse.DTOs.LoginRequestDTO;
import warehouse.DTOs.LoginResponseDTO;
import warehouse.DTOs.SignupRequestDTO;
import warehouse.DTOs.SignupResponseDTO;
import warehouse.Entities.User;
import warehouse.Repos.UserRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO) {
        try {
            log.info("Attempting login for email: {}", loginRequestDTO.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
            );
            User user = (User) authentication.getPrincipal();
            log.info("Auth user: {}", user.getUsername());
            String accessToken = jwtService.generateAccessToken(user);
            log.info("Generated token: {}", accessToken);
            return ResponseEntity.ok(new LoginResponseDTO(user.getUserId(), accessToken, user.getRole()));
        } catch (AuthenticationException e) {
            log.error("Authentication failed for email {}: {}", loginRequestDTO.getEmail(), e.getMessage());
            return ResponseEntity.status(401).body(new LoginResponseDTO(null, null, null));
        } catch (Exception e) {
            log.error("Error during login for email {}: {}", loginRequestDTO.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public User getUserById(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }
}
