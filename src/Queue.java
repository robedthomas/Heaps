/**
 * @author Rob Thomas
 * A simple abstract queue class. The queue returns items in order of when they were added (first in - first out).
 */

public abstract class Queue<Type extends Comparable<Type>> implements Iterable<Type>, Cloneable
{
    /**
     * Gets the number of items in this Queue.
     * @return the number of items in this Queue.
     */
    public abstract int GetSize();

    /**
     * Reports whether or not this Queue is empty.
     * @return true if this Queue is empty, false otherwise.
     */
    public abstract boolean IsEmpty();

    /**
     * Adds a new item to the Queue.
     * @param item the item to add to the Queue.
     */
    public abstract void Enqueue (Type item);

    /**
     * Removes and returns the item that has been in the Queue longest.
     * @return the topmost element of the Queue.
     * @throws IndexOutOfBoundsException when called on an empty Queue.
     */
    public abstract Type Dequeue();
}
