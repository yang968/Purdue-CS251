/*
* DO NOT MODIFY THIS FILE FOR YOUR IMPLEMENTATION
* THIS FILE WILL BE REPLACED DURING TESTING
*/

/******************************************************************************
 *  Compilation:  javac RedBlackBST.java
 *  Execution:    java RedBlackBST < input.txt
 *  Dependencies: StdIn.java StdOut.java  
 *  Data files:   http://algs4.cs.princeton.edu/33balanced/tinyST.txt  
 *    
 *  A symbol table implemented using a left-leaning red-black BST.
 *  This is the 2-3 version.
 *
 *  Note: commented out assertions because DrJava now enables assertions
 *        by default.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *  
 *  % java RedBlackBST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 ******************************************************************************/

import java.util.NoSuchElementException;

/**
 *  The <tt>BST</tt> class represents an ordered symbol table of generic
 *  key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 *  It also provides ordered methods for finding the <em>minimum</em>,
 *  <em>maximum</em>, <em>floor</em>, and <em>ceiling</em>.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be <tt>null</tt>&mdash;setting the
 *  value associated with a key to <tt>null</tt> is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  This implementation uses a left-leaning red-black BST. It requires that
 *  the key type implements the <tt>Comparable</tt> interface and calls the
 *  <tt>compareTo()</tt> and method to compare two keys. It does not call either
 *  <tt>equals()</tt> or <tt>hashCode()</tt>.
 *  The <em>put</em>, <em>contains</em>, <em>remove</em>, <em>minimum</em>,
 *  <em>maximum</em>, <em>ceiling</em>, and <em>floor</em> operations each take
 *  logarithmic time in the worst case, if the tree becomes unbalanced.
 *  The <em>size</em>, and <em>is-empty</em> operations take constant time.
 *  Construction takes constant time.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/33balanced">Section 3.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For other implementations of the same API, see {@link ST}, {@link BinarySearchST},
 *  {@link SequentialSearchST}, {@link BST},
 *  {@link SeparateChainingHashST}, {@link LinearProbingHashST}, and {@link AVLTreeST}.
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */

public class RedBlackBST {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;


   /***************************************************************************
    *  Node helper methods.
    ***************************************************************************/
    // is node x red; false if x is null ?
    private static boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    // number of node in subtree rooted at x; 0 if x is null
    public static int size(Node x) {
        if (x == null) return 0;
        return x.size;
    } 

   /***************************************************************************
    *  Red-black tree insertion.
    ***************************************************************************/

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is <tt>null</tt>.
     *
     * @param key the key
     * @param val the value
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public static Node insert(Node root, Node newnode) {
        root = put(root, newnode);
        root.color = BLACK;
        return root;
    }

    // insert the key-value pair in the subtree rooted at h
    private static Node put(Node h, Node newnode) { 
        if (h == null)
        {
            newnode.color = RED;
            newnode.size = 1;
            newnode.minJobsNode = newnode;
            return newnode;
        }

        if      (newnode.free < h.free) h.left  = put(h.left,  newnode); 
        else if (newnode.free > h.free) h.right = put(h.right, newnode); 
        else
        {
            if (newnode.id < h.id) h.left = put(h.left, newnode);
            else if (newnode.id > h.id) h.right = put(h.right, newnode);
            else return h;
        }

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;
        
        // fix-up minimum augmented entry
        fixMinAug(h);

        return h;
    }

   /**
    *  Red-black tree deletion.
    ***/
    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).    
     *
     * @param  key the key
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
 // delete the key-value pair with the minimum key rooted at h
    private static Node deleteMin(Node h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }
    
    public static Node delete(Node root, Node deletenode) {

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, deletenode.free, deletenode.id);
        if (root != null) root.color = BLACK;
        
        return root;
    }

    // delete the key-value pair with the given key rooted at h
    private static Node delete(Node h, int free, int id) { 
        // assert get(h, key) != null;

        if (free < h.free || (free == h.free && id < h.id))  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, free, id);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if ((free == h.free && id == h.id) && (h.right == null)){

                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (free == h.free && id == h.id) {               
                Node x = min(h.right);

                h.free = x.free;
                h.id = x.id;
                h.numjobs = x.numjobs;
                h.right = deleteMin(h.right);
            }
            else {
                h.right = delete(h.right, free, id);
            }
        }
        return balance(h);
    }

   /***************************************************************************
    *  Red-black tree helper functions.
    ***************************************************************************/

    
    private static void fixMinAug(Node h) {
        Node minNode = h;
        if (h.right != null && h.right.minJobsNode.numjobs < minNode.numjobs)
            minNode = h.right.minJobsNode;
        if (h.left != null && h.left.minJobsNode.numjobs < minNode.numjobs)
            minNode = h.left.minJobsNode;

        h.minJobsNode = minNode;
    }
    
    // make a left-leaning link lean to the right
    private static Node rotateRight(Node h) {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        fixMinAug(h);
        x.right = h;
        fixMinAug(x);
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private static Node rotateLeft(Node h) {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        fixMinAug(h);
        x.left = h;    
        fixMinAug(x);
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // flip the colors of a node and its two children
    private static void flipColors(Node h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private static Node moveRedLeft(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private static Node moveRedRight(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private static Node balance(Node h) {
        // assert (h != null);
        //System.out.format("\tBalancing, machine %d\n",h.id);
        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;     
        // fix-up minimum augmented entry
        fixMinAug(h);
        
        return h;
    }

    // the smallest key in subtree rooted at x; null if no such key
    private static Node min(Node x) { 
        // assert x != null;
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 
}

/*
* DO NOT MODIFY THIS FILE FOR YOUR IMPLEMENTATION
* THIS FILE WILL BE REPLACED DURING TESTING
*/
