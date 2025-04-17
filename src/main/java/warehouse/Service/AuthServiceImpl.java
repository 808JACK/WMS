package warehouse.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import warehouse.AuthDTOs.LoginRequestDTO;
import warehouse.AuthDTOs.LoginResponseDTO;
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
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getEmail(),
                            loginRequestDTO.getPassword()
                    )
            );
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtService.generateAccessToken(user);

            // Set HttpOnly cookie with the token
            ResponseCookie jwtCookie = ResponseCookie.from("token", accessToken)
                    .httpOnly(true)
                    .secure(false) // ✅ true in prod
                    .path("/")
                    .maxAge(24 * 60 * 60)
                    .sameSite("Lax")
                    .build();

            LoginResponseDTO responseDTO = new LoginResponseDTO(
                    user.getUserId(),
                    accessToken,
                    user.getRole(),
                    user.getEmail()
            );

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(responseDTO);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new LoginResponseDTO(null, null, null,"Invalid Credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public User getUserById(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }
}
