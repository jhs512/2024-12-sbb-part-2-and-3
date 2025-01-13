package com.ll.__01_13_sbb.User;

import com.ll.__01_13_sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String name) {
        Optional<SiteUser> user = this.userRepository.findByusername(name);
        if(user.isPresent()){
            return user.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
}
