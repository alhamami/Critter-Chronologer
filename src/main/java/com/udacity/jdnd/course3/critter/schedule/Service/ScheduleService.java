package com.udacity.jdnd.course3.critter.schedule.Service;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jdnd.course3.critter.user.Service.EmployeeService;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.pet.Entity.Pet;
import com.udacity.jdnd.course3.critter.pet.Service.PetService;
import com.udacity.jdnd.course3.critter.schedule.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.Entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.Repo.ScheduleRepo;
import com.udacity.jdnd.course3.critter.user.Entity.Employee;
import com.udacity.jdnd.course3.critter.user.Service.CustomerService;
@Service
public class ScheduleService {

    private final ScheduleRepo scheduleRepo;
    private final PetService petService;
    private final EmployeeService employeeService;

    public ScheduleService(ScheduleRepo scheduleRepo, PetService petService, EmployeeService employeeService) {
        this.scheduleRepo = scheduleRepo;
        this.petService = petService;
        this.employeeService = employeeService;
    }



    public ScheduleDTO createNewSchedule(ScheduleDTO newScheduleDTO) {


        ScheduleDTO scheduleDTO = new ScheduleDTO();

        Schedule schedule = new Schedule();

        List<Employee> employees = new ArrayList<>();
        for(Long employeeId : newScheduleDTO.getEmployeeIds()) {
            employees.add(employeeService.findEmployeeByIdAsIs(employeeId));
        }

        schedule.setEmployees(employees);

        List<Pet> pets = new ArrayList<>();
        for(Long petId : newScheduleDTO.getPetIds()) {
            pets.add(petService.findPetByIdAsIs(petId));
        }

        schedule.setPets(pets);

        schedule.setDate(newScheduleDTO.getDate());
        schedule.setActivities(newScheduleDTO.getActivities());

        Schedule savedSchedule = scheduleRepo.save(schedule);


        if(savedSchedule != null){
            scheduleDTO.setId(savedSchedule.getId());

            List<Long> employeeIds = new ArrayList<>();
            for(Employee employee : savedSchedule.getEmployees()) {
                employeeIds.add(employee.getId());
            }

            scheduleDTO.setEmployeeIds(employeeIds);

            List<Long> petIds = new ArrayList<>();
            for(Pet petId : savedSchedule.getPets()) {
                petIds.add(petId.getId());
            }

            scheduleDTO.setPetIds(petIds);
            scheduleDTO.setDate(savedSchedule.getDate());
            scheduleDTO.setActivities(savedSchedule.getActivities());

        }

        return scheduleDTO;
    }

    public List<ScheduleDTO> findAllSchedules() {


        List<Schedule> schedules = scheduleRepo.findAll();

        List<ScheduleDTO> ScheduleDTOs = new ArrayList<>();

        for(Schedule schedule : schedules){

            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(schedule.getId());
            scheduleDTO.setActivities(schedule.getActivities());
            scheduleDTO.setDate(schedule.getDate());

            List<Long> employeeIds = new ArrayList<>();
            for(Employee employee : schedule.getEmployees()){
                employeeIds.add(employee.getId());
            }
            scheduleDTO.setEmployeeIds(employeeIds);

            List<Long> petIds = new ArrayList<>();
            for(Pet pet : schedule.getPets()){
                petIds.add(pet.getId());
            }
            scheduleDTO.setPetIds(petIds);
            ScheduleDTOs.add(scheduleDTO);
        }

        return ScheduleDTOs;
    }

    public List<ScheduleDTO> findScheduleForPet(Long id) {

        List<Schedule>  schedules = scheduleRepo.findAllByPetsId(id);
        List<ScheduleDTO> ScheduleDTOs = new ArrayList<>();

        for(Schedule schedule : schedules){

            ScheduleDTO scheduleDTO = new ScheduleDTO();

            scheduleDTO.setId(schedule.getId());
            scheduleDTO.setActivities(schedule.getActivities());
            scheduleDTO.setDate(schedule.getDate());

            List<Long> employeeIds = new ArrayList<>();
            for(Employee employee : schedule.getEmployees()){
                employeeIds.add(employee.getId());
            }
            scheduleDTO.setEmployeeIds(employeeIds);


            List<Long> petIds = new ArrayList<>();
            for(Pet pet : schedule.getPets()){
                petIds.add(pet.getId());
            }
            scheduleDTO.setPetIds(petIds);
            ScheduleDTOs.add(scheduleDTO);

        }
        return ScheduleDTOs;
    }


    public List<ScheduleDTO> findScheduleForEmployee(Long id) {

        List<Schedule> schedules = scheduleRepo.findAllByEmployeesId(id);

        List<ScheduleDTO> ScheduleDTOs = new ArrayList<>();

        for(Schedule schedule : schedules){

            ScheduleDTO scheduleDTO = new ScheduleDTO();

            scheduleDTO.setId(schedule.getId());
            scheduleDTO.setActivities(schedule.getActivities());
            scheduleDTO.setDate(schedule.getDate());

            List<Long> employeeIds = new ArrayList<>();
            for(Employee employee : schedule.getEmployees()){
                employeeIds.add(employee.getId());
            }
            scheduleDTO.setEmployeeIds(employeeIds);


            List<Long> petIds = new ArrayList<>();
            for(Pet pet : schedule.getPets()){
                petIds.add(pet.getId());
            }
            scheduleDTO.setPetIds(petIds);
            ScheduleDTOs.add(scheduleDTO);

        }
        return ScheduleDTOs;
    }



}