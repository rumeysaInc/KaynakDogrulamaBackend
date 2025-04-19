package org.dogrula.kaynakdogrulamabackend.controller;

import org.dogrula.kaynakdogrulamabackend.dto.AuthRequest;
import org.dogrula.kaynakdogrulamabackend.dto.AuthResponse;
import org.dogrula.kaynakdogrulamabackend.dto.RegisterRequest;
import org.dogrula.kaynakdogrulamabackend.entity.User;
import org.dogrula.kaynakdogrulamabackend.security.JwtUtil;
import org.dogrula.kaynakdogrulamabackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        boolean authenticated = userService.authenticate(request);

        if (!authenticated) {
            return ResponseEntity.status(401).body(Map.of("message", "Kullanıcı adı veya şifre hatalı."));
        }

        Optional<User> userOpt = userService.getByUsername(request.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = jwtUtil.generateTokenWithRole(user.getUsername(), user.getRole());

            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "Kullanıcı bulunamadı."));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        boolean registered = userService.register(request);

        if (!registered) {
            return ResponseEntity.badRequest().body(Map.of("message", "Bu kullanıcı adı zaten kullanılıyor."));
        }

        return ResponseEntity.ok(Map.of("message", "Kayıt başarılı."));
    }

    @GetMapping("/secret")
    public ResponseEntity<String> getSecret() {
        return ResponseEntity.ok("Bu gizli bilgi sadece giriş yapanlara gösterilir!");
    }
}
