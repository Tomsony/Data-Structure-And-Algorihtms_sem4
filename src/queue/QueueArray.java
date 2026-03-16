package queue; /**
 * Класс, реализующий очередь (FIFO - First In First Out) с использованием циклического массива
 * Эта реализация обеспечивает O(1) для основных операций
 */
/// @author Artem Tomskikh
public class QueueArray {
    // Массив для хранения элементов очереди
    private int[] array;
    // Индекс первого элемента в очереди (головы)
    private int front;
    // Индекс последнего элемента в очереди (хвоста)
    private int rear;
    // Текущее количество элементов в очереди
    private int size;
    // максимальное количество элементов, которые можно поместить в очередь
    private int capacity;

    /**
     * Конструктор создает очередь заданной вместимости
     * @param capacity максимальное количество элементов в очереди
     */
    public QueueArray(int capacity) {
        this.capacity = capacity;
        this.array = new int[capacity];
        this.front = 0;    // Очередь пуста, front указывает на начало
        this.rear = -1;    // Очередь пуста, rear перед front
        this.size = 0;     // Начальный размер - 0
    }

    /**
     * Добавляет элемент в конец очереди
     * @param item элемент для добавления
     * @throws IllegalStateException если очередь переполнена
     */
    public void enqueue(int item) {
        if (isFull()) {
            throw new IllegalStateException("Очередь переполнена");
        }
        // Циклически перемещаем rear если достигли конца массива, переходим в начало
        rear = (rear + 1) % capacity; // Циклическое перемещение
        array[rear] = item; // Записываем элемент
        size++; // Увеличиваем счётчик
    }

    /**
     * Удаляет и возвращает элемент из начала очереди
     * @return первый элемент очереди
     * @throws IllegalStateException если очередь пуста
     */
    public int dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Очередь пуста");
        }
        int item = array[front];
        // Циклически перемещаем front
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    /**
     * Возвращает первый элемент очереди без его удаления
     * @return первый элемент очереди
     * @throws IllegalStateException если очередь пуста
     */
    public int peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Очередь пуста");
        }
        return array[front];
    }

    /**
     * Проверяет, пуста ли очередь
     * @return true если очередь пуста, false в противном случае
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Проверяет, заполнена ли очередь
     * @return true если очередь полна, false в противном случае
     */
    public boolean isFull() {
        return size == capacity;
    }

    /**
     * Возвращает текущее количество элементов в очереди
     * @return размер очереди
     */
    public int size() {
        return size;
    }
}
/// Класс - шаблон (generics)
/// Clear