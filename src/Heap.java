import java.util.Comparator;

/**
 * @author Rob Thomas
 * An abstract class representing a definition of a priority heap.
 */

public abstract class Heap<Type extends Comparable<Type>> extends PriorityQueue<Type>
{
    /* * * * * PUBLIC API * * * * */

    /**
     * Implemented here to satisfy the PriorityQueue requirements. Equivalent to calling Push() on the item.
     * @param item the item to add to the PriorityQueue.
     */
    public void Enqueue (Type item)
    {
        Push(item);
    }

    /**
     * Implemented here to satisfy the PriorityQueue requirements. Equivalent to calling Pop().
     * @return the topmost element of the PriorityQueue.
     * @throws IndexOutOfBoundsException when called on an empty PriorityQueue.
     */
    public Type Dequeue ()
    {
        return Pop();
    }

    /**
     * Adds a new item to the Heap.
     * @param item the item to add to the Heap.
     */
    public abstract void Push (Type item);

    /**
     * Pops off the top element of the Heap, removing it.
     * @return the topmost element of the Heap.
     * @throws IndexOutOfBoundsException when called on an empty Heap.
     */
    public abstract Type Pop ();

    /**
     * Gets the number of items in this Heap.
     * @return the number of items in this Heap.
     */
    public abstract int GetSize();

    /**
     * Reports whether or not this Heap is empty.
     * @return true if this Heap is empty, false otherwise.
     */
    public abstract boolean IsEmpty();

    /**
     * Determines whether or not this heap's items are correctly ordered to follow the underlying ordering.
     * @return true if this heap's items are correctly ordered, false otherwise.
     */
    public abstract boolean ItemsAreInOrder ();
}
