import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

public class HeapTester {
	private void permute (int[] array) {
		final Random random = new Random();
		for (int i = array.length - 1; i >= 0; i--) {
			final int j = random.nextInt(i+1);
			final int temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
	}

	private void printHeap(HeapImpl<Integer> heap) {
		for (int j = 0; j < heap.size(); j++) {
			System.out.print(heap.getElem(j) + " ");
		}

		System.out.println();
	}

	@Test
	public void testShuffled (){
		final int N = 1000;
		final int[] numbers = new int[N];
		for (int i = 0; i < N; i++) {
			numbers[i] = i;
		}
		permute(numbers);

		final HeapImpl<Integer> heap = new HeapImpl<>();
		for (int i = 0; i < N; i++) {
			heap.add(numbers[i]);

			//printHeap(heap);
		}
		assertEquals(N, heap.size());

		for (int i = N-1; i >= 0; i--) {
			try {
				assertEquals((Integer) i, heap.removeFirst());
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
				fail();
			}

			//printHeap(heap);
		}

		assertEquals(0, heap.size());
	}
}
