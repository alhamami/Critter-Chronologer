package com.udacity.jdnd.course3.critter.pet.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udacity.jdnd.course3.critter.pet.Entity.Pet;

public interface PetRepo extends JpaRepository<Pet, Long> {
    
}
