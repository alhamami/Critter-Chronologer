package com.udacity.jdnd.course3.critter.schedule.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.schedule.Entity.Schedule;

import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByEmployeesId(Long employeeId);
    List<Schedule> findAllByPetsId(Long petsId);
}
