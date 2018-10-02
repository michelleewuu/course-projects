package hw8;

/**
 * This class represents location of a point.
 * @specfield x: double
 * @specfield y: double
 */

public class Location {
	private Double x;  // x coordinate
	private Double y;  // y coordinate
	
	// Rep invariant:
		//		x != null && y != null
	
		// Abstract function:
		// 		AF(this) = coordinates of a point p such that 
		//			p.x = this.x;
		//			p.y = this.y;
	
	/**
	 * Constructs coordinates of a point. 
	 * @param x: x coordinate of the point
	 * @param y: y coordinate of the point
	 * @effects: create a new Location Object
	 */
	public Location (double x, double y) {
		this.x = x;
		this.y = y;
		checkRep();
	}
	
	/**
	 * Returns x coordinate of the point.
	 * @return x coordinate of the point
	 */
	public double getX() {
		checkRep();
		return x;
	}
	
	/**
	 * Returns y coordinate of the point.
	 * @return y coordinate of the point
	 */
	public double getY() {
		checkRep();
		return y;
	}
	
	/**
	 * Compares this object to another object.
	 * @param o: object to be compared
	 * @return true if o represents the same coordinates
	 */
	@Override
	public boolean equals(Object o) {
		checkRep();
		if (!(o instanceof Location)) {
			return false;
		}
		Location c = (Location) o;
		checkRep();
		return (c.x.equals(x)) && (c.y.equals(y));
		
	}
	
	/**
	 * Returns a hash code for this Location object.
	 * @return a hash code for this Location object
	 */
	@Override
	public int hashCode() {
		checkRep();
		return x.hashCode() + y.hashCode();
	}
	
	/**
	 * Checks if representation invariant holds.
	 */
	private void checkRep() {
		if (x == null) {
			throw new RuntimeException("x coordinate cannot be null.");
		}	
		if (y == null) {
			throw new RuntimeException("y coordinate cannot be null.");
		}
	}	
}
