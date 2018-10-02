package hw8;

import hw5.Edge;

import hw5.LabeledMultiGraph;
import hw5.Node;
import hw7.MarvelPaths2;
import hw8.CampusDataParser;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CampusPathModel represent the model of the route-finding tool 
 * Location is a class which represent the location of a building
 *
 * Specification fields:
 * @specfiled: shortMap: Map<String, String>
 * @specfiled: fullMap: Map<String, String>
 * @specfiled: locMap: Map<String, Location> 
 * @specfiled: cGraph: LabeledMultiGraph<Location, Double>       
 */
public class CampusPathModel {
	
	// Rep invariant:
		//     fullMap, shortMap, locMap,cGraph != null
		//     Every key of fullMap and the value associated with it are not null.
		//	   Every key of shortMap and the value associated with it are not null.
		//     Every key of locMap and the value associated with it are not null.

		// Abstract function: 
		//     AF(this) = a CampusPathModel m such that
		//		   m.building_names = this.fullMap
		//		   m.building_locations = this.locMap
		//		   m.campus_paths = this.cGraph
	
	// a map of building's names that maps 
	// building's full name to abbreviated name
	private Map<String, String> shortMap;
	
	// a map of building's names that maps 
	// building's abbreviated name to full name
	private Map<String, String> fullMap;
	
	// a map of building's locations that maps 
	// building's abbreviated name to its location
	private Map<String, Location> locMap;
	
	// a graph contains campus paths
	private LabeledMultiGraph<Location, Double> cGraph;
	
	/**
	 * Construct a new CampusPathModel
	 * @requires file names, buildings and paths, passed in is not null
	 * @param buildings name of the file which stores all building data
	 * @param paths name of the file which store all path data
	 * @effect create a new CampusPathModel with data in given files
	 * @throws Exception if the files are not well formed
	 */
	public CampusPathModel(String buildings, String paths) throws Exception {
		if (buildings == null && paths == null) {
			throw new IllegalArgumentException("buildings and paths files passed in " +
					"cannot be null.");
		}
		if (buildings == null) {
			throw new IllegalArgumentException("buildings file passed in cannot be null");
		}
		if (paths == null) {
			throw new IllegalArgumentException("paths file passed in cannot be null");
		}
				
		fullMap = new HashMap<String, String>();	
		shortMap = new HashMap<String, String>();		
		locMap = new HashMap<String,Location>();	
		cGraph = new LabeledMultiGraph<Location, Double>();
		
		CampusDataParser.parseBuildingData(buildings, shortMap, fullMap, locMap);
		CampusDataParser.buildCampusGraph(paths, cGraph);
		checkRep();
	}
	
	/**
	 * Returns a map that contains buildings abbreviated name 
	 * associated with its full name.
	 * @return a map contains buildings abbreviated name associated with 
	 * 		   its full name
	 */
	public Map<String, String> getBuildingNames() {
		checkRep();
		return new HashMap<String, String>(fullMap);		
	}
	
	/**
	 * Returns the full name of the specified building.
	 * @require shortName != null and building is one of 
	 * 			the buildings on campus
	 * @param shortName: abbreviated name of a building on campus
	 * @return String: the full name of the specified building
	 */
	public String getFullName(String shortName) {
		checkRep();
		if (shortName == null) {
			throw new IllegalArgumentException("Building name passed in cannot be null");
		}
		if (!fullMap.containsKey(shortName)) {
			throw new IllegalArgumentException("Building name passed is not one"
					+ "of the buidlings on campus");
		}
		checkRep();
		return fullMap.get(shortName);
	}
	
	/**
	 * Returns the abbreviate name of the specified building.
	 * @require fullName != null and building is one of 
	 * 			the buildings on campus
	 * @param fullName: full name of a building on campus
	 * @return String: the abbreviate name of the specified building
	 */
	public String getShortName(String fullName) {
		checkRep();
		if (fullName == null) {
			throw new IllegalArgumentException("Building name passed in cannot be null");
		}
		if (!shortMap.containsKey(fullName)) {
			throw new IllegalArgumentException("Building name passed is not one"
					+ "of the buidlings on campus");
		}
		checkRep();
		return shortMap.get(fullName);
	}
	
	/**
	 * Returns the location of the specified building.
	 * @require shortName != null
	 * @param shortName: abbreviated name of a building on campus
	 * @return Location: the location of the specified building
	 */
	public Location getLocation(String shortName) {
		checkRep();
		if (shortName == null) {
			throw new IllegalArgumentException("Building name passed in cannot be null");
		}
		checkRep();
		return locMap.get(shortName);
	}
	
	/**
	 * Finds the shortest walking route from one point to another point on campus.
	 * @param start: starting location of the walking route
	 * @param end: end location of the walking route
	 * @requires start, end != null, and start and end are nodes in cGraph
	 * @return the shortest walking route from start to end, or null if 
	 * 		   no path exists from start to end, or start or end is not in graph
	 */
	public Map<Location, Double> findShortestRoute(Location start,
			Location end) {
		Node<Location> startNode = new Node<Location>(start);
		Node<Location> endNode = new Node<Location>(end);
		List<Edge<Location, Double>> route = 
				MarvelPaths2.minPath(startNode, endNode, cGraph);
		if (route == null) {
			return null;
		}
		Map<Location, Double> route_map = new LinkedHashMap<Location, Double>();
		for (Edge<Location, Double> path : route) {
			route_map.put(path.getChild().getData(), path.getLabel());
		}
		return route_map;
	}
	
	/**
	 * Checks if representation invariant holds.
	 */
	public void checkRep() {
		if (fullMap == null) {
			throw new RuntimeException("map of buildings' names is null");
		}
		if (shortMap == null) {
			throw new RuntimeException("map of buildings' names is null");
		}
		if (locMap == null) {
			throw new RuntimeException("map of buildings' locations is null");
		}
		if (cGraph == null) {
			throw new RuntimeException("graph of campus is null");
		}
		Set<String> names = fullMap.keySet();
		for (String name : names) {
			if (name == null) {
				throw new RuntimeException("abbreviated name of the building cannot be null.");
			}
			if (fullMap.get(name) == null) {
				throw new RuntimeException("full name of the building cannot be null.");
			}
		}
		Set<String> names2 = shortMap.keySet();
		for (String name2 : names2) {
			if (name2 == null) {
				throw new RuntimeException("full name of the building cannot be null.");
			}		
			if (shortMap.get(name2) == null) {
				throw new RuntimeException("abbreviated name of the building cannot be null.");
			}
		}
		Set<String> locations = locMap.keySet();
		for (String name : locations) {
			if (name == null) {
				throw new RuntimeException("abbreviated name of the building cannot be null.");
			}		
			if (locMap.get(name) == null) {
				throw new RuntimeException("location of the building cannot be null.");
			}
		}
	}
}

