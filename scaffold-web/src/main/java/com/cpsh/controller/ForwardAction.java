package com.cpsh.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonFormat.Value;

@Controller
public class ForwardAction {

    @RequestMapping(value = "/forward/")
    public String hello() {

        System.out.println("welcome to forwardAction 。。。。");
        return "front/forward";

    }

    
    

}
