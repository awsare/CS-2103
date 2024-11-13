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

	public List<Node> findShortestPath (Node from, Node to) {
		Queue<Node> toSearch = new LinkedList<>();
		Map<Node, Node> childToParent = new HashMap<>();
		toSearch.add(from);

		while (!toSearch.isEmpty()) {
			Node currentNode = toSearch.remove();

			//System.out.println(currentNode.getName());

			if (currentNode.equals(to)) {
				List<Node> path = new LinkedList<Node>();
				Node backtrackingNode = to;
				path.add(backtrackingNode);

				while (backtrackingNode != from) {
					backtrackingNode = childToParent.get(backtrackingNode);
					path.add(backtrackingNode);
				}
				
				Collections.reverse(path);

				return path;
			} else {
				for (Node neighbor : currentNode.getNeighbors()) {
					if (!childToParent.containsKey(neighbor) || neighbor.equals(from)) {
						toSearch.add(neighbor);
						childToParent.put(neighbor, currentNode);
					}
				}
			}
		}

		return null;
	}
}
