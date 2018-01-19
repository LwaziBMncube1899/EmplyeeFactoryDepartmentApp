
package factory.repository;

import factory.model.Department;
import factory.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    Employee findByEmployeeName(String name);

    @Query("select e from Employee e Where e.department = :id")
    List<Employee> findEmployeesByDepartmentId(@Param("id") Department departmentId);
}

