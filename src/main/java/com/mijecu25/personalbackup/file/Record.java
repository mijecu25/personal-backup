package com.mijecu25.personalbackup.file;

import java.io.File;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A record is an object in the file system. It could either be a file or a directory.

 * @author Miguel Velez
 * 
 * @version 0.1.1.3
 */
public class Record {
	
	private static final Logger logger = LogManager.getLogger(Record.class);
	
	private File 	record;
//	private long 	created;
	private long 	lastModified;
	private String 	extension;
	private long 	length; 

	/**
	 * Create are record from the specified {@code pathname}.
	 * 
	 * @param pathname the path to a record.
	 */
	public Record(String pathname) {
		this.record = new File(pathname);
//		this.created = 
		this.lastModified = this.record.lastModified();
		this.extension = this.getFileExtension();
	}
	
	/**
	 * Return the name of the record.
	 * 
	 * @return a string that represents the name of the record.
	 */
	public String getName() {
		return this.record.getName();
	}
	
	/**
	 * Return the path of the record.
	 * 
	 * @return a string that represents the path of the record.
	 */
	public String getPath() {
		return this.record.getPath();
	}
	
	/**
	 * Return a file object that represents this record.
	 * 
	 * @return file object of this record.
	 */
	public File getFile() {
		return this.record;
	}
	
//	public long getCreated() {
//		return this.created;
//	}
	
	/**
	 * Return the last modified date of the record.
	 * 
	 * @return a long representing the last modified time in milliseconds since epoch.
	 */
	public long getLastModified() {
		return this.lastModified;
	}
	
	/**
	 * Return the last modified date of the record.
	 * 
	 * @return a date object representing the last modified time.
	 */
	public Date getLastModifiedDate() {
		return new Date(this.lastModified);
	}
	
	/**
	 * Return the file extension of the record.
	 * 
	 * @return a string representing the file extension of the record.
	 */
	public String getExtension() {
		return this.extension;
	}
	
	/**
	 * Return the directory that contains this record.
	 * 
	 * @return directory that is the parent of this record.
	 */
	public Directory getParent() {
		return new Directory(record.getParent());
	}
	
	/**
	 * Return whether the record is a directory.
	 * 
	 * @return boolean indicating if the record is a directory.
	 */
	public boolean isDirectory() {
		return this.record.isDirectory();
	}
	
	/**
	 * Return the length of the record.
	 * 
	 * @return a long representing the length in bytes of the record.
	 */
	public long getLength() {
		return this.length;
	}
	
//	public long getCreated() {		 
//	        System.out.println("creationTime     = " + attr.creationTime());
//	}
	
	/**
	 * Return the file extension of the record. If the record represents a directory,
	 * the empty string is returned.
	 * 
	 * @return the file extension of the record.
	 */
	private String getFileExtension() {
		// Check if the record is a directory
		if(this.isDirectory()) {
			Record.logger.warn("Tried getting the extension of directory: " + this);
			
			// Return an empty string
			return "";
		}
		
		try {
			// Get the path of the record
			String path = this.getPath();
			
			// Get the position of the last period. This should be where the file extension begins
			int dotPosition = path.lastIndexOf(".");
			
			// Return the file extension
			return path.substring(dotPosition);
		}
		catch(IndexOutOfBoundsException iobe) {
			Record.logger.warn("This file does not have a file extension: " + this);
			
			// Weird that the file does not have a file extension. Just return an empty string
			return "";
		}
	}
	
	/**
	 * Return this record as a directory object. The record must be a directory in
	 * order to be returned as one.
	 * 
	 * @return a reference to a directory object.
	 */
	public Directory getAsDirectory() {
		// If the record is not a directory
		if(!this.isDirectory()) {
			throw new ClassCastException("The record: " + this + " is not a directory");
		}
		
		// Create and return a new directory
		return new Directory(this.record.getPath());
	}
	
	@Override
	public String toString() {
		return this.record.getPath();
	}
	
	@Override
	public boolean equals(Object other) {
		// If this is the same object
		if(this == other) {
			return true;
		}
		
		// If the other object is null or the classes are different
		if(other == null || (this.getClass() != other.getClass())) {
			return false;
		}

		// Cast and create a record object 
		Record object = (Record) other;
		
		// Check if files are different
		if(this.record != object.getFile()) {
			return false;
		}
			
		return true;
	}
	
	@Override
	public int hashCode() {
		// Name, path, file object
		String hash = this.getName() + this.getPath() + this.record.toString();
		
		return hash.hashCode();
	}

}
