webservice 服务配置：

web.xml中配置

<!-- Apache CXF Servlet  webservice控制 -->  
    <servlet>  
        <servlet-name>CXFServlet</servlet-name>  
        <servlet-class>  
            org.apache.cxf.transport.servlet.CXFServlet  
        </servlet-class>  
        <load-on-startup>1</load-on-startup>  
    </servlet>  
    <!-- CXFServlet Mapping -->  
    <servlet-mapping>  
        <servlet-name>CXFServlet</servlet-name>  
        <url-pattern>/*</url-pattern>  
    </servlet-mapping>
    
    
使用spring容器

server端
需要配置server.xml,需要暴露服务地址，调用接口，具体服务实现bean。
容器运行，发布服务，成功会生成wsdl文件


client端
(1), 如果知道服务地址,在xml中或者代码中，直接调用服务地址，运行需要的服务;
(2),如果只有第三方的wsdl文件,可使用辅助工具生成对应的java代码 [apache-cxf:wsdl2java工具,jdk自带的wsimport命令]
client调用代码中使用生成的服务接口来调用所需要的服务。


    