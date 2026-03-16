package deq;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Реализация двусторонней очереди Deque с использованием дженериков через циклический массив.
 *
 * Принцип работы:
 * - Элементы можно добавлять/удалять с обоих концов
 * - Используется циклический массив для эффективного использования памяти
 * - При переполнении массив автоматически расширяется
 *
 * Временная сложность:
 * - addFirst/addLast: амортизированная O(1)
 * - removeFirst/removeLast: O(1)
 * - getFirst/getLast: O(1)
 * - contains: O(n)
 *
 * Пространственная сложность: O(n), где n - количество элементов
 *
 * Правило пяти (C++), адаптированное для Java:
 * 1. Деструктор – в Java управляется сборщиком мусора
 * 2. Конструктор копирования – реализован
 * 3. Оператор присваивания копированием – через clone()
 * 4. Конструктор перемещения – через clone() с последующей очисткой
 * 5. Оператор присваивания перемещением – через clone() с последующей очисткой
 *
 * @param <T> тип элементов в очереди
 * @author Томских Артём ИВТ-23
 */
public class Deque<T> implements DeqInterface<T>, Cloneable, Iterable<T> {

    /** Емкость по умолчанию */
    private static final int DEFAULT_CAPACITY = 10;

    /** Коэффициент для автоматического увеличения */
    private static final double GROWTH_FACTOR = 1.5;

    /** Массив для хранения элементов */
    private Object[] elements;

    /** Индекс первого элемента */
    private int front;

    /** Индекс последнего элемента */
    private int rear;

    /** Текущее количество элементов */
    private int size;

    // ========== КОНСТРУКТОРЫ ==========

    /**
     * Создает пустую двустороннюю очередь с емкостью по умолчанию.
     * Начальная емкость: 10 элементов.
     */
    public Deque() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Создает пустую двустороннюю очередь с указанной начальной емкостью.
     *
     * @param initialCapacity начальная емкость очереди
     * @throws IllegalArgumentException если начальная емкость <= 0
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

    /**
     * Конструктор копирования.
     * Создает глубокую копию существующей очереди.
     *
     * @param other очередь для копирования
     * @throws NullPointerException если other равен null
     */
    public Deque(Deque<T> other) {
        this(other.elements.length);
        System.arraycopy(other.elements, 0, this.elements, 0, other.elements.length);
        this.front = other.front;
        this.rear = other.rear;
        this.size = other.size;
    }

    /**
     * Конструктор перемещения.
     * Создает новую очередь, забирая элементы у другой.
     * После вызова other становится пустой.
     *
     * @param other очередь для перемещения
     */
    public Deque(Deque<T> other, boolean move) {
        this.elements = other.elements;
        this.front = other.front;
        this.rear = other.rear;
        this.size = other.size;

        // Очищаем исходную очередь
        if (move) {
            other.elements = new Object[DEFAULT_CAPACITY];
            other.front = 0;
            other.rear = -1;
            other.size = 0;
        }
    }

    // ========== МЕТОДЫ С ИСКЛЮЧЕНИЯМИ ==========

    /**
     * Добавляет элемент в начало очереди.
     *
     * Алгоритм:
     * 1. Проверяет наличие места (расширяет при необходимости)
     * 2. Вычисляет новую позицию front циклически
     * 3. Если очередь была пуста, обновляет rear
     *
     * @param element элемент для добавления
     * @throws NullPointerException если element равен null
     */
    @Override
    public void addFirst(T element) {
        if (element == null) {
            throw new NullPointerException("Нельзя добавить null в начало очереди");
        }

        ensureCapacity(size + 1);
        front = (front - 1 + elements.length) % elements.length;
        elements[front] = element;
        size++;

        // Если это был первый элемент, обновляем rear
        if (size == 1) {
            rear = front;
        }
    }

    /**
     * Добавляет элемент в конец очереди.
     *
     * @param element элемент для добавления
     * @throws NullPointerException если element равен null
     */
    @Override
    public void addLast(T element) {
        if (element == null) {
            throw new NullPointerException("Нельзя добавить null в конец очереди");
        }

        ensureCapacity(size + 1);
        rear = (rear + 1) % elements.length;
        elements[rear] = element;
        size++;
    }

