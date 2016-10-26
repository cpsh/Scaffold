package com.scaffold.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scaffold.annotation.DataSourceChange;
import com.scaffold.model.SysLog;
import com.scaffold.service.SysLogService;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {
    //使用slf4j接口，代替log4j，这样假如以后不用log4j了，用logback等其它日志框架，只需修改xml配置文件，不需要修改java代码,slf4j+log4j.xml
    private static final Logger logger = LoggerFactory.getLogger(SysLogController.class);

    @Autowired
    private SysLogService logService;

    @RequestMapping(value = "/getMaster", method = RequestMethod.GET)
    @ResponseBody
    public Object selectSysLogs(SysLog sysLog, Integer page, Integer rows) {
        
//        logger.info(".xml : come into ........................");
//        LoggerUtil.info(".properties : come into ........................");
        List<SysLog> list  = logService.selectSysLogs(0,50);
        
        return list;
    }
    
    
    /**
     * //在controller,service接口,实现类中均可进行切换数据源，为了细粒度控制切换的时机，建议放在接口实现类中加注解
     * //    @DataSourceChange(slave = true)
     * @param sysLog
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/getSlave", method = RequestMethod.GET)
    @ResponseBody
    @DataSourceChange(slave = true)
    public Object selectSlaveLogs(SysLog sysLog, Integer page, Integer rows) {
        
        List<SysLog> list  = logService.selectSysLogs(0,50);
        
        return list;
    }
    
}
