package com.cpsh.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.cpsh.model.Person;
import com.cpsh.service.IService.IServPerson;
import com.cpsh.service.ServiceImpl.PersonService;

@Controller
public class PersonAction {
    private final IServPerson service;
    
    @Autowired
    public PersonAction(PersonService service) {
        this.service = service;
    }
    
    
    
    
    @RequestMapping(value = "/personList")
    @ResponseBody
    public List<Person> getPersons(){
        List<Person> persons = new ArrayList<Person>();
        persons = this.service.getPersonList();
        return persons;
    }
    

    
    
    
}
