package warehouse.Service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import warehouse.DTOs.SignupRequestDTO;
import warehouse.DTOs.SignupResponseDTO;
import warehouse.Entities.User;
import warehouse.Exception.ResourceNotFoundException;
import warehouse.Repos.UserRepo;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<SignupResponseDTO> signup(SignupRequestDTO signupRequestDTO) {
        User user = modelMapper.map(signupRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password
        userRepo.save(user);

        SignupResponseDTO responseDTO = new SignupResponseDTO();
        responseDTO.setUserId(user.getUserId());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setRole(user.getRole());

        return ResponseEntity.ok(responseDTO);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found"));
        return user;
    }

    public User getUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }
}