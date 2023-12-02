package com.ragnaroklogin.repositories;

import com.ragnaroklogin.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CharacterRepository extends JpaRepository<Character, UUID> {

}
