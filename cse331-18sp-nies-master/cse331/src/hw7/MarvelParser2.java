package hw7;
import java.io.*;
import java.util.*;

/**
 * Parser utility to load the Marvel Comics dataset using
 * the number of books two characters appear together.
 */
public class MarvelParser2 {
    /**
     * A checked exception class for bad data files
     */
    @SuppressWarnings("serial")
    public static class MalformedDataException extends Exception {
        public MalformedDataException() { }

        public MalformedDataException(String message) {
            super(message);
        }

        public MalformedDataException(Throwable cause) {
            super(cause);
        }

        public MalformedDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }

  /**
   * Reads the Marvel Universe dataset.
   * Each line of the input file contains a character name and a comic
   * book the character appeared in, separated by a tab character
   * 
   * @requires filename is a valid file path
   * @param filename the file that will be read
   * @param commonBooks map from character to another character together
   *        with the number of books they appear in together
 * @return 
   * @modifies characters, books
   * @effects fills characters with a list of all unique character names
   * @effects fills books with a map from each comic book to all characters
   *          appearing in it
   * @throws MalformedDataException if the file is not well-formed:
   *          each line contains exactly two tokens separated by a tab,
   *          or else starting with a # symbol to indicate a comment line.
   */
  public static void parseData(String filename, 
		  Map<String, HashMap<String, Integer>> commonBooks) throws MalformedDataException {
    
	  	BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(filename));
        // a map to store books corresponding to a list of characters in that book
        Map<String, ArrayList<String>> books = 
				new HashMap<String, ArrayList<String>>();
        
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
            if (tokens.length != 2) {
                throw new MalformedDataException("Line should contain exactly one tab: "
                                                 + inputLine);
            }

            String character = tokens[0];
            String book = tokens[1];
            
            // add character to commonBook if it is not already in
            if (!commonBooks.containsKey(character)) {
                commonBooks.put(character, new HashMap<String, Integer>());
            }
            // add character to the corresponding list of characters of book if 
            // it is not already in
            if (!books.containsKey(book)) {
            	books.put(book, new ArrayList<>());
            	List<String> chars = books.get(book);
            	chars.add(character);
            } else {  
            	// if character is already in book, update the number of edges 
            	// from character to all other characters in the book list
            	// of characters  
                List<String> chars = books.get(book);
	        	Map<String, Integer> characterMap = commonBooks.get(character);
	        	for (String c: chars) {
	            	if (!characterMap.containsKey(c)) {
	            		characterMap.put(c,1);
	            	} else {
	            		characterMap.put(c, characterMap.get(c) + 1);
	            	}
	            	// update the number of edges in reverse direction
	            	String c2 = c;
	            	Map<String, Integer> cMap = commonBooks.get(c2);
	            	if (!cMap.containsKey(character)) {
	            		cMap.put(character,1);
	            	} else {
	            		cMap.put(character, cMap.get(character) + 1);
	            	}
	            	// add character to the book's character list 
	            }   
	        	chars.add(character);
            	books.put(book, (ArrayList<String>) chars);
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

