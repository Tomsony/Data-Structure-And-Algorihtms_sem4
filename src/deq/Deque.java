package deq;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Реализация двусторонней очереди deq.Deque с использованием дженериков через циклический массив
 * @param <T> тип элементов в очереди
 */

/**
 Правило 5
 Деструктор – освобождение ресурсов.
 В Java память управляется GC

 Конструктор копирования – корректное копирование объекта.

 Оператор присваивания копированием – безопасное переприсваивание.

 Конструктор перемещения (C++11+) – эффективная передача владения.

 Оператор присваивания перемещением (C++11+) – оптимизация при перемещении.
 */
public class Deque<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double GROWTH_FACTOR = 1.5; // Коеффициент для атвоматического увеличения

    private Object[] elements;
    private int front; // Первый элемент
    private int rear; // Последний
    private int size; // Размер массива

    // 1. Конструктор по умолчанию
    /**
     * Создает пустую двустороннюю очередь с емкостью по умолчанию
     */
    public Deque() {
        this(DEFAULT_CAPACITY);
    }

    // 2. Параметризованный конструктор
    /**
     * Создает пустую двустороннюю очередь с указанной начальной емкостью
     * @param initialCapacity начальная емкость очереди
     * @throws IllegalArgumentException если начальная емкость отрицательна
     */
    public Deque(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Начальная ёмкость должна быть положительной");
        }
        this.elements = new Object[initialCapacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    // 3. Конструктор копирования
    public Deque(Deque<T> other) {
        this(other.elements.length);
        System.arraycopy(other.elements, 0, this.elements, 0, other.elements.length);
        this.front = other.front;
        this.rear = other.rear;
        this.size = other.size;
    }

    // 4. Метод clone() глубокая копия
    @Override
    public Deque<T> clone() {
        try {
            Deque<T> cloned = (Deque<T>) super.clone();
            cloned.elements = Arrays.copyOf(this.elements, this.elements.length);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Невозможно для deq.Deque
        }
    }

    /**
     * Добавляет элемент в начало очереди
     * @param element элемент для добавления
     */
    public void addFirst(T element) {
        ensureCapacity(size + 1);
        front = (front - 1 + elements.length) % elements.length;
        elements[front] = element;
        size++;
        if (size == 1) {
            rear = front;
        }
    }

    /**
     * Добавляет элемент в конец очереди
     * @param element элемент для добавления
     */
    public void addLast(T element) {
        ensureCapacity(size + 1);
        rear = (rear + 1) % elements.length;
        elements[rear] = element;
        size++;
    }

    /**
     * Удаляет и возвращает первый элемент очереди
     * @return первый элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        T element = (T) elements[front];
        elements[front] = null; // Помощь сборщику мусора
        front = (front + 1) % elements.length;
        size--;
        if (isEmpty()) {
            rear = -1;
            front = 0;
        }
        return element;
    }

    /**
     * Удаляет и возвращает последний элемент очереди
     * Удаляет и возвращает последний элемент очереди
     * @return последний элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        T element = (T) elements[rear];
        elements[rear] = null; // Помощь сборщику мусора
        rear = (rear - 1 + elements.length) % elements.length;
        size--;
        if (isEmpty()) {
            rear = -1;
            front = 0;
        }
        return element;
    }

    /**
     * Возвращает первый элемент очереди без удаления
     * @return первый элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return (T) elements[front];
    }

    /**
     * Возвращает последний элемент очереди без удаления
     * @return последний элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return (T) elements[rear];
    }

    /**
     * Проверяет, пуста ли очередь
     * @return true если очередь пуста, false в противном случае
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Возвращает количество элементов в очереди
     * @return размер очереди
     */
    public int size() {
        return size;
    }

    /**
     * Очищает очередь
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[(front + i) % elements.length] = null;
        }
        size = 0;
        front = 0;
        rear = -1;
    }

    // Увеличивает емкость массива при необходимости
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = (int) (elements.length * GROWTH_FACTOR) + 1;
            Object[] newElements = new Object[newCapacity];

            for (int i = 0; i < size; i++) {
                newElements[i] = elements[(front + i) % elements.length];
            }

            elements = newElements;
            front = 0;
            rear = size - 1;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[(front + i) % elements.length]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}