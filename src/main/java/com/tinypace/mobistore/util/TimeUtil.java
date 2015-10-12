package com.tinypace.mobistore.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	
	/** 
     * 得到几天前的时间 
     *  
     * @param d 
     * @param day 
     * @return 
     */  
    public static Date getDateBefore(Date d, int day) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(d);  
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - day);
        return cal.getTime();  
    }
    
    /** 
     * 得到几天后的时间 
     *  
     * @param d 
     * @param day
     * @return 
     */  
    public static Date getDateAfter(Date d, int day) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(d);  
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + day);  
        return cal.getTime();  
    }


}
