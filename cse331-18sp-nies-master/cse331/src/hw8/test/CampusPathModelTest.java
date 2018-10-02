package hw8.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hw5.Edge;
import hw5.Node;
import hw8.CampusPathModel;
import hw8.Location;

import org.junit.Test;


public class CampusPathModelTest {
	private static final int TIMEOUT = 2000;
	
	@Test(timeout = TIMEOUT)
	public void testConstructModelSuccessWithNoException() throws Exception {
		new CampusPathModel("src/hw8/data/dormBuildings.dat", 
									"src/hw8/data/dormPaths.dat");
	}
	
	@Test(timeout = TIMEOUT, expected = Exception.class)
	public void testConstructModelThrowsExceptionBadBuildingsData() throws Exception {
		new CampusPathModel("src/hw8/data/badBuildings.dat", 
									"src/hw8/data/sportsPaths.dat");
	}

	@Test(timeout = TIMEOUT, expected = Exception.class)
	public void testConstructModelThrowsExceptionBadPathsData() throws Exception {
		new CampusPathModel("src/hw8/data/dormBuildings.dat", 
									"src/hw8/data/badPaths.dat");
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testConstructModelWithBothArgumentsNull() throws Exception {
		new CampusPathModel(null, null);
	}

	@Test(timeout = TIMEOUT, expected =IllegalArgumentException.class)
	public void testConstructModelWithPathsNull() throws Exception {
		new CampusPathModel("src/hw8/data/dormBuildings.dat", null);
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testConstructModelWithBuldingsNull() throws Exception {
		new CampusPathModel(null, "src/hw8/data/dormPaths.dat");
	}

	@Test(timeout = TIMEOUT)
	public void testGetBuildingsEmptyBuildings() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/emptyBuildings.dat", 
						"src/hw8/data/emptyPaths.dat");
		assertEquals(new HashMap<String, String>(), model.getBuildingNames());
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetFullNameOfBuildingWithNullArgument() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/emptyBuildings.dat", 
						"src/hw8/data/emptyPaths.dat");
		model.getFullName(null);
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetAbbreviatedNameOfBuildingWithNullArgument() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/emptyBuildings.dat", 
						"src/hw8/data/emptyPaths.dat");
		model.getShortName(null);
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetFullNameOfBuildingWithNonExistingBuilding() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/emptyBuildings.dat", 
						"src/hw8/data/emptyPaths.dat");
		model.getFullName("HAG");
	}

	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetAbbreviatedNameOfBuildingWithNonExistingBuilding() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/emptyBuildings.dat", 
						"src/hw8/data/emptyPaths.dat");
		model.getShortName("Haggett");
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetLocationOfBuildingWithNullArgument() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/emptyBuildings.dat", 
						"src/hw8/data/emptyPaths.dat");
		model.getLocation(null);
	}

	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingWithNonExistingBuilding() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/emptyBuildings.dat", 
						"src/hw8/data/emptyPaths.dat");
		assertTrue(model.getLocation("HAG") == null);
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithBothArgumentNull() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/emptyBuildings.dat", 
						"src/hw8/data/emptyPaths.dat");
		model.findShortestRoute(null, null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithStartingLocationNull() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/emptyBuildings.dat", 
						"src/hw8/data/emptyPaths.dat");
		model.findShortestRoute(null, new Location(0, 0));
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithEndingLocationNull() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/emptyBuildings.dat", 
						"src/hw8/data/emptyPaths.dat");
		model.findShortestRoute(new Location(0, 0), null);
	}

	@Test(timeout = TIMEOUT)
	public void testGetBuildingsTwoBuildings() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/twoBuildings.dat", 
						"src/hw8/data/twoPaths.dat");
		Map<String, String> bn = new HashMap<String, String>();
		bn.put("D1", "Dormitory1");
		bn.put("D2", "Dormitory2");
		assertEquals(bn, model.getBuildingNames());
	}

	@Test(timeout = TIMEOUT)
	public void testGetFullNameOfBuildingTwoBuildingsWithD1() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/twoBuildings.dat", 
						"src/hw8/data/twoPaths.dat");
		assertEquals("Dormitory1", model.getFullName("D1"));
	}

	

	@Test(timeout = TIMEOUT)
	public void testGetAbbreviatedNameOfBuildingTwoBuildingsWithDormitary1() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/twoBuildings.dat", 
						"src/hw8/data/twoPaths.dat");
		assertEquals("D1", model.getShortName("Dormitory1"));
	}

	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingTwoBuildingsWithD1() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/twoBuildings.dat", 
						"src/hw8/data/twoPaths.dat");
		assertEquals(new Location(0, 0), model.getLocation("D1"));
	}

	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteWithBothExistingLocations() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/twoBuildings.dat", 
						"src/hw8/data/twoPaths.dat");
		Location d1 = new Location(0, 0);
		Location d2 = new Location(3, 4);
		Map<Location, Double> route = new LinkedHashMap<Location, Double>();
		route.put(d1, 0.0);
		route.put(d2, 5.0);
		assertEquals(route, model.findShortestRoute(model.getLocation("D1"), 
				model.getLocation("D2")));
	}

	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteWithBothExistingLocationsReverseOrder() throws Exception {
		CampusPathModel model = 
				new CampusPathModel("src/hw8/data/twoBuildings.dat", 
						"src/hw8/data/twoPaths.dat");
		Location d1 = new Location(0, 0);
		Location d2 = new Location(3, 4);
		Map<Location, Double> route = new LinkedHashMap<Location, Double>();
		route.put(d2, 0.0);
		route.put(d1, 5.0);
		assertEquals(route, model.findShortestRoute(model.getLocation("D2"), 
				model.getLocation("D1")));
	}

	@Test(timeout = TIMEOUT)
	public void testGetSportsBuildings() throws Exception {
		CampusPathModel model =
				new CampusPathModel("src/hw8/data/dormBuildings.dat", 
						"src/hw8/data/dormPaths.dat");
		Map<String, String> nameList = new HashMap<String, String>();
		nameList.put("HAG", "Haggett");
		nameList.put("MCH", "McMahon");
		nameList.put("HSN", "Hansee");
		nameList.put("LDR", "Lander");
		nameList.put("MPL", "Maple");
		nameList.put("PLR", "Poplar");
		assertEquals(nameList, model.getBuildingNames());
	}

	@Test(timeout = TIMEOUT)
	public void testGetFullNameOfBuildingSportsBuildingsWithHAG() throws Exception {
		CampusPathModel model =
				new CampusPathModel("src/hw8/data/dormBuildings.dat", 
						"src/hw8/data/dormPaths.dat");
		assertEquals("Haggett", model.getFullName("HAG"));
	}

	@Test(timeout = TIMEOUT)
	public void testGetAbbreviatedNameOfBuildingSportsBuildingsWithHAG() throws Exception {
		CampusPathModel model =
				new CampusPathModel("src/hw8/data/dormBuildings.dat", 
						"src/hw8/data/dormPaths.dat");
		assertEquals("HAG", model.getShortName("Haggett"));
	}

	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingSportsBuildingsWithHAG() throws Exception {
		CampusPathModel model =
				new CampusPathModel("src/hw8/data/dormBuildings.dat", 
						"src/hw8/data/dormPaths.dat");	
		assertEquals(new Location(3, 0), model.getLocation("HAG"));
	}

	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteFromHAGToMPL() throws Exception {
		CampusPathModel model =
				new CampusPathModel("src/hw8/data/dormBuildings.dat", 
						"src/hw8/data/dormPaths.dat");	
		Location hag = model.getLocation("HAG");
		Location mpl = model.getLocation("MPL");
		assertTrue(model.findShortestRoute(hag, mpl) == null);
	}

	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteFromHAGToHSN() throws Exception {
		CampusPathModel model =
				new CampusPathModel("src/hw8/data/dormBuildings.dat", 
						"src/hw8/data/dormPaths.dat");
		Location hag = model.getLocation("HAG");
		Location hsn = model.getLocation("HSN");
		Location mch = model.getLocation("MCH");
		Map <Location,Double> route = new LinkedHashMap<Location, Double>();
		route.put(hag, 0.0);
		route.put(mch, 1.0);
		route.put(hsn, 2.0);
		assertEquals(route, model.findShortestRoute(hag, hsn));
	}
	
	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteFromLDRToMCH() throws Exception {
		CampusPathModel model =
				new CampusPathModel("src/hw8/data/dormBuildings.dat", 
						"src/hw8/data/dormPaths.dat");
		Location ldr = model.getLocation("LDR");
		Location mch = model.getLocation("MCH");
		Map <Location,Double> route = new LinkedHashMap<Location, Double>();
		route.put(ldr, 0.0);
		route.put(mch, 3.0);
		assertEquals(route, model.findShortestRoute(ldr, mch));
	}
}
