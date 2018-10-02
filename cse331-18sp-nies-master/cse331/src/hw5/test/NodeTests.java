package hw5.test;

import hw5.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NodeTests {

	private Node<String> n2 = new Node<String>("b");
	private Node<String> n3 = new Node<String>("a");
	private Node<String> n4 = new Node<String>("b");
	
	@Test
	public void testGetData() {
		assertEquals("a", n3.getData());
		assertEquals("b", n2.getData());
	}
	
	@Test
	public void testEquals() {
		assertTrue(n2.equals(n4));
	}
	
	@Test
	public void testNotEquals() {
		assertFalse(n2.equals(n3));
	}

}
