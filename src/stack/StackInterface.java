package stack;

import java.util.EmptyStackException;

/**
 * Интерфейс StackInterface определяет контракт для реализации стека.
 *
 * @param <T> тип элементов в стеке
 */
public interface StackInterface<T> {

    /**
     * Добавляет элемент на вершину стека.
     *
     * @param element элемент для добавления
     * @throws NullPointerException если element равен null
     */
    void push(T element);

    /**
     * Удаляет и возвращает элемент с вершины стека.
     *
     * @return элемент с вершины стека
     * @throws EmptyStackException если стек пуст
     */
    T pop();

    /**
     * Возвращает элемент с вершины стека без удаления.
     *
     * @return элемент с вершины стека
     * @throws EmptyStackException если стек пуст
     */
    T peek();

    /**
     * Проверяет, пуст ли стек.
     *
     * @return true если стек пуст, false в противном случае
     */
    boolean isEmpty();

    /**
     * Возвращает количество элементов в стеке.
     *
     * @return размер стека
     */
    int size();

    /**
     * Ищет элемент в стеке и возвращает его позицию.
     *
     * @param element элемент для поиска
     * @return позиция (1-индексация) или -1, если элемент не найден
     */
    int search(T element);

    /**
     * Очищает стек.
     */
    void clear();
}