package com.cpsh.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cpsh.model.Person;
import com.cpsh.util.ParamUtil;

@Controller
public class HelloAction {

    /**
     * 内建格式化转换器
     */
    @NumberFormat(style = Style.NUMBER, pattern = "#,###")
    private int totalcount;

    /**
     * 指定字段不允许为null,验证失败则从之前指定的messageSource中获取 消息内容
     */
    @NumberFormat(style = Style.CURRENCY)
    @NotNull(message = "{sumMoney can not be null!}")
    private double sumMoney;

    @NumberFormat(style = Style.PERCENT)
    private double discount;

    @DateTimeFormat(iso = ISO.DATE)
    private Date registerDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderDate;

    @RequestMapping(value = "/hello/")
    public String hello() {

        System.out.println("spring mvc hello world!");
        return "front/hello";

    }

    @RequestMapping(value = "/hello/forward/")
    public String forwardURL() {
        System.out.println("进入HelloAction。。。开始Action转发");
        return "forward:/forward/";
    }

    @RequestMapping(value = "/hello/redirect/")
    public String redirectURL(HttpServletRequest request) {
        System.out.println("进入HelloAction。。。开始Action重定向");
        String param = ParamUtil.getParameter(request, "param");
        System.out.println("param = " + param);
        request.setAttribute("param", "这是一个转发的页面");
        return "redirect:/index.jsp";
    }

    @RequestMapping(value = "/hello1/")
    public ModelAndView hello1(Model model) {
        System.out.println("model and view");
        // 1、收集参数
        // 2、绑定参数到命令对象
        // 3、调用业务对象
        // 4、选择下一个页面
        model.addAttribute("a", "a");// ①添加模型数据
        ModelAndView mv = new ModelAndView();
        mv.addObject("a", "update");// ②在视图渲染之前更新③处同名模型数据
        // 添加模型数据 可以是任意的POJO对象
        mv.addObject("message", " model and view");
        // 设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
        mv.setViewName("front/hello");
        return mv;
    }

    @RequestMapping(value = "/hello2/{id}/{cityPinYin:[a-z]+[0-9]?}", params = "username")
    public String hello2(@PathVariable String id,
            @PathVariable(value = "cityPinYin") String cityPinYin2,
            @RequestParam String username, Model model, ModelMap modelMap,
            Map map) {
        System.out.println("id = " + id);
        System.out.println("cityPinYin = " + cityPinYin2);
        model.addAttribute("message", "model attribute @PathVariable");
        modelMap.put("message", "model attribute @PathVariable new");
        map.put("message", "model attribute @PathVariable last");// model.modelMap,map三者为同一对象，覆盖同名

        System.out.println(model == modelMap);// true
        System.out.println(modelMap == map);// true
        return "front/hello";
    }

    @RequestMapping(value = "/hello3/", method = { RequestMethod.PUT })
    public String hello3(@RequestParam("username") String username1,
            Model model, @Valid @ModelAttribute("user") Person person) {
        System.out.println("param username = " + username1);
        model.addAttribute("username", username1);
        return "front/hello";
    }

    /**
     * 处理ajax请求
     * 
     * 包含下面两个jar包。spring的配置文件开启<mvc:annotation-driven/>,使用spring的内置json准换
     * jackson-core-asl-1.7.2.jar jackson-mapper-asl-1.7.2.jar
     * 
     * @return
     */
    @RequestMapping(value = "/hello/ok")
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

    @RequestMapping(value = "/hello/exception")
    public void exception() {
        try {
            throw new Exception("抛出异常");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
