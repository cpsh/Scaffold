package com.cpsh.util;

import javax.servlet.*;
import java.util.*;

public class ParamUtil {
    /**
     * 获得request中指定名称的参数值,若有中文乱码问题请增加转码部分
     * @param request ServletRequest对象
     * @param paramName 参数名称
     * @return 如果该变量值存在则返回该值，否则返回""
     */
    public static String getParameter( ServletRequest request, String paramName ) {
      long start  = System.currentTimeMillis();
      String temp = request.getParameter(paramName);
      //过滤跨站脚本攻击
      if(temp!=null && temp.indexOf("<script")!=-1){
          return "";
      }

      long take = System.currentTimeMillis() - start;

      
      if(take>1000) System.out.println(paramName +" request take time :" + take);
      if( temp != null && !temp.equals("") && !temp.equals("null")) {//modify by liuxbin
          //若有中文问题，在此添加转码代码，例：temp = new String(temp.getBytes("8859_1"), "GB2312");
          return temp;
      }
      else {
        return "";
      }
      
    }
    
    /**
     * 防止XSS攻击的取参数方法
     * @param request
     * @param paramName
     * @return
     */
    public static String getAvoidXssParameter(ServletRequest request, String paramName){
        return ParamUtil.xssEncode(ParamUtil.getParameter(request, paramName));
    }

    /**
     * 获得request中的int型参数值
     * @param request ServletRequest对象
     * @param paramName 参数名称
     * @param defaultNum 默认值，如果没有返回该值
     * @return 如果该参数值存在则返回其转换为int型的值，否则返回defaultNum
     */
    public static int getIntParameter( ServletRequest request, String paramName, int defaultNum ) {
      String temp = request.getParameter(paramName);
      if( temp != null && !temp.equals("") ) {
        int num = defaultNum;
        try {
            num = Integer.parseInt(temp);
        }
        catch( Exception ignored ) {
        }
        return num;
      }
      else {
        return defaultNum;
      }
    }
    
    /**
     * 获得request中指定名称的参数值,若有中文乱码问题请增加转码部分
     * @param request ServletRequest对象
     * @param paramName 参数名称
     * @param lastQueryMap 上次查询时的请求参数,从session 中取得
     * @param isUseLastQueryParam 是否使用上次查询时的请求参数
     * @return 如果该变量值存在则返回该值，否则返回""
     */
    public static String getParameter( ServletRequest request, String paramName,Map lastQueryMap,boolean isUseLastQueryParam ) {
      long start  = System.currentTimeMillis();
      String temp =null;;
      if(isUseLastQueryParam&&lastQueryMap!=null){
          String[] tempArry = (String[]) lastQueryMap.get(paramName);
          if( tempArry != null && tempArry.length>0&&tempArry[0]!=null){
            temp = tempArry[0];
          }
      }else{
              temp = request.getParameter(paramName);
      }  
      long take = System.currentTimeMillis() - start;
      
      if(take>1000) System.out.println(paramName +" request take time :" + take);
      if( temp != null && !temp.equals("") ) {
          //若有中文问题，在此添加转码代码，例：temp = new String(temp.getBytes("8859_1"), "GB2312");
          return temp;
      }
      else {
        return "";
      }
      
    }
    /**
     * 获得request中的int型参数值
     * @param request ServletRequest对象
     * @param paramName 参数名称
     * @param defaultNum 默认值，如果没有返回该值
     * @param lastQueryMap 上次查询时的请求参数,从session 中取得
     * @param isUseLastQueryParam 是否使用上次查询时的请求参数
     * @return 如果该参数值存在则返回其转换为int型的值，否则返回defaultNum
     */
    public static int getIntParameter( ServletRequest request, String paramName, int defaultNum,Map lastQueryMap,boolean isUseLastQueryParam) {
      String temp =null;;
      if(isUseLastQueryParam&&lastQueryMap!=null){
          String[] tempArry = (String[]) lastQueryMap.get(paramName);
          if( tempArry != null && tempArry.length>0&&tempArry[0]!=null){
              temp = tempArry[0];
          }
      }else{
          temp = request.getParameter(paramName);
      }
      if( temp != null && !temp.equals("") ) {
        int num = defaultNum;
        try {
            num = Integer.parseInt(temp);
        }
        catch( Exception ignored ) {
        }
        return num;
      }
      else {
        return defaultNum;
      }
    }
    /**
     * 获得request中的int型参数值
     * @param request ServletRequest对象
     * @param paramName 参数名称
     * @param defaultNum 默认值，如果没有返回该值
     * @param lastQueryMap 上次查询时的请求参数,从session 中取得
     * @param isUseLastQueryParam 是否使用上次查询时的请求参数
     * @return 如果该参数值存在则返回其转换为int型的值，否则返回defaultNum
     */
    public static long getLongParameter( ServletRequest request, String paramName, long defaultNum,Map lastQueryMap,boolean isUseLastQueryParam) {
        String temp =null;;
          if(isUseLastQueryParam&&lastQueryMap!=null){
              String[] tempArry = (String[]) lastQueryMap.get(paramName);
              if( tempArry != null && tempArry.length>0&&tempArry[0]!=null){
                  temp = tempArry[0];
              }
          }else{
              temp = request.getParameter(paramName);
          }
      if( temp != null && !temp.equals("") ) {
        long num = defaultNum;
        try {
            num = Long.parseLong(temp);
        }
        catch( Exception ignored ) {
        }
        return num;
      }
      else {
        return defaultNum;
      }
    }
    /**
     * 获得request中的int型参数值
     * @param request ServletRequest对象
     * @param paramName 参数名称
     * @param defaultNum 默认值，如果没有返回该值
     * @return 如果该参数值存在则返回其转换为int型的值，否则返回defaultNum
     */
    public static long getLongParameter( ServletRequest request, String paramName, long defaultNum ) {
      String temp = request.getParameter(paramName);
      if( temp != null && !temp.equals("") ) {
        long num = defaultNum;
        try {
            num = Long.parseLong(temp);
        }
        catch( Exception ignored ) {
        }
        return num;
      }
      else {
        return defaultNum;
      }
    }
    
