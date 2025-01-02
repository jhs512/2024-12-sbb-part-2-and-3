package com.mysite.sbb.domain.user.dto;

import lombok.Builder;

@Builder
public record SiteUserDTO(String username,
                   String password,
                   String emial
) {
}
