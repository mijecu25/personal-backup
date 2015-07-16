package com.mijecu25.personalbackup.visitors;

import java.util.spi.CurrencyNameProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mijecu25.dsa.DataStructures.LinkedListQueue;
import com.mijecu25.personalbackup.file.Directory;
import com.mijecu25.personalbackup.file.Path;
import com.mijecu25.personalbackup.file.Record;

/**
 * @author Miguel Velez
 * 
 * @version 0.1.1.2
 */
public class BackupVisitor implements Runnable, FileVisitor {
	
	private static final Logger logger = LogManager.getLogger(BackupVisitor.class);
	
	private Directory 		source;
	private Directory		destination;
	private LinkedListQueue filesQueue;
	private boolean			isDone;

	/**
	 * Create a new BackupManager.
	 */
	public BackupVisitor(String source, String destination) {
		this.source = new Directory(source);
		this.destination = new Directory(destination);
		this.filesQueue = new LinkedListQueue();
		this.isDone = false;

		BackupVisitor.logger.info("Created a new BackupManager object");
		BackupVisitor.logger.info("Source: " + this.source);
		BackupVisitor.logger.info("Destination: " + this.destination);
		
		// Add the source directory in the queue
		this.filesQueue.enqueue(this.source);
	}
	
	/**
	 * When an object implementing interface Runnable is used to create a 
	 * thread, starting the thread causes the object's run method to be called 
	 * in that separately executing thread. 
	 * <br>
	 * <br>
	 * The general contract of the method run is that it may take any action whatsoever.
	 * <br>
	 * <br>
	 * Execute the general logic of backing up files
	 */
	@Override
	public void run() {
		BackupVisitor.logger.info("Executing backup manager main logic");
		
		// Synchronize the backup manager main logic
		synchronized(this) {
			
			// Create holders for records and directories
			Path currentRecord;
			Directory currentDirectory;
			
			// While there are more files to process
			while(!this.filesQueue.isEmpty()) {
		
					// dequeue the next file
					currentRecord = (Path) this.filesQueue.dequeue();
					
					currentRecord.accept(this);
										
//					// If the current record is a directory
//					if(currentRecord.isDirectory()) {
//						// Get the current directory
//						currentDirectory = currentRecord.getAsDirectory();
//						
//						Record[] childRecords = (Record[]) currentDirectory.listFiles();
//						
//						for(Record child : childRecords) {
//							this.filesQueue.enqueue(child);
//						}
//					}
//					else {
//						BackupManager.logger.info("This is where we should copy a file");
//					}
//					
			}
		
			// Notify a thread waiting for the backup manager that it can continue
			this.notify();			
			
			// The backup manager is done executing
			this.isDone = true;
		}
					
	}
	
	/** 
	 * Starts a new thread and returns a reference to it.
	 * 
	 * @return a reference to this thread.
	 */
	public Thread startAsThread() {
		// Create a new thread of this class
		Thread thread;
		
		thread = new Thread(this);
		
		// Start the thread
		thread.start();
		
		// Return reference to thread
		return thread;
	}
	
	/**
	 * Return a value indicating the state of the backup manager
	 * 
	 * @return a boolean value indicating if the backup manager is done processing
	 */
	public boolean isDone() {
		return this.isDone;
	}

	@Override
	public Object visitRecord(Record record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitDirectory(Directory directory) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
