package com.scaffold.webservice.server.impl;

import javax.jws.WebService;

import com.scaffold.webservice.server.inf.ISurveyService;

@WebService
public class SurveyService implements ISurveyService {

	private String excludeName = "Michael";  
    private int leastPonit = 5;  
  
    @Override
    public String vote(String username,int point)  
    {  
        String result = "";  
        if(excludeName.equals(username))  
        {  
            result = " 您不能重复进行投票！";  
        }  
        else  
        {  
            result = " 谢谢您的投票！";  
            if(point < leastPonit)  
            {  
                result += " 您的投票分数太低！";  
            }  
            else  
            {  
                result += " 您的投票分数通过审核！";  
            }  
        }  
        return result;  
    }  
  
    // For IoC  
    public String getExcludeName()  
    {  
        return excludeName;  
    }  
  
    public void setExcludeName(String excludeName)  
    {  
        this.excludeName = excludeName;  
    }  
  
    public int getLeastPonit()  
    {  
        return leastPonit;  
    }  
  
    public void setLeastPonit(int leastPonit)  
    {  
        this.leastPonit = leastPonit;  
    }  
}
