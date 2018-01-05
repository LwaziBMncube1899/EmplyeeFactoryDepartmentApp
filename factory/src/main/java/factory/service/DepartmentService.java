package factory.service;


import factory.model.Department;
import factory.model.Factory;
import factory.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {

        this.departmentRepository = departmentRepository;
    }

    public Department findDepartmentById(Long id) {

        return departmentRepository.findOne(id);
    }

    public List<Department> findByFactoryId(Long id) {

      return   departmentRepository.findDepartmentsByFactory(id);
    }
/*
@Cacheable
    public Department findByName(String name) {
        return departmentRepository.findDepartmentByDepartmentName(name);
    }
*/

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public void deleteAllFactores() {
        departmentRepository.deleteAll();
    }

    public void deleteDepartmentById(Long id) {
        departmentRepository.delete(id);
    }

    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    public void updateDepartment(Department department) {
        saveDepartment(department);
    }

  /*  public boolean isDepartmentExist(Department department){
        return findByName(department.getDepartmentName()) !=null;
    }*/
    @Scheduled(cron = "0 0/1 * * * ?")
public void sayTime(){
    System.out.println(new Date());
}
}
