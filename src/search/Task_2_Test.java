package search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Класс для тестирования бинарного поиска
class SearchTest {

    // Тестовые массивы, которые будут использоваться в тестах
    private int[] sortedArray;       // Отсортированный массив разных значений
    private int[] emptyArray;        // Пустой массив (граничный случай)
    private int[] singleElementArray; // Массив с одним элементом
    private int[] uniformArray;      // Массив с одинаковыми элементами

    // Метод, выполняемый перед каждым тестом для инициализации данных
    @BeforeEach
    void setUp() {
        // Инициализация отсортированного массива
        sortedArray = new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        // Инициализация пустого массива
        emptyArray = new int[0];
        // Инициализация массива с одним элементом
        singleElementArray = new int[]{42};
        // Инициализация массива с повторяющимися элементами
        uniformArray = new int[]{5, 5, 5, 5, 5, 5};
    }

    // Тест поиска элемента в середине массива
    @Test
    @DisplayName("Поиск существующего элемента в середине массива")
    void findElementInMiddle() {
        // Проверяем, что метод возвращает правильный индекс (4) для значения 50
        assertEquals(4, Task_2.binaryOfIndex(sortedArray, 50));
    }

    // Тест поиска первого элемента массива
    @Test
    @DisplayName("Поиск первого элемента массива")
    void findFirstElement() {
        // Проверяем, что метод возвращает 0 (первый индекс) для значения 10
        assertEquals(0, Task_2.binaryOfIndex(sortedArray, 10));
    }

    // Тест поиска последнего элемента массива
    @Test
    @DisplayName("Поиск последнего элемента массива")
    void findLastElement() {
        // Проверяем, что метод возвращает 9 (последний индекс) для значения 100
        assertEquals(9, Task_2.binaryOfIndex(sortedArray, 100));
    }

    // Тест поиска значения меньше минимального в массиве
    @Test
    @DisplayName("Поиск несуществующего элемента (меньше минимального)")
    void findBelowMin() {
        // Проверяем, что метод возвращает -1 для значения 5 (меньше минимального 10)
        assertEquals(-1, Task_2.binaryOfIndex(sortedArray, 5));
    }

    // Тест поиска значения больше максимального в массиве
    @Test
    @DisplayName("Поиск несуществующего элемента (больше максимального)")
    void findAboveMax() {
        // Проверяем, что метод возвращает -1 для значения 105 (больше максимального 100)
        assertEquals(-1, Task_2.binaryOfIndex(sortedArray, 105));
    }

    // Тест поиска в пустом массиве
    @Test
    @DisplayName("Поиск в пустом массиве")
    void searchEmptyArray() {
        // Проверяем, что метод возвращает -1 при поиске в пустом массиве
        assertEquals(-1, Task_2.binaryOfIndex(emptyArray, 10));
    }

    // Тест поиска в массиве из одного элемента (успешный случай)
    @Test
    @DisplayName("Поиск в массиве из одного элемента (найден)")
    void searchSingleElementArrayFound() {
        // Проверяем, что метод возвращает 0 для существующего элемента 42
        assertEquals(0, Task_2.binaryOfIndex(singleElementArray, 42));
    }

    // Тест поиска в массиве из одного элемента (неудачный случай)
    @Test
    @DisplayName("Поиск в массиве из одного элемента (не найден)")
    void searchSingleElementArrayNotFound() {
        // Проверяем, что метод возвращает -1 для несуществующего элемента 43
        assertEquals(-1, Task_2.binaryOfIndex(singleElementArray, 43));
    }

    // Тест поиска в массиве с одинаковыми элементами (успешный случай)
    @Test
    @DisplayName("Поиск в массиве с одинаковыми элементами (найден)")
    void searchUniformArrayFound() {
        // Проверяем, что метод возвращает любой индекс ≥ 0 для существующего элемента 5
        assertTrue(Task_2.binaryOfIndex(uniformArray, 5) >= 0);
    }

