import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Rob Thomas
 * Provides a collection of unit tests for the BinaryHeap class.
 */

public class BinaryHeapTests
{
    static Random random;
    static BinaryHeap<Integer> heap;

    @BeforeAll
    /**
     * Before the test suite is run, sets up a Random generator.
     */
    public static void initAll()
    {
        random = new Random();
    }

    /**
     * Before each individual test, generates a fresh BinaryHeap.
     */
    @BeforeEach
    public void init()
    {
        heap = new BinaryHeap<Integer>();
    }

    /**
     * Verifies that newly generated BinaryHeaps are empty.
     */
    @Test
    public void NewHeapShouldBeEmpty ()
    {
        assertTrue(heap.IsEmpty());
    }

    /**
     * Verifies that attempting to Pop from an empty BinaryHeap throws an IndexOutOfBoundsException.
     */
    @Test
    public void PopFromEmptyHeapShouldThrowException ()
    {
        assertThrows(IndexOutOfBoundsException.class, heap::Pop);
    }

    /**
     * Verifies that pushing to a BinaryHeap increments its size by one.
     */
    @Test
    public void PushToHeapShouldIncreaseSizeByOne ()
    {
        int initSize = heap.GetSize();
        heap.Push(0);
        int finalSize = heap.GetSize();
        assertEquals(initSize + 1, finalSize);
    }

    /**
     * Verifies that pushing to a BinaryHeap some number X times increments the heap's size by X.
     */
    @Test
    public void PushXTimesToHeapShouldIncreaseSizeByX ()
    {
        int initSize = heap.GetSize();
        // Push at least twice, otherwise equivalent to previous tests.
        int numPushes = random.nextInt(100) + 2;
        for (int i = 0; i < numPushes; i++)
        {
            heap.Push(i);
        }
        assertEquals(numPushes, heap.GetSize());
    }

    /**
     * Verifies that popping the contents of a BinaryHeap returns its contents in non-decreasing order.
     */
    @Test
    public void PopShouldReturnInNonDecreasingOrder ()
    {
        System.err.println("---RETURN IN NON-DECREASING ORDER---");
        int numPushes = 32;
        int[] randomOrderList = IntStream.range(0, numPushes).toArray();
        for (int i = 0; i < numPushes; i++)
        {
            int choice = random.nextInt(numPushes - i) + i;
            int swap = randomOrderList[choice];
            randomOrderList[choice] = randomOrderList[i];
            randomOrderList[i] = swap;
        }
        System.err.print("Pushing order:\n");
        for (int x : randomOrderList)
        {
            heap.Push(x);
            System.err.print(x + " ");
            System.err.println(heap);
        }
        System.err.print("\nPopping order:\n");
        int previous = -1;
        while (!heap.IsEmpty())
        {
            int next = heap.Pop();
            System.err.print(next + " ");
            System.err.println(heap);
            assertTrue(heap.ItemsFollowHeapComparator(previous, next));
            previous = next;
        }
        System.err.println();
    }

    /**
     * Verifies the iterator() function is working correctly by iterating through the whole BinaryHeap
     * and checking that each item pushed in is popped out.
     */
    @Test
    public void IteratingThroughWholeHeapShouldReturnAllItems ()
    {
        int numPushes = 32;
        int[] pushList = IntStream.range(0, numPushes).toArray();
        boolean[] itemSeen = new boolean[numPushes];
        for (int x : pushList)
        {
            heap.Push(x);
        }
        for (int p : heap)
        {
            itemSeen[p] = true;
        }
        for (boolean b : itemSeen)
        {
            assertTrue(b);
        }
    }

    /**
     * Verifies that utilizing the Iterator returned by the iterator() function does not alter the state of the heap.
     */
    @Test
    public void IteratingDoesNotAlterHeap ()
    {
        int numPushes = 32;
        int[] pushList = IntStream.range(0, numPushes).toArray();
        for (int x : pushList)
        {
            heap.Push(x);
        }
        int topItem = heap.Peek();
        for (int item : heap)
        {
            assertTrue(heap.GetSize() == numPushes);
            assertEquals(topItem, (int)heap.Peek());
        }
    }

    /**
     * Verifies that constructing a heap with a custom Comparator will make the BinaryHeap compare using that comparator.
     * In particular, this test uses a comparator that ranks greater numbers higher and lesser numbers lower.
     * This should result in the heap popping the greatest values first and the least values last.
     */
    @Test
    public void TestCustomComparator ()
    {
        Comparator<Integer> customComparator = new Comparator<Integer>()
        {
            @Override
            public int compare(Integer o1, Integer o2)
            {
                return o1.compareTo(o2) * -1;
            }
        };
        heap = new BinaryHeap<>(customComparator);
        System.err.println("---RETURN IN NON-INCREASING ORDER---");
        int numPushes = 32;
        int[] randomOrderList = IntStream.range(0, numPushes).toArray();
        for (int i = 0; i < numPushes; i++)
        {
            int choice = random.nextInt(numPushes - i) + i;
            int swap = randomOrderList[choice];
            randomOrderList[choice] = randomOrderList[i];
            randomOrderList[i] = swap;
        }
        System.err.print("Pushing order:\n");
        for (int x : randomOrderList)
        {
            heap.Push(x);
            System.err.print(x + " ");
            System.err.println(heap);
        }
        System.err.print("\nPopping order:\n");
        int previous = numPushes + 1;
        while (!heap.IsEmpty())
        {
            int next = heap.Pop();
            System.err.print(next + " ");
            System.err.println(heap);
            assertTrue(heap.ItemsFollowHeapComparator(previous, next));
            previous = next;
        }
        System.err.println();
    }
}
