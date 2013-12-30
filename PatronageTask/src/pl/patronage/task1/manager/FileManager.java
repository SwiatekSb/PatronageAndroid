package pl.patronage.task1.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import pl.patronage.task1.parser.Items;

import android.os.Environment;

public class FileManager {

	/**
	 * Method load file from given directory and name of file
	 * @param dir of file
	 * @param name of file
	 * @return InnputStrem of this file
	 * @throws IOException 
	 */
	public FileInputStream loadFileSDCard(String dir, String name) throws IOException, FileNotFoundException{
		
		FileInputStream fInput = null;
		fInput = new FileInputStream(getPathFile(dir,name));
	
		return fInput;
	}
	/**
	 * Method save Items like XML file.
	 * @param dir of file
	 * @param name of file
	 * @param items - list of Items of parsing XML file
	 * @throws IOException
	 */
	public void saveXmlFileSDCard(String dir, String name,List<Items> items) throws IOException  {
		   	
		FileOutputStream fOut = new FileOutputStream(getPathFile(dir,name));
		OutputStreamWriter osw = new OutputStreamWriter(fOut);

		osw.write("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?> \n");
		for (Items it : items) {
			osw.write(it.serialize());
		}
		osw.close();		
		fOut.close();
	}
	/**
	 * Method get path to file or create directory if not exist
	 * @param dir - name of Path to directory
	 * @param name - name of file
	 * @return File
	 */
	private File getPathFile(String dir, String name){
		
		File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath()
            + dir);
        if(directory.exists() == false){
        	directory.mkdirs();
        }
        File file = new File(directory, name);
        
        return file;		
	}

	/**
	 * Method save file in concrete directory with concrete name.
	 * @throws IOException
	 */
	public void copyFile(String dir, String name, BufferedReader br ) throws IOException{
		String str = null;
		
		FileOutputStream fOut = new FileOutputStream(getPathFile(dir,name));
        OutputStreamWriter osw = new OutputStreamWriter(fOut);

        while ((str = br.readLine()) != null) {
				osw.write(str);
				osw.write("\n");
		}
        
        osw.flush();
        osw.close();
	}
	
}
