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
		Queue<Node> queue = new LinkedList<>();
		LinkedList<Node> alreadyQueued = new LinkedList<>();
		Map<Node, Node> nodeBefore = new HashMap<>();
		queue.add(from);
		alreadyQueued.add(from);

		while (!queue.isEmpty()) {
			Node currentNode = queue.remove();

			//System.out.println(currentNode.getName());

			if (currentNode.equals(to)) {
				List<Node> path = new LinkedList<Node>();
				Node backtrackingNode = to;
				path.add(backtrackingNode);

				while (backtrackingNode != from) {
					backtrackingNode = nodeBefore.get(backtrackingNode);
					path.add(backtrackingNode);
				}
				
				Collections.reverse(path);

				return path;
			} else {
				for (Node neighbor : currentNode.getNeighbors()) {
					if (!alreadyQueued.contains(neighbor)) {
						queue.add(neighbor);
						alreadyQueued.add(currentNode);
						nodeBefore.put(neighbor, currentNode);
					}
				}
			}
		}

		return null;
	}
}
