import java.awt.*;
import javax.sound.midi.*;

/**
 * Implements a key on a simulated piano keyboard.
 */
public class Key {
	// You are free to add more instance variables if you wish.
	private Polygon _polygon;
	private int _pitch;
	private boolean _isOn;
	private Piano _piano;
	private boolean _isBlack;  // added isBlack, represents if the key is a black key or not

	/**
	 * Returns the polygon associated with this key.
	 * @return the polygon associated with this key.
	 */
	public Polygon getPolygon () {
		return _polygon;
	}

	// You are free to modify the constructor if you wish.
	/**
	 * @param polygon the Polygon that describes the shape and position of this key.
	 * @param pitch the pitch value of the key.
	 * @param piano the Piano associated with this key.
	 */
	public Key (Polygon polygon, int pitch, Piano piano, boolean isBlack) {
		_polygon = polygon;
		_pitch = pitch;
		_piano = piano;
		_isBlack = isBlack; // added isBlack, represents if the key is a black key or not
	}

	// DO NOT MODIFY THIS METHOD.
	/**
	 * Turns the note either on or off.
	 * @param isOn whether the note should be turned on.
	 */
	public void play (boolean isOn) {
		try {

			// Some MIDI technicalities; don't worry too much about it.
			ShortMessage myMsg = new ShortMessage();
			final int VELOCITY = 93;
			myMsg.setMessage(isOn ? ShortMessage.NOTE_ON : ShortMessage.NOTE_OFF, 0, _pitch, VELOCITY);
			
			final int IMMEDIATELY = -1;
			
			// Send the message to the receiver (either local or remote).
			_piano.getReceiver().send(myMsg, IMMEDIATELY);
			
			// Set the key to "on".
			_isOn = isOn;
			
			// Ask the piano to redraw itself (since one of its keys has changed).
			_piano.repaint();

		} catch (InvalidMidiDataException imde) {
			System.out.println("Could not play key!");
		}
	}

	/**
	 * Paints the key using the specified Swing Graphics object.
	 * @param g the Graphics object to be used for painting.
	 */
	public void paint (Graphics g) {
		
		if (this._isOn) { // if the key is on, make it gray
			g.setColor(Color.GRAY);
		} else if (_isBlack) { // if the key is a black key, make it black
			g.setColor(Color.BLACK);
		} else { // else, the key must be a white key, so make it white
			g.setColor(Color.WHITE);
		}
		
		g.fillPolygon(_polygon); // fill the key with the color set above
		g.setColor(Color.BLACK); // change color to black for the key outlines
		g.drawPolygon(_polygon); // draw the black outline
	}

	/**
	 * Returns the boolean isBlack, representing if the key is black or white.
	 * @return the boolean isBlack.
	 */
	public boolean isBlack() {
		return this._isBlack;
	}

	/**
	 * Returns the boolean isOn, representing if the key is on or off.
	 * @return the boolean isOn.
	 */
	public boolean isOn() {
		return this._isOn;
	}

	/**
	 * Returns a String representation describing the key.
	 * @return a String representation describing the key.
	 */
	public String toString () {
		return "Key: " + _pitch;
	}
}
