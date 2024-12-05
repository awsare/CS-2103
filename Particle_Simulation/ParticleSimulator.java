import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

public class ParticleSimulator extends JPanel {
	private HeapImpl<Event> _events;
	private java.util.List<Particle> _particles;
	private java.util.List<Wall> _walls;
	private double _duration;
	private int _width;

	/**
	 * @param filename the name of the file to parse containing the particles
	 */
	public ParticleSimulator (String filename) throws IOException {
		_events = new HeapImpl<>();

		// Parse the specified file and load all the particles.
		Scanner s = new Scanner(new File(filename));
		_width = s.nextInt();
		_duration = s.nextDouble();
		s.nextLine();
		_particles = new ArrayList<>();
		while (s.hasNext()) {
			String line = s.nextLine();
			Particle particle = Particle.build(line);
			_particles.add(particle);
		}

		_walls = new ArrayList<>();
		_walls.add(new Wall(Wall.WallSide.TOP, 0));
		_walls.add(new Wall(Wall.WallSide.BOTTOM, _width));
		_walls.add(new Wall(Wall.WallSide.LEFT, 0));
		_walls.add(new Wall(Wall.WallSide.RIGHT, _width));

		setPreferredSize(new Dimension(_width, _width));
	}

	@Override
	/**
	 * Draws all the particles on the screen at their current locations
	 * DO NOT MODIFY THIS METHOD
	 */
        public void paintComponent (Graphics g) {
		g.clearRect(0, 0, _width, _width);
		for (Particle p : _particles) {
			p.draw(g);
		}
	}

	// Helper class to signify the final event of the simulation.
	private class TerminationEvent extends Event {
		TerminationEvent (double timeOfEvent) {
			super(timeOfEvent, 0);
		}
	}

	/**
	 * Helper method to update the positions of all the particles based on their current velocities.
	 */
	private void updateAllParticles (double delta) {
		for (Particle p : _particles) {
			p.update(delta);
		}
	}


	/**
	 * @param toCheck list of particle to queue events for
	 * @param lastTime last time in simulation
	 */
	public void queueEvents(List<Particle> toCheck, double lastTime) {
		//System.out.println("Adding new events: ");
		for (Particle particleA : toCheck) {
			// Add new events.
			for (Particle particleB : _particles) {
				if (particleA != particleB) {
					//System.out.println("Found a potential particle collision");
					double collisionTime = particleA.getCollisionTime(particleB);
					//System.out.println("Found a potential particle collision, time is " + collisionTime);
					if (collisionTime != Double.POSITIVE_INFINITY) {
						_events.add(new ParticleCollisionEvent(lastTime + collisionTime, lastTime, particleA, particleB));
						//System.out.println("Added the particle collision.");
					}
				}
			}

			for (Wall wall : _walls) {
				double collisionTime = wall.getCollisionTime(particleA);
				//System.out.println("Found a potential wall collision, time is " + collisionTime);
				if (collisionTime != Double.POSITIVE_INFINITY) {
					_events.add(new WallCollisionEvent(lastTime + collisionTime, lastTime, particleA, wall));
					//System.out.println("Added the wall collision.");
				}
			}
		}
	}

	/**
	 * Executes the actual simulation.
	 */
	private void simulate (boolean show) {
		double lastTime = 0;

		// Create initial events, i.e., all the possible
		// collisions between all the particles and each other,
		// and all the particles and the walls.
		queueEvents(_particles, lastTime);

		//System.out.println(_duration);
		
		_events.add(new TerminationEvent(_duration));

		while (_events.size() > 0) {
			//System.out.println("\nNew loop in while loop:");
			//System.out.println("Heap Size: " + _events.size());
			Event event = _events.removeFirst();
			//System.out.println(event);
			//System.out.println("Last time is " + lastTime);
			//System.out.println(event._eventType);
//			for (int j = 0; j < _events.size(); j++) {
//				System.out.print(_events.getElem(j)._eventType + " ");
//			}

//			System.out.println("Time of Event: " + event._timeOfEvent);
//			System.out.println("Last Time: " + lastTime);
			double delta = event._timeOfEvent - lastTime;
//			System.out.println("Delta: " + delta);

			if (event instanceof TerminationEvent) {
				updateAllParticles(delta);
				break;
			}

			// Check if event still valid; if not, then skip this event
			if (event._timeOfEvent < lastTime) {
				System.out.println("Not valid, already happened.");
				continue;
			}

			if (!event.isValid()) {
				System.out.println("Not valid, particle updated after particle collision event created.");
				continue;
			}


			// Since the event is valid, then pause the simulation for the right
			// amount of time, and then update the screen.
			if (show) {
				try {
					Thread.sleep((long) (1000 * delta));
				} catch (InterruptedException ie) {}
			}

			System.out.println("Valid event, updating particles.");

			// Update positions of all particles
			updateAllParticles(delta);

			// Update the velocity of the particle(s) involved in the collision
			// (either for a particle-wall collision or a particle-particle collision).
			// You should call the Particle.updateAfterCollision method at some point.

			event.updateAfterCollision(event._timeOfEvent);

			// Enqueue new events for the particle(s) that were involved in this event.

			// queue method takes in a list of particles to queue events for, this adds relevant particles to a list
			ArrayList<Particle> toCheck = new ArrayList<>();

			if (event instanceof ParticleCollisionEvent) {
				toCheck.add(((ParticleCollisionEvent) event).particleA);
				toCheck.add(((ParticleCollisionEvent) event).particleB);
			} else if (event instanceof WallCollisionEvent) {
				toCheck.add(((WallCollisionEvent) event).particle);
			}

			queueEvents(toCheck, lastTime);

			// Update the time of our simulation
			lastTime = event._timeOfEvent;

			// Redraw the screen
			if (show) {
				repaint();
			}
        }

		// Print out the final state of the simulation
		System.out.println(_width);
		System.out.println(_duration);
		for (Particle p : _particles) {
			System.out.println(p);
		}
	}

	public static void main (String[] args) throws IOException {
		if (args.length < 1) {
			System.out.println("Usage: java ParticleSimulator <filename>");
			System.exit(1);
		}

		ParticleSimulator simulator;

		simulator = new ParticleSimulator(args[0]);
		JFrame frame = new JFrame();
		frame.setTitle("Particle Simulator");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(simulator, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		simulator.simulate(true);
	}
}
