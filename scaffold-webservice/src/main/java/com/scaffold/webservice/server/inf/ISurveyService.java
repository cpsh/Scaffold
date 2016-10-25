package com.scaffold.webservice.server.inf;

import javax.jws.WebService;

@WebService 
public interface ISurveyService {
	 /** 
     * @param username 名字 
     * @param point 分数 
     * @return 
     */  
    public String vote(String username,int point);  
}
