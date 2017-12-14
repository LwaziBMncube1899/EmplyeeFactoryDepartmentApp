package com.factory.repository;

import com.factory.model.Department;
/*import com.factory.model.Employee;*/
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
    Department findDepartmentByDepartmentName(String name);
}
