package com.ragnaroklogin.entities.DTO;

import com.ragnaroklogin.entities.enums.CharacterJob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CharacterResponseDTO(
        @NotBlank String name,
        @NotNull CharacterJob job,
        @NotNull String gender,
        @NotNull Short strength,
        @NotNull Short agility,
        @NotNull Short vitality,
        @NotNull Short intelligence,
        @NotNull Short dexterity,
        @NotNull Short luck,
        @NotNull String player_Id,
        @NotNull String player_login
) {
}
