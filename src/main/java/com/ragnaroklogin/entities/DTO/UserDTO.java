package com.ragnaroklogin.entities.DTO;

import java.util.List;

public record UserDTO(
        String id,
        String login,
        List<CharacterDTO> characters
) {
}
