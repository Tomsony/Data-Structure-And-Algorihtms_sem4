package stack;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.EmptyStackException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс StackTest содержит модульные тесты для проверки функциональности класса Stack.
 *
 * Тесты покрывают следующие аспекты:
 * 1. Основные операции стека (LIFO):
 *    - push() - добавление элемента
 *    - pop() - удаление элемента с вершины
 *    - peek() - просмотр вершины без удаления
 * 2. Граничные случаи:
 *    - Пустой стек
 *    - Стек с одним элементом
 *    - Стек с множеством элементов
 * 3. Исключительные ситуации:
 *    - pop() из пустого стека
 *    - peek() из пустого стека
 *    - push(null)
 * 4. Дополнительные методы:
 *    - isEmpty()
 *    - size()
 *    - clear()
 *    - search()
 *    - toString()
 * 5. Работа с разными типами данных:
 *    - String
 *    - Integer
 *
 * @author Томских Артём ИВТ-23
 */
@DisplayName("Тестирование класса Stack")
class StackTest {

    // ==================== ТЕСТЫ ДЛЯ СТЕКА СТРОК ====================

    /**
     * Тест: Проверка создания пустого стека.
     */
    @Test
    @DisplayName("Создание пустого стека")
    void testConstructor() {
        Stack<String> stack = new Stack<>();

        assertTrue(stack.isEmpty(), "Новый стек должен быть пустым");
        assertEquals(0, stack.size(), "Размер нового стека должен быть 0");
    }

    /**
     * Тест: Проверка операции push и peek.
     */
    @Test
    @DisplayName("Добавление элемента и просмотр вершины")
    void testPushAndPeek() {
        Stack<String> stack = new Stack<>();

        stack.push("Первый");
        assertEquals(1, stack.size(), "Размер должен быть 1");
        assertFalse(stack.isEmpty(), "Стек не должен быть пустым");
        assertEquals("Первый", stack.peek(), "Вершина должна содержать 'Первый'");

        stack.push("Второй");
        assertEquals(2, stack.size(), "Размер должен быть 2");
        assertEquals("Второй", stack.peek(), "Вершина должна содержать 'Второй'");

        stack.push("Третий");
        assertEquals(3, stack.size(), "Размер должен быть 3");
        assertEquals("Третий", stack.peek(), "Вершина должна содержать 'Третий'");
    }

    /**
     * Тест: Проверка операции pop (LIFO принцип).
     */
    @Test
    @DisplayName("Удаление элементов (LIFO принцип)")
    void testPop() {
        Stack<String> stack = new Stack<>();
        stack.push("Первый");
        stack.push("Второй");
        stack.push("Третий");

        // Проверяем LIFO порядок
        assertEquals("Третий", stack.pop(), "Первый pop должен вернуть 'Третий'");
        assertEquals(2, stack.size(), "Размер должен быть 2");

        assertEquals("Второй", stack.pop(), "Второй pop должен вернуть 'Второй'");
        assertEquals(1, stack.size(), "Размер должен быть 1");

        assertEquals("Первый", stack.pop(), "Третий pop должен вернуть 'Первый'");
        assertEquals(0, stack.size(), "Размер должен быть 0");
        assertTrue(stack.isEmpty(), "Стек должен быть пустым");
    }

    /**
     * Тест: Проверка исключения при pop из пустого стека.
     */
    @Test
    @DisplayName("Исключение при pop из пустого стека")
    void testPopFromEmptyStack() {
        Stack<String> stack = new Stack<>();

        assertThrows(EmptyStackException.class, () -> {
            stack.pop();
        }, "pop() из пустого стека должен выбрасывать EmptyStackException");
    }

    /**
     * Тест: Проверка исключения при peek из пустого стека.
     */
    @Test
    @DisplayName("Исключение при peek из пустого стека")
    void testPeekFromEmptyStack() {
        Stack<String> stack = new Stack<>();

        assertThrows(EmptyStackException.class, () -> {
            stack.peek();
        }, "peek() из пустого стека должен выбрасывать EmptyStackException");
    }

    /**
     * Тест: Проверка операции clear.
     */
    @Test
    @DisplayName("Очистка стека")
    void testClear() {
        Stack<String> stack = new Stack<>();
        stack.push("Первый");
        stack.push("Второй");
        stack.push("Третий");

        assertEquals(3, stack.size(), "Размер должен быть 3");
        assertFalse(stack.isEmpty(), "Стек не должен быть пустым");

        stack.clear();

        assertEquals(0, stack.size(), "После clear размер должен быть 0");
        assertTrue(stack.isEmpty(), "После clear стек должен быть пустым");
        assertThrows(EmptyStackException.class, stack::peek, "peek должен бросать исключение");
    }

