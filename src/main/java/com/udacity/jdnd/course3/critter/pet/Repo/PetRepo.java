package com.udacity.jdnd.course3.critter.pet.Repo;

import com.udacity.jdnd.course3.critter.pet.Entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepo extends JpaRepository<Pet, Long> {
    
}
