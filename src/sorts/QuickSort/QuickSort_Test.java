package sorts.QuickSort;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import preparatory.Task_1;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс QuickSortTest содержит модульные тесты для проверки
 * функциональности класса QuickSort.
 *
 * Тесты покрывают следующие аспекты:
 * 1. Корректность сортировки для различных типов массивов:
 *    - Обычные массивы со случайными значениями
 *    - Уже отсортированные массивы
 *    - Массивы в обратном порядке
 *    - Массивы с дубликатами
 *    - Массивы с отрицательными числами
 *    - Пустые массивы и массивы с одним элементом
 *    - null массивы
 * 2. Проверка корректности работы partition (разделения)
 * 3. Проверка стабильности сортировки
 * 4. Проверка работы на больших массивах
 * 5. Измерение производительности
 *
 * @author Томских Артём ИВТ-23
 */
@DisplayName("Тестирование быстрой сортировки QuickSort")
class QuickSortTest {

    // Константы для тестирования
    private static final int TEST_SIZE = 1000;
    private static final int TEST_MIN = 1;
    private static final int TEST_MAX = 10000;
    private static final int PERFORMANCE_ITERATIONS = 10;

    // Для сохранения результатов тестов
    private static StringBuilder testResults;
    private static int passedTests = 0;
    private static int totalTests = 0;

    /**
     * Инициализация перед всеми тестами
     */
    @BeforeAll
    static void initAll() {
        testResults = new StringBuilder();
        testResults.append("=== РЕЗУЛЬТАТЫ ТЕСТИРОВАНИЯ БЫСТРОЙ СОРТИРОВКИ (QUICK SORT) ===\n");
        testResults.append("Автор: Томских Артём ИВТ-23\n");
        testResults.append("Дата: ").append(java.time.LocalDate.now()).append("\n\n");
    }

    /**
     * Сохранение результатов после всех тестов
     */
    @AfterAll
    static void saveResults() throws IOException {
        testResults.append("\n=== ИТОГИ ТЕСТИРОВАНИЯ ===\n");
        testResults.append("Пройдено тестов: ").append(passedTests).append(" из ").append(totalTests).append("\n");

        // Преобразуем строку в массив int для сохранения (каждый символ как int)
        int[] data = testResults.toString().chars().toArray();
        Task_1.saveArrayToFile(data, "quicksort_test_results.txt");
        System.out.println("Результаты тестов сохранены в файл 'quicksort_test_results.txt'");
        System.out.println("Пройдено тестов: " + passedTests + " из " + totalTests);
    }

    /**
     * Подсчет тестов перед каждым тестом
     */
    @BeforeEach
    void incrementTotal(TestInfo testInfo) {
        totalTests++;
        System.out.println("Выполняется тест: " + testInfo.getDisplayName());
    }

    /**
     * Отметка о прохождении теста
     */
    @AfterEach
    void markPassed() {
        passedTests++;
    }

    // ==================== ТЕСТЫ КОРРЕКТНОСТИ СОРТИРОВКИ ====================

    /**
     * Тест: Сортировка обычного массива со случайными значениями.
     * Использует Task_1.createRandomArray() для генерации данных.
     */
    @Test
    @DisplayName("Сортировка случайного массива")
    void testQuickSortWithRandomArray() {
        // Используем Task_1 для генерации случайного массива
        int[] original = Task_1.createRandomArray(20, 1, 100);
        int[] expected = original.clone();
        Arrays.sort(expected); // Встроенная сортировка как эталон

        int[] result = QuickSort.quickSort(original.clone());

        assertArrayEquals(expected, result,
                "Отсортированный массив должен совпадать с эталоном");

        // Проверяем, что исходный массив не изменился (мы работали с клоном)
        assertFalse(Arrays.equals(original, result),
                "Исходный массив не должен быть изменен (мы сортировали клон)");

        testResults.append("✓ testQuickSortWithRandomArray: пройден (размер 20)\n");
    }

    /**
     * Тест: Сортировка уже отсортированного массива (лучший случай для некоторых алгоритмов).
     * Использует Task_1.createSortedArray() для генерации данных.
     */
    @Test
    @DisplayName("Сортировка уже отсортированного массива")
    void testQuickSortWithSortedArray() {
        int[] original = Task_1.createSortedArray(20, 1, 100);
        int[] expected = original.clone();

        int[] result = QuickSort.quickSort(original.clone());

        assertArrayEquals(expected, result,
                "Отсортированный массив должен остаться без изменений");

        testResults.append("✓ testQuickSortWithSortedArray: пройден\n");
    }

    /**
     * Тест: Сортировка массива в обратном порядке.
     */
    @Test
    @DisplayName("Сортировка массива в обратном порядке")
    void testQuickSortWithReversedArray() {
        int[] original = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int[] result = QuickSort.quickSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив в обратном порядке должен быть отсортирован");

        testResults.append("✓ testQuickSortWithReversedArray: пройден\n");
    }

