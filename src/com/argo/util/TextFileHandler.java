/* Yes, I'm aware this class lacks functionality it should have, but it was
 * written with a specific purpose in mind and I can't be bothered to add said
 * functionality until it is needed.
 */

package com.argo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TextFileHandler {
    private final String p;

    public TextFileHandler(String path) {
	p = path;
	if(!new File(p).exists()) {
	    try {
		new File(p).createNewFile();
	    } catch (IOException ex) {}
	}
    }

    public List<String> readLines() throws FileNotFoundException, IOException {
	 BufferedReader inputStream = null;
	 List<String> data = new ArrayList<String>();
	 try {
	    inputStream = new BufferedReader(new FileReader(p));
	    String l;

	    while ((l = inputStream.readLine()) != null) {
                data.add(l);
            }
	} finally {
	    if(inputStream != null) {
		inputStream.close();
	    }
	}
	return data;
    }

    public void writeLines(List<String> data) throws IOException {
	PrintWriter outputStream = null;
	try {
	    outputStream = new PrintWriter(new FileWriter(p));
	    while(!data.isEmpty()) {
		 outputStream.println(data.remove(0));
	    }
	} finally {
	    if(outputStream != null) {
		outputStream.close();
	    }
	}
    }

    public void appendLine(String line) throws IOException  {
	PrintWriter outputStream = null;
	try {
	    outputStream = new PrintWriter(new FileWriter(p, true));
	    outputStream.println(line);
	} finally {
	    if(outputStream != null) {
		outputStream.close();
	    }
	}
    }

    public String getPath() {
	return p;
    }

}
