import java.util.Comparator;

/**
 * @author Rob Thomas
 * An abstract class representing a definition of a priority heap.
 */

public abstract class Heap<Type extends Comparable<Type>>
{
    /* * * * * PUBLIC API * * * * */

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
}
