package com.mysite.sbb.domain.user;

import com.mysite.sbb.domain.user.password.PasswordToken;
import com.mysite.sbb.domain.user.password.PasswordTokenRepository;
import com.mysite.sbb.global.exception.DataNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    private final PasswordTokenRepository passwordTokenRepository;

    @Transactional
    public SiteUser create(String username, String email, String password){
        SiteUser siteUser = SiteUser.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        userRepository.save(siteUser);
        return siteUser;
    }

    @Transactional
    public SiteUser getUser(String username){
        Optional<SiteUser> _siteUser = userRepository.findByUsername(username);
        if(_siteUser.isPresent()){
            return _siteUser.get();
        }else {
            throw new DataNotFoundException("찾는 유저가 없습니다.");
        }
    }

    @Transactional
    public Optional<SiteUser> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void sendPasswordFindEmail(SiteUser siteUser) throws MessagingException {
        String token = UUID.randomUUID().toString();
        passwordTokenRepository.save(
                PasswordToken.builder().user(siteUser).token(token).build()
        );
        Context context = new Context();
        context.setVariable("name", siteUser.getUsername());
        context.setVariable("verificationUrl", "http://localhost:8080/user/%d/password/%s"
                .formatted(siteUser.getId(), token));
        String htmlContent = templateEngine.process("email_form",context);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("springbootemail4@gmail.com");
        helper.setTo(siteUser.getEmail());
        helper.setSubject("Sbb 비밀번호 찾기 메일입니다.");
        helper.setText(htmlContent,true);
        emailSender.send(message);

    }

    @Transactional
    public long count() {
        return userRepository.count();
    }

    @Transactional
    public Optional<SiteUser> findByUserId(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void resetPassword(SiteUser user, String password){
        user.updatePassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Transactional
    public void matchToken(SiteUser user, String token) {
        PasswordToken passwordToken = passwordTokenRepository.findByToken(token).orElseThrow(
                ()->new RuntimeException("비정상적인 접근입니다-1")
        );
        if(!passwordToken.getUser().getId().equals(user.getId())){
            throw new RuntimeException("비정상적인 접근입니다-2");
        }
        if(passwordToken.isExpired()){
            throw new RuntimeException("토큰이 만료되었습니다");
        }
    }
}
