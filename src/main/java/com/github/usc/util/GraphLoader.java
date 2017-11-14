package com.github.usc.util;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.github.usc.graph.FileContent;

import java.io.IOException;

public class GraphLoader {
    /**
     * Loads graph with data from a file.
     * The file should consist of lines with 2 integers each, corresponding
     * to a "from" vertex and a "to" vertex.
     */ 
    public static void loadGraph(com.github.usc.graph.Graph g, String filename) {
        Set<Integer> seen = new HashSet<Integer>();
        try {
        	FileContent fc = new FileContent();
        	String str = fc.getFile(filename);
        	String[] lines = str.split("\\n");
        	for (String line : lines) {
        		String[] l = line.split(" ");
        		int v1 = Integer.parseInt(l[0]);
                int v2 = Integer.parseInt(l[1]);
                if (!seen.contains(v1)) {
                    g.addVertex(v1);
                    seen.add(v1);
                }
                if (!seen.contains(v2)) {
                    g.addVertex(v2);
                    seen.add(v2);
                }
                g.addEdge(v1, v2);
        	}
        }
        catch (Exception e) {
        	e.printStackTrace();
            return;
        }
    }
}
