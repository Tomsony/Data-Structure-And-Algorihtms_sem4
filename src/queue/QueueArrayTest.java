package queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/// @author Artem Tomskikh
public class QueueArrayTest {
    private QueueArray queue;

    // Этот метод выполняется перед каждым тестом
    @BeforeEach
    public void setUp() {
        // Создаем новую очередь вместимостью 3 перед каждым тестом
        queue = new QueueArray(3);
    }

    /**
     * Тестирование поведения пустой очереди
     */
    @Test
    public void testEmptyQueue() {
        // Проверяем, что очередь пуста
        assertTrue(queue.isEmpty());
        // Проверяем, что размер очереди 0
        assertEquals(0, queue.size());
    }

    /**
     * Тестирование добавления элемента и просмотра первого элемента
     */
    @Test
    public void testEnqueueAndPeek() {
        // Добавляем элемент в очередь
        queue.enqueue(10);
        // Проверяем, что очередь не пуста
        assertFalse(queue.isEmpty());
        // Проверяем, что размер стал 1
        assertEquals(1, queue.size());
        // Проверяем, что peek возвращает добавленный элемент
        assertEquals(10, queue.peek());
    }

    /**
     * Тестирование добавления и извлечения элементов
     */
    @Test
    public void testEnqueueAndDequeue() {
        // Добавляем два элемента
        queue.enqueue(20);
        queue.enqueue(30);
        // Проверяем, что первый извлеченный элемент - первый добавленный (20)
        assertEquals(20, queue.dequeue());
        // Проверяем, что следующий элемент для извлечения - 30
        assertEquals(30, queue.peek());
        // Проверяем, что после извлечения размер стал 1
        assertEquals(1, queue.size());
    }

    /**
     * Тестирование циклического поведения очереди
     */
    @Test
    public void testCircularBehavior() {
        // Полностью заполняем очередь
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        // Извлекаем два первых элемента (1 и 2)
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());

        // Добавляем два новых элемента (4 и 5) - они должны занять освободившееся место
        queue.enqueue(4);
        queue.enqueue(5);

        // Проверяем порядок извлечения элементов:
        // Должны получить оставшийся 3, затем добавленные 4 и 5
        assertEquals(3, queue.dequeue());
        assertEquals(4, queue.dequeue());
        assertEquals(5, queue.dequeue());
        // Проверяем, что очередь теперь пуста
        assertTrue(queue.isEmpty());
    }

    /**
     * Тестирование попытки просмотра элемента в пустой очереди
     */
    @Test
    public void testPeekOnEmptyQueue() {
        // Проверяем, что при вызове peek() на пустой очереди выбрасывается исключение
        assertThrows(IllegalStateException.class, () -> queue.peek());
    }

    /**
     * Тестирование попытки извлечения элемента из пустой очереди
     */
    @Test
    public void testDequeueOnEmptyQueue() {
        // Проверяем, что при вызове dequeue() на пустой очереди выбрасывается исключение
        assertThrows(IllegalStateException.class, () -> queue.dequeue());
    }

    /**
     * Тестирование попытки добавления в полную очередь
     */
    @Test
    public void testEnqueueOnFullQueue() {
        // Заполняем очередь
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        // Проверяем, что при попытке добавить четвертый элемент выбрасывается исключение
        assertThrows(IllegalStateException.class, () -> queue.enqueue(4));
    }
}