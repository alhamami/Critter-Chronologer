package com.udacity.jdnd.course3.critter.user.Service;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jdnd.course3.critter.pet.Repo.PetRepo;
import org.springframework.stereotype.Service;
import com.udacity.jdnd.course3.critter.user.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.Entity.Customer;
import com.udacity.jdnd.course3.critter.user.Repo.CustomerRepo;
import java.util.Optional;
import com.udacity.jdnd.course3.critter.pet.Entity.Pet;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final PetRepo petRepo;

    public CustomerService(CustomerRepo customerRepo, PetRepo petRepo) {

        this.customerRepo = customerRepo;
        this.petRepo = petRepo;
    }


    public CustomerDTO createNewCustomer(CustomerDTO newCustomerDTO) {

        CustomerDTO customerDTO = new CustomerDTO();

        Customer customer = new Customer();

        customer.setName(newCustomerDTO.getName());
        customer.setPhoneNumber(newCustomerDTO.getPhoneNumber());
        customer.setNotes(newCustomerDTO.getNotes());

        List<Pet> pets = new ArrayList<>();
        if(newCustomerDTO.getPetIds() != null) {
            for(Long petId : newCustomerDTO.getPetIds()){
                Optional<Pet> pet = petRepo.findById(petId);
                if(pet.isPresent()){
                    pets.add(pet.get());
                }
            }
        }


        customer.setPets(pets);

        Customer savedCustomer = customerRepo.save(customer);

        if(savedCustomer != null){

            customerDTO.setId(savedCustomer.getId());
            customerDTO.setName(savedCustomer.getName());
            customerDTO.setPhoneNumber(savedCustomer.getPhoneNumber());
            customerDTO.setNotes(savedCustomer.getNotes());
            List<Long> petIds = new ArrayList<>();
            for(Pet pet : savedCustomer.getPets()){
                petIds.add(pet.getId());

            }
            customerDTO.setPetIds(petIds);
        }


        
        return customerDTO;
    }

    public CustomerDTO findCustoemrById(Long id) {

        Optional<Customer> customer = customerRepo.findById(id);

        CustomerDTO customerDTO = new CustomerDTO();

        if(customer.isPresent()){

            customerDTO.setId(customer.get().getId());
            customerDTO.setName(customer.get().getName());
            customerDTO.setPhoneNumber(customer.get().getPhoneNumber());
            customerDTO.setNotes(customer.get().getNotes());
            List<Long> petIds = new ArrayList<>();
            for(Pet pet : customer.get().getPets()){
                petIds.add(pet.getId());

            }
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    public Customer findCustoemrByIdAsIs(Long id) {

        Optional<Customer> customer = customerRepo.findById(id);

        if(customer.isPresent()){

            return customer.get();
        }
        return new Customer();
    }

    public List<CustomerDTO> findAllCustomers() {


        List<Customer> customers = customerRepo.findAll();

        List<CustomerDTO> customerDTOs = new ArrayList<>();

        for(Customer customer : customers){

            CustomerDTO customerDTO = new CustomerDTO();
            
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setPhoneNumber(customer.getPhoneNumber());
            customerDTO.setNotes(customer.getNotes());
            List<Long> petIds = new ArrayList<>();
            for(Pet pet : customer.getPets()){
                petIds.add(pet.getId());

            }
            customerDTO.setPetIds(petIds);
            customerDTOs.add(customerDTO);
        }
        
        return customerDTOs;
    }


    
    
}
