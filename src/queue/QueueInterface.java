package queue;

/**
 * Интерфейс QueueInterface определяет контракт для реализации очереди (FIFO).
 *
 * @param <T> тип элементов в очереди
 *
 * @author Томских Артём ИВТ-23
 */
public interface QueueInterface<T> {

    /**
     * Добавляет элемент в конец очереди.
     *
     * @param element элемент для добавления
     * @throws NullPointerException если element равен null
     * @throws IllegalStateException если очередь переполнена (для очередей с ограниченной вместимостью)
     */
    void enqueue(T element);

    /**
     * Удаляет и возвращает элемент из начала очереди.
     *
     * @return первый элемент очереди
     * @throws IllegalStateException если очередь пуста
     */
    T dequeue();

    /**
     * Возвращает первый элемент очереди без его удаления.
     *
     * @return первый элемент очереди
     * @throws IllegalStateException если очередь пуста
     */
    T peek();

    /**
     * Проверяет, пуста ли очередь.
     *
     * @return true если очередь пуста, false в противном случае
     */
    boolean isEmpty();

    /**
     * Проверяет, заполнена ли очередь.
     *
     * @return true если очередь полна, false в противном случае
     */
    boolean isFull();

    /**
     * Возвращает текущее количество элементов в очереди.
     *
     * @return размер очереди
     */
    int size();

    /**
     * Очищает очередь, удаляя все элементы.
     */
    void clear();

    /**
     * Ищет элемент в очереди и возвращает его позицию от начала.
     *
     * @param element элемент для поиска
     * @return позиция элемента (1-индексация) или -1, если элемент не найден
     */
    int search(T element);
}