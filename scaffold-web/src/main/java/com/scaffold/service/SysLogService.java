package com.scaffold.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scaffold.model.SysLog;

/**
 * @description：操作日志
 */
@Service
public interface SysLogService {

    void insertLog(SysLog sysLog);

    List<SysLog> selectSysLogs(int page,int pageSize);

}
