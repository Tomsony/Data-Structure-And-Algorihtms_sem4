package list;

/**
 * Класс двусвязного списка с поддержкой generics (может хранить элементы любого типа T)
 */
public class DoublyLinkedList<T> {

    /**
     * Внутренний класс Node (узел) для хранения данных и ссылок
     * @param <T> тип хранимых данных
     */
    private static class Node<T> {
        T data;         // Данные, хранящиеся в узле
        Node<T> prev;  // Ссылка на предыдущий узел
        Node<T> next;  // Ссылка на следующий узел

        // Конструктор узла
        Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    private Node<T> head; // Первый элемент списка
    private Node<T> tail; // Последний элемент списка
    private int size;     // Количество элементов в списке

    // Конструктор пустого списка
    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Проверяет, содержится ли элемент в списке
     * @param value искомое значение
     * @return true если элемент найден, false в противном случае
     * Временная сложность: O(n)
     */
    public boolean contains(T value) {
        Node<T> current = head;
        // Последовательный перебор всех элементов
        while (current != null) {
            if (current.data.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Добавляет элемент в начало списка
     * @param value значение для добавления
     * Временная сложность: O(1)
     */
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            // Если список пуст, новый узел становится и head и tail
            head = newNode;
            tail = newNode;
        } else {
            // Иначе добавляем перед текущей головой
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    /**
     * Добавляет элемент в конец списка
     * @param value значение для добавления
     * Временная сложность: O(1)
     */
    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);
        if (tail == null) {
            // Если список пуст, новый узел становится и head и tail
            head = newNode;
            tail = newNode;
        } else {
            // Иначе добавляем после текущего хвоста
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * Вставляет элемент после указанного значения
     * @param after значение, после которого нужно вставить
     * @param value значение для вставки
     * @throws IllegalArgumentException если элемент для вставки после не найден
     * Временная сложность: O(n)
     */
    public void insertAfter(T after, T value) {
        Node<T> current = head;
        // Поиск узла с указанным значением
        while (current != null) {
            if (current.data.equals(after)) {
                Node<T> newNode = new Node<>(value);
                // Вставка нового узла после текущего
                newNode.next = current.next;
                newNode.prev = current;
                if (current.next != null) {
                    // Если есть следующий узел, обновляем его ссылку
                    current.next.prev = newNode;
                } else {
                    // Если вставляем в конец, обновляем tail
                    tail = newNode;
                }
                current.next = newNode;
                size++;
                return;
            }
            current = current.next;
        }
        throw new IllegalArgumentException("Element not found: " + after);
    }

    /**
     * Удаляет первый элемент списка
     * @throws IllegalStateException если список пуст
     * Временная сложность: O(1)
     */
    public void removeFirst() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        if (head == tail) {
            // Если в списке один элемент
            head = null;
            tail = null;
        } else {
            // Удаляем ссылку на первый элемент
            head = head.next;
            head.prev = null;
        }
        size--;
    }

    /**
     * Удаляет последний элемент списка
     * @throws IllegalStateException если список пуст
     * Временная сложность: O(1)
     */
    public void removeLast() {
        if (tail == null) {
            throw new IllegalStateException("List is empty");
        }
        if (head == tail) {
            // Если в списке один элемент
            head = null;
            tail = null;
        } else {
            // Удаляем ссылку на последний элемент
            tail = tail.prev;
            tail.next = null;
        }
        size--;
    }

    /**
     * Удаляет первое вхождение указанного значения
     * @param value значение для удаления
     * @throws IllegalArgumentException если элемент не найден
     * Временная сложность: O(n)
     */
    public void remove(T value) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(value)) {
                // Обновляем ссылки соседних узлов
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    // Если удаляем первый элемент
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    // Если удаляем последний элемент
                    tail = current.prev;
                }
                size--;
                return;
            }
            current = current.next;
        }
        throw new IllegalArgumentException("Element not found: " + value);
    }

    /**
     * Объединяет текущий список с другим списком
     * @param other список для объединения
     * Временная сложность: O(1) (так как есть tail)
     */
    public void concatenate(DoublyLinkedList<T> other) {
        if (other.head == null) {
            return; // Нечего объединять
        }
        if (this.head == null) {
            // Если текущий список пуст
            this.head = other.head;
            this.tail = other.tail;
        } else {
            // Связываем хвост текущего с головой другого списка
            this.tail.next = other.head;
            other.head.prev = this.tail;
            this.tail = other.tail;
        }
        this.size += other.size;

        // Очищаем второй список
        other.head = null;
        other.tail = null;
        other.size = 0;
    }

    /**
     * Возвращает количество элементов в списке
     * @return размер списка
     * Временная сложность: O(1)
     */
    public int size() {
        return size;
    }

    /**
     * Преобразует список в строку для вывода
     * @return строковое представление списка
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
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