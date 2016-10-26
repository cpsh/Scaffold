package com.scaffold.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scaffold.dao.SysLogMapper;
import com.scaffold.model.SysLog;
import com.scaffold.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    public void insertLog(SysLog sysLog) {
        // TODO Auto-generated method stub
        
    }

    public List<SysLog> selectSysLogs(int page, int pageSize) {
        List<SysLog> list = new ArrayList<SysLog>();
        list = sysLogMapper.selectSysLogs(page,pageSize);
        return list;
    }
 

   
}
