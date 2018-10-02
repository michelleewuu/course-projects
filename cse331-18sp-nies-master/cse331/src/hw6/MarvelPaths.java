package hw6;

import java.util.HashSet;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.HashMap;
import hw5.LabeledMultiGraph;
import hw5.Node;
import hw5.Edge;

/**
 * This class contains a method to build graph using data 
 * from a specified file, a method to find the shortest 
 * path from one node to another, and allow user to
 * find the shortest path between 2 characters from the Marvel
 * Universe by typing in the names of the 2 characters
 */

public class MarvelPaths {
	
	/**
	 * Builds graph using the data from the file.
	 * @param filename file to be used to build the graph
	 * @requires filename != null
	 * @return a graph which contains all the data from file passed in
	 * @throws Exception if fail to read data from the specified 
	 *         file or the format of the file does not match the expected format
	 */
	public static LabeledMultiGraph<String,String> buildGraph(String filename) throws Exception{
		if (filename == null) {
			throw new IllegalArgumentException("filename cannot be null");
		}
		LabeledMultiGraph<String,String> marvelGraph = new LabeledMultiGraph<String,String>();
		HashSet<String> characters = new HashSet<String>();
		HashMap<String, List<String>> books = 
				new HashMap<String,List<String>>();
		MarvelParser.parseData(filename, characters, books);
		// add all characters to marvelGraph
		for (String c: characters) {
			marvelGraph.addNode(new Node<String>(c));
		}
		// add all books corresponding to characters to marvelGraph
		for (String book: books.keySet()) {
			List<String> chars = books.get(book);
			for (String parent: chars) {
				int i = 1;
				List<String> children = chars.subList(i, chars.size());
				for (String child: children) {
					// Add double direction edges if parent and child node is
					// not the same
					if (!parent.equals(child)) {  
						marvelGraph.addEdge(book, new Node<String>(parent), new Node<String>(child));
						marvelGraph.addEdge(book, new Node<String>(child), new Node<String>(parent));
					}
				}
				i++;
			}
		}
		return marvelGraph;
	}
	
	/**
	 * Finds the shortest path from one character to another character.
	 * 
	 * @param g: the graph used to find shortest path from start to end
	 * @param start: start Node
	 * @param end: destination Node
	 * @requires graph != null && start != null && end != null 
	 *           start and end are both in g
	 * @return the shortest path from start to end, or null if 
	 *         no path exists from start to end
	 */
	public static List<Edge<String,String>> BFS(Node<String> start, Node<String> end, LabeledMultiGraph<String,String> g){
		if (g == null) {
			throw new IllegalArgumentException("graph cannot be null.");
		}
		if (start == null || end == null) {
			throw new IllegalArgumentException("start and end cannot be null.");		
		}
		if (g.containsNode(start) && g.containsNode(end)) {
			Queue<Node<String>> paths = new LinkedList<Node<String>>();
			Map<Node<String>, List<Edge<String,String>>> node_edges = 
					new HashMap<Node<String>, List<Edge<String,String>>>();
			// put the start Node into queue
			paths.add(start);
			// put the start Node and its corresponding empty set of edges into map
			node_edges.put(start, new ArrayList<Edge<String,String>>());
			while (!paths.isEmpty()) {
				Node<String> parent = paths.remove();
				// end Node found, return path
				if (parent.equals(end)) {
					List<Edge<String,String>> path = node_edges.get(parent);
					return new ArrayList<Edge<String,String>>(path);
				}
				// end Node not found yet
				HashSet<Edge<String,String>> edgeList = g.getEdges(parent);
				// use comparator to get edge in alphabetical order
				// compare the childNode of edge first,
				// then compare the label of edge
				Set<Edge<String,String>> sortedEdges =  	
				new TreeSet<Edge<String,String>>(new Comparator<Edge<String,String>>() {
					public int compare(Edge<String,String> e1, Edge<String,String> e2) {	
						if(!(e1.getChild().equals(e2.getChild()))) {
							return e1.getChild().getData().compareTo(e2.getChild().getData());	
						}
						if (!(e1.getLabel().equals(e2.getLabel()))) {
							return e1.getLabel().compareTo(e2.getLabel());	
						}
						return 0;		
					}		
				});
				sortedEdges.addAll(edgeList);
				// add childNodes of the Node currently pointing at 
				// to the queue if it has not been checked
				for (Edge<String,String> e: sortedEdges) {
					Node<String> child = e.getChild(); 
					if (!node_edges.containsKey(child)) {
						List<Edge<String,String>> path = node_edges.get(parent);
						List<Edge<String,String>> newPath = new ArrayList<Edge<String,String>>(path);
						newPath.add(e);
						node_edges.put(child, newPath);
						paths.add(child);
					}
				}
			}	
			// no path found from start Node to end Node
			return null;
		}
		// start or end not in graph
		return null;
	}
	
	/**
	 * Allows user to type in two characters and find the 
	 * shortest path between two characters. 
	 * 
	 * @param args
	 * @throws Exception if file cannot be found
	 */
	public static void main(String[] args) throws Exception {
		LabeledMultiGraph<String,String> marvelGraph = buildGraph("src/hw6/data/marvel.tsv");
		System.out.println("Find the shortest path between 2 Marvel characters");
		Scanner reader = new Scanner(System.in);
		boolean again = true;
		while (again) {
			System.out.println("Please type the starting character's name");
			String start = reader.nextLine();
			System.out.println("Please type the ending character's name");
			String end = reader.nextLine();
			if (!marvelGraph.containsNode(new Node<String>(start)) || 
					!marvelGraph.containsNode(new Node<String>(end))) {
				System.out.println(start + "is an unknown character");
				System.out.println(end + "is an unknown character");
			} else if (!marvelGraph.containsNode(new Node<String>(start))) {
				System.out.println(start + "is an unknown character");
			} else if (!marvelGraph.containsNode(new Node<String>(end))) {
				System.out.println(end + "is an unknown character");
			} else {
				Node<String> current = new Node<String>(start);
				String result = "path from " + start + " to " + end + ":";
				List<Edge<String, String>> path = BFS(new Node<String>(start), new Node<String>(end), marvelGraph);
				if (path == null) {
					result += "\n" + "no path found";
				} else {
					for (Edge<String, String> e: path) {
						result += "\n" + current.getData() + " to " + 
									e.getChild().getData() + " via " + e.getLabel();
						current = e.getChild();
					}
				}
				System.out.println(result);				
			}
			System.out.println("Again?");
			String answer = reader.nextLine();
			answer = answer.toLowerCase();
			if (answer.length() == 0 || answer.charAt(0) != 'y') {
				again = false;
			}
		}
		System.out.println("Bye!");
		reader.close();
	}
}
