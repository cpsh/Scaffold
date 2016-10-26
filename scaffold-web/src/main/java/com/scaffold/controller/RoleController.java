package com.scaffold.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.scaffold.annotation.DataSourceChange;
import com.scaffold.common.util.LoggerUtil;
import com.scaffold.model.Role;
import com.scaffold.model.SysLog;
import com.scaffold.service.RoleService;
import com.scaffold.service.SysLogService;

@Controller
@RequestMapping("/role")
public class RoleController {
    //使用slf4j接口，代替log4j，这样假如以后不用log4j了，用logback等其它日志框架，只需修改xml配置文件，不需要修改java代码,slf4j+log4j.xml
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    
    @RequestMapping(value="/getRoles",method=RequestMethod.GET)
    @ResponseBody
    public Object getRoles() {
    	List<Role> list = roleService.findRoleAll();
		return list;
	}
}
