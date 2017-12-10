package adsProject;

public class DHeap {
	private Huffman.Node [] heap;
	private int size;
	private int arity;
	
	public DHeap(int capicity) {
		this.arity = 4;
		this.heap = new Huffman.Node[capicity];
		this.size = 0;
		
	}
	
	public boolean isEmpty() {
		return size == 0;
	}

	public Huffman.Node findMin() {
		if (size == 0) { throw new java.lang.IllegalStateException("Empty Heap"); }
		
		return heap[0];
	}
	
	public void insert(Huffman.Node toBeInserted) {
		if (size==0) {
			heap[0] = toBeInserted;
			size++;
			return;
		}
		// Start at the bottom, and search to find where
		// we should insert the new element
		//
		// Stop when we find a parent node that is small
		// than the element to be inserted, or we reach the top
		
		int i = size;
		
		for(; heap[(i-1)/arity].getFrequence() > toBeInserted.getFrequence(); i=(i-1)/arity) {
			// if we've reached the top, do the assignment
			if (i==0) break;
			
			// otherwise swap down the parent
			heap[i] = heap[(i-1)/arity];
		}
		
		heap[i] = toBeInserted;
		size++;
		
	}
	
	public Huffman.Node deleteMin() {
		if (size == 0) { throw new java.lang.IllegalStateException("Empty Heap"); }

		Huffman.Node toReturn = heap[0];
		Huffman.Node lastElement = heap[size-1];
		int minChild;
		int i=0;
		
		for(; (i*arity)+1 < size; i=minChild) {
			// Assume initially that the smallest child is
			// the first child
			minChild = (i*arity)+1;
			
			// There are no children for this node
			if (minChild > size) { break; }
			
			// Search through all the children for the
			// smallest value
			int j=1, currentSmallestChild = minChild;
			for(; j<arity; j++) {
				if (minChild+j == size) break;
				if(heap[currentSmallestChild].getFrequence() > heap[minChild+j].getFrequence())
					currentSmallestChild = minChild+j;
			}
			
			minChild = currentSmallestChild;
			
			// if the minChild that we found is smaller
			// than the last element, we should percolate
			// up the child to the parent and keep searching
			// for a suitable place to put the last element
			if (lastElement.getFrequence() > heap[minChild].getFrequence()) {
				heap[i] = heap[minChild];
			} else {
				break;
			}
		}
		
		heap[i] = lastElement;
		size--;
		return toReturn;
	}
}
