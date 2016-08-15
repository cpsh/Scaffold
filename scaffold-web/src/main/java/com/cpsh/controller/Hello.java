package com.cpsh.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Hello {

    @RequestMapping(value = "/hello")
    public String hello() {

        System.out.println("spring mvc hello world!");

        return "front/hello";

    }

    @RequestMapping(value = "/ok")
    @ResponseBody
    public Object ok() {
        System.out.println("ok");
        List<String> list = new ArrayList<String>();
        list.add("电视机");
        list.add("冰箱");
        list.add("山东省");
        list.add("多发点");
        list.add("D大调");
        list.add("规范");
        list.add("啦啦啦");
        list.add("咯就是");
        list.add("阿瓦尔");

        return list;

    }

}
