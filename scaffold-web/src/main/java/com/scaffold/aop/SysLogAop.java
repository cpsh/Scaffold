package com.scaffold.aop;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.scaffold.model.SysLog;

/**
 * @description：AOP 日志
 * @author：sanboot
 * @date：2015/10/28 18:04
 */
@Aspect
@Component
public class SysLogAop {
    private static Logger LOGGER = LoggerFactory.getLogger(SysLogAop.class);

    /*@Autowired
    private LogService logService;*/

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void cutController() {
    }
    
    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void cutService() {
    }

    /**
     * service组件的Around
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("cutService()")
    public Object serviceSysLog(ProceedingJoinPoint point) throws Throwable{
        String strMethodName = point.getSignature().getName();
        String strClassName = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();
        StringBuffer bfParams = new StringBuffer();
        Enumeration<String> paraNames = null;
        HttpServletRequest request = null;
        if (params != null && params.length > 0) {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            paraNames = request.getParameterNames();
            String key;
            String value;
            while (paraNames.hasMoreElements()) {
                key = paraNames.nextElement();
                value = request.getParameter(key);
                bfParams.append(key).append("=").append(value).append("&");
            }
            if (StringUtils.isBlank(bfParams)) {
                bfParams.append(request.getQueryString());
            }
        }

        String strMessage = String
                .format("service - [类名]:%s,[方法]:%s,[参数]:%s", strClassName, strMethodName, bfParams.toString());
        LOGGER.info(strMessage);
        return point.proceed();
    }
    
    
    /**
     *  
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("cutController()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {

        String strMethodName = point.getSignature().getName();
        String strClassName = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();
        StringBuffer bfParams = new StringBuffer();
        Enumeration<String> paraNames = null;
        HttpServletRequest request = null;
        if (params != null && params.length > 0) {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            paraNames = request.getParameterNames();
            String key;
            String value;
            while (paraNames.hasMoreElements()) {
                key = paraNames.nextElement();
                value = request.getParameter(key);
                bfParams.append(key).append("=").append(value).append("&");
            }
            if (StringUtils.isBlank(bfParams)) {
                bfParams.append(request.getQueryString());
            }
        }

        String strMessage = String
                .format("controller - [类名]:%s,[方法]:%s,[参数]:%s", strClassName, strMethodName, bfParams.toString());
        LOGGER.info(strMessage);
        if (isWriteLog(strMethodName)) {
            try {
                Subject currentUser = SecurityUtils.getSubject();
                PrincipalCollection collection = currentUser.getPrincipals();
                if (null != collection) {
                    String loginName = collection.getPrimaryPrincipal().toString();
                    SysLog sysLog = new SysLog();
                    sysLog.setLoginName(loginName);
                    sysLog.setRoleName(loginName);
                    sysLog.setOptContent(strMessage);
                    if (request != null) {
                        sysLog.setClientIp(request.getRemoteAddr());
                    }
                    LOGGER.info(sysLog.toString());
                    //插入DB
//                    logService.insertLog(sysLog);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return point.proceed();
    }

    private boolean isWriteLog(String method) {
        String[] pattern = {"login", "logout", "add", "edit", "delete", "grant"};
        for (String s : pattern) {
            if (method.indexOf(s) > -1) {
                return true;
            }
        }
        return false;
    }
}
