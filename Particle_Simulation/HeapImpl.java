class HeapImpl<T extends Comparable<? super T>> implements Heap<T> {
	private static final int INITIAL_CAPACITY = 128;
	private T[] _storage;
	private int _numElements;

	@SuppressWarnings("unchecked")
	public HeapImpl () {
		_storage = (T[]) new Comparable[INITIAL_CAPACITY];
		_numElements = 0;
	}

	/**
	 * Adds an elem to the heap by adding it to the bottom of the heap and bubbling it up.
	 * @param elem the elem to add to the heap.
	 */
	public void add (T elem) {
		// if storage is at max capacity, double the storage size
		if (_numElements == _storage.length) {
			doubleStorageSize();
		}

		// add the new elem to end of the list
		_storage[_numElements] = elem;

		// bubble up the new last elem
		bubbleUp(_numElements);

		// update numElements
		_numElements++;
	}

	/**
	 * Bubbles up an elem into its correct position by comparing the elem to its current parent and pushing it up correspondingly.
	 * @param elemIndex the index of the elem to bubble up.
	 */
	public void bubbleUp(int elemIndex) {
		int i = elemIndex;
		// if current elem isn't root elem and is smaller than parent, then swap places with parent
		while (i > 0 && _storage[i].compareTo( _storage[getParent(i)]) > 0) {
			swap(i, getParent(i));
			i = getParent(i);
		}
	}

	/**
	 * Removes the first elem in the heap and returns the elem.
	 * @return the first elem in the heap that was removed.
	 */
	public T removeFirst () {
		// if heap is empty then throw an error
		if (_numElements == 0) {
			throw new RuntimeException("Cannot remove first element from an empty heap.");
		}

		// store first elem to be returned later
		T first = _storage[0];

		// moves last elem into first elem position
		_storage[0] = _storage[_numElements - 1];
		_storage[_numElements - 1] = null;

		// update numElements
		_numElements--;

		// bubbles down new first elem
		bubbleDown(0);

		// return first elem
		return first;
	}

	/**
	 * Bubbles down an elem into its correct position by comparing the elem to its current children and pushing it down correspondingly.
	 * @param elemIndex the index of the elem to bubble down.
	 */
	public void bubbleDown(int elemIndex) {
		int i = elemIndex;

		while (true) {
			// store left and right child indexes
			int leftIndex = getLeftChildIndex(i);
			int rightIndex = getRightChildIndex(i);

			// create var to keep track of which child is larger
			int largerValueIndex = i;

			// if left child is larger than parent, then set it to largerValueIndex
			if (leftIndex < _numElements && _storage[leftIndex].compareTo(_storage[largerValueIndex]) > 0) {
				largerValueIndex = leftIndex;
			}

			// if right child is larger than parent and left child, then set it to largerValueIndex
			if (rightIndex < _numElements && _storage[rightIndex].compareTo(_storage[largerValueIndex]) > 0) {
				largerValueIndex = rightIndex;
			}

			// if neither child is larger than parent, then the elem is in the correct position
			if (largerValueIndex == i) {
				break;
			}

			// elem is not in correct position, so swap it with larger child and continue
			swap(i, largerValueIndex);
			i = largerValueIndex;
		}
	}

	/**
	 * Swaps the positions of two elems.
	 * @param indexA the index of the first elem.
	 * @param indexB the index of the second elem.
	 */
	public void swap(int indexA, int indexB) {
		T temp = _storage[indexA];
		_storage[indexA] = _storage[indexB];
		_storage[indexB] = temp;
	}

	/**
	 * @param index the index of the child elem.
	 * @return the parent of the specified child elem.
	 */
	public int getParent(int index) {
		return (index - 1) / 2;
	}

	/**
	 * @param index the index of the parent elem.
	 * @return the left child of the specified parent elem.
	 */
	public int getLeftChildIndex(int index) {
		return (index * 2) + 1;
	}

	/**
	 * @param index the index of the parent elem.
	 * @return the right child of the specified parent elem.
	 */
	public int getRightChildIndex(int index) {
		return (index * 2) + 2;
	}

	/**
	 * Doubles the size of the storage array and copies all data over to the new array.
	 * This method is only called when storage is at max capacity.
	 */
	@SuppressWarnings("unchecked")
	private void doubleStorageSize() {
		T[] newStorage = (T[]) new Comparable[_numElements * 2];
		System.arraycopy(_storage, 0, newStorage, 0, _numElements);
		_storage = newStorage;
	}

	/**
	 * @return the size of the heap.
	 */
	public int size () {
		return _numElements;
	}

	/**
	 * @param index the index of the elem to return.
	 * @return the elem at the specified index.
	 */
	public T getElem(int index) {
		return _storage[index];
	}
}
