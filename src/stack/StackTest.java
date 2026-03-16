package stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.EmptyStackException;

class StackTest {
    private Stack<Integer> stack;  // Тестируемый стек (целочисленный)

    // Метод, выполняемый перед каждым тестом
    @BeforeEach
    void setUp() {
        stack = new Stack<>();  // Инициализация нового стека перед каждым тестом
    }

    /**
     * Тест пустого стека
     * Проверяет, что новый стек пуст и имеет размер 0
     */
    @Test
    void testEmptyStack() {
        assertTrue(stack.isEmpty());  // Проверка, что стек пуст
        assertEquals(0, stack.size());  // Проверка размера пустого стека
    }

    /**
     * Тест добавления элементов в стек (push)
     * Проверяет корректность работы метода push()
     */
    @Test
    void testPush() {
        stack.push(1);  // Добавляем первый элемент
        assertFalse(stack.isEmpty());  // Стек не должен быть пустым
        assertEquals(1, stack.size());  // Размер должен быть 1
        assertEquals(1, stack.peek());  // Верхний элемент должен быть 1

        stack.push(2);  // Добавляем второй элемент
        assertEquals(2, stack.size());  // Размер должен быть 2
        assertEquals(2, stack.peek());  // Верхний элемент должен быть 2
    }

    /**
     * Тест извлечения элементов из стека (pop)
     * Проверяет корректность работы метода pop()
     */
    @Test
    void testPop() {
        stack.push(1);  // Добавляем элементы
        stack.push(2);

        assertEquals(2, stack.pop());  // Первый извлеченный элемент должен быть 2
        assertEquals(1, stack.size());  // Размер должен уменьшиться до 1
        assertEquals(1, stack.pop());  // Следующий извлеченный элемент должен быть 1
        assertTrue(stack.isEmpty());  // Стек должен быть пуст
    }

    /**
     * Тест попытки извлечения из пустого стека
     * Проверяет, что при pop() пустого стека выбрасывается исключение
     */
    @Test
    void testPopEmptyStack() {
        assertThrows(EmptyStackException.class, () -> stack.pop());
    }

    /**
     * Тест просмотра верхнего элемента (peek)
     * Проверяет корректность работы метода peek()
     */
    @Test
    void testPeek() {
        stack.push(10);  // Добавляем элемент
        assertEquals(10, stack.peek());  // Верхний элемент должен быть 10
        assertEquals(1, stack.size());  // peek() не должен изменять размер стека

        stack.push(20);  // Добавляем еще один элемент
        assertEquals(20, stack.peek());  // Теперь верхний элемент должен быть 20
    }

    /**
     * Тест попытки просмотра пустого стека
     * Проверяет, что при peek() пустого стека выбрасывается исключение
     */
    @Test
    void testPeekEmptyStack() {
        assertThrows(EmptyStackException.class, () -> stack.peek());
    }

    /**
     * Тест получения размера стека
     * Проверяет корректность работы метода size()
     */
    @Test
    void testSize() {
        assertEquals(0, stack.size());  // Размер пустого стека

        stack.push(1);  // Добавляем элементы
        stack.push(2);
        assertEquals(2, stack.size());  // Проверяем размер после добавления

        stack.pop();  // Удаляем один элемент
        assertEquals(1, stack.size());  // Проверяем размер после удаления
    }

    /**
     * Тест очистки стека
     * Проверяет корректность работы метода clear()
     */
    @Test
    void testClear() {
        stack.push(1);  // Добавляем элементы
        stack.push(2);
        stack.clear();  // Очищаем стек

        assertTrue(stack.isEmpty());  // Стек должен быть пуст
        assertEquals(0, stack.size());  // Размер должен быть 0
    }

    /**
     * Тест строкового представления стека
     * Проверяет корректность работы метода toString()
     */
    @Test
    void testToString() {
        assertEquals("[]", stack.toString());  // Пустой стек

        stack.push(1);  // Добавляем один элемент
        assertEquals("[1]", stack.toString());  // Проверяем представление

        stack.push(2);  // Добавляем еще элементы
        stack.push(3);
        assertEquals("[3, 2, 1]", stack.toString());  // Проверяем порядок элементов
    }
}