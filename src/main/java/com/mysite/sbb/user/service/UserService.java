package com.mysite.sbb.user.service;

import com.mysite.sbb.global.exceptions.DataNotFoundException;
import com.mysite.sbb.user.entity.SiteUser;
import com.mysite.sbb.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser createUser(String username, String password, String email) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(username);
        siteUser.setEmail(email);
        siteUser.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(siteUser);
        return siteUser;
    }

    public SiteUser getUser(String name) {
        Optional<SiteUser> userOptional = this.userRepository.findByUsername(name);
        if(userOptional.isPresent()){
            return userOptional.get();
        } else {
            throw new DataNotFoundException("siteUser not found");
        }
    }
}
