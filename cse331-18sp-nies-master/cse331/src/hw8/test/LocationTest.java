package hw8.test;

import static org.junit.Assert.*;

import hw8.Location;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains a set of test cases to test 
 * the implementation of the Location class.
 */

public class LocationTest {
	
	private Location loc;
	
	@Before
	public void setUp() {
		loc = new Location(1.2, 3.4);
	}
	
	@Test
	public void testGetX() {
		assertTrue(loc.getX() == 1.2);
	}
	
	@Test
	public void testGetY() {
		assertTrue(loc.getY() == 3.4);
	}
	
	@Test
	public void testEqualsWithNull() {
		assertFalse(loc.equals(null));
	}
	
	@Test
	public void testEqualsWithNonLocationObject() {
		assertFalse(loc.equals("(1.2, 3.4)"));
	}
	
	@Test
	public void testEqualsWithSameLocation() {
		assertTrue(loc.equals(new Location(1.2, 3.4)));
	}
	
	@Test
	public void testHashCode() {
		assertEquals((new Location(1.2, 3.4)).hashCode(), loc.hashCode());
	}
}
