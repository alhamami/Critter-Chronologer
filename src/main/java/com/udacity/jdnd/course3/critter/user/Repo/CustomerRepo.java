package com.udacity.jdnd.course3.critter.user.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.udacity.jdnd.course3.critter.user.Entity.Customer;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    
}