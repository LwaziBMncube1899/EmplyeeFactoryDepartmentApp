package com.factory.controller;

import com.factory.model.Factory;
import com.factory.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/factory")
public class FactoryController {

    private final FactoryService factoryService;

    @Autowired
    public FactoryController(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    /*
    * Create operation
    * We first create our view to return our form
    * Then we create our factory and store in our DB
    * */
    @GetMapping("create-factory")
    private ModelAndView createFactoryView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("factoryForm");
        modelAndView.addObject("factory", new Factory());
        return modelAndView;
    }

    @PostMapping("create-factory")
    private ModelAndView createFactory(@Valid Factory factory, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){

            modelAndView.setViewName("factoryForm");
            modelAndView.addObject("factory", new Factory());

        }

        factoryService.addFactory(factory);
        modelAndView.setViewName("factory");
        return modelAndView;

    }

    /*
    * Read Operation
    * Get/list all factories stored in out system
    * Get a factory by it's ID
    * */

    @GetMapping("factories")
    public String listAll(Model model){
        model.addAttribute("factories ",factoryService.getAll());

        return "factoriesView";
    }

    @PostMapping("factory/{id}")
    private String getFactory(@PathVariable Long id, Model model){

        model.addAttribute("factory", factoryService.getFactoryById(id));
        return "factoryView";
    }

    /*
    * Update operation
    * */

    @PostMapping("edit/{id}")
    private String edit(@PathVariable Long id, Model model){

        model.addAttribute("factory", factoryService.getFactoryById(id));
        return "factoriesView";
    }

    /*
    * Delete Operation
    * */
    @PostMapping("delete/{id}")
    private String delete(@PathVariable Long id){
        factoryService.deleteFactory(id);
        return "factoriesView";
    }
}
