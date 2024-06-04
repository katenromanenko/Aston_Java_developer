import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

/**
 * Unit-тесты для классов MyArrayList и QuickSort.
 */
public class MyArrayListTest {
    private MyArrayList<Integer> list;
    private MyArrayList<SortableInteger> comparableList;

    @BeforeEach
    void setUp() {
        list = new MyArrayList<>();
        comparableList = new MyArrayList<>();
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

    @Test
    void testQuickSortComparable() {
        comparableList.add(new SortableInteger(3));
        comparableList.add(new SortableInteger(1));
        comparableList.add(new SortableInteger(2));
        comparableList.quickSortComparable();
        assertEquals(1, comparableList.get(0).value);
        assertEquals(2, comparableList.get(1).value);
        assertEquals(3, comparableList.get(2).value);
    }
}

class SortableInteger implements Comparable<SortableInteger> {
    int value;

    public SortableInteger(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(SortableInteger other) {
        return Integer.compare(this.value, other.value);
    }
}