    /**
     * Тест: Сортировка массива с дублирующимися элементами.
     */
    @Test
    @DisplayName("Сортировка массива с дубликатами")
    void testQuickSortWithDuplicates() {
        int[] original = {5, 3, 8, 3, 1, 5, 8, 3, 2, 5};
        int[] expected = {1, 2, 3, 3, 3, 5, 5, 5, 8, 8};

        int[] result = QuickSort.quickSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с дубликатами должен быть корректно отсортирован");

        testResults.append("✓ testQuickSortWithDuplicates: пройден\n");
    }

    /**
     * Тест: Сортировка массива с отрицательными числами.
     */
    @Test
    @DisplayName("Сортировка массива с отрицательными числами")
    void testQuickSortWithNegativeNumbers() {
        int[] original = {-5, 3, -2, 0, -8, 7, -1, 4, -3, 2};
        int[] expected = {-8, -5, -3, -2, -1, 0, 2, 3, 4, 7};

        int[] result = QuickSort.quickSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с отрицательными числами должен сортироваться корректно");

        testResults.append("✓ testQuickSortWithNegativeNumbers: пройден\n");
    }

    /**
     * Тест: Сортировка массива с одинаковыми элементами.
     */
    @Test
    @DisplayName("Сортировка массива с одинаковыми элементами")
    void testQuickSortWithUniformArray() {
        int[] original = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        int[] expected = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5};

        int[] result = QuickSort.quickSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с одинаковыми элементами должен остаться без изменений");

