package com.github.usc.graph;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.IOException;

public class FileContent {
	
	public String getFile(String fileName) throws IOException{
		InputStream is = getClass().getResourceAsStream(fileName);
		if (is == null) {
			throw new NullPointerException("File does not exist");
		}
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    StringBuffer sb = new StringBuffer();
	    Scanner s = new Scanner(br);
	    //while (s.hasNext())
	    //    System.out.println(s.nextInt());
	    //String line;
	    while (s.hasNextInt()) 
	    {
	      sb.append(s.nextInt()+" ");
	      sb.append(s.nextInt());
	      sb.append("\n");
	    }
	    br.close();
	    isr.close();
	    is.close();
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException{
		FileContent obj = new FileContent();
		System.out.print(obj.getFile("/test_2.txt"));
	}
}
