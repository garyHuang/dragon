package org.dragon.logs;

import org.apache.log4j.Logger;

public class LogManager {

	static Logger logger = Logger.getLogger(LogManager.class);

	public static void info(Object msg) {
		logger.info(msg);
	}
	public static void log(Object msg){
		logger.info(msg);
	}

	public static void err(Object msg) {
		logger.info(msg);
		if (msg == null) {
			return;
		}
		if (msg instanceof Throwable) {
			Throwable t = (Throwable) msg;
			logger.error("", t);
		}
	}
	
	public static void err(Object msg , Throwable throwable) {
		logger.error(msg, throwable); 
	}
}
