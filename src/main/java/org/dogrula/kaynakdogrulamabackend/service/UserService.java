package org.dogrula.kaynakdogrulamabackend.service;

import org.dogrula.kaynakdogrulamabackend.entity.User;
import org.dogrula.kaynakdogrulamabackend.dto.AuthRequest;
import org.dogrula.kaynakdogrulamabackend.dto.RegisterRequest;
import org.dogrula.kaynakdogrulamabackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return false; // kullanıcı zaten var
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // HASH!
        user.setRole("USER"); // varsayılan rol

        userRepository.save(user);
        return true;
    }

    public boolean authenticate(AuthRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        return userOpt
                .map(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .orElse(false);
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
