package com.mijecu25.personalbackup.visitors;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mijecu25.dsa.DataStructures.LinkedListQueue;
import com.mijecu25.personalbackup.file.Directory;
import com.mijecu25.personalbackup.file.Path;
import com.mijecu25.personalbackup.file.Record;

/**
 * Recursive file tree visitor that backs up records and directories.
 * 
 * @author Miguel Velez 
 * @version 0.1.1.4
 */
public class BackupVisitor implements Runnable, FileVisitor {
	
	private static final Logger logger = LogManager.getLogger(BackupVisitor.class);
	
	private Directory 		source;
	private Directory		destination;
	private LinkedListQueue pathsQueue;
	private List<Thread> children;
	private boolean			isDone;

	/**
	 * Create a new BackupManager.
	 */
	public BackupVisitor(String source, String destination) {
		this.source = new Directory(source);
		this.destination = new Directory(destination);
		this.pathsQueue = new LinkedListQueue();
		this.children = new ArrayList<Thread>();
		this.isDone = false;

		BackupVisitor.logger.info("Created a new BackupVisitor object");
		BackupVisitor.logger.info("Source: " + this.source);
		BackupVisitor.logger.info("Destination: " + this.destination);
		
		// Add the source directory in the queue
		this.pathsQueue.enqueue(this.source);
	}
	
	/**
	 * Execute the general logic of backing up files
	 */
	@Override
	public void run() {
		BackupVisitor.logger.info("Executing Backup Visitor main logic");
		
		// Synchronize the backup manager main logic
		synchronized(this) {
			
			// Create holder for current path
			Path currentPath;
									
			// While there are more files to process
			while(!this.pathsQueue.isEmpty()) {
		
				try {
					// dequeue the next file
					currentPath = (Path) this.pathsQueue.dequeue();
					
					// Pass this visitor to the current path
					currentPath.accept(this);
					
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	
	/**
	 * Visit a record object. Copy the record from the source to the destination.
	 * 
	 * @param record
	 * @return object that is returned after visiting a record.
	 */
	@Override
	public Object visitRecord(Record record) {
		BackupVisitor.logger.info(Thread.currentThread().getName() + " - " + record.getPath());
		if(record.getName().equals("File2.png")) {
			try {
				System.out.println("Wiating");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// We do not need to return anything
		return null;
	}

	/**
	 * Visit a directory object.
	 * 
	 * @param directory
	 * @return object that is returned after visiting a directory.
	 */
	@Override
	public Object visitDirectory(Directory directory) {
		String newDestination = "";
		
		// Get the child paths
		Path[] childPaths = directory.listPaths();
		
		// Loop through every child
		for(Path child : childPaths) {
//			if(child.equals(this.source)) {
//				continue;
//			}
				
			// If the child is a record
			if(child instanceof Record) {
				// Added to the queue to process
				this.pathsQueue.enqueue(child);		
			}
			// If the child is a directory
			else if(child instanceof Directory) {	
				// Get the new destination
				newDestination = this.getDestinationDirectory(child.getName());
				
				// Create a new backup visitor with the child's path and its destination
				BackupVisitor childVisitor = new BackupVisitor(child.getPath(), newDestination);
				
				// Add the thread to the list of children threads of this visitor
				this.children.add(childVisitor.startAsThread());
			}
			
		}	
		
		// Loop through all the threads of this visitor
		for(Thread thread : this.children) {
			try {
				// Wait until this thread dies
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// We do not need to return anything
		return null;
	}
	
	/**
	 * Get the destination directory of the source directory.
	 * 
	 * @param source directory
	 * 
	 * @return string that represents the new destination directory
	 */
	private String getDestinationDirectory(String source) {
		// The destination is the destination of this visitor + the source	
		return this.destination + "\\" + source;
	}
	
}
