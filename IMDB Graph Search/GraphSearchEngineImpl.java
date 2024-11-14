import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

/**
 * Implements the GraphSearchEngine interface.
 */
public class GraphSearchEngineImpl implements GraphSearchEngine {
	public GraphSearchEngineImpl () {
	}


	/**
	 * finds shortest path from actor 1 to actor 2
	 * @param from is actor to search from
	 * @param to is actor to search to
	 * @return path from actor 1 to actor 2
	 */
	public List<Node> findShortestPath (Node from, Node to) {
		Queue<Node> toSearch = new LinkedList<>(); // queue to search
		Map<Node, Node> getToNodeFrom = new HashMap<>(); // hashmap that has a node mapped to another node that it was searched from
		toSearch.add(from);

		while (!toSearch.isEmpty()) {
			Node currentNode = toSearch.remove(); // removes the node we are currently searching

			//System.out.println(currentNode.getName());

			if (currentNode.equals(to)) { // if we found the end
				List<Node> path = new LinkedList<Node>();
				Node backtrackingNode = to;
				path.add(backtrackingNode);

				while (backtrackingNode != from) { // backtracks through hashmap
					backtrackingNode = getToNodeFrom.get(backtrackingNode);
					path.add(backtrackingNode);
				}
				
				Collections.reverse(path); // reverses path because we backtracked

				return path;
			} else {
				for (Node neighbor : currentNode.getNeighbors()) { // loops through current node's neighbors
					if (!getToNodeFrom.containsKey(neighbor) || neighbor.equals(from)) { // checks if we visited the node already
						toSearch.add(neighbor);  // adds neighbor to search queue
						getToNodeFrom.put(neighbor, currentNode); // maps node to node it was searched from
					}
				}
			}
		}

		return null;
	}
}
