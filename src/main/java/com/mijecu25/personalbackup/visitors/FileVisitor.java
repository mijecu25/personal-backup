package com.mijecu25.personalbackup.visitors;

import com.mijecu25.personalbackup.file.Directory;
import com.mijecu25.personalbackup.file.Record;

/**
 * A visitor of files. This class implements part of the "visitor" design pattern for 
 * <code>Path</code> objects. The function in the visitor is called depending on the
 * run-time type of the object being visited. Calling visitor methods returns some 
 * value, the type of which depends on the semantics of the visitor in question. In 
 * general, you will create a visitor object, and then pass it to the 
 * <code>Path.accept()</code> method of the object in question.
 * 
 * @author Miguel Velez
 * @version 0.1.1.1
 */
public interface FileVisitor {
	
	/**
	 * Visit a record object.
	 * 
	 * @param record
	 * @return object that is returned after visiting a record.
	 */
	public Object visitRecord(Record record);
	
	/**
	 * Visit a directory object.
	 * 
	 * @param directory
	 * @return object that is returned after visiting a directory.
	 */
	public Object visitDirectory(Directory directory);

}
