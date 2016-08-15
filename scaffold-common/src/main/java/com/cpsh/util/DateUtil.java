package com.cpsh.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {
private static final SimpleDateFormat DATE_FORMATTER=new SimpleDateFormat();
    
    /**
     * 判断date1和date2是否相同
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isDateDiff(Date date1,Date date2){
        if(date1==null){
            if(date2==null){
                return false;
            }
            return true;
        }else{
            return !date1.equals(date2);
        }
    }
    
    /*
     * txq:  得到前 days+1 天的日期
     */
    public static String getDayInDays(int days){
        StringBuffer result = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,days);
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = ((cal.get(Calendar.MONTH)+1) < 10) ? ("0"+(cal.get(Calendar.MONTH)+1)) : String.valueOf(cal.get(Calendar.MONTH)+1);
        String day = (cal.get(Calendar.DAY_OF_MONTH) < 10) ? ("0"+cal.get(Calendar.DAY_OF_MONTH)) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        result.append(year).append("-").append(month).append("-").append(day);
        return result.toString();
    }
    /**
     * wuzhengqiang 2012-10
     * 此方法用于给定开始时间和结束时间,返回间隔时间中的每一天,以逗号分割!
     * @return 比如开始为:2012-02-25,结束为:2012-03-05,返回的结果就是:2012-02-25,2012-02-26,2012-02-27,2012-02-28,2012-02-29,2012-03-01,2012-03-02,2012-03-03,2012-03-04,2012-03-05
     */
    public static String getBetweenDaysStr(String startDateStr,String endDateStr){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String resultDayStr = "";
        try {
            Date startDate = format.parse(startDateStr);
            Date  endDate= format.parse(endDateStr);//先把开始日期和结束日期字符串转为date
            double between = (endDate.getTime()-startDate.getTime())/1000;//date转为time除以1000表示返回相差的毫秒数
            double  day = between/(24*3600);//每天24小时,一小时等于3600秒，所以相差的毫秒数除以每天的毫秒数
            Date dateStr = startDate;
            resultDayStr = format.format(startDate)+",";//把开始的那一天也加进去
            for (int i = 0; i <day ; i++) {
                 dateStr = addDay(dateStr,1);
                 resultDayStr += format.format(dateStr)+",";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDayStr;
        
    }
    /**wuzhengqiang2012-10
     * 取得两个时间段的合同投放天数,这是两个时间间隔加1天
     * @throws ParseException 
     */ 
    public static int getBetweenDays(String startDateStr,String endDateStr) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(startDateStr);
        Date  endDate= formatter.parse(endDateStr);
        double between = ((endDate.getTime()-startDate.getTime())/1000);
        int  day = (int)between/(24*3600)+1;
        return day;
    }
    /**wuzhengqiang2013-01
     * 取得合同的两个时间段的月数，需求是:不是真正的间隔月数，而是占了几个月的日子,比如:2012-01-01至2012-01-01也是占的一个月的日子,2012-01-10至2012-02-09要占用两个月
     * @throws ParseException 
     */ 
    public static int getBetweenMonths(String startDateStr,String endDateStr) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        c1.setTime(formatter.parse(startDateStr));
        Calendar c2 = Calendar.getInstance();
        c2.setTime(formatter.parse(endDateStr));
        int mouthCount = 0;
         while (!c1.after(c2)) { 
             c1.add(Calendar.MONTH, 1);
             mouthCount++;
             if(c2.get(Calendar.DAY_OF_MONTH)<c1.get(Calendar.DAY_OF_MONTH) && mouthCount<=2 ){
                 mouthCount++;//如果结束时间的天比开始时间的天小,并且总共月数小于两月的,月数要加1
             }
         }
        return mouthCount;
    }

    
    public static String getDayIn3Days(){
        return getDayInDays(-2);
    }
    
    public static String getDayIn2Days(){
        return getDayInDays(-1);
    }
    
    
    public static String getDayInWeek(){
        StringBuffer result = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,-6);
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = ((cal.get(Calendar.MONTH)+1) < 10) ? ("0"+(cal.get(Calendar.MONTH)+1)) : String.valueOf(cal.get(Calendar.MONTH)+1);
        String day = (cal.get(Calendar.DAY_OF_MONTH) < 10) ? ("0"+cal.get(Calendar.DAY_OF_MONTH)) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        result.append(year).append("-").append(month).append("-").append(day);
        return result.toString();
    }
    
    public static String getDayInMonth(){
        StringBuffer result = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.add(Calendar.DAY_OF_MONTH,1);
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = ((cal.get(Calendar.MONTH)+1) < 10) ? ("0"+(cal.get(Calendar.MONTH)+1)) : String.valueOf(cal.get(Calendar.MONTH)+1);
        String day = (cal.get(Calendar.DAY_OF_MONTH) < 10) ? ("0"+cal.get(Calendar.DAY_OF_MONTH)) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        result.append(year).append("-").append(month).append("-").append(day);
        return result.toString();
    }
    
    public static String getCurrentDay(){
        StringBuffer result = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = ((cal.get(Calendar.MONTH)+1) < 10) ? ("0"+(cal.get(Calendar.MONTH)+1)) : String.valueOf(cal.get(Calendar.MONTH)+1);
        String day = (cal.get(Calendar.DAY_OF_MONTH) < 10) ? ("0"+cal.get(Calendar.DAY_OF_MONTH)) : String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        result.append(year).append("-").append(month).append("-").append(day);
        return result.toString();
    }
    
    public static Date currentDate(){
        Calendar cal = Calendar.getInstance();
        return new Date(cal.getTimeInMillis());
    }
    
    public static Date getDate(Date date,int interval){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.add(Calendar.DAY_OF_MONTH,interval);
        return new Date(cal.getTimeInMillis());
    }
    
    public static int getCurrentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    public static int getCurrentMonth(){
        return Calendar.getInstance().get(Calendar.MONTH)+1;
    }
    public static int getToday(){
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
    public static int getYear(Calendar cal){    
        return cal.get(Calendar.YEAR);
    }
    public static int getMonth(Calendar cal){
        return cal.get(Calendar.MONTH)+1;
    }
    public static int getDay(Calendar cal){
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 取得两个时间段的时间间隔 return t2 与t1的间隔天数
     */ 
    public static int getBetweenDays(Calendar c1, Calendar c2){
        int betweenDays = 0;
        // 保证第二个时间一定大于第一个时间
        if (c1.after(c2)) {
            Calendar tmp = c1;
            c1 = c2;
            c2 = tmp;
        }
        int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        betweenDays = c2.get(Calendar.DAY_OF_YEAR)
                - c1.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears; i++) {
            c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
            betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
        }
        return betweenDays;
    } 
    
    public static String getFormattedDateString(java.util.Date date){
        return getFormattedDateString(date,"yyyy-MM-dd HH:mm:ss");
    }
    
    public static String getFormattedDateString(java.util.Date date,String pattern){
        DATE_FORMATTER.applyPattern(pattern);
        return DATE_FORMATTER.format(date);
    }
    
    //add by liuxb start
    /**
     * 得到两个日期秒级差
     * 
     * @param date0
     * @param date1
     * @return
     */
    public static long getSecondDif(Date date0, Date date1) {
        return DateUtil.getMillisecondDif(date0, date1) / 1000;
    }

    /**
     * 得到两个日期分钟差
     * 
     * @param date0
     * @param date1
     * @return
     */
    public static long getMinuteDif(Date date0, Date date1) {
        return DateUtil.getSecondDif(date0, date1) / 60;
    }

    /**
     * 得到两个日期小时差
     * 
     * @param date0
     * @param date1
     * @return
     */
    public static int getHourDif(Date date0, Date date1) {
        return (int) DateUtil.getMinuteDif(date0, date1) / 60;
    }

    /**
     * 得到两个日期天数差
     * 
     * @param date0
     * @param date1
     * @return
     */
    public static int getDayDif(Date date0, Date date1) {
        return (int) DateUtil.getHourDif(date0, date1) / 24;
    }
    
    /**
     * 日期增加秒
     * 
     * @param date0
     * @param date1
     * @return
     */
    public static Date addSecond(Date date, int iSecond) {
        return addDate(date, Calendar.SECOND, iSecond);
    }

    /**
     * 日期增加分钟
     * 
     * @param date0
     * @param date1
     * @return
     */
    public static Date addMinute(Date date, int iMinute) {
        return addDate(date, Calendar.MINUTE, iMinute);
    }

    /**
     * 日期增加小时
     * 
     * @param date0
     * @param date1
     * @return
     */
    public static Date addHour(Date date, int iHour) {
        return addDate(date, Calendar.HOUR, iHour);
    }

    /**
     * 日期增加天数
     * 
     * @param date0
     * @param date1
     * @return
     */
    public static Date addDay(Date date, int iDate) {
        return addDate(date, Calendar.DAY_OF_MONTH, iDate);
    }

    /**
     * 日期增加月
     * 
     * @param date0
     * @param date1
     * @return
     */
    public static Date addMonth(Date date, int iMonth) {
        return addDate(date, Calendar.MONTH, iMonth);
    }

    /**
     * 日期增加年
     * 
     * @param date0
     * @param date1
     * @return
     */
    public static Date addYear(Date date, int iYear) {
        return addDate(date, Calendar.YEAR, iYear);
    }
    
    /**
     * 得到两个日期毫秒级差
     * 
     * @param date0
     * @param date1
     * @return
     */
    public static long getMillisecondDif(Date date0, Date date1) {
        if (date0 == null || date1 == null) {
            return 0;
        }
        return date0.getTime() - date1.getTime();
    }
    
    private static Date addDate(Date date, int iArg0, int iDate) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTime(date);
        canlendar.add(iArg0, iDate);//根据日历的规则，为给定的日历字段添加或减去指定的时间量
        return canlendar.getTime();
    }
    /**
     * 字符串转日期
     * @param sDate
     * @return
     */
    public static Date parseDate(String sDate){
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return simpleDateFormate.parse(sDate);
        } catch (ParseException e) {
            //return null;
        }

        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
        } catch (ParseException e) {
            return null;
        }

    }
    
    /**
     * 字符串转日期
     * @param sDate
     * @param formate
     * @return
     */
    public static Date parseDate(String sDate, String formate) {
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat(formate);
        try {
            return simpleDateFormate.parse(sDate);
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static String getDateFormate(Date date, String formate) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat(formate);
        return simpleDateFormate.format(date);
    }

    public static String get4yMdHmsS(Date date) {
        return getDateFormate(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static String get4yMdHms(Date date) {
        return getDateFormate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String get4yMdHm(Date date) {
        return getDateFormate(date, "yyyy-MM-dd HH:mm");
    }

    public static String get4yMd(Date date) {
        return getDateFormate(date, "yyyy-MM-dd");
    }
    public static String get4yHh(Date date) {
        return getDateFormate(date, "hh:mm:ss");
    }

    public static Date parseDateFullYear(String sDate) {
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormate.parse(sDate);
        } catch (ParseException e) {
            return null;
        }
    }
    //add by liuxb end
/**
 * 根据传进来的时间数组(类似2013-10-17,2013-10-31;2013-10-23,2013-12-01;2013-10-01,2013-12-01),判断去除重复之后，返回总共多少天.
 * Author:Andy Wu.2013-10-23
 * 注意:传进来的参数格式必须以上面的例子为准分割日期
 */
    public static int getBetweenDays(String dateStr) throws ParseException{
        String[] dateArr = dateStr.split(";");
         TreeSet dateSet = new TreeSet();
        for(int i=0;dateArr!=null&&i<dateArr.length;i++){
            String dateString = dateArr[i];
            String[] singleDateStr = dateString.split(",");
            String days = getBetweenDaysStr(singleDateStr[0],singleDateStr[1]);//每个时间段内的所有天数
            String[] daysArray = days.split(",");
            if(daysArray != null && daysArray.length>0){
                for(String daysStr:daysArray){
                    dateSet.add(daysStr);
                }
            }
        }
        int betweenDays = 0;
        if(dateSet!=null && dateSet.size()>0){
            betweenDays = dateSet.size();
        }
        return betweenDays;
    }
    /**
     *根据传进来的日期，返回到现在为止，除开周末还剩余多少小时有效期
     * @param statusCalendar  开始计算时间
     *  @param countDay   累加天数
     * @return  有效的小时数
     */
    public static long getRemainHours(Calendar statusCalendar,int countDay){
        long remainHours = 0;
        
         int j=0;
         while(j<countDay){
             int day = statusCalendar.get(Calendar.DAY_OF_WEEK);//一周当中的星期几,国际标准是星期日是第一天.周六是第七天.
             if(day == Calendar.SUNDAY || day == Calendar.SATURDAY){//看这两个的常量就知道了
                 statusCalendar.add(Calendar.DATE, 1);//周末的时候，天数加1,但是计算时间不增加.
             }else{
                 statusCalendar.add(Calendar.DATE, 1);//不等于周末的才加1天.
                 j++;
             }
         }
         SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar nowCar = Calendar.getInstance();
        remainHours = (statusCalendar.getTimeInMillis()-nowCar.getTimeInMillis())/(60 * 60 * 1000);
        return remainHours;
    }
    
    /**
     * 根据传进来的毫秒数取时间    （小时：分钟：秒钟）
     * ps：当时间超过24小时  会叠加 如： 48:12:12
     * @param _T ：毫秒数
     * @return ： 如  48:12:12 格式
     */
    public static String getHoursByMS(long _T){
        if(_T>0){
            long days = _T/(1000*60*60*24);
            long hours = (_T/(1000*60*60))%24;
            long mins = (_T/(1000*60))%60;
            long seconds = (_T/(1000))%60;
            return days*24+hours+":"+mins+":"+seconds;
        }
        return "00:00:00";
    }
    
    
      /** 
         * 两个时间相差距离多少天多少小时多少分 
          * @param str1 时间参数 1 格式：1990-01-01 12:00:00 
          * @param str2 时间参数 2 格式：2009-01-01 12:00:00 
          * @return String 返回值为：xx天xx小时xx分 
          */  
     public static String getDistanceTime(String str1, String str2) {  
         if(StringUtils.isBlank(str1) || StringUtils.isBlank(str2)){
             return "0天0小时0分"  ;  
         }
         
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
              Date one;  
           Date two;  
             long day = 0;  
             long hour = 0;  
         long min = 0;  
       // long sec = 0;  
            try {  
                one = df.parse(str1);  
                two = df.parse(str2);  
                long time1 = one.getTime();  
                 long time2 = two.getTime();  
            long diff ;  
                if(time1<time2) {  
                     diff = time2 - time1;  
                } else {  
                     diff = time1 - time2;  
                }  
                day = diff / (24 * 60 * 60 * 1000);  
                hour = (diff / (60 * 60 * 1000) - day * 24);  
                 min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
                 //sec = (diff/1000-day*24*60*60-hour*60*60-min*60);  
             } catch (ParseException e) {  
                 e.printStackTrace();  
             }  
            return day + "天" + hour + "小时" + min + "分"  ;  
       }

    
    public static void main(String[] args) throws ParseException {
        int days = getBetweenDays("2013-10-01,2013-10-10;2013-10-12,2013-10-15");
        System.out.println(days);
    }
}
