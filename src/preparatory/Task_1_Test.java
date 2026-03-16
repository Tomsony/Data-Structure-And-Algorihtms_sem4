package preparatory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.*;

class Task_1_Test {
    // Константы для тестирования
    private static final int TEST_SIZE = 1000; // Размер тестовых массивов
    private static final int TEST_MIN = 10;    // Минимальное значение элементов
    private static final int TEST_MAX = 2000;  // Максимальное значение элементов

    private int[] randomArray;  // Массив со случайными значениями
    private int[] sortedArray;  // Отсортированный массив

    /**
     * Метод, выполняемый перед каждым тестом.
     * Инициализирует тестовые массивы.
     */
    @BeforeEach
    void setUp() {
        // Создаем массив со случайными числами
        randomArray = Task_1.createRandomArray(TEST_SIZE, TEST_MIN, TEST_MAX);
        // Создаем отсортированный массив
        sortedArray = Task_1.createSortedArray(TEST_SIZE, TEST_MIN, TEST_MAX);
    }

    /**
     * Тест проверяет корректность работы метода isSorted()
     * на явно заданных отсортированном и неотсортированном массивах.
     */
    @Test
    @DisplayName("Проверка метода isSorted на ручных данных")
    void isSortedTest(TestInfo testInfo) {
        System.out.println("Запущен тест: " + testInfo.getDisplayName());

        // Тестовые данные
        int[] unsortedArray = {1,4,2,9};  // Неотсортированный массив
        int[] sortedArray = {1,2,3,4};     // Отсортированный массив

        // Выполнение проверок
        boolean unsortedResult = Task_1.isSorted(unsortedArray);
        boolean sortedResult = Task_1.isSorted(sortedArray);

        // Вывод отладочной информации
        System.out.println("Результат для неотсортированного массива: " + unsortedResult);
        System.out.println("Результат для отсортированного массива: " + sortedResult);

        // Проверяем, что метод правильно определяет неотсортированный массив
        assertFalse(unsortedResult);
        // Проверяем, что метод правильно определяет отсортированный массив
        assertTrue(sortedResult);
    }

    /**
     * Тест проверяет, что созданный случайным образом массив
     * действительно не является отсортированным.
     */
    @Test
    @DisplayName("Проверка, что случайный массив не отсортирован")
    void testRandomArrayNotSorted() {
        assertFalse(Task_1.isSorted(randomArray));
    }

    /**
     * Тест проверяет, что созданный отсортированный массив
     * действительно определяется как отсортированный.
     */
    @Test
    @DisplayName("Проверка, что sortedArray действительно отсортирован")
    void testSortedArrayIsSorted() {
        assertTrue(Task_1.isSorted(sortedArray));
    }
    /**
     * Несортированный массив где 1 и последний эллемент не отсортированы
      */
    @Test
    void testNotSortedArrayIsSorted1() {
        int[] arr1 = {10,1,2,3,4,1};
        assertFalse(Task_1.isSorted(arr1));
    }

    /**
     * Проверка на сортировку массива с одинаковыми элементами
     */
    @Test
    void testNotSortedArrayIsSorted12() {
        int[] arr2 = {1,1,1,1,1};
        assertTrue(Task_1.isSorted(arr2));
    }


    /**
     * Тест проверяет работу sequentialSearch() в случае,
     * когда искомый элемент не найден (должен вернуть -1).
     */
    @Test
    @DisplayName("Проверка sequentialSearch при отсутствии элемента")
    void testSequentialSearch() {
        // Ищем элемент, которого точно нет в массиве (0 с допуском 0)
        assertEquals(-1, Task_1.sequentialSearch(
                sortedArray,
                n -> Task_1.compareWithPrecision(n, 0, 0)
        ));
    }

    /**
     * Тест проверяет работу sequentialSearch() в случае,
     * когда искомый элемент присутствует в массиве.
     */
    @Test
    @DisplayName("Проверка sequentialSearch при наличии элемента")
    void testEqualsSequentialSearch() {
        // Ищем элемент со значением ~10 (с допуском 0.5)
        // В отсортированном массиве он должен быть на позиции 1
        assertEquals(1, Task_1.sequentialSearch(
                sortedArray,
                n -> Task_1.compareWithPrecision(n, 10.0, 0.5)
        ));
    }

    /**
     * Тест проверяет корректность работы метода compareWithPrecision().
     */
    @Test
    @DisplayName("Проверка compareWithPrecision")
    void testTruePredicate(){
        // Проверяем, что 3 находится в диапазоне 2±2
        assertTrue(Task_1.compareWithPrecision(3, 2, 2));
    }
}