package hw7.test;

import hw5.LabeledMultiGraph;
import hw7.MarvelPaths2;
import hw5.Node;

import org.junit.Before;
import org.junit.Test;

public class MarvelPaths2Tests {
	private LabeledMultiGraph<String, Double> g;

	@Before
	public void setUp() throws Exception {
		g = MarvelPaths2.buildGraph("src/hw7/data/animal.tsv");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildGraphWithNullInput() throws Exception {
		MarvelPaths2.buildGraph(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testminimumCostPathWithNullGraph() {
		MarvelPaths2.minPath(new Node<String>("Otter"),new Node<String>("Monkey"), null);
	}

	

	@Test(expected = IllegalArgumentException.class)
	public void testminPathWithNullStartOfPath() {
		MarvelPaths2.minPath(null, new Node<String>("Otter"), g);

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testminPathWithNullEndOfPath() {
		MarvelPaths2.minPath(new Node<String>("Otter"), null, g);
	}

}
