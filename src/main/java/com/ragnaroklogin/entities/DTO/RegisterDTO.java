package com.ragnaroklogin.entities.DTO;

import com.ragnaroklogin.entities.enums.UserRole;

public record RegisterDTO(
        String login,
        String password,
        UserRole role
) { }
