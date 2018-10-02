package hw5;


/**
 * This represent a node with a data stored in it
 * 
 * Specification fields:
 *  @specfield data : T //Any type of data wanted to be stored in the node
 * 
 * Abstract Invariant:
 * The data stored is not null        
 */
public class Node<T> {
	
	// Rep invariant:
			// data != null 
			// Abstract function:
			//     AF(this) = a node with data, n, such that
			//                n.data = this.data
	
	private T data;

/**
 * Constructor that creates a new Node object with the specified data and an
 * empty list of edges
 * @param data Data stored in the new Node
 * @requires data cannot be null
 */
	public Node(T data) {
		if (data == null) {
			throw new IllegalArgumentException("Data cannot be null.");
		}
		this.data = data;
		checkRep();
	}
	

/**
 * Return data stored in the Node
 *  @return data Data stored in the Node
 */
	public T getData(){
		return data;
	}
	
/**
 * Returns true if arg represent same node as this node.
 *  @param arg object to be compared
 *  @return true if arg represents same node (same data) as this node
 */
	@Override
	public boolean equals(/*@Nullable*/ Object arg) {
		checkRep();
		if (!(arg instanceof Node<?>)) {
			return false;		
		}
		Node<?> n = (Node<?>) arg;
		checkRep();
		return data.equals(n.data);
	}	
	
/**
 * Return hash code of this node. 
 *  @return hash code of this node
 */
	@Override
	public int hashCode() {
		return data.hashCode();
	}

/**
 * Checks if representation invariant holds.
 */
	private void checkRep() throws RuntimeException {
		// Check if the Node itself is null
		if (data == null) {
			throw new RuntimeException("data cannot be null.");	
		}
	}
}






