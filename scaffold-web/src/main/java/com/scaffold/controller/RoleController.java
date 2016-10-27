package com.scaffold.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scaffold.common.exception.BizException;
import com.scaffold.model.Role;
import com.scaffold.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {
    //使用slf4j接口，代替log4j，这样假如以后不用log4j了，用logback等其它日志框架，只需修改xml配置文件，不需要修改java代码,slf4j+log4j.xml
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    
    
    @Autowired
    private TransactionTemplate tt;
    
    
    @RequestMapping(value="/getRoles",method=RequestMethod.GET)
    @ResponseBody
    public Object getRoles() {
    	List<Role> list = roleService.findRoleAll();
		return list;
	}
    
    
    @RequestMapping(value="/addRole",method = RequestMethod.GET)
    @ResponseBody
    public Object addRole(Model model,@RequestParam("rolename") String rolename){
    	Role role = new Role();
    	role.setName(rolename);
    	role.setDescription(rolename);
    	byte b = 0;
    	role.setSeq(b);
    	role.setStatus(b);
    	roleService.addRole(role);
    	return roleService.findRoleAll();
    }
    
    /**
     * 声明式事务 - 配置tx拦截
     * @param model
     * @param rolename
     * @return
     */
    @RequestMapping(value="/transTest1",method = RequestMethod.GET)
    @ResponseBody
    public Object transTest1(Model model,@RequestParam("rolename") String rolename){
    	Role role = new Role();
    	role.setName(rolename);
    	role.setDescription(rolename);
    	byte b = 0;
    	role.setSeq(b);
    	role.setStatus(b);
    	roleService.errorTransaction(role);
    	return roleService.findRoleAll();
    }
    
    /**
     * 声明式事务 - 开启注解
     * @param model
     * @param rolename
     * @return
     */
	@Transactional(propagation=Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    @RequestMapping(value="/transTest2",method = RequestMethod.GET)
    @ResponseBody
    public Object transTest2(Model model,@RequestParam("rolename") String rolename){
    	Role role = new Role();
    	role.setName(rolename);
    	role.setDescription(rolename);
    	byte b = 0;
    	role.setSeq(b);
    	role.setStatus(b);
    	roleService.addRole(role);
    	int i = 1 / 0;
    	/*try {
    		int i = 1 / 0;
		} catch (Exception e) {
			logger.error("抛异常，事务不提交, 插入失败，参数：{}", e.getMessage());
		}*/
    	
    	return roleService.findRoleAll();
    	
    }
    
    /**
     * 编程式事务 - TransactionTemplate类调用execute方法
     * @param model
     * @param rolename
     * @return
     */
	@RequestMapping(value = "/transTest3", method = RequestMethod.GET)
	@ResponseBody
	public Object transTest3(Model model,
			@RequestParam("rolename") final String rolename) {
		// 使用事务管理器模板进行事务控制
		List<Role> list = tt.execute(new TransactionCallback<List<Role>>() {
			@Override
			public List<Role> doInTransaction(TransactionStatus status) {
				Role role = new Role();
				role.setName(rolename);
				role.setDescription(rolename);
				byte b = 0;
				role.setSeq(b);
				role.setStatus(b);
				roleService.addRole(role);
				int i = 1 / 0;
				/*
				 * try { int i = 1 / 0; } catch (Exception e) {
				 * logger.error("抛异常，事务不提交,插入失败，参数：{}", e.getMessage()); }
				 */
				return roleService.findRoleAll();
			}
		});
       	
         return list;
       }
    
}
