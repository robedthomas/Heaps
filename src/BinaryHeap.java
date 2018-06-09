/**
 * @author Rob Thomas
 * A simple binary heap data structure implementation.
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class BinaryHeap<Type extends Comparable<Type>> extends Heap<Type> implements Cloneable
{
    /* * * * * PUBLIC API * * * * */

    /**
     * Constructs an empty BinaryHeap.
     */
    public BinaryHeap ()
    {
        Size = 0;
        Comparer = DefaultComparer;
        Elems = new ArrayList<Type>();
    }

    /**
     * Constructs an empty BinaryHeap using the given Comparator.
     * @param comparer the comparator method to use for comparing items in the binary heap.
     */
    public BinaryHeap (Comparator<Type> comparer)
    {
        Size = 0;
        Comparer = comparer;
        Elems = new ArrayList<Type>();
    }

    /**
     * Gets the number of items in this binary heap.
     * @return the number of items in this binary heap.
     */
    @Override
    public int GetSize ()
    {
        return Size;
    }

    /**
     * Reports whether or not this binary heap is empty.
     * @return true if this binary heap is empty, false otherwise.
     */
    @Override
    public boolean IsEmpty ()
    {
        return Size == 0;
    }

    /**
     * Adds a new item to the binary heap.
     * @param item the item to add to the binary heap.
     */
    @Override
    public void Push (Type item)
    {
        /* Add the item at the next free space. */
        Elems.add(Size, item);
        /* Increment the binary heap Size. */
        Size++;
        /* Float the new item. */
        Float(Size - 1);
    }

    /**
     * Pops off the top element of the binary heap, removing it.
     * @return the topmost element of the binary heap.
     * @throws IndexOutOfBoundsException when called on an empty binary heap.
     */
    @Override
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

    /**
     * Peeks at the top item in the heap.
     * @return the item that would be returned by the next call to Pop().
     * @throws IndexOutOfBoundsException when called on an empty binary heap.
     */
    public Type Peek ()
    {
        if (IsValidIndex(0))
        {
            return ItemAt(0);
        }
        else
        {
            throw new IndexOutOfBoundsException("Tried to peek at an empty binary heap.");
        }
    }

    /**
     * Compares two items based on the Comparator this BinaryHeap is configured to use.
     * @param item the item to be compared.
     * @param other the item to compare to the first item.
     * @return the result of comparing item to other (in that order).
     */
    public int CompareLikeHeap (Type item, Type other)
    {
        return Comparer.compare(item, other);
    }

    /**
     * Verifies that two items follow the heap's invariant - that when an item popped from the heap is compared to
     * an item popped from the heap later, the result will be non-positive. This indicates that the earlier item is
     * sorted before the later item by the heap's underlying comparator.
     * @param earlierItem an item popped earlier from the heap.
     * @param laterItem an item popped later from the heap.
     * @return true if earlierItem would be sorted before laterItem.
     */
    public boolean ItemsFollowHeapComparator (Type earlierItem, Type laterItem)
    {
        return CompareLikeHeap(earlierItem, laterItem) <= 0;
    }

    /**
     * Converts this BinaryHeap to a string representation. This string will be a sequence of integers in the order
     * they are stored in the heap, so an item's children are at twice the item's position.
     * @return a string representing this BinaryHeap.
     */
    public String toString ()
    {
        String str = new String();
        str += "[ ";
        for (int i = 0; i < Size; i++)
        {
            str += ItemAt(i) + " ";
        }
        str += "]";
        return str;
    }

    /**
     * Clones this heap, yielding a separate BinaryHeap object whose contents and properties are identical to this one's.
     * @return a clone of this heap.
     */
    @Override
    public BinaryHeap<Type> clone ()
    {
        BinaryHeap<Type> clone = new BinaryHeap<Type>(Comparer);
        for (int i = 0; i < Size; i++)
        {
            clone.Push(Elems.get(i));
        }
        return clone;
    }

    /* * * * * PRIVATE FIELDS * * * * */

    private int Size = 0;
    private ArrayList<Type> Elems;
    private Comparator<Type> Comparer;
    private final Comparator<Type> DefaultComparer = new Comparator<Type>()
    {
        @Override
        public int compare(Type o1, Type o2)
        {
            return o1.compareTo(o2);
        }
    };

    /* * * * * PRIVATE METHODS * * * * */

    /**
     * Floats up the item at the given index, putting it in its proper position upward.
     * @param index the index in the binary heap of the item to float.
     */
    private void Float (int index)
    {
        /* If this item ranks higher than its parent, swap them, then float this item again. */
        if (HasParent(index))
        {
            if (RanksHigher(ItemAt(index), ItemAt(ParentIndex(index))))
            {
                SwapItems(index, ParentIndex(index));
                Float(ParentIndex(index));
            }
        }
    }

    /**
     * Sinks down the item at the given index, putting it in its proper position downward.
     * @param index the index in the binary heap of the item to sink.
     */
    private void Sink (int index)
    {
        int swappedIndex = -1;
        /* Swap this item with whichever of its children ranks highest and also ranks higher than this item. */
        if (HasLeftChild(index))
        {
            if (RanksLower(ItemAt(index), ItemAt(LeftChild(index))))
            {
                if (HasRightChild(index) && RanksLower(ItemAt(index), ItemAt(RightChild(index))))
                {
                    /* If the parent ranks lower than both its items, swap with the child that ranks highest. */
                    if (RanksLower(ItemAt(LeftChild(index)), ItemAt(RightChild(index))))
                    {
                        SwapItems(index, RightChild(index));
                        swappedIndex = RightChild(index);
                    }
                    else
                    {
                        SwapItems(index, LeftChild(index));
                        swappedIndex = LeftChild(index);
                    }
                }
                else
                {
                    SwapItems(index, LeftChild(index));
                    swappedIndex = LeftChild(index);
                }
            }
            else if (HasRightChild(index) && RanksLower(ItemAt(index), ItemAt(RightChild(index))))
            {
                SwapItems(index, RightChild(index));
                swappedIndex = RightChild(index);
            }
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
        return index >= 0 && index < Size;
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
        throw new IndexOutOfBoundsException(String.format("Tried to get an item at an invalid index: %d.", index));
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
        else
        {
            throw new IndexOutOfBoundsException("Tried to swap two items using an invalid index.");
        }
    }

    /**
     * Compares two items based on the established Comparator.
     * @param a the first item to compare.
     * @param b the second item to compare.
     * @return the result of the comparison.
     */
    private int Compare (Type a, Type b)
    {
        return Comparer.compare(a, b);
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

    /**
     * Determines whether or not the item at the given index has a parent.
     * @param index the index of the item to check for a parent.
     * @return true if the item at the given index has a parent. false otherwise.
     */
    private boolean HasParent (int index)
    {
        return ParentIndex(index) >= 0;
    }

    /**
     * Determines whether or not the item at the given index has a left child.
     * @param index the index of the item to check for a left child.
     * @return true if the item at the given index has a left child. false otherwise.
     */
    private boolean HasLeftChild (int index)
    {
        return IsValidIndex(LeftChild(index));
    }

    /**
     * Determines whether or not the item at the given index has a right child.
     * @param index the index of the item to check for a right child.
     * @return true if the item at the given index has a right child. false otherwise.
     */
    private boolean HasRightChild (int index)
    {
        return IsValidIndex(RightChild(index));
    }

    /**
     * Returns the string representing the subtree starting at the given index.
     * @param startIndex the index of the item to generate the subtree string of.
     * @return a string representing the subtree starting at the given index.
     */
    private String GetSubtreeString (int startIndex)
    {
        String subtreeString = new String();
        if (IsValidIndex(startIndex))
        {
            subtreeString += ItemAt(startIndex) + " ";
            subtreeString += GetSubtreeString(LeftChild(startIndex));
            subtreeString += GetSubtreeString(RightChild(startIndex));
        }
        return subtreeString;
    }

    @Override
    public Iterator<Type> iterator()
    {
        BinaryHeap<Type> clone = clone();
        Iterator<Type> iterator = new Iterator<Type>()
        {
            @Override
            public boolean hasNext()
            {
                return clone.GetSize() > 0;
            }

            @Override
            public Type next()
            {
                return clone.Pop();
            }
        };
        return iterator;
    }
}
