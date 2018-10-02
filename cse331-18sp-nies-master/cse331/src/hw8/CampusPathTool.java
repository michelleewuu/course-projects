package hw8;

import java.util.*;

import hw5.Edge;
import hw5.Node;

/**
 * CampusPathTool is the view, controller, and the main 
 * of the CampusPathTool. This class allows user to 
 * type in some command to call appropriate method from view 
 * to get information from the model.
 */

public class CampusPathTool {
	
	/**
	 * Lists all buildings' names (both abbreviated name and full name).
	 * @requires m != null
	 * @param m: model of the CampusPathTool
	 * @effects print out all buildings' names
	 */
	public static void printAllBuildings(CampusPathModel m) {
		if (m == null) {
			throw new IllegalArgumentException("Campus Model passed in cannot be null");
		}
		String nameList = "Buildings:\n";
		Map<String,String> names = m.getBuildingNames();
		Set<String> sortedShortNames = new TreeSet<String>(names.keySet());
		for (String shortName: sortedShortNames) {
			String fullName = names.get(shortName);
			nameList += "\t" + shortName + ": " + fullName + "\n";
		}
		System.out.println(nameList);
	}
	
	/**
	 * Print menu
	 * @effects print out menu
	 */
	public static void printMenu() {
		String menu = "Menu:\n" + "\t" + "r to find a route\n" +
						  "\t" + "b to see a list of all buildings\n" +
					      "\t" + "q to quit\n";
		System.out.println(menu);
	}
	
	/**
	 * Get direction based on the start and end locations passed in.
	 * Possible output is N, E, S, W, NE, NW, SE, or SW
	 * @param start: start location of a specified part of the path
	 * @param end: end location of a specified part of the path
	 * @return String: the direction of the specified part of the path
	 */
	public static String getDirection(Location start, Location end) {
		double deltX = start.getX() - end.getX();
		double deltY = start.getY() - end.getY();
		double slope = deltY/deltX;
		double angle = Math.atan(slope);
		double pi = Math.PI;
		String dir = "";
		if(angle > -3*pi/8 && angle < -1*pi/8) {
			if (deltY > 0) {
				dir = "NE";
			} else {
				dir = "SW";
			}
		} else if(angle > pi/8 && angle < 3*pi/8) {
			if (deltX > 0) {
				dir = "NW";
			} else {
				dir = "SE";
			}
		} else if (angle >= -1*pi/8 && angle <= pi/8) {
			if (deltX > 0) {
				dir = "W";
			} else {
				dir = "E";
			}
		} else if (deltY > 0) {
			dir = "N";
		} else if (deltY != 0){
			dir ="S";
		}
		return dir;
	}
	
	/**
	 * Gets the shortest walking route from one building to another building on campus.
	 * @requires m, start, end != null and 
	 *           start and end are buildings' abbreviated names
	 * @param m: model of the CampusRouteFindingTool
	 * @param start: starting building's abbreviated name of the path
	 * @param end: ending building's abbreviated name of the path
	 * @effects print out the shortest path from start building to end building
	 *          print out "unknown character" if the names given is not a building's name
	 */
	public static void getShortestPath(CampusPathModel m, String start, String end) {
		if (m == null) {
			throw new IllegalArgumentException("model cannot be null.");
		}
		if (start == null) {
			throw new IllegalArgumentException("start cannot be null.");
		}
		if (end == null) {
			throw new IllegalArgumentException("end cannot be null.");
		}		
		if (!m.getBuildingNames().containsKey(start) && 
				!m.getBuildingNames().containsKey(end)) {
			System.out.println("Unknown building: " + start);
			System.out.println("Unknown building: " + end);
		} else if (!m.getBuildingNames().containsKey(start)) {
			System.out.println("Unknown building: " + start);
		} else if (!m.getBuildingNames().containsKey(end)) {
			System.out.println("Unknown building: " + end);
		} else {
			Location startLoc = m.getLocation(start);
			Location endLoc = m.getLocation(end);
			String fullStart = m.getFullName(start);
			String fullEnd = m.getFullName(end);
			String result = "Path from " + fullStart + " to " + fullEnd + ":";
			Map<Location, Double> shortest_route = m.findShortestRoute(startLoc, endLoc);
			List<Location> locations = new ArrayList<Location>(shortest_route.keySet());
			locations = locations.subList(1, locations.size());
			Location current = startLoc;
			double totalDistance = 0.0;
			for (Location l: locations) {
				Location next = l;
				String dir = getDirection(current, next);
				String nextCoord = "(" + String.format("%.0f",next.getX()) +
						", " + String.format("%.0f",next.getY()) + ")";
				result += "\n" + "\t" + "Walk " + String.format("%.0f",shortest_route.get(l)) + 
				" feet " + dir + " to " +  nextCoord;
				totalDistance += shortest_route.get(l);
				current = next;		
			}
			result += "\n" + "Total distance: " + String.format("%.0f",totalDistance)
			 + " feet";
			System.out.println(result);
		}		
	}
	
	/**
	 * Main method allows user to find shortest walking route 
	 * between two buildings and get all the buildings' names 
	 * on campus.
	 * @param args
	 * @throws Exception is the filed needed can not be found
	 */
	public static void main(String[] args) {
		try {
			CampusPathModel m = new CampusPathModel("src/hw8/data/campus_buildings.dat", 
											"src/hw8/data/campus_paths.dat");
			printMenu();
			Scanner reader = new Scanner(System.in);
			System.out.print("Enter an option ('m' to see the menu): ");
			while (true) {
				String input = reader.nextLine();
				if (input.length() == 0 || input.startsWith("#")) {
					System.out.println(input);
					continue;
				}
				if (input.equals("m")) {
					printMenu();
				} else if (input.equals("b")) {
					printAllBuildings(m);
				} else if (input.equals("r")) {
					System.out.print("Abbreviated name of starting building: ");
					String start = reader.nextLine();
					System.out.print("Abbreviated name of ending building: ");
					String end = reader.nextLine();
					getShortestPath(m, start, end);
					System.out.println();
				} else if (input.equals("q")) {
					reader.close();
					return;
				} else {
					System.out.println("Unknown option\n");
				}
				System.out.print("Enter an option ('m' to see the menu): ");
			}
		} catch (Exception e) {
			System.err.println(e.toString());	
	    	e.printStackTrace(System.err);	
		}
	}
}
