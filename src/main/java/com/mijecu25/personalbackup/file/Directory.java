package com.mijecu25.personalbackup.file;

import java.io.File;

/**
 * A directory is a type of record in the file system. It might contain other directories and files.

 * @author Miguel Velez
 * 
 * @version 0.1.1.3
 * 
 */
public class Directory extends Record {
		
	/**
	 * Create a directory from the specified {@code pathname}
	 * 
	 * @param pathname the path to a directory
	 */
	public Directory(String pathname) {
		super(pathname);
	}
	
	/**
	 * Return a list of child records within this directory.
	 * 
	 * @return
	 */
	public Record[] listRecords() {
		// Get all child file records
		File[] files = this.getFile().listFiles();
		
		// Create the list where the records will be stored
		Record[] list = new Record[files.length];
		
		// Loop through all the files
		for(int i = 0; i < files.length; i++) {
			
			// Get a new record from the array
			Record hold = new Record(files[i].getPath());
						
			// If it is a directory
			if(hold.isDirectory()) {
				// Add it to the list as a directory
				list[i] = this.getAsDirectory();
			}
			else {
				// Add the record to the list
				list[i] = hold;
			}
		}
		
		// Return the list
		return list;
	}
	
}
