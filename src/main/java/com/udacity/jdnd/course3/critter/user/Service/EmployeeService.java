package com.udacity.jdnd.course3.critter.user.Service;

import org.springframework.stereotype.Service;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.DTO.EmployeeSchedule;
import com.udacity.jdnd.course3.critter.user.DTO.TaskSpecification;
import com.udacity.jdnd.course3.critter.user.Entity.Employee;
import com.udacity.jdnd.course3.critter.user.Repo.EmployeeRepo;

import jakarta.transaction.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }


    public EmployeeDTO createNewEmployee(EmployeeDTO newEmployee) {


        EmployeeDTO employeeDTO = new EmployeeDTO();

        Employee employee = new Employee();
        employee.setName(newEmployee.getName());
        employee.setSkills(newEmployee.getSkills());
        employee.setDaysAvailable(newEmployee.getDaysAvailable());

        Employee savedEmployee = employeeRepo.save(employee);

        employeeDTO.setId(savedEmployee.getId());
        employeeDTO.setName(savedEmployee.getName());
        employeeDTO.setSkills(savedEmployee.getSkills());
        employeeDTO.setDaysAvailable(savedEmployee.getDaysAvailable());


        return employeeDTO;
    }


    public EmployeeDTO findEmployeeById(Long id) {

        Optional<Employee> employee = employeeRepo.findById(id);

        EmployeeDTO employeeDTO = new EmployeeDTO();

        if(employee.isPresent()){

            employeeDTO.setId(employee.get().getId());
            employeeDTO.setName(employee.get().getName());
            employeeDTO.setSkills(employee.get().getSkills());
            employeeDTO.setDaysAvailable(employee.get().getDaysAvailable());
        }
        return employeeDTO;
    }

    public Employee findEmployeeByIdAsIs(Long id) {

        Optional<Employee> employee = employeeRepo.findById(id);

        if(employee.isPresent()){
            return employee.get();
        }
        return new Employee();
    }



    public List<EmployeeDTO> findAvailableEmployeesBySkillsAndDay(TaskSpecification taskSpecification) {

        List<EmployeeDTO> employeeDTOs = new ArrayList<>();

        if(taskSpecification != null){

            DayOfWeek day = taskSpecification.getDayOfWeek();

            Set<EmployeeSkill> skills = taskSpecification.getEmployeeSkills();

            if(day != null && skills != null){
                
                List<Employee> employees = employeeRepo.findAll();

                if(employees.size() > 0){

                    for(Employee employee : employees){

                        if(employee.getDaysAvailable().contains(day)){

                            if(employee.getSkills().containsAll(skills)){

                                EmployeeDTO employeeDTO = new EmployeeDTO();

                                employeeDTO.setId(employee.getId());
                                employeeDTO.setName(employee.getName());
                                employeeDTO.setSkills(employee.getSkills());
                                employeeDTO.setDaysAvailable(employee.getDaysAvailable());
                                employeeDTOs.add(employeeDTO);
                            }
                        }
                    }

                }
            }
        }
        return employeeDTOs;
    }
    


    @Transactional
    public void updateAvailabilityByEmployeeId(EmployeeSchedule employeeSchedule) {

        if(employeeSchedule != null){

            Long empId = employeeSchedule.getEmployeeId();
            Set<DayOfWeek> days = employeeSchedule.getDaysAvailable();

            if(empId != null && days != null){

                Optional<Employee> employee = employeeRepo.findById(empId);

                if(employee.isPresent()){

                    employee.get().setDaysAvailable(days);

                    employeeRepo.save(employee.get());

                }else{

                    throw new IllegalArgumentException("Employee not found");
                }

            }
        }

    }
}
