package factory.controller;

import factory.service.FactoryService;
import factory.model.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/factory")
public class FactoryController {

    private final FactoryService factoryService;

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
    private ModelAndView createFactoryView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("factory/factoryForm");
        modelAndView.addObject("factory", new Factory());
        return modelAndView;
    }

    @PostMapping("create-factory")
    private ModelAndView createFactory(Factory factory, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){

            modelAndView.setViewName("factory/factoryForm");
            modelAndView.addObject("factory", new Factory());

        }

        if (factory.getId() == null ) {

            factoryService.saveFactory(factory);
        }else{
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

    @GetMapping("factories")
    public String listAllFactories(Model model){
        model.addAttribute("myFactories",factoryService.factoryList());
        System.out.println("" + factoryService.factoryList());
        return "factory/factoriesView";
    }

    @PostMapping("factory/{id}")
    private String getFactory(@PathVariable Long id, Model model){

        model.addAttribute("factory", factoryService.findFactoryById(id));
        return "factory/factoryView";
    }

    /*
    * Update operation
    * */

    @PutMapping("edit/{id}")
    private String edit(@PathVariable Long id, Model model){

        model.addAttribute("factory", factoryService.findFactoryById(id));
        model.addAttribute("factories", factoryService.factoryList());

        return "factory/factoriesView";
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
