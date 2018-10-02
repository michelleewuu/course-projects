package hw5.test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import hw5.LabeledMultiGraph;
import hw5.Node;

public class GraphTests {

	private final Node<String> NODE_A = new Node<String>("a");
	private final Node<String> NODE_B = new Node<String>("b");
	
	private LabeledMultiGraph<String,String> graph = new LabeledMultiGraph<String,String>();
	private HashSet<Node<String>> nodes = new HashSet<Node<String>>();
	private HashSet<Node<String>> nodes2 = new HashSet<Node<String>>();
	
	@Before
	public void buildNodes2() {
		nodes2.add(NODE_A);
		nodes2.add(NODE_B);
	}
	
	
	@Test
	public void testIsEmptyWhenConstructed() {
		assertTrue(graph.isEmpty());
	}
	
	@Test
	public void testSizeWhenConstructed() {
		assertEquals(0, graph.size());
	}
	
	@Test
	public void testGetNodesWhenConstructed() {
		assertEquals(nodes,graph.getNodes());
	}
	
	@Test
	public void testAddingOneNode() {
		assertTrue(graph.addNode(NODE_A));
	}
	
	@Test
	public void testIsEmptyAfterAddingOneNode() {
		testAddingOneNode();
		assertFalse(graph.isEmpty());
	}
	
	@Test
	public void testSizeAfterAddingOneNode() {
		testAddingOneNode();
		assertEquals(1, graph.size());
	}
	
	@Test
	public void testContainsNodeAAfterAddingNodeA() {
		testAddingOneNode();
		assertTrue(graph.containsNode(NODE_A));
	}
	
	@Test
	public void testNotContainsNodeBAfterAddingNodeA() {
		testAddingOneNode();
		assertFalse(graph.containsNode(NODE_B));
	}
	
	@Test
	public void testChildrenOfNodeAWithoutAddingEdge() {
		testAddingOneNode();
		assertTrue(graph.getChildren(NODE_A).isEmpty());
	}
	
	@Test
	public void testGetNodesAfterAddingOneNode() {
		testAddingOneNode();
		nodes.add(NODE_A);
		assertEquals(nodes,graph.getNodes());
	}
	
	@Test
	public void testAddingSameNodeTwice() {
		testAddingOneNode();
		assertFalse(graph.addNode(NODE_A));
	}
	
	@Test
	public void testSizeAfterAddingSameNodeTwice() {
		testAddingSameNodeTwice();
		assertEquals(1,graph.size());
	}
	
	@Test
	public void testAddingTwoDifferentNodes() {
		testAddingOneNode();
		assertTrue(graph.addNode(NODE_B));
	}
	
	@Test
	public void testGettingDataAfterAddingTwoDifferentNodes() {
		testAddingTwoDifferentNodes();
		assertEquals(graph.getData(NODE_A), "a");
		assertEquals(graph.getData(NODE_B),"b");
	}
	
	@Test
	public void testSizeAfterAddingTwoDifferentNodes() {
		testAddingTwoDifferentNodes();
		assertEquals(2,graph.size());
	}
	
	@Test
	public void testGetNodesAfterAddingTwoDifferentNodes() {
		testAddingTwoDifferentNodes();
		nodes.add(NODE_A);
		nodes.add(NODE_B);
		assertEquals(nodes,graph.getNodes());
	}
	
	@Test
	public void testNumberOfEdgesFromNodeAToNodeBWithoutAddingEdge() {
		testAddingTwoDifferentNodes();
		assertEquals(0,graph.numberOfEdges(NODE_A, NODE_B));
	}
	
	@Test
	public void testNumberOfEdgesFromNodeBToNodeAWithoutAddingEdge() {
		testAddingTwoDifferentNodes();
		assertEquals(0,graph.numberOfEdges(NODE_B, NODE_A));
	}
	
	@Test
	public void testAddingReflexiveEdgeOnNodeA() {
		testAddingOneNode();
		assertTrue(graph.addEdge("AA", NODE_A, NODE_A));
		/*HashSet<Node> childrenOfA = new HashSet<Node>();
		childrenOfA.add(NODE_A);
		assertEquals(childrenOfA, graph.getChildren(NODE_A));*/
	}
	
