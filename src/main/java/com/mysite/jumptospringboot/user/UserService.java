package com.mysite.jumptospringboot.user;

import com.mysite.jumptospringboot.DataNotFoundException;
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
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public SiteUser getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException(username));
//        Optional<SiteUser> _siteUser = userRepository.findByUsername(username);
//        if(_siteUser.isPresent()) {
//            return _siteUser.get();
//        } else {
//            throw new UsernameNotFoundException("User not found");
//        }
    }
}
