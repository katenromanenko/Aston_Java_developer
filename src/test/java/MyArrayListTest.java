import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

/**
 * Unit-тесты для классов MyArrayList и QuickSort.
 */
public class MyArrayListTest {
    private MyArrayList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyArrayList<>();
    }

    @Test
    void testAdd() {
        list.add(1);
        list.add(2);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
    }

    @Test
    void testRemove() {
        list.add(1);
        list.add(2);
        int removedElement = list.remove(0);
        assertEquals(1, removedElement);
        assertEquals(1, list.size());
        assertEquals(2, list.get(0));
    }

    @Test
    void testGet() {
        list.add(1);
        assertEquals(1, list.get(0));
    }

    @Test
    void testSet() {
        list.add(1);
        list.add(2);
        list.set(0, 3);
        assertEquals(3, list.get(0));
    }

    @Test
    void testQuickSort() {
        list.add(3);
        list.add(1);
        list.add(2);
        QuickSort.quickSort(list, Comparator.naturalOrder());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }
}