package org.dragon.utils;

import java.security.SecureRandom;

public class RandomUtils {
	
	
	public static Integer getInt(){
		return new SecureRandom().nextInt();
	}
}
