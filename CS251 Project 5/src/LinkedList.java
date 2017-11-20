/******************************************************************************
 *	LinkedList.java
 *	Modified from LinkedQueue.java from Princeton's Data Structures Library
 *
 *  Compilation:  javac LinkedQueue.java
 *  Execution:    java LinkedQueue < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/13stacks/tobe.txt  
 *
 *  A generic queue, implemented using a singly-linked list.
 *
 *  % java Queue < tobe.txt 
 *  to be or not to be (2 left on queue)
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The {@code LinkedQueue} class represents a first-in-first-out (FIFO)
 *  queue of generic items.
 *  It supports the usual <em>enqueue</em> and <em>dequeue</em>
 *  operations, along with methods for peeking at the first item,
 *  testing if the queue is empty, and iterating through
 *  the items in FIFO order.
 *  <p>
 *  This implementation uses a singly-linked list with a non-static nested class 
 *  for linked-list nodes.  See {@link Queue} for a version that uses a static nested class.
 *  The <em>enqueue</em>, <em>dequeue</em>, <em>peek</em>, <em>size</em>, and <em>is-empty</em>
 *  operations all take constant time in the worst case.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class LinkedList<Item> implements Iterable<Item> {
    private int n;         // number of elements on queue
    public Node first;    // beginning of queue
    public Node last;     // end of queue

    // helper linked list class
    public class Node {
        public Item item;
        public Node next;
    }

    /**
     * Initializes an empty queue.
     */
    public LinkedList() {
        first = null;
        last  = null;
        n = 0;
    }

    /**
     * Is this queue empty?
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this queue.
     * @return the number of items in this queue
     */
    public int size() {
        return n;     
    }

    /**
     * Returns the item least recently added to this queue.
     * @return the item least recently added to this queue
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.item;
    }

    /**
     * Adds the item to this queue.
     * @param item the item to add
     */
    public void add(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else           oldlast.next = last;
        n++;
    }
	
	
	/**
	*	Concatenates two linked lists
	*	@param other The linked list to append the this linked list
	*/
	public void addAll(LinkedList<Item> other)
	{
		if(other.size() == 0)
			return;
		this.last.next = other.first;
		this.last = other.last;
		n += other.size();
	}
	
	/**
	*	Removes the first occurance of the given item
	*	@param item the item to remove
	*/
	public void remove(Item item)
	{
		Node temp = first;
		if(temp.item.equals(item))
		{
			first = first.next;
			n--;
			return;
		}
		while(temp.next != null)
		{
			if(temp.next.item.equals(item))
			{
				temp.next = temp.next.next;
				n--;
				return;
			}
			temp = temp.next;
		}
	}
	
	
	/**
	*	Returns true if item is contained in the list
	*	@param item the item to look for
	*/
	public boolean contains(Item item)
	{
		Node it = first;
		while(it != null)
		{
			if(it.item.equals(item))
				return true;
			it = it.next;
		}
		return false;
	}
	
	/**
	*	Checks for duplicate items in the list
	*	@param item Item to checked for duplicates
	*/
	public boolean hasDuplicates(Item item)
	{
		Node it = first;
		int itemCount = 0;
		while(it != null)
		{
			if(it.item.equals(item))
			{
				itemCount++;
				if(itemCount == 2)
					return true;
			}
			it = it.next;
		}
		return false;
	}

    /**
     * Removes and returns the item on this queue that was least recently added.
     * @return the item on this queue that was least recently added
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null;   // to avoid loitering
        return item;
    }

	
    /**
     * Returns a string representation of this queue.
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
		String ret = "";
		if(n == 0)
			return "[]";
		s.append("[");
        for (Item item : this)
            s.append(item + ", ");
		ret = s.substring(0, s.length()-2);
        return ret + "]";
    } 

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Item> iterator()  {
        return new ListIterator();  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }


}

/******************************************************************************
 *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/