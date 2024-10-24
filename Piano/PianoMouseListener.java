import javax.swing.*;
import java.awt.event.*;
import javax.sound.midi.*;
import java.util.*;

/**
 * Handles mouse press, release, and drag events on the Piano.
 */
public class PianoMouseListener extends MouseAdapter {
	// You are free to add more instance variables if you wish.
	private List<Key> _keys;

	/**
	 * @param keys the list of keys in the piano.
	 */
	public PianoMouseListener (List<Key> keys) {
		_keys = keys;
	}

	// TODO implement this method.
	@Override
	/**
	 * This method is called by Swing whenever the user drags the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseDragged (MouseEvent e) {
		playKey(e.getX(), e.getY());
	}

	// TODO implement this method.
	@Override
	/**
	 * This method is called by Swing whenever the user presses the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mousePressed (MouseEvent e) {
		// To test whether a certain key received the mouse event, you could write something like:
		//	if (key.getPolygon().contains(e.getX(), e.getY())) {
		// To turn a key "on", you could then write:
		//      key.play(true);  // Note that the key should eventually be turned off!
		playKey(e.getX(), e.getY());
	}

	// TODO implement this method.
	@Override
	/**
	 * This method is called by Swing whenever the user releases the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseReleased (MouseEvent e) {
		allKeysOff();
	}

	private void allKeysOff() {
		for (Key key: _keys) {
			key.play(false);
		}
	}

	private Key findKeyToPlay(int mouseX, int mouseY, boolean isBlack) {
		Key keyToPlay = null;

		for (Key key: _keys) {
			if (key.getPolygon().contains(mouseX, mouseY) && (key.isBlack() == isBlack)) {
				keyToPlay = key;
				break;
			}
		}	

		return keyToPlay;
	} 

	private void playKey(int mouseX, int mouseY) {
		Key keyToPlay = findKeyToPlay(mouseX, mouseY, true);

		if (keyToPlay == null) {
			keyToPlay = findKeyToPlay(mouseX, mouseY, false);
		}
		
		if (keyToPlay != null && !keyToPlay.isOn()) {
			allKeysOff();
			keyToPlay.play(true);
			System.out.println("Playing key with pitch " + keyToPlay.getPitch());
		}
	}
}
