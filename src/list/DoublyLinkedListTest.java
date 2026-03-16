package list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DoublyLinkedListTest {
    private DoublyLinkedList<Integer> list; // Тестируемый список

    // Метод, выполняемый перед каждым тестом
    @BeforeEach
    void setUp() {
        list = new DoublyLinkedList<>(); // Инициализация нового списка перед каждым тестом
    }

    // Тест пустого списка
    @Test
    void testEmptyList() {
        assertTrue(list.size() == 0); // Проверка, что размер пустого списка равен 0
    }

    // Тест добавления элементов в начало списка
    @Test
    void testAddFirst() {
        list.addFirst(1); // Добавляем первый элемент
        assertEquals(1, list.size()); // Проверяем, что размер стал 1
        assertEquals("[1]", list.toString()); // Проверяем строковое представление

        list.addFirst(2); // Добавляем второй элемент в начало
        assertEquals(2, list.size()); // Проверяем, что размер стал 2
        assertEquals("[2, 1]", list.toString()); // Проверяем порядок элементов
    }

    // Тест добавления элементов в конец списка
    @Test
    void testAddLast() {
        list.addLast(1); // Добавляем первый элемент в конец
        assertEquals(1, list.size());
        assertEquals("[1]", list.toString());

        list.addLast(2); // Добавляем второй элемент в конец
        assertEquals(2, list.size());
        assertEquals("[1, 2]", list.toString()); // Проверяем порядок элементов
    }

    // Тест вставки элемента после указанного
    @Test
    void testInsertAfter() {
        list.addLast(1); // Создаем список [1, 3]
        list.addLast(3);
        list.insertAfter(1, 2); // Вставляем 2 после 1
        assertEquals(3, list.size()); // Проверяем новый размер
        assertEquals("[1, 2, 3]", list.toString()); // Проверяем порядок

        // Вставка после последнего элемента
        list.insertAfter(3, 4);
        assertEquals("[1, 2, 3, 4]", list.toString());
    }

    // Тест попытки вставки после несуществующего элемента
    @Test
    void testInsertAfterNotFound() {
        list.addLast(1);
        // Проверяем, что попытка вставить после 2 выбросит исключение
        assertThrows(IllegalArgumentException.class, () -> list.insertAfter(2, 3));
    }

    // Тест удаления первого элемента
    @Test
    void testRemoveFirst() {
        list.addLast(1); // Создаем список [1, 2]
        list.addLast(2);
        list.removeFirst(); // Удаляем первый элемент
        assertEquals(1, list.size()); // Проверяем новый размер
        assertEquals("[2]", list.toString()); // Проверяем оставшийся элемент

        list.removeFirst(); // Удаляем последний элемент
        assertEquals(0, list.size()); // Проверяем, что список пуст
        assertTrue(list.toString().equals("[]")); // Проверяем строковое представление
    }

    // Тест попытки удаления из пустого списка
    @Test
    void testRemoveFirstEmptyList() {
        assertThrows(IllegalStateException.class, () -> list.removeFirst());
    }

    // Тест удаления последнего элемента
    @Test
    void testRemoveLast() {
        list.addLast(1); // Создаем список [1, 2]
        list.addLast(2);
        list.removeLast(); // Удаляем последний элемент
        assertEquals(1, list.size());
        assertEquals("[1]", list.toString());

        list.removeLast(); // Удаляем последний оставшийся элемент
        assertEquals(0, list.size());
        assertTrue(list.toString().equals("[]"));
    }

    // Тест попытки удаления из пустого списка
    @Test
    void testRemoveLastEmptyList() {
        assertThrows(IllegalStateException.class, () -> list.removeLast());
    }

    // Тест удаления элемента по значению
    @Test
    void testRemove() {
        list.addLast(1); // Создаем список [1, 2, 3]
        list.addLast(2);
        list.addLast(3);
        list.remove(2); // Удаляем средний элемент
        assertEquals(2, list.size());
        assertEquals("[1, 3]", list.toString());

        // Удаление первого элемента
        list.remove(1);
        assertEquals("[3]", list.toString());

        // Удаление последнего элемента
        list.remove(3);
        assertEquals("[]", list.toString());
    }

    // Тест попытки удаления несуществующего элемента
    @Test
    void testRemoveNotFound() {
        list.addLast(1);
        assertThrows(IllegalArgumentException.class, () -> list.remove(2));
    }

    // Тест проверки наличия элемента
    @Test
    void testContains() {
        assertFalse(list.contains(1)); // Проверяем пустой список

        list.addLast(1); // Добавляем элементы
        list.addLast(2);
        assertTrue(list.contains(1)); // Проверяем наличие
        assertTrue(list.contains(2));
        assertFalse(list.contains(3)); // Проверяем отсутствие
    }

    // Тест объединения двух списков
    @Test
    void testConcatenate() {
        DoublyLinkedList<Integer> list2 = new DoublyLinkedList<>();
        list2.addLast(3); // Создаем второй список [3, 4]
        list2.addLast(4);

        list.addLast(1); // Создаем первый список [1, 2]
        list.addLast(2);
        list.concatenate(list2); // Объединяем списки

        assertEquals(4, list.size()); // Проверяем новый размер
        assertEquals("[1, 2, 3, 4]", list.toString()); // Проверяем порядок
        assertEquals(0, list2.size()); // Проверяем, что второй список очистился
    }

    // Тест объединения с пустым списком
    @Test
    void testConcatenateWithEmpty() {
        DoublyLinkedList<Integer> emptyList = new DoublyLinkedList<>();
        list.addLast(1);
        list.concatenate(emptyList); // Объединяем с пустым списком
        assertEquals(1, list.size()); // Размер не должен измениться
        assertEquals("[1]", list.toString());
    }

    // Тест объединения пустого списка с непустым
    @Test
    void testConcatenateToEmpty() {
        DoublyLinkedList<Integer> list2 = new DoublyLinkedList<>();
        list2.addLast(1);
        list.concatenate(list2); // Пустой список объединяем с [1]
        assertEquals(1, list.size());
        assertEquals("[1]", list.toString());
    }

    // Тест получения размера списка
    @Test
    void testSize() {
        assertEquals(0, list.size()); // Начальный размер
        list.addFirst(1); // Добавляем элемент
        assertEquals(1, list.size());
        list.addLast(2); // Добавляем еще один
        assertEquals(2, list.size());
        list.removeFirst(); // Удаляем элемент
        assertEquals(1, list.size());
    }
}