package dynamic_array;/*
 * Реализация шаблонного класса dynamic_array.DynamicArray (динамический массив)
 * Аналог ArrayList с базовыми операциями и автоматическим изменением размера
 */
import java.util.Arrays;
import java.util.Comparator;

public class DynamicArray<T> { // T - параметр типа (может быть Integer, String и т. д.)
    // Константы для управления размером массива
    public static final int DEFAULT_CAPACITY = 10;          // Начальная емкость по умолчанию
    public static final double GROWTH_FACTOR = 1.5;        // Коэффициент увеличения размера
    public static final double SHRINK_THRESHOLD = 0.25;    // Порог для уменьшения размера

    private Object[] data;  // Внутренний массив для хранения элементов (используется Object[], чтобы хранить любые типы)
    private int size;       // Текущее количество элементов в массиве

    /*
     * Конструкторы
     */

    // Конструктор по умолчанию создаёт массив с ёмкостью по умолчанию (10)
    public DynamicArray() {
        this(DEFAULT_CAPACITY);
    }

    // Конструктор с заданной начальной емкостью
    public DynamicArray(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Ёмкость должна быть положительной");
        }
        this.data = new Object[initialCapacity];
        this.size = 0; // Устанавливается начальное количество элементов в массиве
    }

    /*
     * Основные операции с массивом
     */

    // Добавление элемента в конец массива
    public void add(T element) {
        ensureCapacity(size + 1);  // Проверяем, нужно ли увеличить массив
        data[size++] = element;    // Добавляем элемент и увеличиваем счетчик
    }

    // Удаление последнего элемента массива
    public T removeLast() {
        if (size == 0) { // Проверяем, не пуст ли массив
            throw new IllegalStateException("Array is empty");
        }
        // --size: сначала уменьшаем счётчик элементов, затем используем новое значение
        T removed = (T) data[--size];  // Получаем последний элемент
        data[size] = null;             // Удаляем ссылку для сборщика мусора

        // Уменьшаем массив
        // Условия уменьшения:
        // 1) Текущий размер меньше 25% от ёмкости
        // 2) Текущая ёмкость больше ёмкости по умолчанию
        if (size < data.length * SHRINK_THRESHOLD && data.length > DEFAULT_CAPACITY) {
            resize((int) (data.length / GROWTH_FACTOR));
        }
        // Возвращаем значение, которое было последним элементом до удаления
        return removed;
    }

    /*
     * Методы доступа к элементам
     */

    /** Получение элемента по индексу
    @param index индекс элемента который нужно получить
     @return возвращаемый элемент массива
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);      // Проверка корректности индекса
        return (T) data[index]; // Возвращаем элемент, приведение Object к T
    }

    // Установка значения элемента по индексу
    public void set(int index, T value) {
        checkIndex(index);  // Проверка корректности индекса
        data[index] = value; // Устанавливаем значение
    }

    /*
     * Методы для работы с размером массива
     */

    // Получение текущего количества элементов
    public int size() {
        return size;
    }

    // Получение текущей емкости массива
    public int capacity() {
        return data.length;
    }

    /*
     * Дополнительные операции
     */

    // Удаление элемента по указанному индексу
    public T remove(int index) {
        checkIndex(index);  // Проверка корректности индекса

        T removed = (T) data[index];  // Сохраняем удаляемый элемент

        // Сдвигаем все элементы справа от удаляемого на одну позицию влево
        // 1. Копируем часть массива от index+1 до конца
        // 2. Вставляем эту часть начиная с index
        // 3. Длина копируемого блока: size - index - 1
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null;  // Удаляем последнюю ссылку

        // Проверяем, нужно ли уменьшить массив
        if (size < data.length * SHRINK_THRESHOLD && data.length > DEFAULT_CAPACITY) {
            resize((int) (data.length / GROWTH_FACTOR));
        }

        return removed;
    }

    // Вставка элемента по указанному индексу
    public void insert(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        ensureCapacity(size + 1);  // Проверяем, нужно ли увеличить массив

        // Сдвигаем элементы вправо, чтобы освободить место для нового элемента
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;  // Вставляем новый элемент
        size++;                // Увеличиваем счетчик
    }

    /*
     * Методы поиска и сортировки
     */

    // Линейный поиск элемента в массиве
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i])) {
                return i;  // Возвращаем индекс при нахождении
            }
        }
        return -1;  // Возвращаем -1 если элемент не найден
    }

    // Сортировка массива с использованием компаратора
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> comparator) {
        Arrays.sort((T[]) data, 0, size, comparator);
    }

    /*
     * Вспомогательные методы
     */

    // Проверка корректности индекса
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /** Проверка и увеличение емкости при необходимости
     * @param minCapacity минимально требуемая ёмкость
     *  data.length - текущая ёмкость внутреннего массива.
     * */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) { // массив заполнен, и нужно расширение
            resize((int) (data.length * GROWTH_FACTOR + 1)); // + 1 — чтобы при маленьких размерах (например, data.length = 1) массив гарантированно увеличился
        }
    }

    // Изменение размера внутреннего массива
    /** Создаёт новый массив нужного размера и копирует в него старые элементы
    // @param newCapacity - размер нового массива */
    private void resize(int newCapacity) {
        // Arrays.copyOf() - создаёт новый массив размером newCapacity.
        //Все существующие элементы копируются в новый массив.
        //Оставшиеся ячейки заполняются null.
        data = Arrays.copyOf(data, newCapacity);
    }

    /*
     * Метод для строкового представления массива
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /*
     * Пример использования dynamic_array.DynamicArray
     */
    public static void main(String[] args) {
        // Создаем массив и добавляем 20 элементов
        DynamicArray<Integer> arr = new DynamicArray<>();
        for (int i = 0; i < 20; i++) {
            arr.add(i);
        }
        System.out.println(arr); // [0, 1, 2, ..., 19]

        // Демонстрация удаления элемента
        System.out.println("Удаляем элемент с индексом 5");
        arr.remove(5);
        System.out.println(arr);

        // Демонстрация вставки элемента
        arr.insert(3, 100);
        System.out.println("Вставляем 100 по индексу 3");
        System.out.println(arr);

        // Демонстрация сортировки
        System.out.println("Сортируем");
        arr.sort(Comparator.naturalOrder());
        System.out.println(arr);
    }
}