package com.hyperface.ems.repository;

import com.hyperface.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    public List<Employee> findByFirstName(String name);
    public Employee findByEmail(String email);
}