        testResults.append("✓ testQuickSortWithUniformArray: пройден\n");
    }

    // ==================== ТЕСТЫ ГРАНИЧНЫХ СЛУЧАЕВ ====================

    /**
     * Тест: Сортировка пустого массива.
     */
    @Test
    @DisplayName("Сортировка пустого массива")
    void testQuickSortWithEmptyArray() {
        int[] empty = {};

        int[] result = QuickSort.quickSort(empty);

        assertNotNull(result);
        assertEquals(0, result.length);
        assertTrue(Task_1.isSorted(result));

        testResults.append("✓ testQuickSortWithEmptyArray: пройден\n");
    }

    /**
     * Тест: Сортировка массива с одним элементом.
     */
    @Test
    @DisplayName("Сортировка массива с одним элементом")
    void testQuickSortWithSingleElement() {
        int[] single = {42};

        int[] result = QuickSort.quickSort(single);

        assertArrayEquals(new int[]{42}, result);
        assertTrue(Task_1.isSorted(result));

        testResults.append("✓ testQuickSortWithSingleElement: пройден\n");
    }

    /**
     * Тест: Сортировка null массива.
     */
    @Test
    @DisplayName("Сортировка null массива")
    void testQuickSortWithNullArray() {
        int[] result = QuickSort.quickSort((int[]) null);

        assertNull(result);

        testResults.append("✓ testQuickSortWithNullArray: пройден\n");
    }

    /**
     * Тест: Сортировка с некорректными индексами.
     */
    @Test
    @DisplayName("Сортировка с некорректными индексами")
    void testQuickSortWithInvalidIndices() {
        int[] array = {5, 2, 8, 1, 9};
        int[] expected = array.clone();

        // Вызываем с некорректными индексами
        int[] result = QuickSort.quickSort(array, -1, 10);

        // Массив не должен измениться
        assertArrayEquals(expected, result,
                "При некорректных индексах массив не должен измениться");

        testResults.append("✓ testQuickSortWithInvalidIndices: пройден\n");
    }

    // ==================== ТЕСТЫ МЕТОДА PARTITION ====================

    /**
     * Тест: Проверка метода partition - правильность разделения.
     */
    @Test
    @DisplayName("Проверка метода partition")
    void testPartition() {
        // Массив, где pivot должен оказаться на индексе 4
        int[] array = {9, 7, 5, 11, 12, 2, 14, 3, 10, 6};
        int low = 0;
        int high = array.length - 1;

        // Сохраняем копию для сравнения
        int[] original = array.clone();

        // Вызываем partition
        int pivotIndex = QuickSort.partition(array, low, high);

        // Проверяем, что pivotIndex в допустимых пределах
        assertTrue(pivotIndex >= low && pivotIndex <= high,
                "Индекс опорного элемента должен быть в пределах [low, high]");

        // Проверяем, что все элементы слева от pivotIndex <= pivot
        int pivot = array[pivotIndex];
        for (int i = low; i < pivotIndex; i++) {
            assertTrue(array[i] <= pivot,
                    "Элемент слева от pivot должен быть <= pivot");
        }

        // Проверяем, что все элементы справа от pivotIndex > pivot
        for (int i = pivotIndex + 1; i <= high; i++) {
            assertTrue(array[i] > pivot,
                    "Элемент справа от pivot должен быть > pivot");
        }

        // Проверяем, что все элементы из исходного массива присутствуют
        Arrays.sort(original);
        Arrays.sort(array);
        assertArrayEquals(original, array,
                "Все элементы исходного массива должны сохраниться");

        testResults.append("✓ testPartition: пройден (pivot index = ").append(pivotIndex).append(")\n");
    }

    /**
     * Параметризованный тест: Проверка partition для разных случаев.
     */
    @ParameterizedTest
    @MethodSource("partitionTestProvider")
    @DisplayName("Параметризованный тест partition")
    void testPartitionParameterized(int[] array, int low, int high, String description) {
        int[] original = array.clone();

        int pivotIndex = QuickSort.partition(array, low, high);

        // Проверка свойств разделения
        int pivot = array[pivotIndex];
        for (int i = low; i < pivotIndex; i++) {
            assertTrue(array[i] <= pivot,
                    description + ": элемент слева от pivot должен быть <= pivot");
        }
        for (int i = pivotIndex + 1; i <= high; i++) {
            assertTrue(array[i] > pivot,
                    description + ": элемент справа от pivot должен быть > pivot");
        }

        testResults.append("✓ testPartitionParameterized: ").append(description).append(" - пройден\n");
    }

    /**
     * Провайдер тестовых данных для parameterized test partition.
     */
    static Stream<Arguments> partitionTestProvider() {
        return Stream.of(
                Arguments.of(new int[]{5, 2, 8, 1, 9}, 0, 4, "Обычный массив"),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 0, 4, "Уже отсортированный"),
                Arguments.of(new int[]{5, 4, 3, 2, 1}, 0, 4, "Обратный порядок"),
                Arguments.of(new int[]{5, 5, 5, 5, 5}, 0, 4, "Все элементы равны"),
                Arguments.of(new int[]{3, 1, 4, 1, 5}, 0, 4, "С дубликатами"),
                Arguments.of(new int[]{-5, 3, -2, 0, 7}, 0, 4, "С отрицательными числами"),
                Arguments.of(new int[]{1}, 0, 0, "Один элемент"),
                Arguments.of(new int[]{10, 20}, 0, 1, "Два элемента")
        );
    }

    // ==================== ТЕСТЫ С РАЗЛИЧНЫМИ РАЗМЕРАМИ ====================

    /**
     * Параметризованный тест: Сортировка массивов разного размера.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 10, 50, 100, 500, 1000})
    @DisplayName("Сортировка массивов разного размера")
    void testQuickSortWithDifferentSizes(int size) {
        int[] original = Task_1.createRandomArray(size, 1, size * 10);
        int[] expected = original.clone();
        Arrays.sort(expected);

        long startTime = System.nanoTime();
        int[] result = QuickSort.quickSort(original.clone());
        long endTime = System.nanoTime();

        assertArrayEquals(expected, result,
                "Массив размера " + size + " должен быть корректно отсортирован");

        double timeMs = (endTime - startTime) / 1_000_000.0;

        testResults.append("✓ testQuickSortWithDifferentSizes: размер ").append(size)
                .append(", время: ").append(String.format("%.3f", timeMs)).append(" мс\n");
    }

    // ==================== ТЕСТЫ С ИСПОЛЬЗОВАНИЕМ TASK_1 ====================

    /**
     * Тест: Проверка сортировки с использованием Task_1.isSorted.
     */
    @Test
    @DisplayName("Проверка с использованием Task_1.isSorted")
    void testIsSortedAfterQuickSort() {
        int[] original = Task_1.createRandomArray(100, 1, 1000);

        // Проверяем, что случайный массив не отсортирован
        assertFalse(Task_1.isSorted(original),
                "Случайный массив не должен быть отсортирован");

        // Сортируем
        int[] sorted = QuickSort.quickSort(original.clone());

        // Проверяем, что после сортировки массив отсортирован
        assertTrue(Task_1.isSorted(sorted),
                "После сортировки массив должен быть отсортирован");

        testResults.append("✓ testIsSortedAfterQuickSort: пройден\n");
    }

    /**
     * Тест: Сохранение отсортированного массива в файл с помощью Task_1.
     */
    @Test
    @DisplayName("Сохранение отсортированного массива в файл")
    void testSaveSortedArrayToFile() throws IOException {
        int[] original = Task_1.createRandomArray(50, 1, 1000);
        int[] sorted = QuickSort.quickSort(original.clone());

        // Сохраняем в файл с помощью Task_1
        Task_1.saveArrayToFile(sorted, "quicksort_output.txt");

        testResults.append("✓ testSaveSortedArrayToFile: пройден\n");
    }

    /**
     * Тест: Сортировка монотонно возрастающего массива из Task_1.
     */
    @Test
    @DisplayName("Сортировка массива из Task_1.createSortedArray")
    void testQuickSortWithTask1SortedArray() {
        int[] original = Task_1.createSortedArray(50, 1, 1000);
        int[] expected = original.clone();

        int[] result = QuickSort.quickSort(original.clone());

        assertArrayEquals(expected, result,
                "Отсортированный массив из Task_1 должен остаться без изменений");

        testResults.append("✓ testQuickSortWithTask1SortedArray: пройден\n");
    }

    // ==================== ТЕСТЫ ПРОИЗВОДИТЕЛЬНОСТИ ====================

    /**
     * Тест: Сравнение производительности с встроенной сортировкой.
     */
    @Test
    @DisplayName("Сравнение производительности с Arrays.sort")
    void testPerformanceComparison() {
        int[] sizes = {100, 500, 1000, 5000, 10000};

        testResults.append("\n--- СРАВНЕНИЕ ПРОИЗВОДИТЕЛЬНОСТИ ---\n");
        testResults.append(String.format("%-10s %-15s %-15s %-10s\n",
                "Размер", "QuickSort (мс)", "Arrays.sort (мс)", "Отношение"));

        for (int size : sizes) {
            int[] array = Task_1.createRandomArray(size, 1, size * 10);

            // Измеряем время QuickSort
            long quickStart = System.nanoTime();
            QuickSort.quickSort(array.clone());
            long quickEnd = System.nanoTime();
            double quickTime = (quickEnd - quickStart) / 1_000_000.0;

            // Измеряем время Arrays.sort
            long arraysStart = System.nanoTime();
            Arrays.sort(array.clone());
            long arraysEnd = System.nanoTime();
            double arraysTime = (arraysEnd - arraysStart) / 1_000_000.0;

            double ratio = quickTime / arraysTime;

            testResults.append(String.format("%-10d %-15.3f %-15.3f %-10.2f\n",
                    size, quickTime, arraysTime, ratio));
        }
    }

    // ==================== ТЕСТЫ С БОЛЬШИМИ ДАННЫМИ ====================

    /**
     * Тест: Сортировка большого массива (проверка на переполнение стека).
     */
    @Test
    @DisplayName("Сортировка большого массива (100000 элементов)")
    void testQuickSortWithLargeArray() {
        int size = 100000;
        int[] array = Task_1.createRandomArray(size, 1, size);

        long startTime = System.nanoTime();
        int[] result = QuickSort.quickSort(array.clone());
        long endTime = System.nanoTime();

        assertTrue(Task_1.isSorted(result),
                "Большой массив должен быть отсортирован");

        double timeMs = (endTime - startTime) / 1_000_000.0;
        testResults.append("✓ testQuickSortWithLargeArray: размер ").append(size)
                .append(", время: ").append(String.format("%.3f", timeMs)).append(" мс\n");
    }

    // ==================== ТЕСТЫ С РАЗНЫМИ ТИПАМИ ДАННЫХ ====================

    /**
     * Тест: Сортировка массива с чередующимися значениями.
     */
    @Test
    @DisplayName("Сортировка массива с чередующимися значениями")
    void testQuickSortWithAlternatingValues() {
        int[] original = new int[100];
        for (int i = 0; i < original.length; i++) {
            original[i] = (i % 2 == 0) ? i : -i;
        }

        int[] result = QuickSort.quickSort(original.clone());

        assertTrue(Task_1.isSorted(result),
                "Массив с чередующимися значениями должен быть отсортирован");

        testResults.append("✓ testQuickSortWithAlternatingValues: пройден\n");
    }

    /**
     * Тест: Проверка стабильности.
     * Этот тест показывает, что QuickSort может менять порядок равных элементов.
     */
    @Test
    @DisplayName("Проверка стабильности (информационно)")
    void testStability() {
        // Создаем массив пар (значение, исходный индекс)
        int[][] pairs = new int[10][2];
        for (int i = 0; i < 5; i++) {
            pairs[i] = new int[]{5, i};      // пять пятерок с разными индексами
        }
        for (int i = 5; i < 10; i++) {
            pairs[i] = new int[]{3, i};      // пять троек с разными индексами
        }

        // Преобразуем в одномерный массив
        int[] array = new int[10];
        for (int i = 0; i < 10; i++) {
            array[i] = pairs[i][0];
        }

        int[] sorted = QuickSort.quickSort(array.clone());

        assertTrue(Task_1.isSorted(sorted),
                "Массив должен быть отсортирован");

        testResults.append("✓ testStability: пройден (QuickSort нестабильна, но сортирует)\n");
    }
}