    /**
     * Тест: Проверка метода search (поиск элемента).
     */
    @Test
    @DisplayName("Поиск элемента в стеке")
    void testSearch() {
        Stack<String> stack = new Stack<>();
        stack.push("Первый");
        stack.push("Второй");
        stack.push("Третий");
        stack.push("Четвертый");

        // Поиск существующих элементов (позиция от вершины)
        assertEquals(1, stack.search("Четвертый"), "Вершина стека - позиция 1");
        assertEquals(2, stack.search("Третий"), "Второй элемент - позиция 2");
        assertEquals(3, stack.search("Второй"), "Третий элемент - позиция 3");
        assertEquals(4, stack.search("Первый"), "Четвертый элемент - позиция 4");

        // Поиск несуществующего элемента
        assertEquals(-1, stack.search("Несуществующий"), "Несуществующий элемент должен вернуть -1");

        // Поиск в пустом стеке
        stack.clear();
        assertEquals(-1, stack.search("Что-то"), "В пустом стеке поиск всегда возвращает -1");
    }

    /**
     * Тест: Проверка метода search с дублирующимися элементами.
     * Должен возвращать первое вхождение от вершины.
     */
    @Test
    @DisplayName("Поиск с дублирующимися элементами")
    void testSearchWithDuplicates() {
        Stack<String> stack = new Stack<>();
        stack.push("A");
        stack.push("B");
        stack.push("A");
        stack.push("C");
        stack.push("A");

        // Ищем первое вхождение от вершины
        assertEquals(1, stack.search("A"), "Первое 'A' должно быть на вершине (позиция 1)");

        // Удаляем вершину и проверяем следующее вхождение
        stack.pop(); // удаляем первое 'A'
        assertEquals(2, stack.search("A"), "После удаления вершины, следующее 'A' должно быть на позиции 2");
    }

    /**
     * Тест: Проверка строкового представления стека.
     */
    @Test
    @DisplayName("Строковое представление стека")
    void testToString() {
        Stack<String> stack = new Stack<>();

        // Пустой стек
        assertEquals("[]", stack.toString(), "Пустой стек должен представляться как []");

        // С одним элементом
        stack.push("Первый");
        assertEquals("[Первый]", stack.toString(), "Стек с одним элементом");

        // С несколькими элементами
        stack.push("Второй");
        stack.push("Третий");
        assertEquals("[Третий, Второй, Первый]", stack.toString(),
                "Стек должен выводиться от вершины к основанию");
    }

    // ==================== ТЕСТ ДЛЯ ЦЕЛЫХ ЧИСЕЛ ====================

    /**
     * Тест: Работа стека с целыми числами.
     */
    @Test
    @DisplayName("Работа с целыми числами")
    void testWithIntegers() {
        Stack<Integer> stack = new Stack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(30, stack.peek(), "Вершина должна быть 30");
        assertEquals(30, stack.pop(), "pop должен вернуть 30");
        assertEquals(20, stack.pop(), "pop должен вернуть 20");
        assertEquals(10, stack.pop(), "pop должен вернуть 10");
        assertTrue(stack.isEmpty(), "Стек должен быть пустым");

        assertEquals(-1, stack.search(10), "Поиск в пустом стеке должен вернуть -1");
    }


    // ==================== ПАРАМЕТРИЗОВАННЫЕ ТЕСТЫ ====================

    /**
     * Параметризованный тест: Добавление нескольких элементов.
     */
    @ParameterizedTest
    @ValueSource(strings = {"Один", "Два", "Три", "Четыре", "Пять"})
    @DisplayName("Добавление различных элементов")
    void testPushWithDifferentValues(String value) {
        Stack<String> stack = new Stack<>();
        stack.push(value);

        assertEquals(value, stack.peek(), "Вершина должна содержать добавленный элемент");
        assertEquals(1, stack.size(), "Размер должен быть 1");
        assertEquals(1, stack.search(value), "Поиск должен найти элемент на позиции 1");
    }

