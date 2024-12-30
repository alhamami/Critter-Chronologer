package com.udacity.jdnd.course3.critter.user.DTO;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import java.time.DayOfWeek;
import java.util.Set;

public class TaskSpecification {

    private DayOfWeek dayOfWeek;
    private  Set<EmployeeSkill> employeeSkills;



    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public Set<EmployeeSkill> getEmployeeSkills() {
        return employeeSkills;
    }
    public void setEmployeeSkills(Set<EmployeeSkill> employeeSkills) {
        this.employeeSkills = employeeSkills;
    }




}
