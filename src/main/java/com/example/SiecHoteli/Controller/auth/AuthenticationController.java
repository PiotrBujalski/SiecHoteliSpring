package com.example.SiecHoteli.Controller.auth;

import com.example.SiecHoteli.Config.JwtService;
import com.example.SiecHoteli.Entity.Role;
import com.example.SiecHoteli.Entity.User;
import com.example.SiecHoteli.Repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(register(request));
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user1 = repository.findByLogin(request.getLogin());
        if(user1 != null){
            return AuthenticationResponse.builder().token("Użytkownik o takim loginie znajduje się juz w bazie").build();
        }

        var user = User.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticate(request));
    }

    public AuthenticationResponse authenticate(RegisterRequest request) {
        var user1 = repository.findByLogin(request.getLogin());
        if(user1 == null){
            return AuthenticationResponse.builder().token("Użytkownika o takim loginie nie ma w bazie").build();
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        var user = repository.findByLogin(request.getLogin());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
