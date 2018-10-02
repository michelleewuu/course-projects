package hw7;

import java.util.HashSet;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashMap;
import hw5.LabeledMultiGraph;
import hw5.Node;
import hw5.Edge;


/**
 * This class contains a method to build graph using data 
 * from specified file, and a method to find the 
 * minimum-cost path from one node to another node.
 */
public class MarvelPaths2 {
	
	/**
	 * Builds graph using the data from the file.
	 * @param filename file to be used to build the graph
	 * @requires filename != null
	 * @throws Exception if fail to read data from the specified 
	 * file or the format of the file does not match the expected format
	 */
	public static LabeledMultiGraph<String,Double> buildGraph(String filename) throws Exception{
		if (filename == null) {
			throw new IllegalArgumentException("filename cannot be null");
		}
		LabeledMultiGraph<String,Double> doubleGraph = 
				new LabeledMultiGraph<String,Double>();
		
		// parse the data in order to build graph
		Map<String,HashMap<String,Integer>> commonBook = 
				new HashMap<String,HashMap<String,Integer>>();
		MarvelParser2.parseData(filename,commonBook);
		
		// add all characters and corresponding edges to doubleGraph
		for (String parent: commonBook.keySet()) {
			doubleGraph.addNode(new Node<String>(parent));
			Map<String, Integer> count = commonBook.get(parent);
			for (String child: count.keySet()) {
				int num = count.get(child);
				double label = 1.0 / num;
				Node<String> parentNode =  new Node<String>(parent);
				Node<String> childNode = new Node<String>(child);
				doubleGraph.addEdge(label, parentNode, childNode);
				doubleGraph.addEdge(label, childNode, parentNode);
			}
		}
		return doubleGraph;
	}
	
	/**
	 * Finds the minimum-cost path from one character to another character.
	 * @param graph: the graph used to find shortest path from start to end
	 * @param start: a character
	 * @param end: another character
	 * @requires graph != null && start != null && end != null
	 * @return the minimum-cost path from start to end, or null if 
	 * no path exists from start to end or start or end not in graph
	 * @throws IllegalArgumentException if either start or end is 
	 * not in the graph
	 */
	public static <T> List<Edge<T,Double>> minPath(Node<T> start, Node<T> end, LabeledMultiGraph<T,Double> g){
		if (g == null) {
			throw new IllegalArgumentException("graph cannot be null.");
		}
		if (start == null || end == null) {
			throw new IllegalArgumentException("start and end cannot be null.");		
		}
		if (g.containsNode(start) && g.containsNode(end)) {
			// A path's "priority" in the queue is the total cost of that path.
			// Create the priority queue with specified comparator to order 
			// paths by their total costs.
			PriorityQueue<List<Edge<T,Double>>> active = 
					new PriorityQueue<List<Edge<T,Double>>>(new Comparator<List<Edge<T,Double>>>() {
						public int compare(List<Edge<T,Double>> p1, List<Edge<T,Double>> p2) {	
							double cost1 = 0;
							double cost2 = 0;
							for (Edge<T, Double> e1: p1) {
								cost1 += e1.getLabel();
							}
							for (Edge<T, Double> e2: p2) {
								cost2 += e2.getLabel();
							}
							int num = 0;
							if (cost1-cost2 > 0) {
								num = 1;
							} else if (cost1-cost2 < 0) {
								num = -1;
							}
							return num;		
						}		
					});
			
			// finishes contains Nodes for which we have checked
			// the minimum-cost path from start
			Set<Node<T>> finished = new HashSet<Node<T>>();
			
			// put the start Node into queue and a path with cost of 0.0 
			// because there is no edge in the path
			List<Edge<T, Double>> selfPath = new ArrayList<Edge<T, Double>>();
			selfPath.add(new Edge<T, Double>(0.0, start));
			active.add(selfPath);
			
			while (!active.isEmpty()) {
				// minPath is the lowest-cost path in active and 
				// is the minimum-cost path for some node
				List<Edge<T,Double>> minPath = active.poll();
				// minDest is the end Node of minPath
				Node<T> minDest = minPath.get(minPath.size() - 1).getChild();
				// return minPath if the end Node of minPath 
				// is equal to end passed in by client
				if (minDest.equals(end)) {
					return minPath;
				// if we haven't checked minDest, update the list of paths 
				// by adding children of minDest to minPath
				} else if (!finished.contains(minDest)) {
					for (Edge<T, Double> e: g.getEdges(minDest)) {
						if (!finished.contains(e.getChild())) {
							List<Edge<T,Double>> newPath = 
									new ArrayList<Edge<T,Double>>();
							for (Edge<T,Double> e2: minPath) {
								newPath.add(e2);
							}
							newPath.add(e);
							active.add(newPath);
						}		
					}
					// add minDest to finished after checking it
					finished.add(minDest);
				}
			}
			// no path exist from start to end
			return null;
		}
		// start or end not in graph
		return null;
	}
}
