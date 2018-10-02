package hw9;

import hw8.CampusPathModel;
import hw8.Location;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import javax.swing.*;

/**
 * CampusPathFindingView is a GUI view
 * for campus route finding tool
 */

public class CampusPathFindingView extends JComponent{
	private static final long serialVersionUID = 1L;	
	
	// image of campus map
	private static final String FILE_NAME = "src/hw8/data/campus_map.jpg";
	
	// width of the image displayed
	private int width;
	
	// height of the image displayed
	private int height;
	
	// ratio of width of the image being displayed to actual image's width
	private double width_ratio;
	
	// ratio of height of the image being displayed to actual image's height
	private double height_ratio;
	
	// model of the campus route finding tool
	private CampusPathModel m;
	
	// the shortest route to display on the map
	private Map<Location,Double> route;
	
	// list on the locations in the shortest route being displayed
	private List<Location> locations;
	
	private BufferedImage img;
	
	/**
	 * Construct a GUI view of the campus finding tool
	 * @param m the model of the campus route finding tool
	 * @requires m is not null
	 * @effects create a new CampusPathFindingView
	 */
	public CampusPathFindingView (CampusPathModel m) {
		if (m == null) {
			throw new IllegalArgumentException("model passed in cannot be null");
		}
		this.m = m;
		width = 1000;
		height = 1000;
		// route and locations are initially null
		route = null;
		locations = null;	
		this.setPreferredSize(new Dimension(1000, 1000));	
		// load the image
		try {
			img = ImageIO.read(new File(FILE_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// get the ratio of display/actual width and height 
		// in order to draw the route on the correct position 
		// of the map
		width_ratio = width * 1.0 / img.getWidth();
		height_ratio = height * 1.0 / img.getHeight();
	}
	
	/**
	 * Sets the view to its initial state
	 * @modifies route
	 * @effect set route to its initial state, null
	 */
	public void reset() {
		route = null;
		repaint();
	}
	
	/**
	 * Get the shortest walking route given the full name of 
	 * start and end building
	 * @param start the full name of the starting building
	 * @param end the full name of the ending building
	 * @return total distance of the shortest walking route in feet
	 */
	public double findShortestRoute(String start, String end) {
		Location startLoc = m.getLocation(m.getShortName(start));
		Location endLoc = m.getLocation(m.getShortName(end));
		route = m.findShortestRoute(startLoc, endLoc);
		locations = new ArrayList<Location>();
		locations.add(m.getLocation(start));
		double distance = 0.0;
		for (Location l: route.keySet()) {
			locations.add(l);
			distance += (route.get(l));
		}
		repaint();
		return distance;		
	 }
	 
	/**
	 * Paint the view component on the given Graphics
	 * @param g the graphics to use when painting
	 * @modifies GUI the user sees
	 * @effect either display the route between two buildings the user asks, 
	 * 		   clear the route displayed on the map, or resize the map and 
	 * 		   route (if displays) when the frame is resized 
	 */
	 @Override
	 public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    Graphics2D g2d = (Graphics2D) g;
	    
	    width = getWidth();
	    height = getHeight();
	    width_ratio = width * 1.0 / img.getWidth();
		height_ratio = height * 1.0 / img.getHeight();
	    
	    // draw campus map
		g2d.drawImage(img, 0, 0, width, height, 
				0, 0, img.getWidth(), img.getHeight(), null);
		
		if (route != null) {
			locations = new ArrayList<Location>(route.keySet());
			Location current = locations.get(0);	
			
			// store starting points in order to mark the point later
			int startX = (int) Math.round(current.getX() * width_ratio);
			int startY = (int) Math.round(current.getY() * height_ratio);
			
			// get the start building's position of the shortest route
			int currentX = startX;
			int currentY = startY;
			
			// draw the shortest route on the map in red
			g2d.setColor(Color.RED);
			for (Location l: locations) {
				Location next = l;
				int nextX = (int) Math.round(next.getX() * width_ratio);
				int nextY = (int) Math.round(next.getY() * height_ratio);
				g2d.drawLine(currentX, currentY, nextX, nextY);
				currentX = nextX;
				currentY = nextY;
			}	
			
			// mark the start building's position of the shortest route 
			// by a blue rectangle
			g2d.setColor(Color.BLUE);
			g2d.fillRect(startX - 4, startY - 4, 8, 8);	
			
			// mark the end building's position of the shortest route 
			// by a yellow rectangle
			g2d.setColor(Color.YELLOW);
			g2d.fillRect(currentX - 4, currentY - 4, 8, 8);
		}	
 	}   
}
