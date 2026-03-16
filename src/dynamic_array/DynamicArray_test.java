package dynamic_array;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Comparator;

class DynamicArrayTest {

    // Тесты конструкторов
    @Test
    void testDefaultConstructor() {
        DynamicArray<Integer> array = new DynamicArray<>();
        assertEquals(0, array.size());
        assertTrue(array.capacity() >= DynamicArray.DEFAULT_CAPACITY);
    }

    @Test
    void testCustomCapacityConstructor() {
        DynamicArray<String> array = new DynamicArray<>(20);
        assertEquals(0, array.size());
        assertEquals(20, array.capacity());
    }

    @Test
    void testInvalidCapacityConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new DynamicArray<>(-5));
        assertThrows(IllegalArgumentException.class, () -> new DynamicArray<>(0));
    }

    // Тесты базовых операций
    @Test
    void testAddAndGet() {
        DynamicArray<Integer> array = new DynamicArray<>();
        array.add(10);
        array.add(20);

        assertEquals(2, array.size());
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
    }

    @Test
    void testSet() {
        DynamicArray<String> array = new DynamicArray<>();
        array.add("A");
        array.set(0, "B");

        assertEquals("B", array.get(0));
    }

    @Test
    void testRemoveLast() {
        DynamicArray<Double> array = new DynamicArray<>();
        array.add(1.1);
        array.add(2.2);

        assertEquals(2.2, array.removeLast());
        assertEquals(1, array.size());
    }

    @Test
    void testRemoveLastEmptyArray() {
        DynamicArray<Integer> array = new DynamicArray<>();
        assertThrows(IllegalStateException.class, array::removeLast);
    }

    // Тесты дополнительных операций
    @Test
    void testInsert() {
        DynamicArray<Character> array = new DynamicArray<>();
        array.add('A');
        array.add('C');
        array.insert(1, 'B');

        assertEquals(3, array.size());
        assertEquals('B', array.get(1));
    }

    @Test
    void testRemoveByIndex() {
        DynamicArray<String> array = new DynamicArray<>();
        array.add("X");
        array.add("Y");
        array.add("Z");

        assertEquals("Y", array.remove(1));
        assertEquals(2, array.size());
        assertEquals("Z", array.get(1));
    }

    @Test
    void testIndexOf() {
        DynamicArray<Integer> array = new DynamicArray<>();
        array.add(10);
        array.add(20);
        array.add(10);

        assertEquals(0, array.indexOf(10));
        assertEquals(1, array.indexOf(20));
        assertEquals(-1, array.indexOf(30));
    }

    @Test
    void testSort() {
        DynamicArray<Integer> array = new DynamicArray<>();
        array.add(3);
        array.add(1);
        array.add(2);

        array.sort(Comparator.naturalOrder());

        assertEquals(1, array.get(0));
        assertEquals(2, array.get(1));
        assertEquals(3, array.get(2));
    }

    // Тесты автоматического изменения размера
    @Test
    void testCapacityIncrease() {
        DynamicArray<Integer> array = new DynamicArray<>(2);
        int initialCapacity = array.capacity();

        array.add(1);
        array.add(2);
        array.add(3); // Должно вызвать увеличение

        assertTrue(array.capacity() > initialCapacity);
        assertEquals(3, array.size());
    }

    // Тесты граничных случаев
    @Test
    void testInvalidIndexOperations() {
        DynamicArray<String> array = new DynamicArray<>();
        array.add("Test");

        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(-1, "X"));
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> array.insert(2, "Y"));
    }

    @Test
    void testToString() {
        DynamicArray<Integer> array = new DynamicArray<>();
        array.add(1);
        array.add(2);

        assertEquals("[1, 2]", array.toString());
    }
}