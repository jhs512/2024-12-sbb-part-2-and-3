package com.ll.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(username);
        siteUser.setEmail(email);
        siteUser.setPassword(passwordEncoder.encode(password));

        return userRepository.save(siteUser);
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> op = userRepository.findByUsername(username);
        op.orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return op.get();
    }
}
