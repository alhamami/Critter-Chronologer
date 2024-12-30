package com.udacity.jdnd.course3.critter.schedule.Controller;

import com.udacity.jdnd.course3.critter.pet.Service.PetService;
import com.udacity.jdnd.course3.critter.schedule.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.Service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.Service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CustomerService customerService;
    private final PetService petService;

    public ScheduleController(ScheduleService scheduleService, CustomerService customerService, PetService petService) {
        this.scheduleService = scheduleService;
        this.customerService = customerService;
        this.petService = petService;
    }
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return scheduleService.createNewSchedule(scheduleDTO);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        return scheduleService.findAllSchedules();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        List<ScheduleDTO> scheduleDTOs = scheduleService.findScheduleForPet(petId);

        if(scheduleDTOs.size() > 0){
            return scheduleDTOs;
        }else{
            throw new UnsupportedOperationException("Schedule not exist");
        }
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {

        List<ScheduleDTO> scheduleDTOs = scheduleService.findScheduleForEmployee(employeeId);

        if(scheduleDTOs.size() > 0){
            return scheduleDTOs;
        }else{
            throw new UnsupportedOperationException("Schedule not exist");
        }

    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        CustomerDTO customerDTO = customerService.findCustoemrById(customerId);

        for(Long petId :  customerDTO.getPetIds()){
            scheduleDTOs.addAll(scheduleService.findScheduleForPet(petId));
        }

        if(scheduleDTOs.size() > 0){

            return scheduleDTOs;
        }else{
            throw new UnsupportedOperationException("Schedule not exist");
        }
    }
}
