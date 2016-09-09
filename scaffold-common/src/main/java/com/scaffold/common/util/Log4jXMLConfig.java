package com.scaffold.common.util;

import java.net.URL;  
import org.apache.log4j.xml.DOMConfigurator;

public class Log4jXMLConfig {
    private static Log4jXMLConfig instance;  
    
    public static synchronized Log4jXMLConfig initia()  
    {  
        if (instance == null)  
        {  
            return new Log4jXMLConfig();  
        }  
        return instance;  
    }  
  
    private Log4jXMLConfig()  
    {  
//        final URL url = Log4jXMLConfig.class.getResource("/WEB-INF/log4j.xml");
        final String url = "WebRoot/WEB-INF/log4j.xml";
        DOMConfigurator.configure(url);  
    } 
}
