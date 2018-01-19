package factory.controller;


import factory.model.Department;
import factory.model.Factory;
import factory.model.PageWrapper;
import factory.service.DepartmentService;
import factory.service.EmployeeService;
import factory.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    private  final EmployeeService employeeService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, FactoryService factoryService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.factoryService = factoryService;
        this.employeeService = employeeService;
    }

/*
    * ----------------------Create operation-----------------------------------------------
    * We first create our view to return our form
    * Then we create our department and store it in our DB
    *
*/

    @GetMapping("create-department")
    private ModelAndView createDepartmentView(Pageable pageable){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("department/departmentForm");
        modelAndView.addObject("department", new Department());
       // model.addAttribute("factory",factoryService.findAll());
     modelAndView.addObject("factory", factoryService.findAll(pageable));

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
    public String listAllDepartments(Model model, Pageable pageable){
        PageWrapper<Department> page = new PageWrapper<Department>
                (departmentService.findAll(pageable), "departments");

        model.addAttribute("page",page);
        model.addAttribute("myDepartments", departmentService.findAll(pageable));
        return "department/departmentsView";
    }

    @GetMapping("departmentEmployees/{id}")
    private String searchDepartment(@PathVariable Long id, Model model, Pageable pageable){

        model.addAttribute("myDepartment", departmentService.findDepartmentById(id));

        model.addAttribute("factory", factoryService.findAll(pageable));

        model.addAttribute("myEmployees", employeeService.findDepartmentEmployeeByID(departmentService.findDepartmentById(id)));

        return "department/departmentView";
    }

    @GetMapping("view/{id}")
    private String view(@PathVariable Long id, Model model){

        model.addAttribute("myDepartment", departmentService.findDepartmentById(id));

        //model.addAttribute("factory", factoryService.factoryList());
        return "department/departmentView";
    }

  /*  * Update operation
    *
*/

    @GetMapping("edit/{id}")
        public ModelAndView edit(@PathVariable("id") long id) {
            ModelAndView model = new ModelAndView("department/departmentUpdate");
            Department department = departmentService.findDepartmentById(id);
            model.addObject("myDepartment", departmentService.findDepartmentById(id));
           // model.addObject("factory", factoryService.factoryList());
            return model;

    }

    @PostMapping("update")
    public ModelAndView update(@RequestParam("id") Long id,
                               @RequestParam("departmentName") String name,
                               @RequestParam("departmentDescription") String description,
                               @RequestParam("factory") Factory factory) {
        Department department = departmentService.findDepartmentById(id);

        department.setDepartmentName(name);
        department.setDepartmentDescription(description);
        department.setFactory(factory);
        departmentService.saveDepartment(department);
        return new ModelAndView("redirect:/department/departments");
    }

    /** Delete Operation
    *
*/
    @PostMapping("delete/{id}")
    private String delete(@PathVariable Long id){

        departmentService.deleteDepartmentById(id);
        return "department/departmentsView";

    }
}
