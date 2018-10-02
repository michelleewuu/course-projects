package hw8.test;

import static org.junit.Assert.*;
import hw5.LabeledMultiGraph;
import hw5.Node;
import hw8.CampusDataParser;
import hw8.Location;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


public class CampusDataParserTest {
	
	private Map<String, String> fullMap;
	private Map<String, String> shortMap;
	private Map<String, Location> locMap;
	private LabeledMultiGraph<Location, Double> cGraph;
	
	@Before
	public void setUp() throws Exception {
		fullMap = new HashMap<String, String>();
		shortMap = new HashMap<String, String>();
		locMap = new HashMap<String, Location>();
		cGraph = new LabeledMultiGraph<Location, Double>();
	}
	
	@Test
	public void testParseEmptyBuildingData() throws Exception {
		CampusDataParser.parseBuildingData("src/hw8/data/emptyBuildings.dat", shortMap,
				fullMap, locMap);
		assertEquals(new HashMap<String,String>(), shortMap);
		assertEquals(new HashMap<String,String>(), fullMap);
		assertEquals(new HashMap<String,Location>(), locMap);	
	}
	
	@Test
	public void testParseEmptyPathsData() throws Exception {
		CampusDataParser.buildCampusGraph("src/hw8/data/emptyPaths.dat", cGraph);
		assertTrue(cGraph.isEmpty());
	}
	
	@Test
	public void testParseBuildingDataWithTwoBuildings() throws Exception {
		CampusDataParser.parseBuildingData("src/hw8/data/twoBuildings.dat", 
				shortMap, fullMap, locMap);
		Map<String, String> sMap = new HashMap<String, String>();
		Map<String, Location> lMap = new HashMap<String, Location>();
		sMap.put("D1", "Dormitory1");
		sMap.put("D2", "Dormitory2");
		lMap.put("D1", new Location(0, 0));
		lMap.put("D2", new Location(3, 4));
		assertEquals(sMap, fullMap);
		assertEquals(lMap, locMap);
	}
	
	@Test
	public void testParsePathsDataWithTwoPaths() throws Exception {
		CampusDataParser.buildCampusGraph("src/hw8/data/twoPaths.dat", cGraph);
		LabeledMultiGraph<Location, Double> cg = 
				new LabeledMultiGraph<Location, Double>();
		Node<Location> d1 = new Node<Location>(new Location(0, 0));
		Node<Location> d2 = new Node<Location>(new Location(3, 4));
		cg.addNode(d1);
		cg.addNode(d2);
		cg.addEdge(5.0, d1, d2);
		cg.addEdge(5.0, d2, d1);
		assertEquals(cg.getNodes(), cGraph.getNodes());
		for (Node<Location> n: cg.getNodes()) {
			assertEquals(cg.getEdges(n), cGraph.getEdges(n));
		}
	}
	
	@Test
	public void testParseBuildingDataWithTwoBuildingsAndComment() throws Exception {
		CampusDataParser.parseBuildingData("src/hw8/data/twoBuildingsWithComment.dat", 
				shortMap, fullMap, locMap);
		Map<String, String> sMap = new HashMap<String, String>();
		Map<String, Location> lMap = new HashMap<String, Location>();
		sMap.put("D1", "Dormitory1");
		sMap.put("D2", "Dormitory2");
		lMap.put("D1", new Location(0, 0));
		lMap.put("D2", new Location(3, 4));
		assertEquals(sMap, fullMap);
		assertEquals(lMap, locMap);
	}
	
	@Test
	public void testParsePathsDataWithTwoPathsAndComment() throws Exception {
		CampusDataParser.buildCampusGraph("src/hw8/data/twoPathsWithComment.dat", cGraph);
		LabeledMultiGraph<Location, Double> cg = 
				new LabeledMultiGraph<Location, Double>();
		Node<Location> d1 = new Node<Location>(new Location(0, 0));
		Node<Location> d2 = new Node<Location>(new Location(3, 4));
		cg.addNode(d1);
		cg.addNode(d2);
		cg.addEdge(5.0, d1, d2);
		cg.addEdge(5.0, d2, d1);
		assertEquals(cg.getNodes(), cGraph.getNodes());
		for (Node<Location> n: cg.getNodes()) {
			assertEquals(cg.getEdges(n), cGraph.getEdges(n));
		}
	}
	
