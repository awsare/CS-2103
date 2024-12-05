/**
 * Represents a collision between a particle and another particle, or a particle and a wall.
 */
abstract class Event implements Comparable<Event> {

	private double _timeOfEvent;
	private double _timeEventCreated;

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
			return +1;
		} else if (_timeOfEvent == e._timeOfEvent) {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * @return time of event
	 */
	public double getTimeOfEvent() {
		return _timeOfEvent;
	}

	/**
	 * @return time event created
	 */
	public double getTimeEventCreated() {
		return _timeEventCreated;
	}

	public boolean isValid() {
		return false;
	}

	public void updateAfterCollision(double now, double width) {}
}
