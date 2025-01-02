package com.mysite.sbb.domain.user.mapper;


import com.mysite.sbb.domain.user.dto.SiteUserDTO;
import com.mysite.sbb.domain.user.entity.SiteUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public SiteUserDTO toDTO(SiteUser user) {
        return SiteUserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .emial(user.getEmail())
                .build();
    }
}
