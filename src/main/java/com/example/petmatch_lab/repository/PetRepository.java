
package com.example.petmatch_lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.petmatch_lab.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {

}

