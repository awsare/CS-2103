import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.awt.event.*;
import org.junit.jupiter.api.*;

/**
 * Contains a set of unit tests for the Piano class.
 */
class PianoTester {
	private TestReceiver _receiver;
	private Piano _piano;
	private PianoMouseListener _mouseListener;

	private MouseEvent makeMouseEvent (int x, int y) {
		return new MouseEvent(_piano, 0, 0, 0, x, y, 0, false);
	}

	@BeforeEach
	void setup () {
		// A new TestReceiver will be created before running *each*
		// test. Hence, the "turn on" and "turn off" counts will be
		// reset to 0 before *each* test.
		_receiver = new TestReceiver();
		_piano = new Piano(_receiver);
		_mouseListener = _piano.getMouseListener();
	}

	// Pressing the mouse on the upper leftmost pixel should cause the key to turn on.
	@Test
	void testClickUpperLeftMostPixel () {
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
	}

	// Pressing the mouse on the upper rightmost pixel should cause the key to turn on.
	@Test
	void testClickUpperRightMostPixel(){
		_mouseListener.mousePressed(makeMouseEvent(Piano.WIDTH-1, 0));
		assertTrue(_receiver.isKeyOn(83));
	}
	
	// Pressing the mouse on the lower rightmost pixel should cause the key to turn on.
	@Test
	void testClickLowerRightMostPixel(){
		_mouseListener.mousePressed(makeMouseEvent(Piano.WIDTH-1, Piano.HEIGHT-1));
		assertTrue(_receiver.isKeyOn(83));
	}

	// Pressing the mouse on the lower leftmost pixel should cause the key to turn on.
	@Test
	void testClickLowerLeftMostPixel(){
		_mouseListener.mousePressed(makeMouseEvent(0, Piano.HEIGHT-1));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
	}

	// Test that pressing and dragging the mouse *within* the same key
	// should cause the key to be turned on only once, not multiple times.
	@Test
	void testDragWithinKey () {
		_mouseListener.mouseDragged(makeMouseEvent(0, 0));
		_mouseListener.mouseDragged(makeMouseEvent(0, 1));
		assertTrue(_receiver.getKeyOnCount(48) <= 1);
	}

	// Test that pressing and dragging the mouse between two keys
	// should cause the keys to be turned on only once, not multiple times.
	@Test
	void testDragBetweenKeys () {
		_mouseListener.mouseDragged(makeMouseEvent(25, 160));
		assertTrue(_receiver.isKeyOn(48) == true);
		assertTrue(_receiver.isKeyOn(50) == false);
		_mouseListener.mouseDragged(makeMouseEvent(60, 160));
		assertTrue(_receiver.isKeyOn(48) == false);
		assertTrue(_receiver.isKeyOn(50) == true);
		assertTrue(_receiver.getKeyOnCount(48) <= 1);
		assertTrue(_receiver.getKeyOnCount(50) <= 1);
	}
}