    /**
     * Удаляет и возвращает первый элемент очереди.
     *
     * @return первый элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }

        @SuppressWarnings("unchecked")
        T element = (T) elements[front];
        elements[front] = null; // Помощь сборщику мусора
        front = (front + 1) % elements.length;
        size--;

        // Сбрасываем указатели если очередь пуста
        if (isEmpty()) {
            rear = -1;
            front = 0;
        }

        return element;
    }

    /**
     * Удаляет и возвращает последний элемент очереди.
     *
     * @return последний элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }

        @SuppressWarnings("unchecked")
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
     * Возвращает первый элемент очереди без удаления.
     *
     * @return первый элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    @Override
    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        @SuppressWarnings("unchecked")
        T element = (T) elements[front];
        return element;
    }

    /**
     * Возвращает последний элемент очереди без удаления.
     *
     * @return последний элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    @Override
    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        @SuppressWarnings("unchecked")
        T element = (T) elements[rear];
        return element;
    }

    // ========== МЕТОДЫ БЕЗ ИСКЛЮЧЕНИЙ ==========

    /**
     * Добавляет элемент в начало очереди.
     *
     * @param element элемент для добавления
     * @return true если элемент добавлен, false при ошибке
     */
    @Override
    public boolean offerFirst(T element) {
        try {
            addFirst(element);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Добавляет элемент в конец очереди.
     *
     * @param element элемент для добавления
     * @return true если элемент добавлен, false при ошибке
     */
    @Override
    public boolean offerLast(T element) {
        try {
            addLast(element);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Удаляет и возвращает первый элемент очереди или null если очередь пуста.
     *
     * @return первый элемент или null
     */
    @Override
    public T pollFirst() {
        if (isEmpty()) {
            return null;
        }
        return removeFirst();
    }

    /**
     * Удаляет и возвращает последний элемент очереди или null если очередь пуста.
     *
     * @return последний элемент или null
     */
    @Override
    public T pollLast() {
        if (isEmpty()) {
            return null;
        }
        return removeLast();
    }

    /**
     * Возвращает первый элемент очереди без удаления или null если очередь пуста.
     *
     * @return первый элемент или null
     */
    @Override
    public T peekFirst() {
        if (isEmpty()) {
            return null;
        }
        return getFirst();
    }

    /**
     * Возвращает последний элемент очереди без удаления или null если очередь пуста.
     *
     * @return последний элемент или null
     */
    @Override
    public T peekLast() {
        if (isEmpty()) {
            return null;
        }
        return getLast();
    }

    // ========== ОБЩИЕ МЕТОДЫ ==========

    /**
     * Проверяет, пуста ли очередь.
     *
     * @return true если очередь пуста, false в противном случае
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Возвращает количество элементов в очереди.
     *
     * @return размер очереди
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Очищает очередь.
     * Удаляет все элементы и сбрасывает указатели.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[(front + i) % elements.length] = null;
        }
        size = 0;
        front = 0;
        rear = -1;
    }

    /**
     * Проверяет, содержит ли очередь указанный элемент.
     *
     * @param element элемент для поиска
     * @return true если элемент найден, false в противном случае
     */
    @Override
    public boolean contains(T element) {
        if (element == null) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            int index = (front + i) % elements.length;
            if (element.equals(elements[index])) {
                return true;
            }
        }
        return false;
    }

    // ========== МЕТОДЫ КОПИРОВАНИЯ ==========

    /**
     * Создает глубокую копию очереди.
     *
     * @return клон очереди
     */
    @Override
    @SuppressWarnings("unchecked")
    public Deque<T> clone() {
        try {
            Deque<T> cloned = (Deque<T>) super.clone();
            cloned.elements = Arrays.copyOf(this.elements, this.elements.length);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Deque должен поддерживать клонирование", e);
        }
    }

    /**
     * Перемещает элементы из другой очереди в эту.
     * После вызова other становится пустой.
     *
     * @param other очередь для перемещения
     */
    public void moveFrom(Deque<T> other) {
        this.elements = other.elements;
        this.front = other.front;
        this.rear = other.rear;
        this.size = other.size;

        // Очищаем исходную очередь
        other.elements = new Object[DEFAULT_CAPACITY];
        other.front = 0;
        other.rear = -1;
        other.size = 0;
    }

    // ========== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ==========

    /**
     * Увеличивает емкость массива при необходимости.
     *
     * @param minCapacity минимально необходимая емкость
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = (int) (elements.length * GROWTH_FACTOR) + 1;
            resize(newCapacity);
        }
    }

    /**
     * Изменяет размер массива до указанной емкости.
     *
     * @param newCapacity новая емкость
     */
    private void resize(int newCapacity) {
        Object[] newElements = new Object[newCapacity];

        // Копируем элементы в правильном порядке
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(front + i) % elements.length];
        }

        elements = newElements;
        front = 0;
        rear = size - 1;
    }

    /**
     * Возвращает текущую емкость массива.
     *
     * @return емкость массива
     */
    public int capacity() {
        return elements.length;
    }

    // ========== ITERABLE ==========

    /**
     * Возвращает итератор для обхода элементов очереди от начала к концу.
     *
     * @return итератор
     */
    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T element = (T) elements[(front + current) % elements.length];
                current++;
                return element;
            }
        };
    }

    // ========== OBJECT METHODS ==========

    /**
     * Возвращает строковое представление очереди.
     * Элементы выводятся от начала к концу.
     *
     * @return строка с элементами очереди
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

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

    /**
     * Сравнивает эту очередь с другим объектом.
     *
     * @param obj объект для сравнения
     * @return true если очереди равны
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Deque)) return false;

        Deque<?> other = (Deque<?>) obj;
        if (this.size != other.size) return false;

        for (int i = 0; i < size; i++) {
            Object thisElem = this.elements[(this.front + i) % this.elements.length];
            Object otherElem = other.elements[(other.front + i) % other.elements.length];

            if (!thisElem.equals(otherElem)) return false;
        }
        return true;
    }
}