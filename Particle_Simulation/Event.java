/**
 * Represents a collision between a particle and another particle, or a particle and a wall.
 */
abstract class Event implements Comparable<Event> {

	double _timeOfEvent;
	double _timeEventCreated;

//	enum EventType {
//		WALL, PARTICLE;
//	}
//
//	EventType _eventType;
//
//	Particle _particleA;
//	Particle _particleB;
//	Wall _wall;

//	/**
//	 * @param timeOfEvent the time when the collision will take place
//	 * @param timeEventCreated the time when the event was first instantiated and added to the queue
//	 */
	public Event (/*EventType eventType,*/ double timeOfEvent, double timeEventCreated) {
		//_eventType = eventType;
		_timeOfEvent = timeOfEvent;
		_timeEventCreated = timeEventCreated;
	}

//	/**
//	 * @param _particleA particle A in event
//	 */
//	public void setParticleA(Particle _particleA) {
//		this._particleA = _particleA;
//	}
//
//	/**
//	 * @param _particleB particle B in event
//	 */
//	public void setParticleB(Particle _particleB) {
//		this._particleB = _particleB;
//	}
//
//	/**
//	 * @param wall wall in event
//	 */
//	public void setWall(Wall wall) {
//		this._wall = wall;
//	}

	/**
	 * Compares two Events based on their event times. Since you are implementing a maximum heap,
	 * this method assumes that the event with the smaller event time should receive higher priority.
	 */
	@Override
	public int compareTo (Event e) {
		if (_timeOfEvent < e._timeOfEvent) {
			return +1;
		} else if (_timeOfEvent == e._timeOfEvent) {
			return 0;
		} else {
			return -1;
		}
	}

	public boolean isValid() {
		return false;
	}

	public void updateAfterCollision(double now) {

	}

//	public String toString() {
//		return /*"Event Type: " + _eventType + */" Time Of Event: " + _timeOfEvent + " Time Event Created: " + _timeEventCreated;
//	}
}
