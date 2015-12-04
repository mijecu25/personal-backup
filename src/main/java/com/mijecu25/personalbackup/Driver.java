package com.mijecu25.personalbackup;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mijecu25.personalbackup.visitors.BackupVisitor;

/**
 * This is the driver class.
 * 
 * @author Miguel Velez
 * @version 0.1.1.2
 */

public class Driver {

//	private static StringBuilder SOURCE = new StringBuilder("C:/"
//																+ "Users/"
//																+ "Miguel/"
//																+ "Desktop/"
//																+ "Backup/"
//																+ "Documents");
//	
//	
//
//	private static StringBuilder SERVER = new StringBuilder("C:/"
//																+ "Users/"
//																+ "Miguel/"
//																+ "Desktop/"
//																+ "Backup/"
//																+ "Server");
	
	private static final Logger logger = LogManager.getLogger(Driver.class);
				
	public static void main(String[] args) throws IOException {
		final long startTime = System.nanoTime();
		
		
		Driver.logger.info("Start backup");  
		
		StringBuilder source = new StringBuilder("TODO"); // TODO
		StringBuilder destination = new StringBuilder("TODO"); // TODO
				
		BackupVisitor backupVisitor = new BackupVisitor(source.toString(), destination.toString());

		synchronized (backupVisitor) {
			backupVisitor.startAsThread();
			
			try {
				backupVisitor.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
						
		Driver.logger.info("End backup");
				
		final long endTime = System.nanoTime();
		
		Driver.logger.info("Total execution time: " + (endTime - startTime)/1000000000.0 + " seconds");	
	}

}