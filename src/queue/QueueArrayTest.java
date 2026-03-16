package queue;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс QueueArrayTest содержит модульные тесты для проверки функциональности класса QueueArray.
 *
 * Тесты покрывают следующие аспекты:
 * 1. Основные операции очереди (FIFO):
 *    - enqueue() - добавление в конец
 *    - dequeue() - удаление из начала
 *    - peek() - просмотр начала без удаления
 * 2. Граничные случаи:
 *    - Пустая очередь
 *    - Полная очередь
 *    - Очередь с одним элементом
 * 3. Исключительные ситуации
 * 4. Циклическое поведение
 *
 * @author Томских Артём ИВТ-23
 */
@DisplayName("Тестирование циклической очереди на массиве")
class QueueArrayTest {

    // ==================== ТЕСТЫ КОНСТРУКТОРА ====================

    @Test
    @DisplayName("Создание очереди с корректной вместимостью")
    void testConstructorWithValidCapacity() {
        QueueArray<String> queue = new QueueArray<>(5);

        assertTrue(queue.isEmpty());
        assertFalse(queue.isFull());
        assertEquals(0, queue.size());
    }

    @Test
    @DisplayName("Создание очереди с некорректной вместимостью")
    void testConstructorWithInvalidCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new QueueArray<>(0));
        assertThrows(IllegalArgumentException.class, () -> new QueueArray<>(-1));
    }

    // ==================== ТЕСТЫ ENQUEUE ====================

    @Test
    @DisplayName("Добавление элементов в очередь")
    void testEnqueue() {
        QueueArray<String> queue = new QueueArray<>(3);

        queue.enqueue("A");
        assertEquals(1, queue.size());
        assertEquals("A", queue.peek());
        assertFalse(queue.isEmpty());
        assertFalse(queue.isFull());

        queue.enqueue("B");
        assertEquals(2, queue.size());
        assertEquals("A", queue.peek()); // Голова не меняется

        queue.enqueue("C");
        assertEquals(3, queue.size());
        assertTrue(queue.isFull());
    }

    @Test
    @DisplayName("Добавление null в очередь")
    void testEnqueueNull() {
        QueueArray<String> queue = new QueueArray<>(3);

        assertThrows(NullPointerException.class, () -> queue.enqueue(null));
        assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("Добавление в полную очередь")
    void testEnqueueFullQueue() {
        QueueArray<String> queue = new QueueArray<>(2);
        queue.enqueue("A");
        queue.enqueue("B");

        assertThrows(IllegalStateException.class, () -> queue.enqueue("C"));
    }

    // ==================== ТЕСТЫ DEQUEUE ====================

    @Test
    @DisplayName("Удаление элементов из очереди (FIFO порядок)")
    void testDequeue() {
        QueueArray<String> queue = new QueueArray<>(5);
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());
        assertEquals(2, queue.size());
        assertEquals("B", queue.peek());

        assertEquals("B", queue.dequeue());
        assertEquals(1, queue.size());
        assertEquals("C", queue.peek());

        assertEquals("C", queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("Удаление из пустой очереди")
    void testDequeueEmptyQueue() {
        QueueArray<String> queue = new QueueArray<>(3);

        assertThrows(IllegalStateException.class, () -> queue.dequeue());
    }

    // ==================== ТЕСТЫ PEEK ====================

    @Test
    @DisplayName("Просмотр головы очереди")
    void testPeek() {
        QueueArray<String> queue = new QueueArray<>(3);
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals("A", queue.peek());
        assertEquals(2, queue.size()); // Размер не меняется

        queue.dequeue();
        assertEquals("B", queue.peek());
    }

    @Test
    @DisplayName("Просмотр пустой очереди")
    void testPeekEmptyQueue() {
        QueueArray<String> queue = new QueueArray<>(3);

        assertThrows(IllegalStateException.class, () -> queue.peek());
    }

    // ==================== ТЕСТЫ ЦИКЛИЧЕСКОГО ПОВЕДЕНИЯ ====================

    @Test
    @DisplayName("Циклическое поведение очереди")
    void testCircularBehavior() {
        QueueArray<String> queue = new QueueArray<>(3);

        // Заполняем очередь
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        assertTrue(queue.isFull());

        // Удаляем элементы
        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.dequeue());

        // Добавляем новые элементы (должны переиспользовать начало массива)
        queue.enqueue("D");
        queue.enqueue("E");

        // Проверяем порядок
        assertEquals("C", queue.dequeue());
        assertEquals("D", queue.dequeue());
        assertEquals("E", queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("Многократное циклическое переполнение")
    void testRepeatedCircularBehavior() {
        QueueArray<Integer> queue = new QueueArray<>(3);

        for (int cycle = 0; cycle < 5; cycle++) {
            // Добавляем элементы
            queue.enqueue(cycle * 3 + 1);
            queue.enqueue(cycle * 3 + 2);
            queue.enqueue(cycle * 3 + 3);

            assertTrue(queue.isFull());

            // Удаляем элементы
            assertEquals(cycle * 3 + 1, queue.dequeue());
            assertEquals(cycle * 3 + 2, queue.dequeue());
            assertEquals(cycle * 3 + 3, queue.dequeue());

            assertTrue(queue.isEmpty());
        }
    }

    // ==================== ТЕСТЫ CLEAR ====================

    @Test
    @DisplayName("Очистка очереди")
    void testClear() {
        QueueArray<String> queue = new QueueArray<>(5);
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertFalse(queue.isEmpty());
        assertEquals(3, queue.size());

        queue.clear();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertFalse(queue.isFull());
        assertThrows(IllegalStateException.class, () -> queue.peek());
    }

    // ==================== ТЕСТЫ SEARCH ====================

    @Test
    @DisplayName("Поиск элементов в очереди")
    void testSearch() {
        QueueArray<String> queue = new QueueArray<>(5);
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");

        assertEquals(1, queue.search("A"));
        assertEquals(2, queue.search("B"));
        assertEquals(3, queue.search("C"));
        assertEquals(4, queue.search("D"));
        assertEquals(-1, queue.search("E"));
        assertEquals(-1, queue.search(null));
    }

    @Test
    @DisplayName("Поиск в циклической очереди")
    void testSearchInCircularQueue() {
        QueueArray<String> queue = new QueueArray<>(3);
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        queue.dequeue(); // Удаляем A
        queue.dequeue(); // Удаляем B
        queue.enqueue("D");
        queue.enqueue("E");

        // Теперь очередь: C, D, E
        assertEquals(1, queue.search("C"));
        assertEquals(2, queue.search("D"));
        assertEquals(3, queue.search("E"));
    }
}