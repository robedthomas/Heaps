/**
 * @author Rob Thomas
 * A simple binary heap data structure implementation.
 */

import java.util.ArrayList;
import java.util.Comparator;

public class BinaryHeap<Type extends Comparable<Type>> 
{
    /* * * * * PUBLIC API * * * * */

    /**
     * Constructs an empty BinaryHeap.
     */
    public BinaryHeap ()
    {
        Size = 0;
        Comparer = null;
    }

    /**
     * Constructs an empty BinaryHeap using the given Comparator.
     * @param comparer the comparator method to use for comparing items in the binary heap.
     */
    public BinaryHeap (Comparator<Type> comparer)
    {
        Size = 0;
        Comparer = comparer;
    }

    /**
     * Gets the number of items in this binary heap.
     * @return the number of items in this binary heap.
     */
    public int GetSize ()
    {
        return Size;
    }

    /**
     * Reports whether or not this binary heap is empty.
     * @return true if this binary heap is empty, false otherwise.
     */
    public boolean IsEmpty ()
    {
        return Size == 0;
    }

    /**
     * Adds a new item to the binary heap.
     * @param item the item to add to the binary heap.
     */
    public void Push (Type item)
    {
        /* Add the item at the next free space. */
        Elems.add(Size, item);
        /* Float the new item. */
        Float(Size);
        /* Increment the binary heap Size. */
        Size++;
    }

    /**
     * Pops off the top element of the binary heap, removing it.
     * @return the topmost element of the binary heap.
     * @throws IndexOutOfBoundsException when called on an empty binary heap.
     */
    public Type Pop ()
    {
        /* If the binary heap is empty, throw an exception. */
        if (IsEmpty())
        {
            throw new IndexOutOfBoundsException("Tried to pop from an empty binary heap.");
        }
        /* Save the top item. */
        Type topItem = ItemAt(0);
        /* Swap top item with the last item. */
        SwapItems(0, LastItem());
        /* Decrement the binary heap Size. */
        Size--;
        /* Sink the new top item (was the last item). */
        Sink(0);
        /* Return the popped item. */
        return topItem;
    }

    /* * * * * PRIVATE FIELDS * * * * */

    private int Size = 0;
    private ArrayList<Type> Elems;
    private Comparator<Type> Comparer;

    /* * * * * PRIVATE METHODS * * * * */

    /**
     * Floats up the item at the given index, putting it in its proper position upward.
     * @param index the index in the binary heap of the item to float.
     */
    private void Float (int index)
    {
        /* If this item ranks higher than its parent, swap them, then float this item again. */
        if (RanksHigher(ItemAt(index), ItemAt(ParentIndex(index))))
        {
            SwapItems(index, ParentIndex(index));
            Float(ParentIndex(index));
        }
    }

    /**
     * Sinks down the item at the given index, putting it in its proper position downward.
     * @param index the index in the binary heap of the item to sink.
     */
    private void Sink (int index)
    {
        int swappedIndex = -1;
        /* If this item ranks lower than its left child, swap them. */
        if (RanksLower(ItemAt(index), ItemAt(LeftChild(index))))
        {
            SwapItems(index, LeftChild(index));
            swappedIndex = LeftChild(index);
        }
        /* Otherwise, do the same for the right child too. */
        else if (RanksLower(ItemAt(index), ItemAt(RightChild(index))))
        {
            SwapItems(index, RightChild(index));
            swappedIndex = RightChild(index);
        }
        /* If this item was swapped, sink it again. */
        if (swappedIndex >= 0)
        {
            Sink(swappedIndex);
        }
    }

    /**
     * Checks whether the given index points to an item in the binary heap.
     * @param index the index to check for validity.
     * @return true if the index points to an item in the binary heap, false otherwise.
     */
    private boolean IsValidIndex (int index)
    {
        return index < Size;
    }

    /**
     * Gets the item at the given index.
     * @param index the index of the item to be returned.
     * @return the item at the given index.
     * @throws IndexOutOfBoundsException when index is invalid.
     */
    private Type ItemAt (int index)
    {
        if (IsValidIndex(index))
        {
            return Elems.get(index);
        }
        throw new IndexOutOfBoundsException("Tried to get an item at an invalid index.");
    }

    /**
     * Gets the index of the lowest item in the binary heap.
     * @return the index of the binary heap's lowest item.
     */
    private int LastItem ()
    {
        return Size - 1;
    }

    /**
     * Swaps two items in the binary heap.
     * @param firstIndex the index of the first item.
     * @param secondIndex the index of the second item.
     * @throws IndexOutOfBoundsException when either index is invalid.
     */
    private void SwapItems (int firstIndex, int secondIndex)
    {
        if (IsValidIndex(firstIndex) && IsValidIndex(secondIndex))
        {
            Type firstItem = ItemAt(firstIndex);
            Elems.set(firstIndex, ItemAt(secondIndex));
            Elems.set(secondIndex, firstItem);
        }
        throw new IndexOutOfBoundsException("Tried to swap two items using an invalid index.");
    }

    /**
     * Compares two items based on the established Comparator.
     * @param a the first item to compare.
     * @param b the second item to compare.
     * @return the result of the comparison.
     */
    private int Compare (Type a, Type b)
    {
        if (Comparer != null)
        {
            return Comparer.compare(a, b);
        }
        return a.compareTo(b);
    }

    /**
     * Determines whether one item should come before another in the binary heap.
     * @param attacker the item that might rank higher.
     * @param defender the item to compare to.
     * @return true if attacker ranks higher than defender. false otherwise.
     */
    private boolean RanksHigher (Type attacker, Type defender)
    {
        return Compare(attacker, defender) < 0;
    }

    /**
     * Determines whether one item should come after another in the binary heap.
     * @param attacker the item that might rank lower.
     * @param defender the item to compare to.
     * @return true if attacker ranks lower than defender. false otherwise.
     */
    private boolean RanksLower (Type attacker, Type defender)
    {
        return Compare(attacker, defender) > 0;
    }

    /**
     * Determines whether two items have equal rank in the binary heap.
     * @param attacker the first item.
     * @param defender the second item.
     * @return true if the two items have equal ranking. false otherwise.
     */
    private boolean TiesWith (Type attacker, Type defender)
    {
        return Compare(attacker, defender) == 0;
    }

    /**
     * Gets the index of the parent of the item at the given index.
     * @param childIndex the index of the item whose parent will be found.
     * @return the index of the item's parent.
     */
    private int ParentIndex (int childIndex)
    {
        return (childIndex - 1) / 2;
    }

    /**
     * Gets the index of the left child of the item at the given index.
     * @param parentIndex the index of the item whose left child will be found.
     * @return if item has a left child, the index of the item's left child. Otherwise, a negative number.
     */
    private int LeftChild (int parentIndex)
    {
        int childIndex = (2 * parentIndex) + 1;
        if (IsValidIndex(childIndex))
        {
            return childIndex;
        }
        return -1;
    }

    /**
     * Gets the index of the right child of the item at the given index.
     * @param parentIndex the index of the item whose right child will be found.
     * @return if item has a right child, the index of the item's right child. Otherwise, a negative number.
     */
    private int RightChild (int parentIndex)
    {
        int childIndex = (2 * parentIndex) + 2;
        if (IsValidIndex(childIndex))
        {
            return childIndex;
        }
        return -1;
    }
}
