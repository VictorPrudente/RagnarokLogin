package com.ragnaroklogin.controllers;

import com.ragnaroklogin.entities.User;
import com.ragnaroklogin.entities.Character;
import com.ragnaroklogin.entities.DTO.CharacterDTO;
import com.ragnaroklogin.entities.DTO.CharacterResponseDTO;
import com.ragnaroklogin.repositories.CharacterRepository;
import com.ragnaroklogin.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Characters")
public class CharacterController {

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    UserRepository userRepository;

    @Operation(
            description = "'Get' endpoint for characters",
            summary = "Retrieve all characters in a list.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Tokens",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/characters")
    public ResponseEntity<List<Character>> findAllCharacters() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(characterRepository.findAll());
    }

    @Operation(
            summary = "Retrieve a characters by its ID.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Tokens",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/characters/{id}")
    public ResponseEntity<Object> findCharacterById(@PathVariable(value = "id") String id) {
        Optional<Character> character = characterRepository.findById(id);
        if (character.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("character not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(character.get());
    }

    @Operation(
            description = "'Post' endpoint for characters",
            summary = "Save a character in the Database.",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Tokens",
                            responseCode = "403"
                    ),
            }
    )
    @PostMapping("/characters")
    public ResponseEntity<CharacterResponseDTO> saveCharacter(@RequestBody @Valid CharacterDTO characterDTO) {

        String playerId = characterDTO.player().id();
        Optional<User> player = userRepository.findById(playerId);

        var character = new Character();
        BeanUtils.copyProperties(characterDTO, character);

        if (player.isPresent()) {
            character.setPlayer(player.get());
            Character newCharacter = characterRepository.save(character);
            CharacterResponseDTO responseDTO = new CharacterResponseDTO(
                    newCharacter.getName(),
                    newCharacter.getJob(),
                    newCharacter.getGender(),
                    newCharacter.getStrength(),
                    newCharacter.getAgility(),
                    newCharacter.getVitality(),
                    newCharacter.getIntelligence(),
                    newCharacter.getDexterity(),
                    newCharacter.getLuck(),
                    player.get().getId(),
                    player.get().getLogin()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @Operation(
            description = "'Delete' endpoint for characters",
            summary = "Delete a character by its ID.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Tokens",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    )
            }
    )
    @DeleteMapping("/characters/{id}")
    public ResponseEntity<Object> deleteCharacter(@PathVariable(value = "id") String id) {
        Optional<Character> character = characterRepository.findById(id);
        if (character.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Character not found");
        }
        characterRepository.delete(character.get());
        return ResponseEntity.status(HttpStatus.OK).body("Character deleted successfully");
    }
}
