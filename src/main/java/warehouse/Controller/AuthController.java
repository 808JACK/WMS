package warehouse.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.AuthDTOs.LoginRequestDTO;
import warehouse.AuthDTOs.LoginResponseDTO;
import warehouse.AuthDTOs.SignupRequestDTO;
import warehouse.AuthDTOs.SignupResponseDTO;
import warehouse.Service.AuthService;
import warehouse.Service.UserService;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    @PostMapping("/signup")

    public ResponseEntity<SignupResponseDTO> signup(@RequestBody SignupRequestDTO signupRequestDTO){
        return userService.signup(signupRequestDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        log.info("Login endpoint hit with email: {}", loginRequestDTO.getEmail());
        return authService.login(loginRequestDTO);
    }
}
