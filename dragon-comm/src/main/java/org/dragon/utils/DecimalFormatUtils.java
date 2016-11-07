package org.dragon.utils;

import java.text.DecimalFormat;

public class DecimalFormatUtils {
	
	public static String numToString(Number num){
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(num);
	}
	
}
