package com.wolai.platform.util;

import java.util.Random;

import com.wolai.platform.entity.License;
import com.wolai.platform.entity.idEntity;

public class CommonUtils {

    public static boolean IsAvailable(Object obj)  {
    	idEntity entity = (License)obj;
        return !entity.getIsDelete() && !entity.getIsDisable();
    }
    
	public static String RandomNum(int length){
		StringBuffer psw = new StringBuffer();
		String[] all = {"0","1","2","3","4","5","6","7","8","9"};
		int max = all.length;

		for(int i= 0 ; i<length; i++){
			Random r =  new Random();
			int randomInt = r.nextInt(max);
			psw.append(all[randomInt]);
		}
		return psw.toString();
	}
}