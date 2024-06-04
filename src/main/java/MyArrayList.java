import java.util.Comparator;
/**
 * MyArrayList представляет собой динамический массив, который имитирует поведение java.util.ArrayList.
 * Он поддерживает обобщенные типы и предоставляет методы для добавления, удаления, доступа к элементам и определения размера.
 *
 * @param <E> тип элементов в этом списке
 */
public class MyArrayList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size = 0;

    /**
     * Создает пустой список с начальной емкостью десять.
     */
    @SuppressWarnings("unchecked") // Подавляем предупреждение, т.к. приведение типов безопасно в данном контексте
    public MyArrayList() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Добавляет указанный элемент в конец этого списка.
     *
     * @param e элемент, который нужно добавить в этот список
     * @return true (как указано в Collection.add(E))
     */
    public boolean add(E e) {
        ensureCapacity(size + 1);
        elements[size++] = e;
        return true;
    }

    /**
     * Удаляет элемент в указанной позиции в этом списке.
     *
     * @param index индекс элемента, который нужно удалить
     * @return элемент, который был удален из списка
     * @throws IndexOutOfBoundsException если индекс выходит за пределы диапазона (index < 0 || index >= size)
     */
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E oldValue = (E) elements[index];

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            for (int i = index; i < size - 1; i++) {
                elements[i] = elements[i + 1];
            }
        }
        elements[--size] = null; // Очистка для работы сборщика мусора

        return oldValue;
    }

    /**
     * Возвращает элемент в указанной позиции в этом списке.
     *
     * @param index индекс элемента для возврата
     * @return элемент в указанной позиции в этом списке
     * @throws IndexOutOfBoundsException если индекс выходит за пределы диапазона (index < 0 || index >= size)
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) elements[index];
    }

    /**
     * Заменяет элемент в указанной позиции в этом списке на указанный элемент.
     *
     * @param index индекс элемента для замены
     * @param element элемент, который нужно сохранить в указанной позиции
     * @return элемент, ранее находившийся в указанной позиции
     * @throws IndexOutOfBoundsException если индекс выходит за пределы диапазона (index < 0 || index >= size)
     */
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E oldValue = (E) elements[index];
        elements[index] = element;
        return oldValue;
    }

    /**
     * Возвращает количество элементов в этом списке.
     *
     * @return количество элементов в этом списке
     */
    public int size() {
        return size;
    }

    /**
     * Увеличивает емкость этого экземпляра MyArrayList, если это необходимо, чтобы обеспечить возможность
     * хранения по крайней мере указанного количества элементов.
     *
     * @param minCapacity желаемая минимальная емкость
     */
    @SuppressWarnings("unchecked") // Подавляем предупреждение, т.к. приведение типов безопасно в данном контексте
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = elements.length + (elements.length >> 1);
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            Object[] newElements = new Object[newCapacity];
            // Используем System.arraycopy для копирования элементов
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    /**
     * Переопределение метода toString для класса MyArrayList.
     * Этот метод возвращает строковое представление списка, содержащего все элементы списка.
     *
     * @return строковое представление списка
     */
    @Override
    public String toString() {
        // Создаем объект StringBuilder для эффективного конструирования строки
        StringBuilder sb = new StringBuilder();

        // Добавляем открывающую скобку
        sb.append("[");

        // Проходим по всем элементам списка
        for (int i = 0; i < size; i++) {
            // Добавляем элемент в строку

            sb.append(elements[i]);

            // Если это не последний элемент, добавляем запятую и пробел
            if (i < size - 1) {
                sb.append(", ");
            }
        }

        // Добавляем закрывающую скобку
        sb.append("]");

        // Возвращаем построенную строку
        return sb.toString();
    }

    /**
     * Сортирует список по возрастанию, если элементы реализуют интерфейс Comparable.
     *
     *
     */
    @SuppressWarnings("unchecked")
    public void quickSortComparable() {
        QuickSort.quickSort(this, (Comparator<? super E>) Comparator.naturalOrder());
    }
}


