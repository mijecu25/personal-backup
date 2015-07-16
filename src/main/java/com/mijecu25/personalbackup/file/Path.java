package com.mijecu25.personalbackup.file;

import java.io.File;
import java.util.Date;

import com.mijecu25.personalbackup.visitors.FileVisitor;

/**
 * Path class that represents an object in the file system.
 * 
 * @author Miguel Velez
 * @version 0.1.1.1
 */
public abstract class Path extends File {

	private static final long serialVersionUID = 638321617215803197L;
	
	private long length; 

	public Path(String pathname) {
		super(pathname);
		
		this.length = this.getLength();
	}
	
	/**
	 * Abstract method that takes a file visitor to perform some action.
	 * 
	 * @param fileVisitor
	 * @return object returned after visiting some object.
	 */
	abstract public Object accept(FileVisitor fileVisitor);
	
	/**
	 * Return the length of the record.
	 * 
	 * @return a long representing the length in bytes of the record.
	 */
	public long getLength() {
		return this.length;
	}
	
	/**
	 * Return the name of the record.
	 * 
	 * @return a string that represents the name of the record.
	 */
	@Override
	public String getName() {
		return super.getName();
	}
	
	/**
	 * Return the path of the record.
	 * 
	 * @return a string that represents the path of the record.
	 */
	@Override
	public String getPath() {
		return super.getPath();
	}
	
	/**
	 * Return the last modified date of the record.
	 * 
	 * @return a long representing the last modified time in milliseconds since epoch.
	 */
	public long getLastModified() { 
		return super.lastModified();
	}
	
	/**
	 * Return the last modified date of the record.
	 * 
	 * @return a date object representing the last modified time.
	 */
	public Date getLastModifiedDate() {
		return new Date(this.getLastModified());
	}
	
	/**
	 * Return the directory that contains this record.
	 * 
	 * @return directory that is the parent of this record.
	 */
	public Directory getParentDirectory() {
		return new Directory(super.getParent());
	}
	
	/**
	 * Return whether the record is a directory.
	 * 
	 * @return boolean indicating if the record is a directory.
	 */
	@Override
	public boolean isDirectory() {
		return super.isDirectory();
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
		return new Directory(this.getPath());
	}
	
	@Override
	public String toString() {
		return this.getPath();
	}

}
