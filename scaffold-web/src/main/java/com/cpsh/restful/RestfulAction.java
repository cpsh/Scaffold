package com.cpsh.restful;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/userinfo")
public class RestfulAction {

    /* 更新 */
    @RequestMapping(value = "/{id}/update/", method = RequestMethod.PUT)
    public String update(@PathVariable Long id, HttpServletRequest request,
            HttpServletResponse response) {

        System.out.println("update : id = " + id);
        return "front/restful";

    }
    
    /* 保存新增 */
    @RequestMapping(method = RequestMethod.GET)
    public String show(HttpServletRequest request,
            HttpServletResponse response) {
        return "front/restful";
    }
}