	@Test
	public void testGetChildrenAfterAddingReflexiveEdge() {
		testAddingReflexiveEdgeOnNodeA();
		HashSet<Node<String>> childrenOfA = new HashSet<Node<String>>();
		childrenOfA.add(NODE_A);
		assertEquals(childrenOfA, graph.getChildren(NODE_A));
	}
	
	@Test
	public void testNumberOfEdgesFromNodeAToNodeAAfterAddingReflexiveEdge() {
		testAddingReflexiveEdgeOnNodeA();
		assertEquals(1,graph.numberOfEdges(NODE_A, NODE_A));
	}
	
	@Test
	public void testRemoveReflexiveEdgeOnNodeA() {
		testAddingReflexiveEdgeOnNodeA();
		assertTrue(graph.removeEdge("AA", NODE_A, NODE_A));
		assertTrue(graph.getChildren(NODE_A).isEmpty());
	}
	
	@Test
	public void testRemoveEdgeOnNodeAWithNonExistingEdge() {
		testAddingReflexiveEdgeOnNodeA();
		assertFalse(graph.removeEdge("AA", NODE_A, NODE_B));
	}
	
	@Test
	public void testRemoveEdgeWithExistingEdgeButDifferentLabel() {
		testAddingReflexiveEdgeOnNodeA();
		assertFalse(graph.removeEdge("AB", NODE_A, NODE_A));
	}
	
	@Test
	public void testAddingOneEdgeBetweenTwoNodes() {
		testAddingTwoDifferentNodes();
		assertTrue(graph.addEdge("AB", NODE_A, NODE_B));
	}
	
	@Test
	public void testChildrenOfNodeAAfterAddingOneEdgeBetweenTwoNodes() {
		testAddingOneEdgeBetweenTwoNodes();
		HashSet<Node<String>> childrenOfA = new HashSet<Node<String>>();
		childrenOfA.add(NODE_B);
		assertEquals(childrenOfA, graph.getChildren(NODE_A));
	}
	
	@Test
	public void testChildrenOfNodeBAfterAddingOneEdgeBetweenNodeAAndNodeB() {
		testAddingOneEdgeBetweenTwoNodes();
		assertTrue(graph.getChildren(NODE_B).isEmpty());
	}
	
	@Test
	public void testNumberOfEdgesFromNodeAToNodeBAfterAddingOneEdgeBetweenNodeAAndNodeB() {
		testAddingOneEdgeBetweenTwoNodes();
		assertEquals(1,graph.numberOfEdges(NODE_A, NODE_B));
	}
	
	@Test
	public void testNumberOfEdgesFromNodeBToNodeAAfterAddingOneEdgeBetweenNodeAAndNodeB() {
		testAddingOneEdgeBetweenTwoNodes();
		assertEquals(0,graph.numberOfEdges(NODE_B, NODE_A));
	}
	
	@Test
	public void testAddingEdgeWithOnlyDifferentLableAfterAddingOneEdge() {
		testAddingOneEdgeBetweenTwoNodes();
		assertTrue(graph.addEdge("AB2",NODE_A, NODE_B));
	}
	
	@Test
	public void testNumberOfEdgesFromAToBAfterAddingEdgeWithOnlyDifferentLable() {
		testAddingEdgeWithOnlyDifferentLableAfterAddingOneEdge(); 
		assertEquals(2, graph.numberOfEdges(NODE_A, NODE_B));
	}
	
	@Test
	public void testAddingSameEdgeAfterAddingOneEdgeBetweenTwoNodes() {
		testAddingOneEdgeBetweenTwoNodes();
		assertFalse(graph.addEdge("AB",NODE_A, NODE_B));
		assertEquals(1,graph.numberOfEdges(NODE_A, NODE_B));
	}
	
	@Test
	public void testChildrenOfNodeAAfterAddingSameEdge() {
		testAddingSameEdgeAfterAddingOneEdgeBetweenTwoNodes();
		HashSet<Node<String>> childrenOfA = new HashSet<Node<String>>();
		childrenOfA.add(NODE_B);
		assertEquals(childrenOfA, graph.getChildren(NODE_A));
	}
	
	@Test
	public void testChildrenOfNodeBAfterAddingSameEdge() {
		testChildrenOfNodeAAfterAddingSameEdge();
		assertTrue(graph.getChildren(NODE_B).isEmpty());
	}
	
