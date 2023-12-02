package com.ragnaroklogin.entities;

import com.ragnaroklogin.entities.enums.CharacterJob;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CharacterDTO(
        @NotBlank String name,

        @NotNull CharacterJob job,

        @NotNull String gender,

        @NotNull Short strength,

        @NotNull Short agility,

        @NotNull Short vitality,

        @NotNull Short intelligence,

        @NotNull Short dexterity,

        @NotNull Short luck
) {
}
