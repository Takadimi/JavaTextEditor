package texteditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileOperations {
	
	public void saveFile(File f, String fileText) {
		
		//System.out.println("Selected File: " + f.getPath());
		//System.out.println("TEXT: " + fileText);
		
		try {
			
			FileWriter fw = new FileWriter(f.getPath());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (int i = 0; i < fileText.length(); i++) {
				
				bw.write((char) fileText.charAt(i));
				
			}
			
			bw.close();
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		
	}
	
	public ArrayList<String> openFile(File f) {
		
		ArrayList<String> fileText = new ArrayList<String>();
		
		try {
			
			FileReader fr = new FileReader(f.getPath());
			BufferedReader br = new BufferedReader(fr);
			
			boolean eof = false;
			
			while (!eof) {
				
				String line = br.readLine();
				
				if (line == null) {
					eof = true;
				} else {
					fileText.add(line);
				}
				
			}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return fileText;
		
	}
	
}