	// tokens not separated by tab character
	@Test(expected = Exception.class)
	public void testParseBadFormatedBuildingData() throws Exception {
		CampusDataParser.parseBuildingData("src/hw8/data/badBuildings.dat", 
				shortMap, fullMap, locMap);
	}
	
	// bad formated on indented line
	@Test(expected = Exception.class)
	public void testBuildBadFormatedPaths() throws Exception {
		CampusDataParser.buildCampusGraph("src/hw8/data/badPaths.dat", cGraph);
	}
	
	// indented line comes before non-indented line
	@Test(expected = Exception.class)
	public void testBuildBadFormatedPaths2() throws Exception {
		CampusDataParser.buildCampusGraph("src/hw8/data/badPaths2.dat", cGraph);
	}
	
	@Test
	public void testParseDormBuildingData() throws Exception {
		CampusDataParser.parseBuildingData("src/hw8/data/dormBuildings.dat",
				shortMap, fullMap, locMap);
		Map<String, String> nameList = new HashMap<String, String>();
		Map<String, Location> locList = new HashMap<String, Location>();
		nameList.put("HAG", "Haggett");
		nameList.put("MCH", "McMahon");
		nameList.put("HSN", "Hansee");
		nameList.put("LDR", "Lander");
		nameList.put("MPL", "Maple");
		nameList.put("PLR", "Poplar");
		locList.put("HAG", new Location(3, 0));
		locList.put("MCH", new Location(0, 4));
		locList.put("HSN", new Location(4, 7));
		locList.put("LDR", new Location(7, 3));
		locList.put("MPL", new Location(10, 10));
		locList.put("PLR", new Location(15, 15));
		assertEquals(nameList, fullMap);
		assertEquals(locList, locMap);	
	}
	
	@Test
	public void testDormBuildPaths() throws Exception {
		CampusDataParser.buildCampusGraph("src/hw8/data/dormPaths.dat", cGraph);
		LabeledMultiGraph<Location, Double> cp = new LabeledMultiGraph<Location, Double>();
		Node<Location> hag = new Node<Location>(new Location(3, 0));
		Node<Location> mch = new Node<Location>(new Location(0, 4));
		Node<Location> hsn = new Node<Location>(new Location(4, 7));
		Node<Location> ldr = new Node<Location>(new Location(7, 3));
		Node<Location> mpl = new Node<Location>(new Location(10, 10));
		Node<Location> plr = new Node<Location>(new Location(15, 15));
		cp.addNode(hag);
		cp.addNode(mch);
		cp.addNode(hsn);
		cp.addNode(ldr);
		cp.addNode(mpl);
		cp.addNode(plr);
		cp.addEdge(1.0, hag, mch);
		cp.addEdge(4.0, hag, hsn);
		cp.addEdge(3.0, hag, ldr);
		cp.addEdge(1.0, mch, hag);
		cp.addEdge(2.0, mch, hsn);
		cp.addEdge(3.0, mch, ldr);
		cp.addEdge(4.0, hsn, hag);
		cp.addEdge(2.0, hsn, mch);
		cp.addEdge(3.0, hsn, ldr);
		cp.addEdge(3.0, ldr, hag);
		cp.addEdge(3.0, ldr, mch);
		cp.addEdge(3.0, ldr, hsn);
		cp.addEdge(5.0, mpl, plr);
		cp.addEdge(5.0, plr, mpl);
		assertEquals(cp.getNodes(), cGraph.getNodes());
		for (Node<Location> n: cp.getNodes()) {
			assertEquals(cp.getEdges(n), cGraph.getEdges(n));
		}
	}
}