    /**
     * 获得request中的double型参数值
     * @param request ServletRequest对象
     * @param paramName 参数名称
     * @param defaultNum 默认值，如果没有返回该值
     * @return 如果该参数值存在则返回其转换为double型的值，否则返回defaultNum
     */
    public static double getDoubleParameter( ServletRequest request, String paramName, double defaultNum ) {
      String temp = request.getParameter(paramName);
      if( temp != null && !temp.equals("") ) {
        double num = defaultNum;
        try {
            num = Double.parseDouble(temp);
        }
        catch( Exception ignored ) {
        }
        return num;
      }
      else {
        return defaultNum;
      }
    }

      /**
       * 获得request中的float型参数值
       * @param request ServletRequest对象
       * @param paramName 参数名称
       * @param defaultNum 默认值，如果没有返回该值
       * @return 如果该参数值存在则返回其转换为float型的值，否则返回defaultNum
       */
      public static double getFloatParameter( ServletRequest request, String paramName, double defaultNum ) {
          String temp = request.getParameter(paramName);
          if( temp != null && !temp.equals("") ) {
              double num = defaultNum;
              try {
                  num = Float.parseFloat(temp);
              }
              catch( Exception ignored ) {
              }
              return num;
          }
          else {
              return defaultNum;
          }
      }
    
    
      public static String encodeURL(String s) {
          if(s==null) return s;
          String param = null;
          
          try {
              //s = new String(s.getBytes("gb2312"),"iso-8859-1");
              param = java.net.URLEncoder.encode(s, "utf-8");
          } catch (java.io.UnsupportedEncodingException ex) {
          }
          return param;
          //return s;
      }
      
      public static String toParamString(ServletRequest request){
          String sRet = "";String tmp = "";
          HashMap map = (HashMap)request.getParameterMap();
          Iterator iter = map.keySet().iterator();
          while(iter.hasNext()){
              tmp = (String)iter.next();
              sRet += "&"+tmp+"="+encodeURL(request.getParameter(tmp));
          }
          return sRet;
      }
      
      /**
       * 将容易引起xss漏洞的半角字符直接替换成全角字符
       * @author 51auto 2013-3-13
       * @param request
       * @param paramName
       * @return
       */
      public static String getParamValusByXSSEncode(ServletRequest request,String paramName){
          String[] rs = request.getParameterValues(paramName);
          String values = "";
          if(rs!=null&&rs.length>0){
              for (int i = 0; i < rs.length; i++) {
                  values += (i==0?rs[i]:","+rs[i]);
              }
          }
          return xssEncode(values);
      }
      
      /**
       * 将容易引起xss漏洞的半角字符直接替换成全角字符
       * 
       * @param s
       * @return
       */
      public static String xssEncode(String s) {
          if (s == null || s.isEmpty()) {
              return s;
          }
          StringBuilder sb = new StringBuilder(s.length() + 16);
          for (int i = 0; i < s.length(); i++) {
              char c = s.charAt(i);
              switch (c) {
              case '>':
                  sb.append('＞');// 全角大于号
                  break;
              case '<':
                  sb.append('＜');// 全角小于号
                  break;
              case '\'':
                  sb.append('‘');// 全角单引号
                  break;
              case '\"':
                  sb.append('“');// 全角双引号
                  break;
              case '&':
                  sb.append('＆');// 全角
                  break;
              case '\\':
                  sb.append('＼');// 全角斜线
                  break;
              case '#':
                  sb.append('＃');// 全角井号
                  break;
              case '=':
                  sb.append('＝');// 全角等号
                  break;
              case '(':
                  sb.append('（');// 全角左括号
                  break;
              case ')':
                  sb.append('）');// 全角右括号
                  break;
              default:
                  sb.append(c);
                  break;
              }
          }
          return sb.toString();
      }
      
      /**
       * 得到前台复选框的值，用逗号隔开。
       * @author chunlai.yang 2012-6-30
       * @param request
       * @param paramName
       * @return
       */
      public static String getParamValus(ServletRequest request,String paramName){
          String[] rs = request.getParameterValues(paramName);
          String values = "";
          if(rs!=null&&rs.length>0){
              for (int i = 0; i < rs.length; i++) {
                  values += (i==0?rs[i]:","+rs[i]);
              }
          }
          return values;
      }
  }


