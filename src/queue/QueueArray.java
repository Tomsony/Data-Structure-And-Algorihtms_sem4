package queue;


/**
 * Класс QueueArray реализует очередь (FIFO - First In First Out)
 * с использованием циклического массива.
 *
 * Принцип работы:
 * - Элементы добавляются в конец (хвост) и удаляются из начала (голова)
 * - Используется циклический массив для эффективного использования памяти
 * - Индексы front и rear перемещаются по кругу
 *
 * Временная сложность:
 * - enqueue: O(1) - добавление в конец
 * - dequeue: O(1) - удаление из начала
 * - peek: O(1) - просмотр первого элемента
 * - search: O(n) - линейный поиск
 * - size/isEmpty/isFull: O(1)
 *
 * Пространственная сложность: O(n), где n - вместимость очереди
 *
 * @param <T> тип элементов в очереди
 *
 * @author Томских Артём ИВТ-23
 */
public class QueueArray<T> implements QueueInterface<T> {

    /** Массив для хранения элементов очереди */
    private T[] array;

    /** Индекс первого элемента в очереди (головы) */
    private int front;

    /** Индекс последнего элемента в очереди (хвоста) */
    private int rear;

    /** Текущее количество элементов в очереди */
    private int size;

    /** Максимальное количество элементов, которые можно поместить в очередь */
    private int capacity;

    /**
     * Создает очередь с заданной вместимостью.
     *
     * @param capacity максимальное количество элементов в очереди
     * @throws IllegalArgumentException если capacity <= 0
     */
    @SuppressWarnings("unchecked")
    public QueueArray(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Вместимость должна быть положительной");
        }

        this.capacity = capacity;
        this.array = (T[]) new Object[capacity]; // Создаем массив объектов и приводим к T[]
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    /**
     * Добавляет элемент в конец очереди.
     *
     * Алгоритм:
     * 1. Проверяет, не переполнена ли очередь
     * 2. Циклически перемещает rear (если достигнут конец массива, переходит в начало)
     * 3. Записывает элемент в array[rear]
     * 4. Увеличивает размер
     *
     * Временная сложность: O(1)
     *
     * @param element элемент для добавления
     * @throws NullPointerException если element равен null
     * @throws IllegalStateException если очередь переполнена
     */
    @Override
    public void enqueue(T element) {
        if (element == null) {
            throw new NullPointerException("Нельзя добавить null в очередь");
        }

        if (isFull()) {
            throw new IllegalStateException("Очередь переполнена");
        }

        // Циклически перемещаем rear
        rear = (rear + 1) % capacity;
        array[rear] = element;
        size++;
    }

    /**
     * Удаляет и возвращает элемент из начала очереди.
     *
     * Алгоритм:
     * 1. Проверяет, не пуста ли очередь
     * 2. Сохраняет элемент из array[front]
     * 3. Обнуляет ссылку для сборщика мусора
     * 4. Циклически перемещает front
     * 5. Уменьшает размер
     * 6. Возвращает сохраненный элемент
     *
     * Временная сложность: O(1)
     *
     * @return первый элемент очереди
     * @throws IllegalStateException если очередь пуста
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Очередь пуста");
        }

        T item = array[front];
        array[front] = null; // Помогаем сборщику мусора

        // Циклически перемещаем front
        front = (front + 1) % capacity;
        size--;

        return item;
    }

    /**
     * Возвращает первый элемент очереди без его удаления.
     *
     * Временная сложность: O(1)
     *
     * @return первый элемент очереди
     * @throws IllegalStateException если очередь пуста
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Очередь пуста");
        }
        return array[front];
    }

    /**
     * Проверяет, пуста ли очередь.
     *
     * Временная сложность: O(1)
     *
     * @return true если очередь пуста, false в противном случае
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Проверяет, заполнена ли очередь.
     *
     * Временная сложность: O(1)
     *
     * @return true если очередь полна, false в противном случае
     */
    @Override
    public boolean isFull() {
        return size == capacity;
    }

    /**
     * Возвращает текущее количество элементов в очереди.
     *
     * Временная сложность: O(1)
     *
     * @return размер очереди
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Очищает очередь, удаляя все элементы.
     *
     * Алгоритм:
     * 1. Обнуляет все элементы массива для помощи GC
     * 2. Сбрасывает указатели в начальное состояние
     *
     * Временная сложность: O(n)
     */
    @Override
    public void clear() {
        // Очищаем ссылки для сборщика мусора
        for (int i = 0; i < capacity; i++) {
            array[i] = null;
        }

        front = 0;
        rear = -1;
        size = 0;
    }

    /**
     * Ищет элемент в очереди и возвращает его позицию от начала.
     *
     * Позиция считается от головы очереди:
     * - 1 - первый элемент (голова)
     * - 2 - второй элемент
     * - и так далее
     * - -1 - элемент не найден
     *
     * Алгоритм:
     * 1. Начинает с головы очереди
     * 2. Проходит по всем элементам (учитывая цикличность)
     * 3. Сравнивает каждый элемент с искомым
     * 4. Возвращает позицию при совпадении
     *
     * Временная сложность: O(n)
     *
     * @param element элемент для поиска
     * @return позиция элемента (1-индексация) или -1, если элемент не найден
     */
    @Override
    public int search(T element) {
        if (element == null || isEmpty()) {
            return -1;
        }

        int index = front;
        for (int position = 1; position <= size; position++) {
            if (array[index].equals(element)) {
                return position;
            }
            index = (index + 1) % capacity;
        }

        return -1;
    }

    /**
     * Возвращает строковое представление очереди.
     * Элементы выводятся от головы к хвосту.
     *
     * Временная сложность: O(n)
     *
     * @return строка с элементами очереди в формате [элемент1, элемент2, ...]
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        int index = front;

        for (int i = 0; i < size; i++) {
            sb.append(array[index]);
            if (i < size - 1) {
                sb.append(", ");
            }
            index = (index + 1) % capacity;
        }

        sb.append("]");
        return sb.toString();
    }
}