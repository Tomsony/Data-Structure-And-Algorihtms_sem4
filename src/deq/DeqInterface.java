package deq;

import java.util.NoSuchElementException;

/**
 * Интерфейс DequeInterface определяет контракт для двусторонней очереди (Double-Ended Queue).
 * Поддерживает добавление и удаление элементов с обоих концов.
 *
 * @param <T> тип элементов в очереди
 * @author Томских Артём ИВТ-23
 */
public interface DeqInterface<T> {
    /**
     * Добавляет элемент в начало очереди.
     *
     * @param element элемент для добавления
     * @throws NullPointerException если element равен null
     * @throws IllegalStateException если очередь не может быть расширена
     */
    void addFirst(T element);

    /**
     * Добавляет элемент в конец очереди.
     *
     * @param element элемент для добавления
     * @throws NullPointerException если element равен null
     * @throws IllegalStateException если очередь не может быть расширена
     */
    void addLast(T element);

    /**
     * Удаляет и возвращает первый элемент очереди.
     *
     * @return первый элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    T removeFirst();

    /**
     * Удаляет и возвращает последний элемент очереди.
     *
     * @return последний элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    T removeLast();

    /**
     * Возвращает первый элемент очереди без удаления.
     *
     * @return первый элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    T getFirst();

    /**
     * Возвращает последний элемент очереди без удаления.
     *
     * @return последний элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    T getLast();

    /**
     * Добавляет элемент в начало очереди.
     *
     * @param element элемент для добавления
     * @return true если элемент добавлен, false в противном случае
     */
    boolean offerFirst(T element);

    /**
     * Добавляет элемент в конец очереди.
     *
     * @param element элемент для добавления
     * @return true если элемент добавлен, false в противном случае
     */
    boolean offerLast(T element);

    /**
     * Удаляет и возвращает первый элемент очереди или null если очередь пуста.
     *
     * @return первый элемент или null
     */
    T pollFirst();

    /**
     * Удаляет и возвращает последний элемент очереди или null если очередь пуста.
     *
     * @return последний элемент или null
     */
    T pollLast();

    /**
     * Возвращает первый элемент очереди или null если очередь пуста.
     *
     * @return первый элемент или null
     */
    T peekFirst();

    /**
     * Возвращает последний элемент очереди или null если очередь пуста.
     *
     * @return последний элемент или null
     */
    T peekLast();

    /**
     * Проверяет, пуста ли очередь.
     *
     * @return true если очередь пуста, false в противном случае
     */
    boolean isEmpty();

    /**
     * Возвращает количество элементов в очереди.
     *
     * @return размер очереди
     */
    int size();

    /**
     * Очищает очередь.
     */
    void clear();

    /**
     * Проверяет, содержит ли очередь указанный элемент.
     *
     * @param element элемент для поиска
     * @return true если элемент найден, false в противном случае
     */
    boolean contains(T element);
}