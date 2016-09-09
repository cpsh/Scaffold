package com.scaffold.common.util;

import org.apache.log4j.Logger;

public class LoggerUtil {

    private static Logger debugLogger = null ;
    private static Logger infoLogger = null ;
    private static Logger warnLogger = null ;
    private static Logger errorLogger = null ;

    static{
        debugLogger = Logger.getLogger("debugLog");
        infoLogger = Logger.getLogger("MyinfoLog");
        warnLogger = Logger.getLogger("warnLog");
        errorLogger = Logger.getLogger("errorLog");
    }

    public static void debug(Object message){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        debugLogger.debug(stack[2].getClassName()+"."+stack[2].getMethodName()+"() --- Line:"+stack[2].getLineNumber()+" --- Message:"+message);
    }

    public static void info(Object message){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        infoLogger.info(stack[2].getClassName()+"."+stack[2].getMethodName()+"() --- Line:"+stack[2].getLineNumber()+" --- Message:"+message);
    }

    public static void warn(Object message){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        warnLogger.warn(stack[2].getClassName()+"."+stack[2].getMethodName()+"() --- Line:"+stack[2].getLineNumber()+" --- Message:"+message);
    }

    public static void error(Object message){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        errorLogger.error(stack[2].getClassName()+"."+stack[2].getMethodName()+"() --- Line:"+stack[2].getLineNumber()+" --- Message:"+message);
    }
}