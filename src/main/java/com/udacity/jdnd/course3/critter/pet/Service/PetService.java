package com.udacity.jdnd.course3.critter.pet.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.udacity.jdnd.course3.critter.user.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.Entity.Customer;
import com.udacity.jdnd.course3.critter.user.Service.CustomerService;
import org.springframework.stereotype.Service;
import com.udacity.jdnd.course3.critter.pet.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.pet.Entity.Pet;
import com.udacity.jdnd.course3.critter.pet.Repo.PetRepo;

@Service
public class PetService {

    private final PetRepo petRepo;
    private final CustomerService customerService;

    public PetService(PetRepo petRepo,  CustomerService customerService) {
        this.petRepo = petRepo;
        this.customerService = customerService;
    }



    public PetDTO createNewPet(PetDTO newPetDTO) {


        PetDTO petDTO = new PetDTO();

        Pet pet = new Pet();

        pet.setName(newPetDTO.getName());
        pet.setType(newPetDTO.getType());

        Customer customer = customerService.findCustoemrByIdAsIs(newPetDTO.getOwnerId());

        for(Pet petItem : customer.getPets()){
            if(petItem.getName().equals(petDTO.getName())){
                return petDTO;
            }
        }

        pet.setCustomer(customer);

        pet.setBirthDate(newPetDTO.getBirthDate());
        pet.setNotes(newPetDTO.getNotes());
        Pet savedPet = petRepo.save(pet);


        Customer ownerCustomer = savedPet.getCustomer();

        List<Pet> pets = customer.getPets();
        pets.add(savedPet);
        ownerCustomer.setPets(pets);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ownerCustomer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(ownerCustomer.getPhoneNumber());
        customerDTO.setNotes(ownerCustomer.getNotes());

        List<Long> petIds = new ArrayList<>();
        for(Pet petItem : ownerCustomer.getPets()){
            petIds.add(petItem.getId());
        }
        customerDTO.setPetIds(petIds);
        customerService.createNewCustomer(customerDTO);

        if(savedPet != null) {
            petDTO.setId(savedPet.getId());
            petDTO.setName(savedPet.getName());
            petDTO.setType(savedPet.getType());
            petDTO.setBirthDate(savedPet.getBirthDate());
            petDTO.setNotes(savedPet.getNotes());
            petDTO.setOwnerId(customer.getId());
        }

        return petDTO;
    }



    public PetDTO findPetById(Long id) {
        
        Optional<Pet> pet = petRepo.findById(id);

        PetDTO petDTO = new PetDTO();

        if(pet.isPresent()){

            petDTO.setId(pet.get().getId());
            petDTO.setName(pet.get().getName());
            petDTO.setType(pet.get().getType());
            petDTO.setBirthDate(pet.get().getBirthDate());
            petDTO.setNotes(pet.get().getNotes());
            petDTO.setOwnerId(pet.get().getCustomer().getId());
        }

        return petDTO;
    }


    public Pet findPetByIdAsIs(Long id) {

        Optional<Pet> pet = petRepo.findById(id);

        if(pet.isPresent()){
            return pet.get();
        }

        return new Pet();
    }


    public List<PetDTO> findAllPets() {

        List<Pet> pets = petRepo.findAll();

        List<PetDTO> petDTOs = new ArrayList<>();

        for(Pet pet : pets){

            PetDTO petDTO = new PetDTO();
            
            petDTO.setId(pet.getId());
            petDTO.setName(pet.getName());
            petDTO.setType(pet.getType());
            petDTO.setBirthDate(pet.getBirthDate());
            petDTO.setNotes(pet.getNotes());
            petDTO.setOwnerId(pet.getCustomer().getId());
            petDTOs.add(petDTO);
        }
        
        return petDTOs;
    }

    
}
