package project.application.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.application.config.TokenConfig;
import project.application.dto.user.LoginRequest;
import project.application.dto.user.LoginResponse;
import project.application.dto.user.RegisterUserRequest;
import project.application.dto.user.UserResponse;
import project.application.entities.Users;
import project.application.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenConfig tokenConfig;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(
                request.name(),
                request.password()
        );
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        Users users = (Users) authentication.getPrincipal();
        String token = tokenConfig.generateToken(users);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid  RegisterUserRequest request){
        Users users = new Users();
        users.setName(request.name());
        users.setPassword(passwordEncoder.encode(request.password()));

        repository.save(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(users.getName()));
    }
}
