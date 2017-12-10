package adsProject;

class PairingHeap
{
	class PairNode
	{
	    Huffman.Node element;
	    PairNode leftChild;
	    PairNode nextSibling;
	    PairNode prev;
	 
	    /* Constructor */
	    public PairNode(Huffman.Node x)
	    {
	        element = x;
	        leftChild = null;
	        nextSibling = null;
	        prev = null;
	    }
	}
	 
	    private PairNode root; 
	    private PairNode [ ] treeArray = new PairNode[ 5 ];
	    /* Constructor */
	    public PairingHeap( )
	    {
	        root = null;
	      }
	    /* Check if heap is empty */
	    public boolean isEmpty() 
	    {
	        return root == null;
	    }
	    /* Make heap logically empty */ 
	    public void makeEmpty( )
	    {
	        root = null;
	    }
	    /* Function to insert data */
	    public Huffman.Node getNode()
	    {
	    	return root.element;
	    }
	    public PairNode insert(Huffman.Node x)
	    {
	        PairNode newNode = new PairNode( x );
	        if (root == null)
	            root = newNode;
	        else
	            root = compareAndLink(root, newNode);
	        return newNode;
	    }
	    /* Function compareAndLink */
	    private PairNode compareAndLink(PairNode first, PairNode second)
	    {
	        if (second == null)
	            return first;
	 
	        if (second.element.getFrequence() < first.element.getFrequence())
	        {
	            /* Attach first as leftmost child of second */
	            second.prev = first.prev;
	            first.prev = second;
	            first.nextSibling = second.leftChild;
	            if (first.nextSibling != null)
	                first.nextSibling.prev = first;
	            second.leftChild = first;
	            return second;
	        }
	        else
	        {
	            /* Attach second as leftmost child of first */
	            second.prev = first;
	            first.nextSibling = second.nextSibling;
	            if (first.nextSibling != null)
	                first.nextSibling.prev = first;
	            second.nextSibling = first.leftChild;
	            if (second.nextSibling != null)
	                second.nextSibling.prev = second;
	            first.leftChild = second;
	            return first;
	        }
	    }
	    private PairNode combineSiblings(PairNode firstSibling)
	    {
	        if( firstSibling.nextSibling == null )
	            return firstSibling;
	        int numSiblings = 0;
	        for ( ; firstSibling != null; numSiblings++)
	        {
	            treeArray = doubleIfFull( treeArray, numSiblings );
	            treeArray[ numSiblings ] = firstSibling;
	            /* break links */
	            firstSibling.prev.nextSibling = null;  
	            firstSibling = firstSibling.nextSibling;
	        }
	        treeArray = doubleIfFull( treeArray, numSiblings );
	        treeArray[ numSiblings ] = null;
	        int i = 0;
	        for ( ; i + 1 < numSiblings; i += 2)
	            treeArray[ i ] = compareAndLink(treeArray[i], treeArray[i + 1]);
	        int j = i - 2;
	        if (j == numSiblings - 3)
	            treeArray[ j ] = compareAndLink( treeArray[ j ], treeArray[ j + 2 ] );
	        for ( ; j >= 2; j -= 2)
	            treeArray[j - 2] = compareAndLink(treeArray[j-2], treeArray[j]);
	        return treeArray[0];
	    }
	    private PairNode[] doubleIfFull(PairNode [ ] array, int index)
	    {
	        if (index == array.length)
	        {
	            PairNode [ ] oldArray = array;
	            array = new PairNode[index * 2];
	            for( int i = 0; i < index; i++ )
	                array[i] = oldArray[i];
	        }
	        return array;
	    }
	    /* Delete min element */
	    public int deleteMin( )
	    {
	        if (isEmpty( ) )
	            return -1;
	        int x = root.element.getFrequence();
	        if (root.leftChild == null)
	            root = null;
	        else
	            root = combineSiblings( root.leftChild );
	        return x;
	    }
	    /* inorder traversal */
	    public void inorder()
	    {
	        inorder(root);
	    }
	    private void inorder(PairNode r)
	    {
	        if (r != null)
	        {
	            inorder(r.leftChild);
	            System.out.print(r.element +" ");
	            inorder(r.nextSibling);
	        }
	    }
}
