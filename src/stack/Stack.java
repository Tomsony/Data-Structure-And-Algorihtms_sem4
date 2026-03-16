package stack;

import java.util.EmptyStackException;

/**
 * Класс stack.Stack реализует структуру данных "стек" с принципом LIFO (последний вошел, первый вышел).
 * @param <T> тип элементов стека
 */
public class Stack<T> {
    private static class Node<T> {
        T data;         // Данные узла
        Node<T> next;   // Ссылка на следующий узел

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> top;    // Вершина стека
    private int size;       // Количество элементов в стеке

    /**
     * Создает пустой стек
     */
    public Stack() {
        top = null;
        size = 0;
    }

    /**
     * Добавляет элемент на вершину стека
     * @param element элемент для добавления
     */
    public void push(T element) {
        Node<T> newNode = new Node<>(element);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Удаляет и возвращает элемент с вершины стека
     * @return элемент с вершины стека
     * @throws EmptyStackException если стек пуст
     */
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    /**
     * Возвращает элемент с вершины стека без удаления
     * @return элемент с вершины стека
     * @throws EmptyStackException если стек пуст
     */
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.data;
    }

    /**
     * Проверяет, пуст ли стек
     * @return true если стек пуст, false в противном случае
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Возвращает количество элементов в стеке
     * @return размер стека
     */
    public int size() {
        return size;
    }

    /**
     * Очищает стек
     */
    public void clear() {
        top = null;
        size = 0;
    }

    /**
     * Возвращает строковое представление стека
     * @return строка с элементами стека
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = top;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}