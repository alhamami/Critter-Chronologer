package com.udacity.jdnd.course3.critter.pet.Controller;

import com.udacity.jdnd.course3.critter.pet.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.pet.Service.PetService;
import com.udacity.jdnd.course3.critter.user.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.Service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return petService.createNewPet(petDTO);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetDTO petDTO = petService.findPetById(petId);

        if (petDTO == null) {
            return petDTO;
        }else{
            throw new UnsupportedOperationException("Pet not exist");
        }
    }

    @GetMapping
    public List<PetDTO> getPets(){

        return petService.findAllPets();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        CustomerDTO customerDTO = customerService.findCustoemrById(ownerId);
        List<PetDTO> petDTOs = new ArrayList<>();

        for (Long petId : customerDTO.getPetIds()) {
            petDTOs.add(petService.findPetById(petId));
        }
        return petDTOs;
    }
}
