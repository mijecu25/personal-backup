package com.mijecu25.personal_backup;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//import org.apache.commons.io.FileUtils;
//import org.apache.log4j.Logger;


/**
 * @author Miguel Velez
 * @version 0.3
 * 
 * This is the backup class.
 */


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
	 * */
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
	 * */
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
	 * */
	private void write(File src, File dest) throws IOException {
//		try {
////			FileUtils.copyFile(src, dest, true);
//		} 
//		catch (IOException e) {
//			
//			throw new IOException(e.getMessage());
//		}
	}
	
	/** This method deletes the folder or file in the destination.*/
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
	 * */
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