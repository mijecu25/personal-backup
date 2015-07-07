package com.mijecu25.personalbackup.file;

import java.io.File;

/**
 * A record is an object in the file system. It could either be a file or a directory.

 * @author Miguel Velez
 * 
 * @version 0.1
 */
public class Record {
	
	private File directory;

	/**
	 * Create are record from the specified {@code pathname}.
	 * 
	 * @param pathname the path to a record.
	 */
	public Record(String pathname) {
		this.directory = new File(pathname);
	}
	
	/**
	 * Return the name of the record.
	 * 
	 * @return a string that represents the name of the record.
	 */
	public String getName() {
		return this.directory.getName();
	}
	
	/**
	 * Return the path of the record.
	 * 
	 * @return a string that represents the path of the record
	 */
	public String getPath() {
		return this.directory.getPath();
	}
	
	@Override
	public String toString() {
		return this.directory.getPath();
	}

}
