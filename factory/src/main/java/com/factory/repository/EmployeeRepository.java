package com.factory.repository;

import com.factory.model.Employee;
import com.factory.model.Factory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    Employee findByEmployeeName(String name);
}
