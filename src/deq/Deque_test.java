package deq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

/**
 * Класс для тестирования реализации deq.Deque (двусторонней очереди)
 */
class DequeTest {
    // Тестовые экземпляры deq.Deque для разных типов данных
    private Deque<Integer> intDeque;   // deq.Deque для целых чисел
    private Deque<String> stringDeque; // deq.Deque для строк

    /**
     * Метод, выполняемый перед каждым тестом.
     * Инициализирует тестовые экземпляры deq.Deque.
     */
    @BeforeEach
    void setUp() {
        intDeque = new Deque<>();
        stringDeque = new Deque<>();
    }

    // ========== ТЕСТЫ КОНСТРУКТОРОВ ==========

    /**
     * Тест конструктора по умолчанию.
     * Проверяет, что созданная очередь пуста и имеет размер 0.
     */
    @Test
    void testDefaultConstructor() {
        assertTrue(intDeque.isEmpty()); // Очередь должна быть пустой
        assertEquals(0, intDeque.size()); // Размер должен быть 0
    }

    /**
     * Тест конструктора с заданной емкостью.
     * Проверяет создание очереди с указанным начальным размером.
     */
    @Test
    void testCustomCapacityConstructor() {
        Deque<Integer> deque = new Deque<>(20);
        assertTrue(deque.isEmpty()); // Новая очередь должна быть пустой
    }

    /**
     * Тест конструктора с недопустимой емкостью.
     * Проверяет выброс исключения при попытке создать очередь с отрицательной или нулевой емкостью.
     */
    @Test
    void testInvalidCapacityConstructor() {
        // Ожидаем IllegalArgumentException при отрицательной емкости
        assertThrows(IllegalArgumentException.class, () -> new Deque<>(-5));
        // Ожидаем IllegalArgumentException при нулевой емкости
        assertThrows(IllegalArgumentException.class, () -> new Deque<>(0));
    }


    /**
     * Тест конструктора копирования.
     * Проверяет создание глубокой копии существующей очереди.
     */
    @Test
    void testCopyConstructor() {
        // Заполняем исходную очередь
        intDeque.addLast(1);
        intDeque.addLast(2);

        // Создаем копию через конструктор копирования
        Deque<Integer> copy = new Deque<>(intDeque);

        // Проверяем, что размер и элементы совпадают
        assertEquals(intDeque.size(), copy.size());
        assertEquals(intDeque.getFirst(), copy.getFirst());
        assertEquals(intDeque.getLast(), copy.getLast());

        // Проверяем, что это глубокая копия
        intDeque.removeFirst(); // Модифицируем оригинал
        assertNotEquals(intDeque.size(), copy.size()); // Копия не должна измениться
    }


    /**
     * Тест метода clear().
     * Проверяет корректность очистки очереди.
     */
    @Test
    void testClear() {
        // Добавляем элементы
        intDeque.addLast(1);
        intDeque.addLast(2);

        // Очищаем очередь
        intDeque.clear();

        // Проверяем, что очередь пуста
        assertTrue(intDeque.isEmpty());
        assertEquals(0, intDeque.size());
    }

    // ТЕСТЫ ОСНОВНЫХ ОПЕРАЦИЙ

    /**
     * Тест добавления элементов в начало и конец очереди.
     */
    @Test
    void testAddFirstAndLast() {
        intDeque.addFirst(10); // Добавляем в начало
        intDeque.addLast(20);  // Добавляем в конец

        // Проверяем размер и элементы
        assertEquals(2, intDeque.size());
        assertEquals(10, intDeque.getFirst()); // Первый элемент
        assertEquals(20, intDeque.getLast());  // Последний элемент
    }

    /**
     * Тест удаления первого элемента.
     */
    @Test
    void testRemoveFirst() {
        // Заполняем очередь
        intDeque.addLast(10);
        intDeque.addLast(20);

        // Удаляем и проверяем
        assertEquals(10, intDeque.removeFirst());
        assertEquals(1, intDeque.size()); // Размер должен уменьшиться
    }

    /**
     * Тест удаления последнего элемента.
     */
    @Test
    void testRemoveLast() {
        // Заполняем очередь
        intDeque.addFirst(10);
        intDeque.addLast(20);

        // Удаляем и проверяем
        assertEquals(20, intDeque.removeLast());
        assertEquals(1, intDeque.size()); // Размер должен уменьшиться
    }

    // ТЕСТЫ ГРАНИЧНЫХ СЛУЧАЕВ

    /**
     * Тест удаления из пустой очереди.
     * Ожидается NoSuchElementException.
     */
    @Test
    void testRemoveFromEmpty() {
        assertThrows(NoSuchElementException.class, () -> intDeque.removeFirst());
        assertThrows(NoSuchElementException.class, () -> intDeque.removeLast());
    }

    /**
     * Тест получения элементов из пустой очереди.
     * Ожидается NoSuchElementException.
     */
    @Test
    void testGetFromEmpty() {
        assertThrows(NoSuchElementException.class, () -> intDeque.getFirst());
        assertThrows(NoSuchElementException.class, () -> intDeque.getLast());
    }


    /**
     * Тест строкового представления.
     */
    @Test
    void testToString() {
        intDeque.addLast(1);
        intDeque.addLast(2);

        assertEquals("[1, 2]", intDeque.toString());
    }

    /**
     * Тест работы с пользовательским классом.
     */
    @Test
    void testWithCustomClass() {
        class Person {
            String name;
            Person(String name) { this.name = name; }
            @Override public String toString() { return name; }
        }

        Deque<Person> personDeque = new Deque<>();
        Person alice = new Person("Alice");
        personDeque.addLast(alice);

        assertEquals(alice, personDeque.getFirst());
    }

    /**
     * Тест добавления null-элементов.
     */
    @Test
    void testNullElements() {
        stringDeque.addFirst(null); // Добавляем null в начало
        stringDeque.addLast("not null"); // Добавляем строку в конец

        assertNull(stringDeque.removeFirst()); // Первый элемент должен быть null
        assertEquals("not null", stringDeque.removeLast()); // Последний - строка
    }
}