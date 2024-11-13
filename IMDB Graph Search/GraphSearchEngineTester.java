import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;
import java.io.*;

/**
 * Code to test an <tt>GraphSearchEngine</tt> implementation.
 */
public class GraphSearchEngineTester {
	@Test
	@Timeout(5)
	void testShortestPath1 () {
		final GraphSearchEngine searchEngine = new GraphSearchEngineImpl();
		final IMDBGraph graph;
		try {
			graph = new IMDBGraphImpl(IMDBGraphImpl.IMDB_DIRECTORY + "/testActors.tsv", IMDBGraphImpl.IMDB_DIRECTORY + "/testMovies.tsv");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			assertTrue(false);
			return;
		}
		final Node actor1 = graph.getActor("Kris");
		final Node actor2 = graph.getActor("Sandy");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		assertEquals(5, shortestPath.size());
		final String[] correctNames = { "Kris", "Blah2", "Sara", "Blah3", "Sandy" };
		int idx = 0;
		for (Node node : shortestPath) {
			assertEquals(correctNames[idx++], node.getName());
		}
	}

	@Test
	@Timeout(60)
	void fullDatabaseTest1 () {
		final GraphSearchEngine searchEngine = new GraphSearchEngineImpl();
		final IMDBGraph graph;
		try {
			graph = new IMDBGraphImpl(IMDBGraphImpl.IMDB_DIRECTORY + "/name.basics.tsv.gz", IMDBGraphImpl.IMDB_DIRECTORY + "/title.basics.tsv.gz");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			assertTrue(false);
			return;
		}
		final Node actor1 = graph.getActor("Tom Cruise");
		final Node actor2 = graph.getActor("Tim Robbins");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		assertEquals(5, shortestPath.size());
		final String[] correctNames = {actor1.getName(), "Minority Report", "Paul M. Lane", "Howard the Duck", actor2.getName()};
		int idx = 0;
		for (Node node : shortestPath) {
			assertEquals(correctNames[idx++], node.getName());
		}
	}

	@Test
	@Timeout(60)
	void fullDatabaseTest2 () {
		final GraphSearchEngine searchEngine = new GraphSearchEngineImpl();
		final IMDBGraph graph;
		try {
			graph = new IMDBGraphImpl(IMDBGraphImpl.IMDB_DIRECTORY + "/name.basics.tsv.gz", IMDBGraphImpl.IMDB_DIRECTORY + "/title.basics.tsv.gz");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			assertTrue(false);
			return;
		}
		final Node actor1 = graph.getActor("Nicolas Cage");
		final Node actor2 = graph.getActor("Robert De Niro");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		assertEquals(7, shortestPath.size());
		final String[] correctNames = {actor1.getName(), "Face/Off", "Dominique Swain", "Lolita 3", "Don Cerrone", "Cape Fear 2", actor2.getName()};
		int idx = 0;
		for (Node node : shortestPath) {
			assertEquals(correctNames[idx++], node.getName());
		}
	}

	@Test
	@Timeout(60)
	void fullDatabaseTest3 () {
		final GraphSearchEngine searchEngine = new GraphSearchEngineImpl();
		final IMDBGraph graph;
		try {
			graph = new IMDBGraphImpl(IMDBGraphImpl.IMDB_DIRECTORY + "/name.basics.tsv.gz", IMDBGraphImpl.IMDB_DIRECTORY + "/title.basics.tsv.gz");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			assertTrue(false);
			return;
		}
		final Node actor1 = graph.getActor("Matt Damon");
		final Node actor2 = graph.getActor("Johnny Depp");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		assertEquals(5, shortestPath.size());
		final String[] correctNames = {actor1.getName(), "The Talented Mr. Ripley", "Jack Davenport", "Pirates of the Caribbean: Dead Man's Chest", actor2.getName()};
		int idx = 0;
		for (Node node : shortestPath) {
			assertEquals(correctNames[idx++], node.getName());
		}
	}

	@Test
	@Timeout(60)
	void fullDatabaseTest4 () {
		final GraphSearchEngine searchEngine = new GraphSearchEngineImpl();
		final IMDBGraph graph;
		try {
			graph = new IMDBGraphImpl(IMDBGraphImpl.IMDB_DIRECTORY + "/name.basics.tsv.gz", IMDBGraphImpl.IMDB_DIRECTORY + "/title.basics.tsv.gz");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			assertTrue(false);
			return;
		}
		final Node actor1 = graph.getActor("James D. Forsha");
		final Node actor2 = graph.getActor("Marion Singer");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		assertEquals(11, shortestPath.size());
		final String[] correctNames = {actor1.getName(), "The Irishman 2", "Jean Wiener", "At 3:25", "Paul Poiret", "L'inhumaine", "Darius Milhaud", "The Beloved Vagabond", "Boris Korlin", "To the Death", actor2.getName()};
		int idx = 0;
		for (Node node : shortestPath) {
			assertEquals(correctNames[idx++], node.getName());
		}
	}

	@Test
	@Timeout(60)
	void fullDatabaseTest5 () {
		final GraphSearchEngine searchEngine = new GraphSearchEngineImpl();
		final IMDBGraph graph;
		try {
			graph = new IMDBGraphImpl(IMDBGraphImpl.IMDB_DIRECTORY + "/name.basics.tsv.gz", IMDBGraphImpl.IMDB_DIRECTORY + "/title.basics.tsv.gz");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			assertTrue(false);
			return;
		}
		final Node actor1 = graph.getActor("Tom Cruise");
		final Node actor2 = graph.getActor("Marlon Wayans");
		final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actor2);
		assertEquals(5, shortestPath.size());
		final String[] correctNames = {actor1.getName(), "The Last Samurai 3", "J.C. Brown", "G.I. Joe: The Rise of Cobra", actor2.getName()};
		int idx = 0;
		for (Node node : shortestPath) {
			assertEquals(correctNames[idx++], node.getName());
		}
	}
}
