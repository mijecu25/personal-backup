package com.mijecu25.personal_backup;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//TODO use jFIlechooser.


//Distinguish between folder and files
//how to know if we have to delete a file.
//Make sure that the modified date is the same when copying the file.
//Get the number of files.
//Check if a file in the server is no longer in the original, have to delete.

/**
 * @author Miguel Velez
 * @version 0.4.1
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
		final long startTime = System.currentTimeMillis();
		
		
//		Driver.logger.info("Start backup");  
		
		
		StringBuilder server = new StringBuilder("//MEMELOSCLOUD/LenovoWin8");	
		LinkedList<StringBuilder> source = new LinkedList<StringBuilder>();

		source.add(new StringBuilder("C:/Users/Miguel/Documents"));
		source.add(new StringBuilder("C:/Users/Miguel/Downloads"));
		source.add(new StringBuilder("C:/Users/Miguel/Music"));
		source.add(new StringBuilder("C:/Users/Miguel/Pictures"));
		source.add(new StringBuilder("C:/Users/Miguel/Videos"));

		for(int i  = 0; i < source.size();i++) {
			Backup b = new Backup(source.get(i), server);
			
			b.backupAll();
		} 
		
		//Driver.test();
		
//		Driver.logger.info("End backup");
				
		final long endTime = System.currentTimeMillis();
//		Driver.logger.info("Total execution time: " + (endTime - startTime)/1000.0 + " seconds");		
		}
	
	public static void test() throws IOException {
		Backup b = new Backup(SOURCE, SERVER);
		
		b.backupAll();
			
		/*File f2 = new File(SERVER.append("/Users/Miguel/Desktop/Backup/Documents/File2").toString());
		
		Driver.logger.debug(f2.exists() + "");
		
		
		
		FileUtils.deleteDirectory(f2);
		FileUtils.deleteQuietly(f2);
		
		try {
			FileUtils.forceDelete(f2);
		}
		catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}*/
	}
}