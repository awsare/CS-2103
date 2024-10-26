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

	private int BLACK_KEY_OFFSET = Piano.BLACK_KEY_WIDTH/2; // added a constant to reduce redundancy

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

	// Pressing the mouse on the left most white key should cause the key to turn on.
	@Test
	void testClickLeftMostWhiteKey () {
		int X_LEFT = 0;
		int X_RIGHT = Piano.WHITE_KEY_WIDTH-1;
		int Y_TOP = 0;
		int Y_BOTTOM = Piano.WHITE_KEY_HEIGHT-1;

		// top left pixel
		assertTrue(!_receiver.isKeyOn(Piano.START_PITCH));
		_mouseListener.mousePressed(makeMouseEvent(X_LEFT, Y_TOP));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
		_mouseListener.mouseReleased(makeMouseEvent(X_LEFT, Y_TOP));
		assertTrue(!_receiver.isKeyOn(Piano.START_PITCH));

		// bottom left pixel
		_mouseListener.mousePressed(makeMouseEvent(X_LEFT, Y_BOTTOM));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
		_mouseListener.mouseReleased(makeMouseEvent(X_LEFT, Y_BOTTOM));
		assertTrue(!_receiver.isKeyOn(Piano.START_PITCH));

		// top right pixel
		_mouseListener.mousePressed(makeMouseEvent(X_RIGHT - BLACK_KEY_OFFSET, Y_TOP));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
		_mouseListener.mouseReleased(makeMouseEvent(X_RIGHT - BLACK_KEY_OFFSET, Y_TOP));
		assertTrue(!_receiver.isKeyOn(Piano.START_PITCH));

		// bottom right pixel
		_mouseListener.mousePressed(makeMouseEvent(X_RIGHT, Y_BOTTOM));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
		_mouseListener.mouseReleased(makeMouseEvent(X_RIGHT, Y_BOTTOM));
		assertTrue(!_receiver.isKeyOn(Piano.START_PITCH));

		assertTrue(_receiver.getKeyOnCount(Piano.START_PITCH) == 4);
	}

	// Pressing the mouse on the right most white key should cause the key to turn on.
	@Test
	void testClickRightMostWhiteKey() {
		int X_LEFT = (Piano.NUM_WHITE_KEYS-1) * Piano.WHITE_KEY_WIDTH;
		int X_RIGHT = Piano.NUM_WHITE_KEYS * Piano.WHITE_KEY_WIDTH-1;
		int Y_TOP = 0;
		int Y_BOTTOM = Piano.WHITE_KEY_HEIGHT-1;

		// top left pixel
		assertTrue(!_receiver.isKeyOn(Piano.END_PITCH));
		_mouseListener.mousePressed(makeMouseEvent(X_LEFT + BLACK_KEY_OFFSET, Y_TOP));
		assertTrue(_receiver.isKeyOn(Piano.END_PITCH));
		_mouseListener.mouseReleased(makeMouseEvent(X_LEFT + BLACK_KEY_OFFSET, Y_TOP));
		assertTrue(!_receiver.isKeyOn(Piano.END_PITCH));

		// bottom left pixel
		_mouseListener.mousePressed(makeMouseEvent(X_LEFT, Y_BOTTOM));
		assertTrue(_receiver.isKeyOn(Piano.END_PITCH));
		_mouseListener.mouseReleased(makeMouseEvent(X_LEFT, Y_BOTTOM));
		assertTrue(!_receiver.isKeyOn(Piano.END_PITCH));

		// top right pixel
		_mouseListener.mousePressed(makeMouseEvent(X_RIGHT, Y_TOP));
		assertTrue(_receiver.isKeyOn(Piano.END_PITCH));
		_mouseListener.mouseReleased(makeMouseEvent(X_RIGHT, Y_TOP));
		assertTrue(!_receiver.isKeyOn(Piano.END_PITCH));

		// bottom right pixel
		_mouseListener.mousePressed(makeMouseEvent(X_RIGHT, Y_BOTTOM));
		assertTrue(_receiver.isKeyOn(Piano.END_PITCH));
		_mouseListener.mouseReleased(makeMouseEvent(X_RIGHT, Y_BOTTOM));
		assertTrue(!_receiver.isKeyOn(Piano.END_PITCH));

		assertTrue(_receiver.getKeyOnCount(Piano.END_PITCH) == 4);
	}

	// Pressing the mouse on the a middle white key (one with two black keys on top of it) should cause the key to turn on.
	@Test
	void testClickMiddleWhiteKey() {
		int PITCH_TO_TEST = 67;
		int WHITE_KEY_NUM = 12;

		int X_LEFT = (WHITE_KEY_NUM - 1) * Piano.WHITE_KEY_WIDTH;
		int X_RIGHT = (WHITE_KEY_NUM) * Piano.WHITE_KEY_WIDTH - 1;
		int Y_TOP = 0;
		int Y_BOTTOM = Piano.WHITE_KEY_HEIGHT - 1;

		// top left pixel
		assertTrue(!_receiver.isKeyOn(PITCH_TO_TEST));
		_mouseListener.mousePressed(makeMouseEvent(X_LEFT + BLACK_KEY_OFFSET, Y_TOP));
		assertTrue(_receiver.isKeyOn(PITCH_TO_TEST));
		_mouseListener.mouseReleased(makeMouseEvent(X_LEFT + BLACK_KEY_OFFSET, Y_TOP));
		assertTrue(!_receiver.isKeyOn(PITCH_TO_TEST));

		// bottom left pixel
		_mouseListener.mousePressed(makeMouseEvent(X_LEFT, Y_BOTTOM));
		assertTrue(_receiver.isKeyOn(PITCH_TO_TEST));
		_mouseListener.mouseReleased(makeMouseEvent(X_LEFT, Y_BOTTOM));
		assertTrue(!_receiver.isKeyOn(PITCH_TO_TEST));

		// top right pixel
		_mouseListener.mousePressed(makeMouseEvent(X_RIGHT - BLACK_KEY_OFFSET, Y_TOP));
		assertTrue(_receiver.isKeyOn(PITCH_TO_TEST));
		_mouseListener.mouseReleased(makeMouseEvent(X_RIGHT- BLACK_KEY_OFFSET, Y_TOP));
		assertTrue(!_receiver.isKeyOn(PITCH_TO_TEST));

		// bottom right pixel
		_mouseListener.mousePressed(makeMouseEvent(X_RIGHT, Y_BOTTOM));
		assertTrue(_receiver.isKeyOn(PITCH_TO_TEST));
		_mouseListener.mouseReleased(makeMouseEvent(X_RIGHT, Y_BOTTOM));
		assertTrue(!_receiver.isKeyOn(PITCH_TO_TEST));

		assertTrue(_receiver.getKeyOnCount(PITCH_TO_TEST) == 4);
	}

	// Pressing the mouse on the a black key should cause the key to turn on.
	@Test
	void testClickBlackKey() {
		int PITCH_TO_TEST = Piano.START_PITCH + 1;

		int X_LEFT = Piano.WHITE_KEY_WIDTH - Piano.BLACK_KEY_WIDTH/2;
		int X_RIGHT = Piano.WHITE_KEY_WIDTH + Piano.BLACK_KEY_WIDTH/2 -1;
		int Y_TOP = 0;
		int Y_BOTTOM = Piano.BLACK_KEY_HEIGHT-1;

		// top left pixel
		assertTrue(!_receiver.isKeyOn(PITCH_TO_TEST));
		_mouseListener.mousePressed(makeMouseEvent(X_LEFT, Y_TOP));
		assertTrue(_receiver.isKeyOn(PITCH_TO_TEST));
		_mouseListener.mouseReleased(makeMouseEvent(X_LEFT, Y_TOP));
		assertTrue(!_receiver.isKeyOn(PITCH_TO_TEST));

		// bottom left pixel
		_mouseListener.mousePressed(makeMouseEvent(X_LEFT, Y_BOTTOM));
		assertTrue(_receiver.isKeyOn(PITCH_TO_TEST));
		_mouseListener.mouseReleased(makeMouseEvent(X_LEFT, Y_BOTTOM));
		assertTrue(!_receiver.isKeyOn(PITCH_TO_TEST));

		// top right pixel
		_mouseListener.mousePressed(makeMouseEvent(X_RIGHT, Y_TOP));
		assertTrue(_receiver.isKeyOn(PITCH_TO_TEST));
		_mouseListener.mouseReleased(makeMouseEvent(X_RIGHT, Y_TOP));
		assertTrue(!_receiver.isKeyOn(PITCH_TO_TEST));

		// bottom right pixel
		_mouseListener.mousePressed(makeMouseEvent(X_RIGHT, Y_BOTTOM));
		assertTrue(_receiver.isKeyOn(PITCH_TO_TEST));
		_mouseListener.mouseReleased(makeMouseEvent(X_RIGHT, Y_BOTTOM));
		assertTrue(!_receiver.isKeyOn(PITCH_TO_TEST));

		assertTrue(_receiver.getKeyOnCount(PITCH_TO_TEST) == 4);
		assertTrue(_receiver.getKeyOnCount(PITCH_TO_TEST - 1) == 0); // checks white keys under havent been played
		assertTrue(_receiver.getKeyOnCount(PITCH_TO_TEST + 1) == 0); // checks white keys under havent been played
	}

	// Test that pressing and dragging the mouse *within* the same key
	// should cause the key to be turned on only once, not multiple times.
	@Test
	void testDragWithinKey () {
		int X = 0;
		int Y = 0;

		assertTrue(_receiver.getKeyOnCount(Piano.START_PITCH) == 0);
		_mouseListener.mouseDragged(makeMouseEvent(X, Y));
		_mouseListener.mouseDragged(makeMouseEvent(X + 5, Y + 5)); // drags down and right five pixels
		assertTrue(_receiver.getKeyOnCount(Piano.START_PITCH) == 1);
	}

	// Test that pressing and dragging the mouse between two white keys
	// should cause the keys to be turned on only once, not multiple times.
	@Test
	void testDragBetweenWhiteKeys () {
		int X_LEFT = Piano.WHITE_KEY_WIDTH-1;
		int X_RIGHT = Piano.WHITE_KEY_WIDTH;
		int Y = Piano.WHITE_KEY_HEIGHT-1;

		int FIRST_WHITE_KEY_PITCH = Piano.START_PITCH;
		int SECOND_WHITE_KEY_PITCH = FIRST_WHITE_KEY_PITCH + 2;

		assertTrue(_receiver.isKeyOn(FIRST_WHITE_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(SECOND_WHITE_KEY_PITCH) == false);

		// drags over first white key
		_mouseListener.mouseDragged(makeMouseEvent(X_LEFT, Y));
		assertTrue(_receiver.isKeyOn(FIRST_WHITE_KEY_PITCH) == true);
		assertTrue(_receiver.isKeyOn(SECOND_WHITE_KEY_PITCH) == false);

		// drags over second white key
		_mouseListener.mouseDragged(makeMouseEvent(X_RIGHT, Y));
		assertTrue(_receiver.isKeyOn(FIRST_WHITE_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(SECOND_WHITE_KEY_PITCH) == true);

		// releases mouse
		_mouseListener.mouseReleased(makeMouseEvent(X_RIGHT, Y));
		assertTrue(_receiver.isKeyOn(FIRST_WHITE_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(SECOND_WHITE_KEY_PITCH) == false);

		assertTrue(_receiver.getKeyOnCount(FIRST_WHITE_KEY_PITCH) == 1);
		assertTrue(_receiver.getKeyOnCount(SECOND_WHITE_KEY_PITCH) == 1);
	}

	// Test that pressing and dragging the mouse between two white keys and back to the firsrt
	// should cause the first key to be turned on twice and the second key to be turned on once.
	@Test
	void testDragBackToWhiteKey () {
		int X_LEFT = Piano.WHITE_KEY_WIDTH-1;
		int X_RIGHT = Piano.WHITE_KEY_WIDTH;
		int Y = Piano.WHITE_KEY_HEIGHT-1;

		int FIRST_WHITE_KEY_PITCH = Piano.START_PITCH;
		int SECOND_WHITE_KEY_PITCH = FIRST_WHITE_KEY_PITCH + 2;

		assertTrue(_receiver.isKeyOn(FIRST_WHITE_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(SECOND_WHITE_KEY_PITCH) == false);

		// drags over first white key
		_mouseListener.mouseDragged(makeMouseEvent(X_LEFT, Y));
		assertTrue(_receiver.isKeyOn(FIRST_WHITE_KEY_PITCH) == true);
		assertTrue(_receiver.isKeyOn(SECOND_WHITE_KEY_PITCH) == false);

		// drags over second white key
		_mouseListener.mouseDragged(makeMouseEvent(X_RIGHT, Y));
		assertTrue(_receiver.isKeyOn(FIRST_WHITE_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(SECOND_WHITE_KEY_PITCH) == true);

		// drags back to first white key
		_mouseListener.mouseDragged(makeMouseEvent(X_LEFT, Y));
		assertTrue(_receiver.isKeyOn(FIRST_WHITE_KEY_PITCH) == true);
		assertTrue(_receiver.isKeyOn(SECOND_WHITE_KEY_PITCH) == false);

		// releases mouse
		_mouseListener.mouseReleased(makeMouseEvent(X_LEFT, Y));
		assertTrue(_receiver.isKeyOn(FIRST_WHITE_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(SECOND_WHITE_KEY_PITCH) == false);

		assertTrue(_receiver.getKeyOnCount(FIRST_WHITE_KEY_PITCH) == 2);
		assertTrue(_receiver.getKeyOnCount(SECOND_WHITE_KEY_PITCH) == 1);
	}

	// Test that pressing and dragging the mouse between three keys (black, white, black)
	// should cause the keys to be turned on only once, not multiple times.
	@Test
	void testDragBetweenBlackAndWhiteKeys () {
		int X_LEFT = Piano.WHITE_KEY_WIDTH;
		int X_MID = Piano.WHITE_KEY_WIDTH + Piano.WHITE_KEY_WIDTH/2;
		int X_RIGHT = Piano.WHITE_KEY_WIDTH + Piano.WHITE_KEY_WIDTH;
		int Y = 0;

		int FIRST_BLACK_KEY_PITCH = 49;
		int WHITE_KEY_PITCH = FIRST_BLACK_KEY_PITCH + 1;
		int SECOND_BLACK_KEY_PITCH = WHITE_KEY_PITCH + 1;

		assertTrue(_receiver.isKeyOn(FIRST_BLACK_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(WHITE_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(SECOND_BLACK_KEY_PITCH) == false);

		// drags over first black key
		_mouseListener.mouseDragged(makeMouseEvent(X_LEFT, Y));
		assertTrue(_receiver.isKeyOn(FIRST_BLACK_KEY_PITCH) == true);
		assertTrue(_receiver.isKeyOn(WHITE_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(SECOND_BLACK_KEY_PITCH) == false);

		// drags over middle white key
		_mouseListener.mouseDragged(makeMouseEvent(X_MID, Y));
		assertTrue(_receiver.isKeyOn(FIRST_BLACK_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(WHITE_KEY_PITCH) == true);
		assertTrue(_receiver.isKeyOn(SECOND_BLACK_KEY_PITCH) == false);

		// drags over second black key
		_mouseListener.mouseDragged(makeMouseEvent(X_RIGHT, Y));
		assertTrue(_receiver.isKeyOn(FIRST_BLACK_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(WHITE_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(SECOND_BLACK_KEY_PITCH) == true);
		
		// releases mouse
		_mouseListener.mouseReleased(makeMouseEvent(X_RIGHT, Y));
		assertTrue(_receiver.isKeyOn(FIRST_BLACK_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(WHITE_KEY_PITCH) == false);
		assertTrue(_receiver.isKeyOn(SECOND_BLACK_KEY_PITCH) == false);

		assertTrue(_receiver.getKeyOnCount(FIRST_BLACK_KEY_PITCH) == 1);
		assertTrue(_receiver.getKeyOnCount(WHITE_KEY_PITCH) == 1);
		assertTrue(_receiver.getKeyOnCount(SECOND_BLACK_KEY_PITCH) == 1);
	}
}
