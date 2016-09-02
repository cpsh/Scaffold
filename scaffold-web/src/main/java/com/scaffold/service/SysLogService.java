package com.scaffold.service;

import java.util.List;

import com.scaffold.model.SysLog;

/**
 * @description：操作日志
 * @author：sanboot
 * @date：2015/10/30 10:35
 */
public interface SysLogService {

    void insertLog(SysLog sysLog);

    List<SysLog> selectSysLogs(int page,int pageSize);

}
