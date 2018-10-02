package hw5;

import java.util.HashSet;
import java.util.HashMap;

/**
 * This object represents a Labeled MultiGraph with collection of Nodes
 * in it. Each Node can only appear in the Graph once. 
 * A Node is an Object with a data stored in it.
 * An Edge is an Object with a label stored in it and pointing to a Node
 * 
 * Specification fields:
 *  @specified: graph: HashMap<Node<T>, HashSet<Edge<T,E>> // Map of all the Nodes 
 *              in graph, each corresponding to the Set of Edges starting from it
 *  
 * Abstract Invariant:
 *  keySet of graph is not null and every Node inside the set is not null
 *  corresponding Set of Edge to each Node is not null and each Edge 
 *  in it is not null
 */
public class LabeledMultiGraph<T,E> {
	// Rep invariant:
		//     graph != null
		//     Every node in graph is not null.
		//	   Corresponding Set of Edges to each Node is not null
		//     Each Edge inside the Set is not null

		// Abstract function:
		//    AF(this) = directed graph g such that
		//    All Nodes in this = this.graph.keySet()
	    //    All Edges starting from Node n = this.graph.getKey(n)
		//    graph is empty if there is no node in graph
		
	private final HashMap<Node<T>, HashSet<Edge<T,E>>> graph;
	
/**
 * Constructor which creates a new LabeledMultiGraph with an empty Map 
 *  @effect create an empty graph
 */
	public LabeledMultiGraph() {
		graph = new HashMap<Node<T>, HashSet<Edge<T,E>>>();
		checkRep();
	}
	
/**
 * Add a given Node to the Graph if the node is not in the Graph already
 * return true if the Node can be added, else return false
 *  @param newNode: the Node to be added to this
 *  @requires newNode is not null
 *  @modifies this
 *  @effect add newNode to the Graph if it is not already in the Graph
 *  @return boolean canAdd: true if newNode can be added, false otherwise
 */
	public boolean addNode(Node<T> newNode) {
		if (newNode == null) {
			throw new IllegalArgumentException("Node cannot be null");
		}
		boolean canAdd = !graph.containsKey(newNode);
		if (canAdd) {
			graph.put(newNode,new HashSet<Edge<T,E>>());
		}
		return canAdd;
	}

/**
 * Remove a given Node if it is in the Graph and all the edges related to it, 
 * return true if it is in the graph and can be removed, false other wise
 *  @param arg: the Node to be removed from the Graph
 *  @requires arg is not null
 *  @modifies this
 *  @effect remove arg from the Graph if it is in the Graph
 *  @return boolean canRemove: true if arg can be removed, else return false
 */
	public boolean removeNode(Node<T> arg) {
		if (arg == null) {
			throw new IllegalArgumentException(arg + " cannot be null");
		}
		boolean canRemove = graph.containsKey(arg);
		if (canRemove) {
			graph.keySet().remove(arg);
			for (Node<T> n: graph.keySet()) {
				for (Edge<T,E> e: graph.get(n)) {
					if (e.getChild().equals(arg)) {
						removeEdge(e.getLabel(), n, e.getChild());
					}
				}
			}
		}
		checkRep();
		return canRemove;
	}	
	
/** 
 * Add Edge starting from Node start pointing to Node end labeled with label if
 * it doesn't exist, create new start Node if start node is not in the graph
 * @param Node start, Node end, String label
 * @modifies this
 * @requires start, end, label are not null, start is in the graph
 * @effect add a new Edge starting from start pointing to end labeled with 
 *         label to this
 * @return boolean canAdd: whether the Edge can be add or not
 */
	public boolean addEdge(E label, Node<T> start, Node<T> end) {
		if (label == null) {
			throw new IllegalArgumentException(label + " cannot be null");
		}
		if (start == null || end == null) {
			throw new IllegalArgumentException("Node cannot be null.");
		}
		addNode(start); 
		Edge<T,E> newEdge = new Edge<T,E>(label, end);
		HashSet<Edge<T,E>> start_edges = graph.get(start);
		boolean canAdd = !start_edges.contains(newEdge);
		if (canAdd) {
			start_edges.add(newEdge);
		}
		return canAdd;		
	}

/** 
 * Remove Edge starting from start pointing to end labeled with label
 * if it exists, return true if the Edge can be removed, else false
 * @param Node start, Node end, String label
 * @modifies this
 * @requires: label,start and end are not null
 * @effect Remove all the Edges starting from start pointing to end labeled 
 *         with label from the Graph
 * @return boolean canRemove: true if the Edge with properties above can be removes
 *         false other wise
 */
	public boolean removeEdge(E label, Node<T> start, Node<T> end) {
		if (label == null) {
			throw new IllegalArgumentException(label + " cannot be null");
		}
		if (start == null || end == null) {
			throw new IllegalArgumentException("Node cannot be null.");
		}
		if (!graph.containsKey(start)) {
			return false;
		}
		Edge<T,E> e = new Edge<T,E>(label, end);
		HashSet<Edge<T,E>> edges = graph.get(start);
		boolean canRemove = edges.contains(e);
		if (canRemove) {
			edges.remove(e);
		}
		return canRemove;
	}
	

/**
 * Return data stored in a given Node
 *  @param Node arg
 *  @requires arg is not null and is in the Graph
 *  @return E: the data stored in arg	
 */
	public E getData(Node<E> arg) {
		if (arg == null) {
			throw new IllegalArgumentException("Node cannot be null");
		}
		if (!graph.containsKey(arg)) {
			throw new IllegalArgumentException("Node is not in the graph");
		}
		return arg.getData();
	}
	
/**
 * Given a Node arg, return all of its child Nodes
 *  @param Node arg whose children we are going to get
 *  @requires arg is not null and is in the graph
 *  @return HashSet containing all the child Nodes of arg
 */
	public HashSet<Node<T>> getChildren(Node<T> arg) {
		if (arg == null) {
			throw new IllegalArgumentException("Node cannot be null");
		}
		if (!graph.containsKey(arg)) {
			throw new IllegalArgumentException("Node is not in the graph");
		}
		HashSet<Node<T>> result = new HashSet<Node<T>> ();
		for (Edge<T,E> e: graph.get(arg)) {
			result.add(e.getChild());
		}
		return result;
	}
	
/**
 * Return the set of nodes in this
 * @return a set of nodes in this
 */
	public HashSet<Node<T>> getNodes() {
		HashSet<Node<T>> result = new HashSet<Node<T>> ();
		for (Node<T> n: graph.keySet()) {
			result.add(n);
		}
		return result;
	}
	
/**
 * Given a node in this return the set of edges starting from it
 * @param Node arg to get edges from
 * @requires arg != null and arg is in this
 * @return a set of edges starting from this node
 */
	public HashSet<Edge<T,E>> getEdges(Node<T> arg) {
		HashSet<Edge<T,E>> result = new HashSet<Edge<T,E>>();
		for (Edge<T,E> e: graph.get(arg)) {
			result.add(e);
		}
		return result;
	}
	
/**
 * Return true if node n is in the graph.
 * @param n a node
 * @requires n != null
 * @return true if node n is in this.nodeSet
 */
	public boolean containsNode(Node<T> n) {	
		if (n == null){
			throw new IllegalArgumentException("Node cannot be null.");	
		}
		return graph.containsKey(n);
	}
	
/**
 * Returns number of nodes in the graph.
 * @return number of nodes in the graph
 */
	public int size() {
		return graph.keySet().size();
	}

