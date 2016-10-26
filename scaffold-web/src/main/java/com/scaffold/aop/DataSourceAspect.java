package com.scaffold.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scaffold.annotation.DataSourceChange;
import com.scaffold.datasource.DynamicDataSource;
import com.scaffold.exception.DataSourceAspectException;

/**
 * 有{@link com.scaffold.annotation.DataSourceChange}注解的方法，调用时会切换到指定的数据源
 */
@Aspect
public class DataSourceAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut(value = "@annotation(com.scaffold.annotation.DataSourceChange)")
    private void changeDS() {}

    @Around(value = "changeDS()", argNames = "pjp")
    public Object doAround(ProceedingJoinPoint pjp) {
        Object retVal = null;
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        DataSourceChange annotation = method.getAnnotation(DataSourceChange.class);
        boolean selectedDataSource = false;
        
        String strMethodName = pjp.getSignature().getName();
        String strClassName = pjp.getTarget().getClass().getName();
        
        try {
            if (null != annotation) {
                selectedDataSource = true;
                if (annotation.slave()) {
                    DynamicDataSource.useSlave();
                    String strMessage = String
                            .format("[类名]:%s,[方法]:%s -- 切换从库数据源", strClassName, strMethodName);
                    LOGGER.info(strMessage);
                } else {
                    DynamicDataSource.useMaster();
                }
            }
            retVal = pjp.proceed();
        } catch (Throwable e) {
        	String strMessage = String
                    .format("[类名]:%s,[方法]:%s -- 数据源切换错误", strClassName, strMethodName);
            LOGGER.error(strMessage, e);
            throw new DataSourceAspectException("数据源切换错误", e);
        } finally {
            if (selectedDataSource) {
                DynamicDataSource.reset();
            }
        }
        return retVal;
    }
}
