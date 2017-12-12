package com.factory.controller;

import com.factory.model.Department;
import com.factory.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /*
    * ----------------------Create operation-----------------------------------------------
    * We first create our view to return our form
    * Then we create our department and store it in our DB
    * */
    @GetMapping("create-department")
    private ModelAndView createDepartmentView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("departmentForm");
        modelAndView.addObject("department", new Department());
        return modelAndView;
    }

    @PostMapping("create-department")
    private ModelAndView createDepartment(Department department, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){

            modelAndView.setViewName("departmentForm");
            modelAndView.addObject("department", new Department());

        }

        if (department.getId() == 0 ) {

            departmentService.saveDepartment(department);
        }else{
            departmentService.updateDepartment(department);
        }
        modelAndView.setViewName("department");
        return modelAndView;

    }

    /*
    * ----------------------Read Operation----------------------------------------
    * Get/list all departments stored in out system
    * Get a department by it's ID
    * */

    @GetMapping("departments")
    public String listAllFactories(Model model){
        model.addAttribute("department ",departmentService.findAll());

        return "departmentsView";
    }

    @PostMapping("department/{id}")
    private String getDepartment(@PathVariable Long id, Model model){

        model.addAttribute("department", departmentService.findDepartmentById(id));
        return "departmentView";
    }

    /*
    * Update operation
    * */

    @PutMapping("edit/{id}")
    private String edit(@PathVariable Long id, Model model){

        model.addAttribute("department", departmentService.findDepartmentById(id));
        model.addAttribute("departments", departmentService.findAll());

        return "departmentsView";
    }

    /*
    * Delete Operation
    * */
    @PostMapping("delete/{id}")
    private String delete(@PathVariable Long id){

        departmentService.deleteDepartmentById(id);
        return id.toString();
    }
}
