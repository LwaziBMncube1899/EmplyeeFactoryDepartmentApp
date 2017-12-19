package factory.repository;

import factory.model.Department;
/*import Employee;*/
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
    Department findDepartmentByDepartmentName(String name);
}
