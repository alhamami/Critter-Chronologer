package com.udacity.jdnd.course3.critter.user.DTO;

import java.time.DayOfWeek;
import java.util.Set;

public class EmployeeSchedule {
    
    private Set<DayOfWeek> daysAvailable;
    private long employeeId;

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }
    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
    public long getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    
}
