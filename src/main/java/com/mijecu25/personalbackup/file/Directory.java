package com.mijecu25.personalbackup.file;

/**
 * @author Miguel Velez
 * 
 * @version 0.1
 * 
 * A directory is a type of record in the file system. It might contain other directories and files.
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
