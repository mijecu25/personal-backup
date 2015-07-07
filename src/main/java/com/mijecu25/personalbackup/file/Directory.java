package com.mijecu25.personalbackup.file;

/**
 * A directory is a type of record in the file system. It might contain other directories and files.

 * @author Miguel Velez
 * 
 * @version 0.1
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
	
}
