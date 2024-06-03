import java.util.Comparator; // Добавляем импорт для Comparator

/**
 * Главный класс для демонстрации работы MyArrayList и QuickSort.
 */
public class Main {
    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);

        System.out.println("Исходный список: " + list);

        QuickSort.quickSort(list, Comparator.naturalOrder());

        System.out.println("Отсортированный список: " + list);

        // Выводим список
        System.out.println("Список после добавления элементов: " + list);

        // Удаляем элемент из списка
        int removedElement = list.remove(1);

        // Выводим список после удаления элемента
        System.out.println("Список после удаления элемента: " + list);

        // Получаем элемент из списка
        int element = list.get(0);
        System.out.println("Первый элемент списка: " + element);

        // Заменяем элемент в списке
        list.set(0, 4);
        System.out.println("Список после замены первого элемента: " + list);

        // Получаем размер списка
        int size = list.size();
        System.out.println("Размер списка: " + size);
    }
}
