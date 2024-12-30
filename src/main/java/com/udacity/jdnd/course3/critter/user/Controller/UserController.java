package com.udacity.jdnd.course3.critter.user.Controller;

import com.udacity.jdnd.course3.critter.pet.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.pet.Service.PetService;
import com.udacity.jdnd.course3.critter.user.DTO.*;
import com.udacity.jdnd.course3.critter.user.Service.CustomerService;
import com.udacity.jdnd.course3.critter.user.Service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final CustomerService customerService;
    private final PetService petService;
    private final EmployeeService employeeService;

    public UserController(CustomerService customerService, PetService petService, EmployeeService employeeService) {
        this.petService = petService ;
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.createNewCustomer(customerDTO);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){

        return customerService.findAllCustomers();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){

        PetDTO petDTO = petService.findPetById(petId);

        if(petDTO != null){
            if(petDTO.getOwnerId() != 0){
                return customerService.findCustoemrById(petDTO.getOwnerId());
            }else{
                throw new UnsupportedOperationException("There is no owner for this pet");
            }
        }else{
            throw new UnsupportedOperationException("Pet not exist");
        }
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createNewEmployee(employeeDTO);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        EmployeeDTO employeeDTO = employeeService.findEmployeeById(employeeId);

        if(employeeDTO != null){
            return employeeDTO;
        }else{
            throw new UnsupportedOperationException("Employee not exist");
        }
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        EmployeeSchedule employeeSchedule = new EmployeeSchedule();
        employeeSchedule.setDaysAvailable(daysAvailable);
        employeeSchedule.setEmployeeId(employeeId);
        employeeService.updateAvailabilityByEmployeeId(employeeSchedule);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {

        TaskSpecification taskSpec = new TaskSpecification();
        taskSpec.setEmployeeSkills(employeeDTO.getSkills());
        taskSpec.setDayOfWeek(employeeDTO.getDate().getDayOfWeek());
        return employeeService.findAvailableEmployeesBySkillsAndDay(taskSpec);
    }

}