	@Test
	public void AddingTwoInverseDirectionsEdges() {
		testAddingOneEdgeBetweenTwoNodes();
		assertTrue(graph.addEdge("BA",NODE_B, NODE_A));
	}
	
	@Test
	public void testChildrenOfNodeAAfterAddingTwoInverseDirectionsEdges() {
		AddingTwoInverseDirectionsEdges();
		HashSet<Node<String>> childrenOfA = new HashSet<Node<String>>();
		childrenOfA.add(NODE_B);
		assertEquals(childrenOfA, graph.getChildren(NODE_A));
	}
	
	@Test
	public void testChildrenOfNodeBAfterAddingTwoInverseDirectionsEdges() {
		AddingTwoInverseDirectionsEdges();
		HashSet<Node<String>> childrenOfB = new HashSet<Node<String>>();
		childrenOfB.add(NODE_A);
		assertEquals(childrenOfB, graph.getChildren(NODE_B));
	}
	
	@Test
	public void testNumberofEdgeFromBtoAAfterAddingTwoInverseDirectionEdges() {
		AddingTwoInverseDirectionsEdges();
		assertEquals(1,graph.numberOfEdges(NODE_B, NODE_A));
	}
	
	@Test
	public void testRemoveNodeBAfterAddingTwoInverseDirectionEdges() {
		AddingTwoInverseDirectionsEdges();
		assertTrue(graph.removeNode(NODE_B));
		assertFalse(graph.containsNode(NODE_B));
	}
	
	@Test
	public void testGetChildrenOfAAfterRemovingB() {
		AddingTwoInverseDirectionsEdges();
		graph.removeNode(NODE_B);
		assertTrue(graph.getChildren(NODE_A).isEmpty());
	}
	
	@Test
	public void testSizeAfterRemovingNodeB() {
		testRemoveNodeBAfterAddingTwoInverseDirectionEdges();
		assertEquals(graph.size(), 1);
	}
	
	@Test
	public void testMakingAGraphWithTwoNodesAndFourEdges() {
		testAddingReflexiveEdgeOnNodeA();
		assertTrue(graph.addNode(NODE_B));
		assertTrue(graph.addEdge("AB", NODE_A, NODE_B));
		assertTrue(graph.addEdge("BA", NODE_B, NODE_A));
		assertTrue(graph.addEdge("BB", NODE_B, NODE_B));
	}
	
	@Test
	public void testChildrenOfAAferMakingAGraphWithTwoNodesAndFourEdges() {
		testMakingAGraphWithTwoNodesAndFourEdges();
		HashSet<Node<String>> childrenOfA = new HashSet<Node<String>>();
		childrenOfA.add(NODE_A);
		childrenOfA.add(NODE_B);
		assertEquals(childrenOfA, graph.getChildren(NODE_A));
	}
	
	@Test
	public void testChildrenOfBAferMakingAGraphWithTwoNodesAndFourEdges() {
		testMakingAGraphWithTwoNodesAndFourEdges();
		HashSet<Node<String>> childrenOfB = new HashSet<Node<String>>();
		childrenOfB.add(NODE_A);
		childrenOfB.add(NODE_B);
		assertEquals(childrenOfB, graph.getChildren(NODE_A));
	}
	
	@Test 
	public void testNumberOfEdgesFromAtoBAfterMakingAGraphWithTwoNodesAndFourEdges() {
		testMakingAGraphWithTwoNodesAndFourEdges();
		assertEquals(1, graph.numberOfEdges(NODE_A, NODE_B));
	}
	
	@Test 
	public void testNumberOfEdgesFromAtoAAfterMakingAGraphWithTwoNodesAndFourEdges() {
		testMakingAGraphWithTwoNodesAndFourEdges();
		assertEquals(1, graph.numberOfEdges(NODE_A, NODE_A));
	}
	
	@Test 
	public void testNumberOfEdgesFromBtoAAfterMakingAGraphWithTwoNodesAndFourEdges() {
		testMakingAGraphWithTwoNodesAndFourEdges();
		assertEquals(1, graph.numberOfEdges(NODE_B, NODE_A));
	}
	
	@Test 
	public void testNumberOfEdgesFromBtoBAfterMakingAGraphWithTwoNodesAndFourEdges() {
		testMakingAGraphWithTwoNodesAndFourEdges();
		assertEquals(1, graph.numberOfEdges(NODE_B, NODE_B));
	}
		
}
