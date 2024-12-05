/**
 * Represents a collision between a particle and another particle, or a particle and a wall.
 */
abstract class Event implements Comparable<Event> {

	double _timeOfEvent;
	double _timeEventCreated;

	public Event (double timeOfEvent, double timeEventCreated) {
		_timeOfEvent = timeOfEvent;
		_timeEventCreated = timeEventCreated;
	}

	/**
	 * Compares two Events based on their event times. Since you are implementing a maximum heap,
	 * this method assumes that the event with the smaller event time should receive higher priority.
	 */
	@Override
	public int compareTo (Event e) {
		if (_timeOfEvent < e._timeOfEvent) {
			return -1;
		} else if (_timeOfEvent == e._timeOfEvent) {
			return 0;
		} else {
			return +1;
		}
	}

	public boolean isValid() {
		return false;
	}

	public void updateAfterCollision(double now) {}

//	public String toString() {
//		return /*"Event Type: " + _eventType + */" Time Of Event: " + _timeOfEvent + " Time Event Created: " + _timeEventCreated;
//	}
}
