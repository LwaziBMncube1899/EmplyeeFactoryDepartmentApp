package com.factory.controller;

import com.factory.model.Employee;
import com.factory.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /*
    * ----------------------Create operation-----------------------------------------------
    * We first create our view to return our form
    * Then we create our employee and store it in our DB
    * */
    @GetMapping("create-employee")
    private ModelAndView createEmployeeView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("employeeForm");
        modelAndView.addObject("employee", new Employee());
        return modelAndView;
    }

    @PostMapping("create-employee")
    private ModelAndView createEmployee(Employee employee, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){

            modelAndView.setViewName("employeeForm");
            modelAndView.addObject("employee", new Employee());

        }

        if (employee.getId() == 0 ) {

            employeeService.saveEmployee(employee);
        }else{
            employeeService.updateEmployee(employee);
        }
        modelAndView.setViewName("employee");
        return modelAndView;

    }

    /*
    * ----------------------Read Operation----------------------------------------
    * Get/list all employees stored in out system
    * Get a employee by it's ID
    * */

    @GetMapping("employees")
    public String listAllFactories(Model model){
        model.addAttribute("employee ",employeeService.findAll());

        return "employee/employeesView";
    }

    @PostMapping("employee/{id}")
    private String getEmployee(@PathVariable Long id, Model model){

        model.addAttribute("employee", employeeService.findEmployeeById(id));
        return "employee/employeeView";
    }

    /*
    * Update operation
    * */

    @PutMapping("edit/{id}")
    private String edit(@PathVariable Long id, Model model){

        model.addAttribute("employee", employeeService.findEmployeeById(id));
        model.addAttribute("employees", employeeService.findAll());

        return "employee/employeesView";
    }

    /*
    * Delete Operation
    * */
    @PostMapping("delete/{id}")
    private String delete(@PathVariable Long id){

        employeeService.deleteEmployeeById(id);
        return id.toString();
    }
}
