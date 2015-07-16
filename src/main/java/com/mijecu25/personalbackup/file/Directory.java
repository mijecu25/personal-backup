package com.mijecu25.personalbackup.file;

import com.mijecu25.personalbackup.visitors.FileVisitor;

//import java.io.File;

/**
 * A directory is a folder in the file system. It might contain other directories
 * and records. It is represented by a path.

 * @author Miguel Velez
 * 
 * @version 0.1.1.4
 * 
 */
public class Directory extends Path {
		
	private static final long serialVersionUID = -8247494653909338083L;

	/**
	 * Create a directory from the specified {@code pathname}
	 * 
	 * @param pathname the path to a directory
	 */
	public Directory(String pathname) {
		super(pathname);
	}

	/**
	 * Accept a file visitor and call the <code>visitDirectory</code> method of the visitor
	 * by passing this directory object.
	 * 
	 * @param fileVisitor
	 * @return object returned after visiting this directory object
	 */
	@Override
	public Object accept(FileVisitor fileVisitor) {
		return fileVisitor.visitDirectory(this);
	}
	
//	/**
//	 * Return a list of child records within this directory.
//	 * 
//	 * @return
//	 */
//	public Record[] listRecords() {
//		// Get all child file records
//		File[] files = this.listFiles();
//		
//		// Create the list where the records will be stored
//		Record[] list = new Record[files.length];
//		
//		// Loop through all the files
//		for(int i = 0; i < files.length; i++) {
//			
//			// Get a new record from the array
//			Record hold = new Record(files[i].getPath());
//						
//			// If it is a directory
//			if(hold.isDirectory()) {
//				// Add it to the list as a directory
//				list[i] = this.getAsDirectory();
//			}
//			else {
//				// Add the record to the list
//				list[i] = hold;
//			}
//		}
//		
//		// Return the list
//		return list;
//	}
	
}
