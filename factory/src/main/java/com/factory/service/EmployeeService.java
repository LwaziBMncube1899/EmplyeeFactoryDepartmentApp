
package com.factory.service;


import com.factory.model.Employee;
import com.factory.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


  @Autowired

    public EmployeeService(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }



    public Employee findEmployeeById(Long id) {

        return employeeRepository.findOne(id);
    }

    public Employee findByName(String name) {
        return employeeRepository.findByEmployeeName(name);
    }
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void deleteAllFactores() {
        employeeRepository.deleteAll();
    }
    public void deleteEmployeeById(Long id) {
        employeeRepository.delete(id);
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employee) {
        saveEmployee(employee);
    }

    public boolean isEmployeeExist(Employee employee){
        return findByName(employee.getEmployeeName()) !=null;
    }

}
