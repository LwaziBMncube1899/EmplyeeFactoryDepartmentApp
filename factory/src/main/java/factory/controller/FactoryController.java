package factory.controller;

import factory.common.PagerResp;
import factory.model.Factory;
import factory.model.PageWrapper;
import factory.service.DepartmentService;
import factory.service.FactorySearchServiceImpl;
import factory.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/factory")
public class FactoryController {

    private final FactoryService factoryService;

    @Autowired
    private final DepartmentService departmentService = null;

    private FactorySearchServiceImpl factorySearchServiceImpl;

    @Autowired
    public FactoryController(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    /*
     * ----------------------Create operation-----------------------------------------------
     * We first create our view to return our form
     * Then we create our factory and store it in our DB
     * */
    @GetMapping("create-factory")
    private ModelAndView createFactoryView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("factory/factoryForm");
        modelAndView.addObject("factory", new Factory());
        return modelAndView;
    }

    @PostMapping("create-factory")
    private ModelAndView createFactory(Factory factory, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {

            modelAndView.setViewName("factory/factoryForm");
            modelAndView.addObject("factory", new Factory());

        }

        if (factory.getId() == null) {

            factoryService.saveFactory(factory);
        } else {
            factoryService.updateFactory(factory);
        }
        modelAndView.setViewName("factory/factoryForm");
        return modelAndView;

    }

    /*
     * ----------------------Read Operation----------------------------------------
     * Get/list all factories stored in out system
     * Get a factory by it's ID
     * */


    @RequestMapping("jFactories")
    @ResponseBody
    public PagerResp returnAllFactories(Integer page, Integer limit, Model model) {

        /*
       Model model, Pageable pageable

       Page<Factory> factoryPage = factoryService.findAll(pageable);
        PageWrapper<Factory> page = new PageWrapper<Factory>(factoryPage"/factories");

        model.addAttribute("page",page);
        model.addAttribute("myFactories", page.getContent());
        return "factory/factoriesView";
          ModelAndView model;

        model = new ModelAndView("factory/factoriesView").addObject("myFactories", factories);
        */
        PageRequest request = new PageRequest(page - 1, limit);
        Page<Factory> factories = factoryService.findAll(request);

        PagerResp resp = new PagerResp();
        resp.setCount(factories.getTotalElements());
        resp.setData(factories.getContent());
        return resp;

    }

/*    @GetMapping("search/{queryString}")
    public List<Factory> searchFactory(@RequestParam(value = "pageNumber") Integer pageNumber,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                       @RequestParam(value = "searchContent") String searchContent) {
        return factorySearchServiceImpl.searchFactory(pageNumber,pageSize,searchContent);

    }*/



    @GetMapping("factories")
    private ModelAndView listAllFactories(Pageable pageable, Model model){

           PageWrapper<Factory> page = new PageWrapper<>(factoryService.findAll(pageable),"factories");

           model.addAttribute("page", page);
           model.addAttribute("myFactories", factoryService.findAll(pageable));
/*
        for(Factory fact : factories) {
            System.out.println(fact.getId() + "" + fact.getName() + "" + fact.getAddress() + "" + fact.getContactNumber() + ""  + fact.getDepartments());
        }
        System.out.println(" " + factories.getContent());
*/

        return new ModelAndView("factory/factoriesView");
    }


    @GetMapping("factoryDepartments/{id}")
    private String listFactoryDepartment(@PathVariable Long id, Model model){

        model.addAttribute("myFactories", factoryService.findFactoryById(id));
        model.addAttribute("myDepartments", departmentService.findDepartmentsByFactoryId(factoryService.findFactoryById(id)));

       return "factory/factoryView";

    }
    @GetMapping("factory/{id}")
    private String getFactory(@PathVariable Long id, Model model){

        model.addAttribute("myFactories", factoryService.findFactoryById(id));

        return "factory/factoryView";
    }


    @GetMapping("edit/{id}")
    public ModelAndView edit(@PathVariable("id") long id) {
        ModelAndView model = new ModelAndView("factory/factoryUpdate");
       model.addObject("myFactory", factoryService.findFactoryById(id));
        return model;

    }



    @PostMapping("update")
    public ModelAndView update(@RequestParam("id") Long id,
                               @RequestParam("name") String name,
                               @RequestParam("address") String address,
                               @RequestParam("email") String email,
                               @RequestParam("contactNumber") String contactNumber) {
        Factory factory = factoryService.findFactoryById(id);

        factory.setName(name);
        factory.setAddress(address);
        factory.setEmail(email);
        factory.setContactNumber(contactNumber);
        factoryService.saveFactory(factory);
        return new ModelAndView("redirect:/factory/factories");
    }

    /*
    * Delete Operation
    * */
    @PostMapping("delete/{id}")
    private String delete(@PathVariable Long id){

        factoryService.deleteFactoryById(id);
        return id.toString();
    }
}