	/**
	 * Returns number of edges from one node to another node. 
	 * @param Node start,origin of the edge 
	 *        Node end,childNode of the edge
	 * @requires start, end != null, start and end are in the graph
	 * @throws IllegalArgumentException if either node start or end not in graph
	 * @return number of edges from start to end
	 */
	public int numberOfEdges(Node<T> start, Node<T> end) {
		if (start == null || end == null)
			throw new IllegalArgumentException("Node cannot be null.");
		// check if nodes of the edge passed in already exist in the graph
		if (!(graph.containsKey(start))){
			throw new IllegalArgumentException("Node " + start + 
					" passed in is not present in the graph.");
		}
		if (!(graph.containsKey(end))){
			throw new IllegalArgumentException("Node " + end + 
					" passed in is not present in the graph.");
		}
		HashSet<Edge<T,E>> edges = graph.get(start);
		int count = 0;
		for (Edge<T,E> e: edges) {
			if (e.getChild().equals(end))
				count++;
		}		
		return count;
	}
	
/**
 * Returns true if the graph is empty.
 * @return true if the graph is empty
 */
	public boolean isEmpty() {
		return graph.isEmpty();
	}
	
	/**
	 * Checks if representation invariant holds.
	 */
	private void checkRep() throws RuntimeException {		
		// check if the graph itself is null
		if (graph == null){
			throw new RuntimeException("The graph cannot be null.");
		}		
		// check if any node in graph is null
		for (Node<T> n : graph.keySet()) {
			if (n == null) {
				throw new RuntimeException("Node cannot be null.");
			}	
			// check if the Set of Edge corresponding to n is null
			if (graph.get(n) == null) {
				throw new RuntimeException("Edge set cannot be null.");
			}
			// check if any edge inside the set is null
			for (Edge<T,E> e: graph.get(n)) {
				if (e == null) {
					throw new RuntimeException("Edge cannot be null.");
				}
			}
		}
	}
	
}

