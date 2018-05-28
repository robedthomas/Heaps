import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

/**
 * @author Rob Thomas
 * Provides a collection of unit tests for the BinaryHeap class.
 */

public class BinaryHeapTests
{
    static Random random;
    static BinaryHeap<Integer> heap;

    @BeforeAll
    public static void initAll()
    {
        random = new Random();
    }

    @BeforeEach
    public void init()
    {
        heap = new BinaryHeap<Integer>();
    }

    @Test
    public void NewHeapShouldBeEmpty ()
    {
        assertTrue(heap.IsEmpty());
    }

    @Test
    public void PopFromEmptyHeapShouldThrowException ()
    {
        assertThrows(IndexOutOfBoundsException.class, heap::Pop);
    }

    @Test
    public void PushToHeapShouldIncreaseSizeByOne ()
    {
        int initSize = heap.GetSize();
        heap.Push(0);
        int finalSize = heap.GetSize();
        assertEquals(initSize + 1, finalSize);
    }

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
}
