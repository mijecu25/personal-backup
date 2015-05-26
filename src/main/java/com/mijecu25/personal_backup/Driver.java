package com.mijecu25.personal_backup;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Miguel Velez
 * @version 0.1
 * 
 * This is the backup class.
 */

public class Driver {

	private static StringBuilder SOURCE = new StringBuilder("C:/"
																+ "Users/"
																+ "Miguel/"
																+ "Desktop/"
																+ "Backup/"
																+ "Documents");
	
	

	private static StringBuilder SERVER = new StringBuilder("C:/"
																+ "Users/"
																+ "Miguel/"
																+ "Desktop/"
																+ "Backup/"
																+ "Server");
	
	private static final Logger logger = LogManager.getLogger(Driver.class);
				
	public static void main(String[] args) throws IOException {
		final long startTime = System.nanoTime();
		
		
		Driver.logger.info("Start backup");  
		
		
//		StringBuilder server = new StringBuilder("//MEMELOSCLOUD/LenovoWin8");	
//		LinkedList<StringBuilder> source = new LinkedList<StringBuilder>();
//
//		source.add(new StringBuilder("C:/Users/Miguel/Documents"));
//		source.add(new StringBuilder("C:/Users/Miguel/Downloads"));
//		source.add(new StringBuilder("C:/Users/Miguel/Music"));
//		source.add(new StringBuilder("C:/Users/Miguel/Pictures"));
//		source.add(new StringBuilder("C:/Users/Miguel/Videos"));
//
//		for(int i  = 0; i < source.size();i++) {
//			Backup b = new Backup(source.get(i), server);
//			
//			b.backupAll();
//		} 
		
		
		Driver.logger.info("End backup");
				
		final long endTime = System.nanoTime();
		Driver.logger.info("Total execution time: " + (endTime - startTime)/1000000000.0 + " seconds");	
		}

}