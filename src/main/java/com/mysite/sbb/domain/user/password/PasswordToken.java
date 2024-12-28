package com.mysite.sbb.domain.user.password;

import com.mysite.sbb.domain.user.SiteUser;
import com.mysite.sbb.global.superclass.OnlyId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class PasswordToken extends OnlyId {
    @Column(unique = true)
    private String token;

    @ManyToOne
    private SiteUser user;

    @CreatedDate
    private LocalDateTime createDate;

    public boolean isExpired() {
        Duration duration = Duration.between(this.createDate, LocalDateTime.now());
        return duration.toHours() >= 1;
    }
}