    /**
     * Тест: Проверка добавления null.
     */
    @Test
    @DisplayName("Попытка добавления null")
    void testPushNull() {
        Stack<String> stack = new Stack<>();

        assertThrows(NullPointerException.class, () -> {
            stack.push(null);
        }, "push(null) должен выбрасывать NullPointerException");

        assertTrue(stack.isEmpty(), "Стек должен остаться пустым");
    }

    // ==================== ТЕСТЫ НА БОЛЬШИХ ДАННЫХ ====================

    /**
     * Тест: Работа с большим количеством элементов.
     */
    @Test
    @DisplayName("Большое количество элементов")
    void testLargeStack() {
        Stack<Integer> stack = new Stack<>();
        int count = 10000;

        // Добавляем много элементов
        for (int i = 0; i < count; i++) {
            stack.push(i);
        }

        assertEquals(count, stack.size(), "Размер должен быть " + count);
        assertEquals(count - 1, stack.peek(), "Вершина должна содержать последний элемент");

        // Проверяем LIFO порядок при удалении
        for (int i = count - 1; i >= 0; i--) {
            assertEquals(i, stack.pop(), "pop должен возвращать элементы в обратном порядке");
        }

        assertTrue(stack.isEmpty(), "После удаления всех элементов стек должен быть пустым");
    }

    // ==================== ТЕСТЫ НА СООТВЕТСТВИЕ LIFO ====================

    /**
     * Тест: Проверка принципа LIFO с чередованием операций.
     */
    @Test
    @DisplayName("Сложные сценарии LIFO")
    void testComplexLifoScenarios() {
        Stack<String> stack = new Stack<>();

        stack.push("A");
        stack.push("B");
        assertEquals("B", stack.pop(), "Должен вернуть B");

        stack.push("C");
        stack.push("D");
        assertEquals("D", stack.pop(), "Должен вернуть D");
        assertEquals("C", stack.peek(), "Вершина должна быть C");

        stack.push("E");
        assertEquals("E", stack.pop(), "Должен вернуть E");
        assertEquals("C", stack.pop(), "Должен вернуть C");
        assertEquals("A", stack.pop(), "Должен вернуть A");

        assertTrue(stack.isEmpty(), "Стек должен быть пустым");
    }

    /**
     * Тест: Проверка состояния стека после различных операций.
     */
    @Test
    @DisplayName("Состояние стека после операций")
    void testStackState() {
        Stack<String> stack = new Stack<>();

        // Начальное состояние
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());

        // После push
        stack.push("X");
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        assertEquals("X", stack.peek());

        // После pop
        stack.pop();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());

        // После clear
        stack.push("Y");
        stack.push("Z");
        stack.clear();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    // ==================== ТЕСТЫ НА ПАМЯТЬ ====================

    /**
     * Тест: Проверка, что удаленные элементы освобождаются.
     */
    @Test
    @DisplayName("Освобождение памяти после удаления")
    void testMemoryLeak() {
        Stack<String> stack = new Stack<>();

        // Добавляем элемент
        stack.push("Элемент");

        // Удаляем его
        String removed = stack.pop();
        assertEquals("Элемент", removed);

        // Проверяем, что стек пуст
        assertTrue(stack.isEmpty());

        // Повторный поиск не должен найти удаленный элемент
        assertEquals(-1, stack.search("Элемент"));
    }

    /**
     * Тест: Проверка метода equals для поиска.
     */
    @Test
    @DisplayName("Поиск с equals")
    void testSearchWithEquals() {
        Stack<String> stack = new Stack<>();
        stack.push(new String("Test")); // Создаем новую строку, не из пула

        // Должен найти по equals, а не по ссылке
        assertEquals(1, stack.search("Test"), "Поиск должен использовать equals");
    }

    // ==================== ТЕСТЫ НА УСТОЙЧИВОСТЬ ====================

    /**
     * Тест: Многократные операции без падений.
     */
    @Test
    @DisplayName("Многократные операции")
    void testRepeatedOperations() {
        Stack<Integer> stack = new Stack<>();

        for (int cycle = 0; cycle < 10; cycle++) {
            // Добавляем элементы
            for (int i = 0; i < 100; i++) {
                stack.push(i);
            }

            assertEquals(100, stack.size());

            // Удаляем половину
            for (int i = 0; i < 50; i++) {
                stack.pop();
            }

            assertEquals(50, stack.size());

            // Очищаем
            stack.clear();
            assertEquals(0, stack.size());
        }
    }
}