import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

public class ParticleSimulator extends JPanel {
	private Heap<Event> _events;
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
			super(null,  timeOfEvent, 0);
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

	public void queueEvents(List<Particle> toCheck, double lastTime) {
		for (Particle particle : toCheck) {
			// Add new events.
			for (Particle other : _particles) {
				if (!particle.equals(other)) {
					double collisionTime = particle.getCollisionTime(other);
					if (collisionTime != Double.POSITIVE_INFINITY) {
						Event e = new Event(Event.EventType.PARTICLE, lastTime + collisionTime, lastTime);
						e.setParticleA(particle);
						e.setParticleB(other);
						_events.add(e);
					}
				}
			}

			for (Wall wall : _walls) {
				double collisionTime = wall.getCollisionTime(particle);
				if (collisionTime != Double.POSITIVE_INFINITY) {
					Event e = new Event(Event.EventType.WALL, lastTime + collisionTime, lastTime);
					e.setWall(wall);
					e.setParticleA(particle);
					_events.add(e);
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
		
		_events.add(new TerminationEvent(_duration));
		while (_events.size() > 0) {
			Event event = _events.removeFirst();
			double delta = event._timeOfEvent - lastTime;
			System.out.println(delta);

			if (event instanceof TerminationEvent) {
				updateAllParticles(delta);
				break;
			}

			// Check if event still valid; if not, then skip this event
			// if (event not valid) {
			//   continue;
			// }
//			if (event._timeOfEvent < lastTime) {
//				continue;
//			}

			if (event._eventType == Event.EventType.PARTICLE) {
				if (event._particleA.getLastUpdateTime() > event._timeOfEvent || event._particleB.getLastUpdateTime() > lastTime) {
					continue;
				}
			} else if (event._eventType == Event.EventType.WALL) {
				if (event._particleA.getLastUpdateTime() > lastTime) {
					continue;
				}
			}

//			double checkColTime;
//			if (event._eventType == Event.EventType.PARTICLE) {
//				checkColTime = event._particleA.getCollisionTime(event._particleB);
//			} else {
//				checkColTime = event._wall.getCollisionTime(event._particleA);
//			}
//
//			if (checkColTime != Double.POSITIVE_INFINITY) {
//				continue;
//			}

			if (event._eventType == Event.EventType.PARTICLE) {
				System.out.println("Particle A: " + event._particleA.getName() + ", Particle B: " + event._particleB.getName());
			} else if (event._eventType == Event.EventType.WALL) {
				System.out.println("Particle A: " + event._particleA.getName() + ", Wall: " + event._wall._side);
			}


			// Since the event is valid, then pause the simulation for the right
			// amount of time, and then update the screen.
			if (show) {
				try {
					Thread.sleep((long) delta);
				} catch (InterruptedException ie) {}
			}

			// Update positions of all particles
			updateAllParticles(delta);

			// Update the velocity of the particle(s) involved in the collision
			// (either for a particle-wall collision or a particle-particle collision).
			// You should call the Particle.updateAfterCollision method at some point.

			if (event._eventType == Event.EventType.PARTICLE) {
				event._particleA.updateAfterParticleCollision(lastTime, event._particleB);
			} else if (event._eventType == Event.EventType.WALL) {
				event._particleA.updateAfterWallCollision(lastTime, event._wall);
			}

			// Enqueue new events for the particle(s) that were involved in this event.

			ArrayList<Particle> toCheck = new ArrayList<>();

			toCheck.add(event._particleA);
			if (event._eventType == Event.EventType.PARTICLE) {
				toCheck.add(event._particleB);
			}

			queueEvents(toCheck, lastTime);

//			for (Particle other : _particles) {
//				if (!event._particleA.equals(other)) {
//					double collisionTime = event._particleA.getCollisionTime(other);
//					if (collisionTime != Double.POSITIVE_INFINITY) {
//						Event e = new Event(Event.EventType.PARTICLE, collisionTime, lastTime);
//						e.setParticleA(event._particleA);
//						e.setParticleB(other);
//						_events.add(e);
//					}
//				}
//
//				if (event._eventType == Event.EventType.PARTICLE) {
//					if (!event._particleB.equals(other)) {
//						double collisionTime = event._particleB.getCollisionTime(other);
//						if (collisionTime != Double.POSITIVE_INFINITY) {
//							Event e = new Event(Event.EventType.PARTICLE, collisionTime, lastTime);
//							e.setParticleA(event._particleB);
//							e.setParticleB(other);
//							_events.add(e);
//						}
//					}
//				}
//			}
//
//			for (Wall wall : _walls) {
//				double collisionTimeA = wall.getCollisionTime(event._particleA);
//				if (collisionTimeA != Double.POSITIVE_INFINITY) {
//					Event e = new Event(Event.EventType.WALL, collisionTimeA, lastTime);
//					e.setWall(wall);
//					e.setParticleA(event._particleA);
//					_events.add(e);
//				}
//
//				if (event._eventType == Event.EventType.PARTICLE) {
//					double collisionTimeB = wall.getCollisionTime(event._particleB);
//					if (collisionTimeB != Double.POSITIVE_INFINITY) {
//						Event e = new Event(Event.EventType.WALL, collisionTimeB, lastTime);
//						e.setParticleA(event._particleB);
//						e.setWall(wall);
//						_events.add(e);
//					}
//				}
//			}

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
