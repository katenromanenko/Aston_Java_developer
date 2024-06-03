

import java.util.Comparator;

/**
 * QuickSort provides a static method to sort a MyArrayList using the QuickSort algorithm.
 */
public class QuickSort {

    /**
     * Sorts the specified list according to the order induced by the specified comparator using QuickSort algorithm.
     *
     * @param list the list to be sorted
     * @param comparator the comparator to determine the order of the list
     * @param <E> the type of elements in the list
     */
    public static <E> void quickSort(MyArrayList<E> list, Comparator<? super E> comparator) {
        quickSort(list, 0, list.size() - 1, comparator);
    }

    private static <E> void quickSort(MyArrayList<E> list, int low, int high, Comparator<? super E> comparator) {
        if (low < high) {
            int pi = partition(list, low, high, comparator);
            quickSort(list, low, pi - 1, comparator);
            quickSort(list, pi + 1, high, comparator);
        }
    }

    private static <E> int partition(MyArrayList<E> list, int low, int high, Comparator<? super E> comparator) {
        E pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private static <E> void swap(MyArrayList<E> list, int i, int j) {
        E temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}


