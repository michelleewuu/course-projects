package hw8;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import hw5.LabeledMultiGraph;
import hw5.Node;
import jdk.nashorn.internal.parser.Token;

/**
 * Parser utility to load the campus buildings and paths datasets.
 */

public class CampusDataParser {
	/**
	 * Parse campus buildings data.
	 * Each line of the input contains abbreviated name of a building, 
	 * full name of a building, and x and y coordinate of location of 
	 * the building's entrance. And these four tokens are separated by 
	 * a tab character.
	 * 
	 * @requires buildings, fullMap, shortMap and locMap are all not null
	 * @param buildings file which contains data of campus buildings
	 * @param fullMap: a map of building's names that maps building's 
	 * 		  abbreviated name to full name (empty initially)
	 * @param shortMap: a map of building's names that maps building's 
	 * 		  full name to abbreviated name (empty initially)
	 * @param locMap: a map of building's locations that maps 
	 * 		  building's abbreviated name to its location (empty initially)
	 * @throws Exception if the format of the file does not match the 
	 * 		   expected format
	 */
    public static void parseBuildingData(String buildings, Map<String, String> shortMap,
    		Map<String,String> fullMap, Map<String, Location> locMap) throws Exception{
    	BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(buildings));        
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                // Ignore comment lines.
                if (inputLine.startsWith("#")) {
                    continue;
                }
                // Parse the data, stripping out quotation marks and throwing
	    		// an exception for malformed lines.
	    		inputLine = inputLine.replace("\"", "");
	    		String[] tokens = inputLine.split("\t");
	    		if (tokens.length != 4) {
	    			throw new Exception("Line should contain one tab between " +
	    					"each pair of tokens: " + inputLine);
	    		}    		
	    		String shortName = tokens[0];
	    		String fullName = tokens[1];
	    		double x = Double.parseDouble(tokens[2]);
	    		double y = Double.parseDouble(tokens[3]);	    		
	    		// map building's full name to its abbreviate name
	    		shortMap.put(fullName, shortName);
	    		// map building's abbreviated name to its full name
	    		fullMap.put(shortName, fullName);
	    		// map building's abbreviated name to its location
	    		locMap.put(shortName, new Location(x,y));
            }   
	    } catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	    	 if (reader != null) {
	             try {
	                 reader.close();
	             } catch (IOException e) {
	                 System.err.println(e.toString());
	                 e.printStackTrace(System.err);
	             }
	         }
	     }
    }
	    
	/**
	 * Read campus paths data and construct a campus graph.
	 * @requires paths and campusGraph are all not null
	 * @param paths: name of file which contains data of campus paths
	 * @param campusPath: a graph that contains campus paths (empty initially)
	 * @throws Exception if the format of the file does not match the 
	 * 		   expected format
	 */
    public static void buildCampusGraph(String paths, 
    		LabeledMultiGraph<Location, Double> campusGraph) throws Exception{
    	BufferedReader reader = null;
    	try {
             reader = new BufferedReader(new FileReader(paths));  
             String inputLine;
             Location start = null;	
             while ((inputLine = reader.readLine()) != null) {
                 // Ignore comment lines.
                 if (inputLine.startsWith("#")) {
                     continue;
                 }
                // Parse the data, stripping out quotation marks and throwing
 	    		// an exception for malformed lines.
 	    		inputLine = inputLine.replace("\"", "");
 	    		// stripping out tab character from indented lines
 	    		inputLine = inputLine.replace("\t", "");
 	    		// split location and distance from indented line
 	    		String[] tokens = inputLine.split(": "); 	
 	    		// split x,y coordinates from location
 	    		String[] coords = tokens[0].split(",");
     			double x = Double.parseDouble(coords[0]);
     			double y = Double.parseDouble(coords[1]);
     			Location loc = new Location(x,y);	     					
     		    // tokens.length == 1 means the line is non-indented line
	    		// tokens.length == 2 means the line is indented line
	    		// else means the file is not well-formed
     			if (tokens.length == 1) {
     				// set location to be the coordinates of non-indented 
	    			// line in order to add edges when parse indented lines
     				start = loc;
     				campusGraph.addNode(new Node<Location>(start)); 				
     			} else if (tokens.length == 2) {
     				if (start == null) {
     					throw new Exception("File is not well-formed. " +
	    						"Non-indented line should come before " +
	    						"indented line."); 					
     				}
     				double distance = Double.parseDouble(tokens[1]);
     				campusGraph.addEdge(distance, new Node<Location>(start), 
     						new Node<Location>(loc));     				
     			} else {
     				throw new Exception ("File is not well-formed. " +
	    					"Non-indented line should contain coordinates of " +
	    					"the location of the start location, and indented "+
	    					"line should contain coordinates of end locations "+
	    					"followed by a colon and the distance from start "+
	    					"location the that point: " + inputLine);	    					
     			}
             }
        } catch (IOException e) {
 	        System.err.println(e.toString());
 	        e.printStackTrace(System.err);
 	    } finally {
 	    	 if (reader != null) {
 	            try {
 	                reader.close();
 	            } catch (IOException e) {
 	                System.err.println(e.toString());
 	                e.printStackTrace(System.err);
 	            }
 	        }
 	    }
    }    
}




