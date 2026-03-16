package deq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Класс DequeTest содержит модульные тесты для проверки функциональности класса Deque.
 *
 * Тесты покрывают:
 * 1. Конструкторы (по умолчанию, параметризованный, копирования, перемещения)
 * 2. Основные операции (add, remove, get)
 * 3. Альтернативные методы (offer, poll, peek)
 * 4. Граничные случаи (пустая очередь, полная очередь)
 * 5. Исключительные ситуации
 * 6. Методы копирования (clone, move)
 * 7. Автоматическое расширение массива
 * 8. Iterable и итераторы
 * 9. equals, toString
 *
 * @author Томских Артём ИВТ-23
 */
class DequeTest {

    private Deque<Integer> intDeque;
    private Deque<String> stringDeque;

    @BeforeEach
    void setUp() {
        intDeque = new Deque<>();
        stringDeque = new Deque<>();
    }

    // ========== ТЕСТЫ КОНСТРУКТОРОВ ==========

    @Test
    void testDefaultConstructor() {
        assertTrue(intDeque.isEmpty());
        assertEquals(0, intDeque.size());
        assertEquals(10, intDeque.capacity());
    }

    @Test
    void testCustomCapacityConstructor() {
        Deque<Integer> deque = new Deque<>(20);
        assertEquals(20, deque.capacity());
        assertTrue(deque.isEmpty());
    }

