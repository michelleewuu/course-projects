package hw5;

/**
 * This represent an edge with a label value and a child node
 * 
 * Specification fields:
 *  @specfield: childNode: Node<T> //the Node this edge is pointing to
 *  @specfield: label: E //the label on this edge
 */
public class Edge <T, E> {
	// Rep invariant:
		// childNode != null && label != null
		// Abstract function:
		//     AF(this) = a labeled edge without origin, arg, such that
		//                arg.destination = this.dest
		//                arg.label = this.label
	
	private final Node<T> childNode;
	private final E label;
	
/**
 * Constructor that creates an Edge with the label on it label and 
 * pointing to childNode
 *  @param label Label on the edge
 *         childNode The node this edge is pointing to
 *  @requires label != null && childNode != null
 *  @effects construct a new Edge with the label on it label and 
 *           pointing to childNode  
 */
	public Edge(E label, Node<T> childNode) {
		if (label == null || childNode == null) {
			throw new IllegalArgumentException("Arguments cannot be null.");
		}
		this.label = label;
		this.childNode = childNode;
		checkRep();
	}
	
/**
 * Return the value of the label on this Edge
 *  @return the label on this Edge
 */
	public E getLabel() {
		return label;
	}
	
	
/**
 * Return the node this Edge is pointing to
 *  @return childNode of this Edge
 */
	public Node<T> getChild() {
		return childNode;
	}
	
/**
 * Returns true if arg represent same edge as this edge.
 *  @param arg object to be compared
 *  @return true if arg represents same edge 
 * (same childNode and same label) as this edge
 */
	@Override
	public boolean equals(/*@Nullable*/ Object arg) {
		checkRep();
		if (!(arg instanceof Edge<?,?>)) {
			return false;		
		}
		Edge<?,?> labelEdge = (Edge<?,?>) arg;
		checkRep();
		return childNode.equals(labelEdge.childNode) && label.equals(labelEdge.label);

	}
		
/**
 * Return hash code of this edge. 
 *  @return hash code of this edge
 */
	@Override
	public int hashCode() {
		return childNode.hashCode() + label.hashCode();
	}

/**
 * Checks if representation invariant holds.
 *  @throws RunTimeException if client pass in null parameters to the constructor
 */
	private void checkRep() throws RuntimeException {
		if (childNode == null) {
			throw new RuntimeException("childNode cannot be null.");	
		}
		if (label == null) {
			throw new RuntimeException("Label cannot be null.");
		}
	}

}
