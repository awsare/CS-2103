import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.util.stream.*;
import java.util.function.*;

public class IMDBGraphImpl implements IMDBGraph {
	// Set this to the directory path containing the IMDB files. On Linux/Mac OS,
	// this might be: "/Users/sarah/IMDB". On Windows, this might be:
	// "C:/Users/sarah/IMDB". (These are made-up examples but give a sense
	// of the required syntax.)
	public static final String IMDB_DIRECTORY = "/Users/awsare/Documents/CS2103/IMDB Graph Search";
	//public static final String IMDB_DIRECTORY = "C:/Users/nwirt/Documents/GitHub/CS-2103/IMDB Graph Search"; 
	private static final int PROGRESS_FREQUENCY = 100000;

	private static final String GREEN_OUTPUT = "\033[1;92m";
	private static final String YELLOW_OUTPUT = "\033[1;93m";
	private static final String PURPLE_OUTPUT = "\033[1;95m";
	private static final String RESET_OUTPUT = "\033[0m";

	private static class IMDBNode implements Node {
		private final String _name;
		private final Collection<IMDBNode> _neighbors;

		public IMDBNode (String name) {
			_name = name;

			// Note: could also use an ArrayList, but LinkedList is likely slightly
			// more efficient in this program.
			_neighbors = new LinkedList<IMDBNode>();
		}

		public String getName () {
			return _name;
		}

		public Collection<IMDBNode> getNeighbors () {
			return _neighbors;
		}
	}

	private final Map<String, IMDBNode> _actorNamesToNodes = new HashMap<>();
	private final Map<String, IMDBNode> _movieNamesToNodes = new HashMap<>();

	/**
	 * Returns a name (based on the specified name) that is guaranteed to be unique
	 * within the specified map.
	 * @param name the actor or movie name
	 * @param map the map within which to ensure uniqueness
	 * @return a guaranteed unique name
	 */
	private static String ensureUniqueName (String name, Map<String, IMDBNode> map) {
		String finalName = name;
		int counter = 2;
		while (map.containsKey(finalName)) {
			finalName = name + " " + counter;
			counter++;
		}
		return finalName;
	}

	/**
	 * Returns the movies in the dataset.
	 * @return the movies in the dataset.
	 */
	public Node getMovie (String name) {
		return _movieNamesToNodes.get(name);
	}

	/**
	 * Returns the actors (of any gender) in the dataset.
	 * @return the actors in the dataset.
	 */
	public Node getActor (String name) {
		return _actorNamesToNodes.get(name);
	}

	/**
	 * Loads the actor data contained in the specified file.
	 * @param filename full path to the actor data file.
	 * @param idsToTitles a map from the movie ID to the movie title.
	 */
	private void processActors (String filename, Map<String, String> idsToTitles) throws IOException {
		System.out.print("Processing actors... " + GREEN_OUTPUT);

		InputStream inputStream = new FileInputStream(filename);
		if (filename.endsWith(".gz")) {
			inputStream = new GZIPInputStream(inputStream);
		}
		final Scanner s = new Scanner(inputStream, "ISO-8859-1");

		int idx = 0;
		s.nextLine();  // skip first line
		while (s.hasNextLine()) {
			final String line = s.nextLine();
			final String[] fields = line.split("\t");
			final int NUM_REQUIRED_FIELDS = 6;
			if (fields.length >= NUM_REQUIRED_FIELDS) {
				final String actorId = fields[0];
				final String name = fields[1];
				final String profession = fields[4];
				final String[] knownFor = fields[5].split(",");

				// Only worry about actors (of any gender)
				if (profession.contains("actor") || profession.contains("actress")) {
					// Show progress
					if (idx++ % PROGRESS_FREQUENCY == 0) {
						System.out.print("*");
					}

					// Give each person with the same name a unique "finalName".
					final String finalName = ensureUniqueName(name, _actorNamesToNodes);

					// Create a new node for the actor, add them to _actorNamesToNodes,
					// and set the neighbors of the new actor node appropriately.
					// Also set the actor to be a neighbor of each of the actor's movies.
					final IMDBNode actorNode = new IMDBNode(finalName); // makes actor node
					_actorNamesToNodes.put(finalName, actorNode);
					
					for (String id : knownFor) {  // loops through known for movies

						//System.out.println("Movie ID found: " + elem);

						String title = idsToTitles.get(id); // converts id to title

						if (title != null) {
							IMDBNode movieNode = (IMDBNode) getMovie(title); // gets movie from title

							actorNode._neighbors.add(movieNode); // links actor to movie
							movieNode._neighbors.add(actorNode); // links movie to actor

							//System.out.println(finalName + " acts in " + idsToTitles.get(elem));
						}
					}
				}
			}
		}

		System.out.println(RESET_OUTPUT + " ...finished!");
	}

