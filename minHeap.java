package heap;

public class minHeap {
    //Heap storage: an array of Entry objects
    //Entry objects are essentially nodes in the heap
	private final ArrayList<HeapPQEntry> entries;
	//imported class to keep track of complexity, outputs of a graph showing how ticks grow as data set
	//size grows
	private final Ticker ticker;

    private void swap(int i, int j) {
		//check that priorities are valid (greater than 0 and less than the total number of entries with 0 being the highest priority)
		if(i >= 0 && i < entries.size() && j >= 0 && j < entries.size()) { 
			ticker.tick(4); //if statement tests
			//swap positions of entries
			entries.get(i).position = j;
			entries.get(j).position = i;
			ticker.tick(2);
			//swap actual entries
			HeapPQEntry temp = entries.get(i);
			entries.set(i, entries.get(j));
			entries.set(j, temp);
			ticker.tick(3);
		}
	}

	@Override
	public int size() {
		ticker.tick();
		return entries.size();
	}

	@Override
	private void repairHeapAtEntry(MinHeap<E, P>.HeapPQEntry entry) {
		ticker.tick(); //tick for assignment statement
		int index = entry.position;
		//execute bubbleUp and bubbleDown to make sure entries are correctly ordered
		//according to their priorities
		bubbleUp(index);
		bubbleDown(index);
	}

	@Override
	public PQEntry<E, P> insert(E thing, P priority) {
		//create new entry to be inserted with value 'thing' and priority 'priority'
		HeapPQEntry e = new HeapPQEntry(thing, priority);
		//set initial position to the current size of the entries and add to heap
		e.position = entries.size();
		entries.add(e);	
		ticker.tick(3); //tick three times for three constant time statements above
		//call repair method to bubble entry up or down according to its given priority	
		repairHeapAtEntry(e);
		ticker.tick(); //return statement
		return e;
	}

	@Override
	public PQEntry<E, P> extractMin() {
		//return null if there are no entries in the min heap
		if(entries.isEmpty()) {
			ticker.tick(2); //if statement test and return statement
			return null;
		}
		//swap current root with last element for easier removal from leaf position
		swap(entries.size() - 1, 0);
		//remove root (now swapped to the last element's position)
		HeapPQEntry removed = entries.remove(entries.size() - 1);
		ticker.tick(); //tick for getting the removed element
		//update the new root to its correct position according to its priority
		bubbleDown(0);
		//return the removed root
		ticker.tick(); //return statement
		return removed;
	}

	//these methods are useful because the heap is stored in an ArrayList
	private int parentIndex(int index) { //return index of parent of the entry at the given index
		ticker.tick();//return statement
		return (index-1)/2;
	}
	private int leftChildIndex(int index) { //return index of left child of entry at given index
		ticker.tick();//return statement
		return 2*index+1;
	}
	private int rightChildIndex(int index) { //return index of right child of entry at given index
		ticker.tick();//return statement
		return 2*index+2;
	}

	private void bubbleDown(int startIndex) {
		//base case: no more children, node is leaf node, cannot swap down any more
		if(leftChildIndex(startIndex) >= entries.size()) {
			ticker.tick(2); //if statement test and return
			return;
		}

		//get priorities of the current entry and the left child's entry
		ticker.tick(2);
		P curMin = entries.get(startIndex).getPriority();
		P lMin = entries.get(leftChildIndex(startIndex)).getPriority();
		
		
		//find min child's index (child with higher priority)
		//default to assuming left child has a higher priority
		int minIndex = leftChildIndex(startIndex);
		ticker.tick();  //assignment statement tick
		if(rightChildIndex(startIndex) < entries.size()) { //if right child exists and has a higher priority than left child, update minIndex
			ticker.tick(); //if statement test
			if(lMin.compareTo(entries.get(rightChildIndex(startIndex)).getPriority()) > 0) { 
				minIndex = rightChildIndex(startIndex);
				ticker.tick(2); // if statement test and assignment
			} 
		}	
		
		//base case: no more heap violation, current entry is already smaller (has higher priority) than minimum child
		if(curMin.compareTo(entries.get(minIndex).getPriority()) < 0) {
			ticker.tick(2) //if statement test and return
			return;
		}
		//if we reach this point there is still a heap violation
		//swap current entry with its highest priority child 
		swap(startIndex, minIndex);
		bubbleDown(minIndex); //recursively travel to the index that the entry has been swapped to
	}

	private void bubbleUp(int startIndex) {
		//get priorities of current entry and the current entry's parent 
		P parent = entries.get(parentIndex(startIndex)).getPriority();
		P cur = entries.get(startIndex).getPriority();
		ticker.tick(2); //get priorities ticks

		//base case: reached root, cannot swap up any more
		if(startIndex <= 0) {
			ticker.tick(2) // if statement and return
			return;
		}
		//swap if current entry's priority is less than (aka higher than) parent's priority
		if(cur.compareTo(parent) < 0) {
			ticker.tick() //if statement test
			swap(startIndex, parentIndex(startIndex));
			bubbleUp(parentIndex(startIndex)); //recursively travel to the index that the entry has been swapped to
		}
		//if we reach this point that means there is no more heap violation, method will automatically return
	}
}
