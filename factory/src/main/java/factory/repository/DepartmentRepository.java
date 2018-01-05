package factory.repository;

import factory.model.Department;
/*import Employee;*/
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
    /*Department findDepartmentByDepartmentName(String name);

    @Query( "select d.departmentName from Department d where d.factory = :id" )
    Department findByFactory(@Param("id") Long factoryDepartmentId);
*/
    @Query( "select d from Department d WHERE d.factory = :id" )
    List<Department>  findDepartmentsByFactory(@Param("id") Long id);

}
