package com.mijecu25.personalbackup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mijecu25.dsa.DataStructures.LinkedListQueue;
import com.mijecu25.personalbackup.file.Directory;
import com.mijecu25.personalbackup.file.Record;

/**
 * @author Miguel Velez
 * 
 * @version 0.1.1.2
 */
public class BackupManager implements Runnable {
	
	private static final Logger logger = LogManager.getLogger(BackupManager.class);
	
	private Directory 		source;
	private Directory		destination;
	private LinkedListQueue filesQueue;
	private boolean			isDone;

	/**
	 * Create a new BackupManager.
	 */
	public BackupManager(String source, String destination) {
		this.source = new Directory(source);
		this.destination = new Directory(destination);
		this.filesQueue = new LinkedListQueue();
		this.isDone = false;

		BackupManager.logger.info("Created a new BackupManager object");
		BackupManager.logger.info("Source: " + this.source);
		BackupManager.logger.info("Destination: " + this.destination);
		
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
		BackupManager.logger.info("Executing backup manager main logic");
		
		// Synchronize the backup manager main logic
		synchronized(this) {
			
			// Create holders for records and directories
			Record currentRecord;
			Directory currentDirectory;
			
			// While there are more files to process
			while(!this.filesQueue.isEmpty()) {
		
					// dequeue the next file
					currentRecord = (Record) this.filesQueue.dequeue();
										
					// If the current record is a directory
					if(currentRecord.isDirectory()) {
						// Get the current directory
						currentDirectory = currentRecord.getAsDirectory();
						
						Record[] childRecords = currentDirectory.listRecords();
						
						for(Record child : childRecords) {
							this.filesQueue.enqueue(child);
						}
					}
					
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
	
}
