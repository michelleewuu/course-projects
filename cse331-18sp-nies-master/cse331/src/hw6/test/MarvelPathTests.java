package hw6.test;

import hw6.MarvelPaths;
import hw5.LabeledMultiGraph;
import hw5.Node;
import org.junit.Before;
import org.junit.Test;


public class MarvelPathTests {
	private LabeledMultiGraph<String,String> g;
	
	@Before
	public void setUp() throws Exception {
		g = MarvelPaths.buildGraph("src/hw6/data/food.tsv");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildGraphWithNullInput() throws Exception {
		MarvelPaths.buildGraph(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBFSWithNullGraph() {
		MarvelPaths.BFS(new Node<String>("CheeseCake"), new Node<String>("CarrotCake"), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBFSWithNullStartOfPath() {
		MarvelPaths.BFS(null, new Node<String>("CarrotCake"), g);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBFSWithNullEndOfPath() {
		MarvelPaths.BFS(new Node<String>("CarrotCake"), null, g);
	}

}
