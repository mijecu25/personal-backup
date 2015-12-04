package com.mijecu25.personalbackup.visitors;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import com.mijecu25.dsa.DataStructures.LinkedListQueue;
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
//	private LinkedListQueue pathsQueue;
	private List<Thread> children;
	private boolean			isDone;

	/**
	 * Create a new BackupManager.
	 */
	public BackupVisitor(String source, String destination) {
		this.source = new Directory(source);
		this.destination = new Directory(destination);
//		this.pathsQueue = new LinkedListQueue();
		this.children = new ArrayList<Thread>();
		this.isDone = false;

		BackupVisitor.logger.info("Created a new BackupVisitor object");
		BackupVisitor.logger.info("Source: " + this.source);
		BackupVisitor.logger.info("Destination: " + this.destination);
		
		// Add the source directory in the queue
//		this.pathsQueue.enqueue(this.source);
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
//			while(!this.pathsQueue.isEmpty()) {
//		
//				try {
//					// dequeue the next file
//					currentPath = (Path) this.pathsQueue.dequeue();
//					
//					// Pass this visitor to the current path
//					currentPath.accept(this);
//					
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//															
//			}
					
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
//				this.pathsQueue.enqueue(child);		
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

/*
package com.mijecu25.personalbackup;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//import org.apache.commons.io.FileUtils;
//import org.apache.log4j.Logger;

public class Backup {
	
//	private static final Logger logger = LoggerFactory.getLogger(Backup.class); 
//	private static final Logger logger = Logger.getLogger(Backup.class); 
	
	private StringBuilder 	src;
	private StringBuilder 	dest;
	private int				writes;
	private int				copies;
	private int				count;
	private int				deletes;
	
	private File trash =  new File("//MEMELOSCLOUD/LenovoWin8/Trash");
	
	public Backup(StringBuilder source, StringBuilder destination) {
		this.src = source;
		this.dest = destination;
		this.copies = 0;
		this.writes = 0;
		this.count = 0;
		this.deletes = 0;
	}
	
	/** This method creates the source folder and calls the work 
	 * method to initialize the backup process. Displays resulting
	 * information.
	 * 
	public void backupAll() throws IOException {
		File source;
		
//		Backup.logger.info("Working with this folder: " + this.getSrc());
		
		source = new File(this.getSrc().toString());
										
		this.work(source);
		
//		Backup.logger.info("Number of files processed: " + this.getCount());
//		Backup.logger.info("Copies: " + this.copies);
//		Backup.logger.info("Writes: " + this.writes);
//		Backup.logger.info("Deletes: " + this.deletes);
//		Backup.logger.info("");
	}
	
	/** This method puts the source folder in a queue. Then it removes an element.
	 * If the element is a folder, it lists all of if files and puts them back in
	 * the queue. Otherwise, it calls a function to create the path of the file.
	 * Then it writes the file if it is not in the destination folder. It also 
	 * writes the file if there is a new version of the file.
	 * 
	private void work(File source) throws IOException {
		Queue<File> 		folders = new LinkedList<File>();
		File				tmpFile, writeFile, serverFolder;
		File[] 				tmpFolders, serverFiles;
				
		folders.add(source);
				
		while(folders.size() != 0) {
			tmpFile = folders.remove();
						
			if(tmpFile.isDirectory()) {
				tmpFolders = tmpFile.listFiles();
				serverFolder = new File(this.buildServerPath(tmpFile).toString());
				serverFiles = serverFolder.listFiles();
				
				if(serverFiles != null) {
					this.checkDeleteFromServer(tmpFolders, serverFiles);
				}
								
				if(tmpFolders != null) {
					for(int i=0; i < tmpFolders.length; i++) {
						folders.add(tmpFolders[i]);
					}
				}
								
			}
			else {
				writeFile = new File(this.buildServerPath(tmpFile).toString());
								
				if(!writeFile.exists()) {
					this.write(tmpFile, writeFile);
					this.setWrites(this.getWrites() + 1);
				}
				else if (tmpFile.lastModified() > writeFile.lastModified()){
					this.write(tmpFile, writeFile);
					this.setCopies(this.getCopies() + 1);
				}
				
				this.setCount(this.getCount() + 1);
				
				if((this.getCount() %  100) == 0) {
//					Backup.logger.info("I have done " + this.getCount() + " operations");
//					Backup.logger.info("Size of queue: " + folders.size());
				}	
			}
		}
	}
	
	
	public StringBuilder getSrc() {	return this.src; }

	public StringBuilder getDest() { return dest; }
	
	public int getCount() {	return this.count; }
	
	public int getCopies() { return this.copies; }
	
	public int getWrites() { return this.writes; }
	
	public int getDeletes() { return this.deletes; }
	

	private void setDeletes(int d) { this.deletes = d; }
	
	private void setCount(int c) { this.count = c; }
	
	private void setCopies(int c) { this.copies = c; }
	
	private void setWrites(int w) { this.writes = w; }
	
	/** This method copies the source file in the destination folder.
	 * 
	private void write(File src, File dest) throws IOException {
//		try {
////			FileUtils.copyFile(src, dest, true);
//		} 
//		catch (IOException e) {
//			
//			throw new IOException(e.getMessage());
//		}
	}
	
	/** This method deletes the folder or file in the destination.
	//TODO check if folder is empty. If it is, delete.
	private void deleteFromServer(File delete) throws IOException {
//		try {
//			//FileUtils.forceDelete(delete);
//			//if(delete.isFile()) {
//				FileUtils.moveToDirectory(delete, this.trash, false);
//			//}
//		} 
//		catch (IOException e) {
//			try {
//				FileUtils.forceDelete(delete);
//			}
//			catch(IOException e2) {
//				throw new IOException(e2.getMessage());			
//			}
//			
//		}		
	}
	
	/** This method builds the path of the file in the server. It can be
	 * used to for files and folders.
	 * 
	private StringBuilder buildServerPath(File tmpFile) {
		StringBuilder 	destPath = new StringBuilder(this.getDest());
		
		destPath = new StringBuilder(this.getDest());
		destPath.append(this.getSrc().substring(this.getSrc().indexOf("/")));
		destPath.append(tmpFile.getAbsolutePath().substring(this.getSrc().length()).toString());
		
		return destPath;		
	}
	
	private void checkDeleteFromServer(File[] tmpFiles, File[] srvFiles) throws IOException {
		List<String>			checkList;
		String[] 				srcFiles = new String[tmpFiles.length];
		String[] 				serverFiles = new String[srvFiles.length];
		
		for(int i=0; i < tmpFiles.length; i++){
			srcFiles[i] = tmpFiles[i].getName();
		}
		
		for(int i=0; i < srvFiles.length; i++){
			serverFiles[i] = srvFiles[i].getName();
		}
		
		checkList = Arrays.asList(srcFiles);
				
		for(int i=0; i < serverFiles.length; i++) {
			
			if(!checkList.contains(serverFiles[i])) {
				this.deleteFromServer(srvFiles[i]);
				this.setDeletes(this.getDeletes()+1);
			}
		}
		
	}
		
}
 
 */
