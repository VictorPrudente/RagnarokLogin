package com.ragnaroklogin.controllers;

import com.ragnaroklogin.entities.Character;
import com.ragnaroklogin.entities.CharacterDTO;
import com.ragnaroklogin.repositories.CharacterRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CharacterController {

    @Autowired
    CharacterRepository characterRepository;

    @GetMapping("/characters")
    public ResponseEntity<List<Character>> findAllCharacters() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(characterRepository.findAll());
    }

    @GetMapping("/characters/{id}")
    public ResponseEntity<Object> findCharacterById(@PathVariable(value = "id") UUID id) {
        Optional<Character> character = characterRepository.findById(id);
        if (character.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("character not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(character.get());
    }

    @PostMapping("/characters")
    public ResponseEntity<Character> saveCharacter(@RequestBody @Valid CharacterDTO characterDTO) {
        var character = new Character();
        BeanUtils.copyProperties(characterDTO, character);

        return ResponseEntity.status(HttpStatus.CREATED).body(characterRepository.save(character));
    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity<Object> deleteCharacter(@PathVariable(value = "id") UUID id){
        Optional<Character> character = characterRepository.findById(id);
        if (character.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Character not found");
        }
        characterRepository.delete(character.get());
        return ResponseEntity.status(HttpStatus.OK).body("Character deleted successfully");
    }
}