    // Тест поиска в массиве с одинаковыми элементами (неудачный случай)
    @Test
    @DisplayName("Поиск в массиве с одинаковыми элементами (не найден)")
    void searchUniformArrayNotFound() {
        // Проверяем, что метод возвращает -1 для несуществующего элемента 6
        assertEquals(-1, Task_2.binaryOfIndex(uniformArray, 6));
    }

    // Тест поиска элемента в левой половине массива
    @Test
    @DisplayName("Поиск элемента левее середины")
    void findElementLeftOfCenter() {
        // Проверяем, что метод возвращает 2 для значения 30 (левая половина)
        assertEquals(2, Task_2.binaryOfIndex(sortedArray, 30));
    }

    // Тест поиска элемента в правой половине массива
    @Test
    @DisplayName("Поиск элемента правее середины")
    void findElementRightOfCenter() {
        // Проверяем, что метод возвращает 7 для значения 80 (правая половина)
        assertEquals(7, Task_2.binaryOfIndex(sortedArray, 80));
    }

    // Тест поиска в массиве с дубликатами значений
    @Test
    @DisplayName("Поиск с дубликатами (возвращает любой подходящий индекс)")
    void findWithDuplicates() {
        // Создаем массив с дубликатами значения 30
        int[] withDuplicates = {10, 20, 30, 30, 30, 40, 50};
        // Выполняем поиск
        int result = Task_2.binaryOfIndex(withDuplicates, 30);
        // Проверяем, что результат находится в допустимом диапазоне индексов (2-4)
        assertTrue(result >= 2 && result <= 4);
    }

    /** ---Тесты для интерполяционного поиска---*/

    // Тест поиска элемента в середине массива
    @Test
    @DisplayName("Поиск существующего элемента в середине массива")
    void findElementInMiddle2() {
        // Проверяем, что метод возвращает правильный индекс (4) для значения 50
        assertEquals(4, Task_2.interpolationSearch(sortedArray, 50));
    }

    // Тест поиска первого элемента массива
    @Test
    @DisplayName("Поиск первого элемента массива")
    void findFirstElement2() {
        // Проверяем, что метод возвращает 0 (первый индекс) для значения 10
        assertEquals(0, Task_2.interpolationSearch(sortedArray, 10));
    }

    // Тест поиска последнего элемента массива
    @Test
    @DisplayName("Поиск последнего элемента массива")
    void findLastElement2() {
        // Проверяем, что метод возвращает 9 (последний индекс) для значения 100
        assertEquals(9, Task_2.interpolationSearch(sortedArray, 100));
    }

    // Тест поиска значения меньше минимального в массиве
    @Test
    @DisplayName("Поиск несуществующего элемента (меньше минимального)")
    void findBelowMin2() {
        // Проверяем, что метод возвращает -1 для значения 5 (меньше минимального 10)
        assertEquals(-1, Task_2.interpolationSearch(sortedArray, 5));
    }

    // Тест поиска значения больше максимального в массиве
    @Test
    @DisplayName("Поиск несуществующего элемента (больше максимального)")
    void findAboveMax2() {
        // Проверяем, что метод возвращает -1 для значения 105 (больше максимального 100)
        assertEquals(-1, Task_2.interpolationSearch(sortedArray, 105));
    }

    // Тест поиска в пустом массиве
    @Test
    @DisplayName("Поиск в пустом массиве")
    void searchEmptyArray2() {
        // Проверяем, что метод возвращает -1 при поиске в пустом массиве
        assertEquals(-1, Task_2.interpolationSearch(emptyArray, 10));
    }

    // Тест поиска в массиве из одного элемента (успешный случай)
    @Test
    @DisplayName("Поиск в массиве из одного элемента (найден)")
    void searchSingleElementArrayFound2() {
        // Проверяем, что метод возвращает 0 для существующего элемента 42
        assertEquals(0, Task_2.interpolationSearch(singleElementArray, 42));
    }
}