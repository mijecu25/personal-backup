package com.mijecu25.personalbackup.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mijecu25.personalbackup.visitors.FileVisitor;

/**
 * A record is an actual file in the file system. It is represented by a path.

 * @author Miguel Velez
 * 
 * @version 0.1.1.4
 */
public class Record extends Path {
	
	private static final long serialVersionUID = -4425526491071969544L;

	private static final Logger logger = LogManager.getLogger(Record.class);

	private String 	extension;

	/**
	 * Create are record from the specified {@code pathname}.
	 * 
	 * @param pathname the path to a record.
	 */
	public Record(String pathname) {
		super(pathname);
		
		this.extension = this.getFileExtension();
	}
	
	/**
	 * Accept a file visitor and call the <code>visitRecord</code> method of the visitor
	 * by passing this record object.
	 * 
	 * @param fileVisitor
	 * @return object returned after visiting this record object
	 */
	@Override
	public Object accept(FileVisitor fileVisitor) {
		return fileVisitor.visitRecord(this);
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
		if(this != object) {
			return false;
		}
			
		return true;
	}
	
	@Override
	public int hashCode() {
		// Name, path, file object
		String hash = this.getName() + this.getPath() + this.toString();
		
		return hash.hashCode();
	}

}
