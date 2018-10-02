package hw5.test;

import hw5.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class EdgeTests {
	
	private Node<String> n1 = new Node<String>("d");
	private Node<String> n2 = new Node<String> ("b");
	private Edge<String,String>  edge_one = new Edge<String, String> ("a", n2);
	private Edge<String,String>  edge_two = new Edge<String, String> ("c", n1);
	private Edge<String,String>  edge_three = new Edge<String, String> ("c", n1);	
	
	@Test
	public void testGetLabel() {
		assertEquals("a", edge_one.getLabel());
	}
	
	@Test
	public void testGetChild() {
		assertEquals(n2, edge_one.getChild());		
	}
	
	@Test
	public void testEquals() {
		assertTrue(edge_three.equals(edge_two));
	}
	
	@Test
	public void testNotEquals() {
		assertFalse(edge_one.equals(edge_two));
	}
	
}
