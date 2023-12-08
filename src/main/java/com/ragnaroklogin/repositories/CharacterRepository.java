package com.ragnaroklogin.repositories;

import com.ragnaroklogin.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, String> {

}
