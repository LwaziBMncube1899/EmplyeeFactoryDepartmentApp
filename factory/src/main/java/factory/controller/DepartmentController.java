package factory.controller;

import factory.model.Department;
import factory.service.DepartmentService;
import factory.service.FactoryService;
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
    private final FactoryService factoryService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, FactoryService factoryService) {
        this.departmentService = departmentService;
        this.factoryService = factoryService;
    }

/*
    * ----------------------Create operation-----------------------------------------------
    * We first create our view to return our form
    * Then we create our department and store it in our DB
    *
*/

    @GetMapping("create-department")
    private ModelAndView createDepartmentView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("department/departmentForm");
        modelAndView.addObject("department", new Department());
       // model.addAttribute("factory",factoryService.findAll());
       modelAndView.addObject("factory", factoryService.factoryList());

        return modelAndView;
    }

    @PostMapping("create-department")
    private ModelAndView createDepartment(Department department, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){

            modelAndView.setViewName("department/departmentForm");
            modelAndView.addObject("department", new Department());


        }

        if (department.getId() == null ) {

            departmentService.saveDepartment(department);
        }else{
            departmentService.updateDepartment(department);
        }
        modelAndView.setViewName("department/departmentForm");
        return modelAndView;

    }
/*

    * ----------------------Read Operation----------------------------------------
    * Get/list all departments stored in out system
    * Get a department by it's ID
    *
*/


    @GetMapping("departments")
    public String listAllDepartments(Model model){
         model.addAttribute("myDepartments",departmentService.findAll());
        System.out.println(departmentService.findAll().get(0).getDepartmentName()+ " " + departmentService.findAll().get(0).getDepartmentDescription());
        return "department/departmentsView";
    }

    @GetMapping("search/{id}")
    private String searchDepartment(@PathVariable Long id, Model model){

        model.addAttribute("getDepartment", departmentService.findDepartmentById(id));
        /*
        model.addAttribute("factory", factoryService.factoryList());
        */return "department/departmentUpdate";
    }

  /*  * Update operation
    *
*/

    @PostMapping("edit/{id}")
    private String edit(Department department){
        if (department.getId() == null ) {

            departmentService.saveDepartment(department);
        }else{
            departmentService.updateDepartment(department);
        }

        return "department/departmentsView";
    }

    /** Delete Operation
    *
*/
    @PostMapping("delete/{id}")
    private String delete(@PathVariable Long id){

        departmentService.deleteDepartmentById(id);
        return id.toString();
    }
}
