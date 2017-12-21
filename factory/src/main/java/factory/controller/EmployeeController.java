
package factory.controller;

import factory.service.DepartmentService;
import factory.service.EmployeeService;
import factory.model.Employee;
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
    private final DepartmentService departmentService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

/*
    * ----------------------Create operation-----------------------------------------------
    * We first create our view to return our form
    * Then we create our employee and store it in our DB
    * */

    @GetMapping("create-employee")
    private ModelAndView createEmployeeView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("employee/employeeForm");
        modelAndView.addObject("employee", new Employee());
        modelAndView.addObject("department", departmentService.findAll());
        return modelAndView;
    }

    @PostMapping("create-employee")
    private ModelAndView createEmployee(Employee employee, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){

            modelAndView.setViewName("employee/employeeForm");
            modelAndView.addObject("employee", new Employee());

        }

        if (employee.getId() == null ) {

            employeeService.saveEmployee(employee);
        }else{
            employeeService.updateEmployee(employee);
        }
        modelAndView.setViewName("employee/employeeForm");
        return modelAndView;

    }


/*
    * ----------------------Read Operation----------------------------------------
    * Get/list all employees stored in out system
    * Get a employee by it's ID
    * */


    @GetMapping("employees")
    public String listAllEmployees(Model model){
        model.addAttribute("myEmployee",employeeService.findAll());
        return "employee/employeesView";
    }

    @GetMapping("employee/{id}")
    private String getEmployee(@PathVariable Long id, Model model){

        model.addAttribute("myEmployee", employeeService.findEmployeeById(id));
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

