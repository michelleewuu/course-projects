package hw9;

import hw8.CampusPathModel;

/**
 * CampusPathsMain puts together all the MVC components and 
 * allows the user to ask for the shortest route between two 
 * buildings on campus.
 */

public class CampusPathsMain {
	public static void main(String[] args) throws Exception {
		CampusPathModel m =  
				new CampusPathModel("src/hw8/data/campus_buildings.dat", 
											"src/hw8/data/campus_paths.dat");
		new CampusPathFindingGUI(m);
	}
}
