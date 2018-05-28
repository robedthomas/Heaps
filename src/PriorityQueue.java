/**
 * @author Rob Thomas
 * A simple abstract priority queue class. PriorityQueue differs from Queue in that it should
 * return items in order of priority when calling Dequeue().
 */

public abstract class PriorityQueue<Type extends Comparable<Type>> extends Queue<Type>
{
    /**
     * Gets the number of items in this PriorityQueue.
     * @return the number of items in this PriorityQueue.
     */
    @Override
    public abstract int GetSize();

    /**
     * Reports whether or not this PriorityQueue is empty.
     * @return true if this PriorityQueue is empty, false otherwise.
     */
    @Override
    public abstract boolean IsEmpty();

    /**
     * Adds a new item to the PriorityQueue.
     * @param item the item to add to the PriorityQueue.
     */
    @Override
    public abstract void Enqueue (Type item);

    /**
     * Removes and returns the item of highest priority in the PriorityQueue.
     * @return the topmost element of the PriorityQueue.
     * @throws IndexOutOfBoundsException when called on an empty PriorityQueue.
     */
    @Override
    public abstract Type Dequeue();
}