    @Test
    void testInvalidCapacityConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Deque<>(-5));
        assertThrows(IllegalArgumentException.class, () -> new Deque<>(0));
    }

    @Test
    void testCopyConstructor() {
        // Заполняем исходную очередь
        intDeque.addLast(1);
        intDeque.addLast(2);
        intDeque.addLast(3);

        // Создаем копию
        Deque<Integer> copy = new Deque<>(intDeque);

        // Проверяем равенство
        assertEquals(intDeque.size(), copy.size());
        assertEquals(intDeque.getFirst(), copy.getFirst());
        assertEquals(intDeque.getLast(), copy.getLast());
        assertEquals(intDeque.toString(), copy.toString());

        // Проверяем независимость
        intDeque.removeFirst();
        assertNotEquals(intDeque.size(), copy.size());
    }

    @Test
    void testMoveConstructor() {
        intDeque.addLast(1);
        intDeque.addLast(2);
        intDeque.addLast(3);

        Deque<Integer> moved = new Deque<>(intDeque, true);

        // Проверяем, что элементы переместились
        assertEquals(3, moved.size());
        assertEquals(1, moved.getFirst());

        // Проверяем, что исходная очередь очистилась
        assertTrue(intDeque.isEmpty());
        assertEquals(0, intDeque.size());
    }

    // ========== ТЕСТЫ ОСНОВНЫХ ОПЕРАЦИЙ ==========

    @Test
    void testAddFirstAndLast() {
        intDeque.addFirst(1);
        intDeque.addLast(2);
        intDeque.addFirst(0);
        intDeque.addLast(3);

        assertEquals(4, intDeque.size());
        assertEquals(0, intDeque.getFirst());
        assertEquals(3, intDeque.getLast());
        assertEquals("[0, 1, 2, 3]", intDeque.toString());
    }

    @Test
    void testRemoveFirstAndLast() {
        intDeque.addLast(1);
        intDeque.addLast(2);
        intDeque.addLast(3);

        assertEquals(1, intDeque.removeFirst());
        assertEquals(3, intDeque.removeLast());
        assertEquals(2, intDeque.removeFirst());
        assertTrue(intDeque.isEmpty());
    }

    @Test
    void testGetFirstAndLast() {
        intDeque.addLast(1);
        intDeque.addLast(2);

        assertEquals(1, intDeque.getFirst());
        assertEquals(2, intDeque.getLast());
        assertEquals(2, intDeque.size()); // Размер не меняется
    }

    // ========== ТЕСТЫ АЛЬТЕРНАТИВНЫХ МЕТОДОВ ==========

    @Test
    void testOfferMethods() {
        assertTrue(intDeque.offerFirst(1));
        assertTrue(intDeque.offerLast(2));

        assertEquals(1, intDeque.peekFirst());
        assertEquals(2, intDeque.peekLast());
        assertEquals(2, intDeque.size());
    }

    @Test
    void testPollMethods() {
        assertNull(intDeque.pollFirst());
        assertNull(intDeque.pollLast());

        intDeque.addLast(1);
        intDeque.addLast(2);

        assertEquals(1, intDeque.pollFirst());
        assertEquals(2, intDeque.pollLast());
        assertTrue(intDeque.isEmpty());
    }

    @Test
    void testPeekMethods() {
        assertNull(intDeque.peekFirst());
        assertNull(intDeque.peekLast());

        intDeque.addLast(1);
        assertEquals(1, intDeque.peekFirst());
        assertEquals(1, intDeque.peekLast());
        assertEquals(1, intDeque.size());
    }

    // ========== ТЕСТЫ ГРАНИЧНЫХ СЛУЧАЕВ ==========

    @Test
    void testEmptyDequeOperations() {
        assertTrue(intDeque.isEmpty());
        assertEquals(0, intDeque.size());

        assertThrows(NoSuchElementException.class, () -> intDeque.getFirst());
        assertThrows(NoSuchElementException.class, () -> intDeque.getLast());
        assertThrows(NoSuchElementException.class, () -> intDeque.removeFirst());
        assertThrows(NoSuchElementException.class, () -> intDeque.removeLast());
    }

    @Test
    void testAddNull() {
        assertThrows(NullPointerException.class, () -> intDeque.addFirst(null));
        assertThrows(NullPointerException.class, () -> intDeque.addLast(null));

        // offer допускает null
        assertFalse(intDeque.offerFirst(null));
        assertFalse(intDeque.offerLast(null));
    }

    // ========== ТЕСТЫ АВТОМАТИЧЕСКОГО РАСШИРЕНИЯ ==========

    @Test
    void testAutoResizing() {
        Deque<Integer> deque = new Deque<>(2);
        assertEquals(2, deque.capacity());

        deque.addLast(1);
        deque.addLast(2);
        assertEquals(2, deque.capacity());
        assertFalse(deque.isEmpty());

        deque.addLast(3); // Должно расшириться
        assertTrue(deque.capacity() > 2);
        assertEquals(3, deque.size());
        assertEquals(1, deque.getFirst());
        assertEquals(3, deque.getLast());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100, 1000})
    void testResizingWithManyElements(int count) {
        for (int i = 0; i < count; i++) {
            intDeque.addLast(i);
        }

        assertEquals(count, intDeque.size());
        assertTrue(intDeque.capacity() >= count);

        for (int i = 0; i < count; i++) {
            assertEquals(i, intDeque.removeFirst());
        }
    }

    // ========== ТЕСТЫ КОПИРОВАНИЯ ==========

    @Test
    void testClone() {
        intDeque.addLast(1);
        intDeque.addLast(2);
        intDeque.addLast(3);

        Deque<Integer> clone = intDeque.clone();

        assertEquals(intDeque.size(), clone.size());
        assertEquals(intDeque.toString(), clone.toString());

        // Проверяем независимость
        intDeque.removeFirst();
        assertNotEquals(intDeque.size(), clone.size());
    }

    @Test
    void testMoveFrom() {
        Deque<Integer> source = new Deque<>();
        source.addLast(1);
        source.addLast(2);
        source.addLast(3);

        Deque<Integer> target = new Deque<>();
        target.moveFrom(source);

        assertEquals(3, target.size());
        assertTrue(source.isEmpty());
    }

    // ========== ТЕСТЫ ITERATOR ==========

    @Test
    void testIterator() {
        intDeque.addLast(1);
        intDeque.addLast(2);
        intDeque.addLast(3);

        Iterator<Integer> iterator = intDeque.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(3, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @Test
    void testIteratorEmpty() {
        Iterator<Integer> iterator = intDeque.iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @Test
    void testForEachLoop() {
        intDeque.addLast(1);
        intDeque.addLast(2);
        intDeque.addLast(3);

        int sum = 0;
        for (int num : intDeque) {
            sum += num;
        }
        assertEquals(6, sum);
    }

    // ========== ТЕСТЫ CONTAINS ==========

    @Test
    void testContains() {
        intDeque.addLast(1);
        intDeque.addLast(2);
        intDeque.addLast(3);

        assertTrue(intDeque.contains(2));
        assertFalse(intDeque.contains(4));
        assertFalse(intDeque.contains(null));
    }

    // ========== ТЕСТЫ EQUALS И HASHCODE ==========

    @Test
    void testEquals() {
        Deque<Integer> deque1 = new Deque<>();
        Deque<Integer> deque2 = new Deque<>();

        deque1.addLast(1);
        deque1.addLast(2);
        deque2.addLast(1);
        deque2.addLast(2);

        assertEquals(deque1, deque2);

        deque2.addLast(3);
        assertNotEquals(deque1, deque2);
    }

    // ========== ТЕСТЫ С РАЗНЫМИ ТИПАМИ ==========

    @Test
    void testWithStrings() {
        stringDeque.addLast("A");
        stringDeque.addLast("B");
        stringDeque.addFirst("C");

        assertEquals("C", stringDeque.getFirst());
        assertEquals("B", stringDeque.getLast());
        assertEquals("[C, A, B]", stringDeque.toString());
    }

    @Test
    void testWithCustomClass() {
        class Person {
            String name;
            Person(String name) { this.name = name; }
            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof Person)) return false;
                return this.name.equals(((Person) obj).name);
            }
        }

        Deque<Person> personDeque = new Deque<>();
        Person alice = new Person("Alice");
        Person bob = new Person("Bob");

        personDeque.addLast(alice);
        personDeque.addLast(bob);

        assertTrue(personDeque.contains(alice));
        assertEquals(alice, personDeque.getFirst());
        assertEquals(bob, personDeque.getLast());
    }

    // ========== ТЕСТЫ СЛОЖНЫХ СЦЕНАРИЕВ ==========

    @Test
    void testMixedOperations() {
        intDeque.addLast(1);
        intDeque.addFirst(0);
        intDeque.addLast(2);
        intDeque.addFirst(-1);

        assertEquals(4, intDeque.size());
        assertEquals("[-1, 0, 1, 2]", intDeque.toString());

        assertEquals(-1, intDeque.removeFirst());
        assertEquals(2, intDeque.removeLast());
        assertEquals(0, intDeque.removeFirst());
        assertEquals(1, intDeque.removeLast());

        assertTrue(intDeque.isEmpty());
    }

    @Test
    void testCircularBehavior() {
        Deque<Integer> deque = new Deque<>(3);

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        deque.removeFirst();
        deque.removeFirst();

        deque.addLast(4);
        deque.addLast(5);

        assertEquals("[3, 4, 5]", deque.toString());
    }
}