	/**
	 * Loads the movie title data contained in the specified file.
	 * @param filename full path to the movie title file.
	 * @return a map from the movie ID to the movie title.
	 */
	private Map<String, String> processTitles (String filename) throws IOException {
		System.out.print("Processing movies... " + GREEN_OUTPUT);

		final Map<String, String> idsToTitles = new HashMap<>();

		InputStream inputStream = new FileInputStream(filename);
		if (filename.endsWith(".gz")) {
			inputStream = new GZIPInputStream(inputStream);
		}
		final Scanner s = new Scanner(inputStream, "ISO-8859-1");

		int idx = 0;
		s.nextLine();  // skip first line
		while (s.hasNextLine()) {
			final String line = s.nextLine();
			final String[] fields = line.split("\t");
			final String movieId = fields[0];
			final int NUM_REQUIRED_FIELDS = 3;
			if (fields.length >= NUM_REQUIRED_FIELDS) {
				final String type = fields[1];
				if (type.contains("movie")) {
					final String title = fields[2];
					if (idx++ % PROGRESS_FREQUENCY == 0) {
						System.out.print("*");
					}

					final String finalTitle = ensureUniqueName(title, _movieNamesToNodes);
					final IMDBNode movie = new IMDBNode(finalTitle);
					_movieNamesToNodes.put(finalTitle, movie);
					// Associate the movieId with the title
					idsToTitles.put(movieId, finalTitle);
				}
			}
		}

		System.out.println(RESET_OUTPUT + " ...finished!");

		return idsToTitles;
	}

	/**
	 * Creates a new IMDB graph by parsing the specified data files.
	 * @param actorsFilename full path to the actor data file.
	 * @param titlesFilename full path to the movie titles file.
	 */
	public IMDBGraphImpl (String actorsFilename, String titlesFilename) throws IOException {
		// Load the movies & actors from the data files
		// First load the movie titles
		final Map<String, String> idsToTitles = processTitles(titlesFilename);

		// Now parse the actors
		processActors(actorsFilename, idsToTitles);
	}

	/**
	 * Returns the list of movies.
	 * @return the list of movies.
	 */
	public Collection<? extends Node> getMovies () {
		return _movieNamesToNodes.values();
	}

	/**
	 * Returns the list of actors.
	 * @return the list of actors.
	 */
	public Collection<? extends Node> getActors () {
		return _actorNamesToNodes.values();
	}

	/**
	 * Simple interactive program that asks user for two actors and then lists
	 * a shortest path (if it exists).
	 */
	public static void main (String[] args) {
		try {

			final IMDBGraph graph = new IMDBGraphImpl(IMDB_DIRECTORY + "/name.basics.tsv.gz",
			                                          IMDB_DIRECTORY + "/title.basics.tsv.gz");

			System.out.println("\nActors size: " + GREEN_OUTPUT + graph.getActors().size() + RESET_OUTPUT);
			System.out.println("Movies size: " + GREEN_OUTPUT +  graph.getMovies().size() + RESET_OUTPUT);

			final GraphSearchEngine graphSearcher = new GraphSearchEngineImpl();

			while (true) {
				System.out.println();
				final Scanner s = new Scanner(System.in);

				System.out.print("Actor 1: ");
				final String actorName1 = s.nextLine().trim();
				final Node node1 = graph.getActor(actorName1);
				
				if (node1 == null) {
					System.out.println("Actor 1 not found.");
					continue;
				}

				System.out.print("Actor 2: ");
				final String actorName2 = s.nextLine().trim();
				final Node node2 = graph.getActor(actorName2);

				if (node2 == null) {
					System.out.println("Actor 2 not found.");
					continue;
				}

				System.out.println("Finding shortest path from " + GREEN_OUTPUT + node1.getName() + RESET_OUTPUT + " to " + GREEN_OUTPUT + node2.getName() + RESET_OUTPUT + "...\n");
				long startTime = System.currentTimeMillis();

				List<Node> shortestPath = graphSearcher.findShortestPath(node1, node2);

				double searchTimeSeconds = (System.currentTimeMillis() - startTime) / 1000.0;
				System.out.println("Breadth-first graph search took " + GREEN_OUTPUT + searchTimeSeconds + RESET_OUTPUT + " seconds.");

				if (shortestPath != null) {
					System.out.println("Shortest path is " + GREEN_OUTPUT + shortestPath.size() + RESET_OUTPUT + " nodes long.\n");
					for (int i = 0; i < shortestPath.size(); i++) {
						if (i % 2 == 0) {
							System.out.print(i + 1 + ".\t[Actor] ");
						} else {
							System.out.print(i + 1 + ".\t[Movie] ");
						}

						if (i == 0 || i == shortestPath.size() - 1) {
							System.out.println(GREEN_OUTPUT + shortestPath.get(i).getName() + RESET_OUTPUT);
						} else {
							System.out.println(shortestPath.get(i).getName());
						}
					}
				} else {
					System.out.println("No path found.");
				}
			}
		} catch (IOException ioe) {
			System.out.println("Couldn't load data.");
		}
	}
}
