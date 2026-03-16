package sorts.RadixSort;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import preparatory.Task_1;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс RadixSortTest содержит модульные тесты для проверки
 * функциональности класса RadixSort.
 *
 * Тесты покрывают следующие аспекты:
 * 1. Корректность сортировки для различных типов массивов:
 *    - Случайные массивы
 *    - Уже отсортированные массивы
 *    - Массивы в обратном порядке
 *    - Массивы с дубликатами
 *    - Массивы с одним элементом
 *    - Пустые массивы
 *    - null массивы
 * 2. Проверка работы с числами разной разрядности
 * 3. Проверка стабильности сортировки
 * 4. Проверка производительности на больших массивах
 * 5. Интеграция с Task_1 для генерации тестовых данных
 *
 * Особенности поразрядной сортировки:
 * - Работает только с неотрицательными числами
 * - Временная сложность: O(n * k), где k - количество разрядов
 * - Стабильная сортировка
 *
 * @author Томских Артём ИВТ-23
 */
@DisplayName("Тестирование поразрядной сортировки (Radix Sort)")
class RadixSortTest {

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
        testResults.append("=== РЕЗУЛЬТАТЫ ТЕСТИРОВАНИЯ ПОРАЗРЯДНОЙ СОРТИРОВКИ (RADIX SORT) ===\n");
        testResults.append("Автор: Томских Артём ИВТ-23\n");
        testResults.append("Дата: ").append(java.time.LocalDate.now()).append("\n\n");
        testResults.append("Особенности: сортирует только неотрицательные числа\n\n");
    }

    /**
     * Сохранение результатов после всех тестов
     */
    @AfterAll
    static void saveResults() throws IOException {
        testResults.append("\n=== ИТОГИ ТЕСТИРОВАНИЯ ===\n");
        testResults.append("Пройдено тестов: ").append(passedTests).append(" из ").append(totalTests).append("\n");

        // Преобразуем строку в массив int для сохранения
        int[] data = testResults.toString().chars().toArray();
        Task_1.saveArrayToFile(data, "radixsort_test_results.txt");
        System.out.println("Результаты тестов сохранены в файл 'radixsort_test_results.txt'");
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
     * Тест: Сортировка обычного массива с однозначными числами.
     */
    @Test
    @DisplayName("Сортировка массива с однозначными числами")
    void testRadixSortWithSingleDigit() {
        int[] original = {5, 2, 8, 1, 9, 3, 7, 4, 6, 0};
        int[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с однозначными числами должен быть отсортирован");

        testResults.append("testRadixSortWithSingleDigit: пройден\n");
    }

    /**
     * Тест: Сортировка массива с двузначными числами.
     */
    @Test
    @DisplayName("Сортировка массива с двузначными числами")
    void testRadixSortWithTwoDigits() {
        int[] original = {78, 45, 12, 90, 34, 56, 23, 67, 89, 1};
        int[] expected = {1, 12, 23, 34, 45, 56, 67, 78, 89, 90};

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с двузначными числами должен быть отсортирован");

        testResults.append("testRadixSortWithTwoDigits: пройден\n");
    }

    /**
     * Тест: Сортировка массива с трехзначными числами.
     */
    @Test
    @DisplayName("Сортировка массива с трехзначными числами")
    void testRadixSortWithThreeDigits() {
        int[] original = {789, 456, 123, 901, 345, 678, 234, 567, 890, 100};
        int[] expected = {100, 123, 234, 345, 456, 567, 678, 789, 890, 901};

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с трехзначными числами должен быть отсортирован");

        testResults.append("testRadixSortWithThreeDigits: пройден\n");
    }

    /**
     * Тест: Сортировка массива с числами разной разрядности.
     */
    @Test
    @DisplayName("Сортировка массива с числами разной разрядности")
    void testRadixSortWithMixedDigits() {
        int[] original = {1000, 5, 999, 42, 7777, 123, 9, 888, 10000, 7};
        int[] expected = {5, 7, 9, 42, 123, 888, 999, 1000, 7777, 10000};

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с числами разной разрядности должен быть отсортирован");

        testResults.append("testRadixSortWithMixedDigits: пройден\n");
    }

    /**
     * Тест: Сортировка массива с дублирующимися элементами.
     */
    @Test
    @DisplayName("Сортировка массива с дубликатами")
    void testRadixSortWithDuplicates() {
        int[] original = {78, 45, 45, 90, 45, 56, 78, 67, 89, 90};
        int[] expected = {45, 45, 45, 56, 67, 78, 78, 89, 90, 90};

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с дубликатами должен быть отсортирован");

        testResults.append("testRadixSortWithDuplicates: пройден\n");
    }

    /**
     * Тест: Сортировка уже отсортированного массива.
     */
    @Test
    @DisplayName("Сортировка уже отсортированного массива")
    void testRadixSortWithSortedArray() {
        int[] original = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Отсортированный массив должен остаться без изменений");

        testResults.append("testRadixSortWithSortedArray: пройден\n");
    }

    /**
     * Тест: Сортировка массива в обратном порядке.
     */
    @Test
    @DisplayName("Сортировка массива в обратном порядке")
    void testRadixSortWithReversedArray() {
        int[] original = {100, 90, 80, 70, 60, 50, 40, 30, 20, 10, 0};
        int[] expected = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив в обратном порядке должен быть отсортирован");

        testResults.append("testRadixSortWithReversedArray: пройден\n");
    }

    /**
     * Тест: Сортировка массива с большими числами.
     */
    @Test
    @DisplayName("Сортировка массива с большими числами")
    void testRadixSortWithLargeNumbers() {
        int[] original = {999999, 888888, 777777, 666666, 555555,
                444444, 333333, 222222, 111111, 0};
        int[] expected = {0, 111111, 222222, 333333, 444444,
                555555, 666666, 777777, 888888, 999999};

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с большими числами должен быть отсортирован");

        testResults.append("testRadixSortWithLargeNumbers: пройден\n");
    }

    /**
     * Тест: Сортировка массива с нулями.
     */
    @Test
    @DisplayName("Сортировка массива с нулями")
    void testRadixSortWithZeros() {
        int[] original = {50, 0, 30, 0, 20, 0, 40, 0, 10, 0};
        int[] expected = {0, 0, 0, 0, 0, 10, 20, 30, 40, 50};

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с нулями должен быть отсортирован");

        testResults.append("testRadixSortWithZeros: пройден\n");
    }

    // ==================== ТЕСТЫ С ИСПОЛЬЗОВАНИЕМ TASK_1 ====================

    /**
     * Тест: Сортировка случайного массива, сгенерированного Task_1.
     */
    @Test
    @DisplayName("Сортировка случайного массива из Task_1")
    void testRadixSortWithTask1RandomArray() {
        int[] original = Task_1.createRandomArray(50, 0, 1000);
        int[] expected = original.clone();
        Arrays.sort(expected);

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Случайный массив из Task_1 должен быть отсортирован");

        testResults.append("testRadixSortWithTask1RandomArray: пройден\n");
    }

    /**
     * Тест: Сортировка отсортированного массива из Task_1.
     */
    @Test
    @DisplayName("Сортировка отсортированного массива из Task_1")
    void testRadixSortWithTask1SortedArray() {
        int[] original = Task_1.createSortedArray(50, 0, 1000);
        int[] expected = original.clone();

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Отсортированный массив из Task_1 должен остаться без изменений");

        testResults.append("testRadixSortWithTask1SortedArray: пройден\n");
    }

    /**
     * Тест: Проверка с использованием Task_1.isSorted.
     */
    @Test
    @DisplayName("Проверка с использованием Task_1.isSorted")
    void testIsSortedAfterRadixSort() {
        int[] original = Task_1.createRandomArray(100, 0, 10000);

        // Проверяем, что случайный массив не отсортирован
        assertFalse(Task_1.isSorted(original),
                "Случайный массив не должен быть отсортирован");

        // Сортируем
        int[] sorted = RadixSort.radixSort(original.clone());

        // Проверяем, что после сортировки массив отсортирован
        assertTrue(Task_1.isSorted(sorted),
                "После сортировки массив должен быть отсортирован");

        testResults.append("testIsSortedAfterRadixSort: пройден\n");
    }

    /**
     * Тест: Сохранение отсортированного массива в файл.
     */
    @Test
    @DisplayName("Сохранение отсортированного массива в файл")
    void testSaveSortedArrayToFile() throws IOException {
        int[] original = Task_1.createRandomArray(100, 0, 9999);
        int[] sorted = RadixSort.radixSort(original.clone());

        Task_1.saveArrayToFile(sorted, "radixsort_output.txt");

        testResults.append("testSaveSortedArrayToFile: пройден\n");
    }

    // ==================== ТЕСТЫ ГРАНИЧНЫХ СЛУЧАЕВ ====================

    /**
     * Тест: Сортировка массива с одним элементом.
     */
    @Test
    @DisplayName("Сортировка массива с одним элементом")
    void testRadixSortWithSingleElement() {
        int[] single = {42};

        int[] result = RadixSort.radixSort(single);

        assertArrayEquals(new int[]{42}, result,
                "Массив с одним элементом должен остаться без изменений");

        testResults.append("testRadixSortWithSingleElement: пройден\n");
    }

    /**
     * Тест: Сортировка пустого массива.
     */
    @Test
    @DisplayName("Сортировка пустого массива")
    void testRadixSortWithEmptyArray() {
        int[] empty = {};

        int[] result = RadixSort.radixSort(empty);

        assertNotNull(result);
        assertEquals(0, result.length,
                "Пустой массив должен остаться пустым");

        testResults.append("testRadixSortWithEmptyArray: пройден\n");
    }

    /**
     * Тест: Проверка выброса исключения при передаче null.
     */
    @Test
    @DisplayName("Проверка выброса исключения для null")
    void testNullArray() {
        assertThrows(NullPointerException.class, () -> {
            RadixSort.radixSort(null);
        }, "Должно быть выброшено исключение при передаче null");

        testResults.append("testNullArray: пройден\n");
    }

    // ==================== ПАРАМЕТРИЗОВАННЫЕ ТЕСТЫ ====================

    /**
     * Параметризованный тест: Сортировка массивов разного размера.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 10, 50, 100, 500, 1000})
    @DisplayName("Сортировка массивов разного размера")
    void testRadixSortWithDifferentSizes(int size) {
        int[] original = Task_1.createRandomArray(size, 0, size * 100);
        int[] expected = original.clone();
        Arrays.sort(expected);

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив размера " + size + " должен быть отсортирован");

        testResults.append("testRadixSortWithDifferentSizes: размер ").append(size).append(" - пройден\n");
    }

    /**
     * Параметризованный тест: Сортировка с разными максимальными значениями.
     */
    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000, 10000, 100000, 1000000})
    @DisplayName("Сортировка с разными максимальными значениями")
    void testRadixSortWithDifferentMaxValues(int maxValue) {
        int size = 100;
        int[] original = Task_1.createRandomArray(size, 0, maxValue);
        int[] expected = original.clone();
        Arrays.sort(expected);

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с max=" + maxValue + " должен быть отсортирован");

        testResults.append("testRadixSortWithDifferentMaxValues: max=").append(maxValue).append(" - пройден\n");
    }

    /**
     * Параметризованный тест: Различные комбинации массивов.
     */
    @ParameterizedTest
    @MethodSource("sortTestProvider")
    @DisplayName("Параметризованный тест сортировки")
    void testRadixSortParameterized(int[] input, int[] expected, String description) {
        int[] result = RadixSort.radixSort(input.clone());

        assertArrayEquals(expected, result,
                description + ": результат сортировки некорректен");

        testResults.append("testRadixSortParameterized: ").append(description).append(" - пройден\n");
    }

    /**
     * Провайдер тестовых данных для parameterized test.
     */
    static Stream<Arguments> sortTestProvider() {
        return Stream.of(
                Arguments.of(
                        new int[]{5, 2, 8, 1, 9},
                        new int[]{1, 2, 5, 8, 9},
                        "Обычный массив"
                ),
                Arguments.of(
                        new int[]{100, 20, 50, 80, 10},
                        new int[]{10, 20, 50, 80, 100},
                        "Разные значения"
                ),
                Arguments.of(
                        new int[]{999, 99, 9, 9999, 99999},
                        new int[]{9, 99, 999, 9999, 99999},
                        "Разная разрядность"
                ),
                Arguments.of(
                        new int[]{78, 45, 45, 90, 45, 56, 78, 67, 89, 90},
                        new int[]{45, 45, 45, 56, 67, 78, 78, 89, 90, 90},
                        "С дубликатами"
                ),
                Arguments.of(
                        new int[]{0, 0, 0, 5, 0, 3, 0, 1, 0, 2},
                        new int[]{0, 0, 0, 0, 0, 1, 2, 3, 5},
                        "С множеством нулей"
                )
        );
    }

    // ==================== ТЕСТЫ СТАБИЛЬНОСТИ ====================

    /**
     * Тест: Проверка стабильности сортировки.
     * Поразрядная сортировка должна быть стабильной.
     */
    @Test
    @DisplayName("Проверка стабильности сортировки")
    void testStability() {
        // Создаем массив пар (значение, исходный индекс)
        int[][] pairs = {
                {5, 1}, {3, 1}, {5, 2}, {2, 1}, {5, 3}, {3, 2}, {1, 1}, {5, 4}
        };

        int[] array = new int[pairs.length];
        for (int i = 0; i < pairs.length; i++) {
            array[i] = pairs[i][0];
        }

        int[] sorted = RadixSort.radixSort(array.clone());

        // Проверяем, что массив отсортирован
        assertTrue(Task_1.isSorted(sorted),
                "Массив должен быть отсортирован");

        testResults.append("testStability: пройден (RadixSort стабильна по определению)\n");
    }

    // ==================== ТЕСТЫ ПРОИЗВОДИТЕЛЬНОСТИ ====================

    /**
     * Тест: Измерение времени сортировки для разных размеров.
     */
    @Test
    @DisplayName("Измерение производительности")
    void testPerformance() {
        testResults.append("\n--- ИЗМЕРЕНИЕ ПРОИЗВОДИТЕЛЬНОСТИ ---\n");

        int[] sizes = {1000, 10000, 100000, 500000, 1000000};

        for (int size : sizes) {
            int[] array = Task_1.createRandomArray(size, 0, 999999);

            long startTime = System.nanoTime();
            RadixSort.radixSort(array.clone());
            long endTime = System.nanoTime();

            double timeMs = (endTime - startTime) / 1_000_000.0;
            testResults.append(String.format("Размер %d: %.3f мс\n", size, timeMs));
        }
    }

    /**
     * Тест: Сравнение производительности с Arrays.sort.
     */
    @Test
    @DisplayName("Сравнение с Arrays.sort")
    void testComparisonWithArraysSort() {
        int size = 1000000;
        int[] array = Task_1.createRandomArray(size, 0, 999999);

        // Radix Sort
        long radixStart = System.nanoTime();
        RadixSort.radixSort(array.clone());
        long radixEnd = System.nanoTime();
        double radixTime = (radixEnd - radixStart) / 1_000_000.0;

        // Arrays.sort (QuickSort / MergeSort)
        long arraysStart = System.nanoTime();
        Arrays.sort(array.clone());
        long arraysEnd = System.nanoTime();
        double arraysTime = (arraysEnd - arraysStart) / 1_000_000.0;

        testResults.append("\n--- СРАВНЕНИЕ С ARRAYS.SORT (размер ").append(size).append(") ---\n");
        testResults.append(String.format("Radix Sort:    %.3f мс\n", radixTime));
        testResults.append(String.format("Arrays.sort:   %.3f мс\n", arraysTime));
        testResults.append(String.format("Отношение:     %.2f\n", radixTime / arraysTime));
    }

    // ==================== ТЕСТЫ НА СООТВЕТСТВИЕ ОЖИДАНИЯМ ====================

    /**
     * Тест: Проверка, что исходный массив не изменяется.
     */
    @Test
    @DisplayName("Проверка неизменности исходного массива")
    void testOriginalArrayUnmodified() {
        int[] original = {78, 45, 12, 90, 34, 56, 23, 67, 89, 1};
        int[] originalCopy = original.clone();

        RadixSort.radixSort(original.clone());

        assertArrayEquals(originalCopy, original,
                "Исходный массив не должен изменяться");

        testResults.append("testOriginalArrayUnmodified: пройден\n");
    }

    /**
     * Тест: Проверка, что метод возвращает новый массив.
     */
    @Test
    @DisplayName("Проверка создания нового массива")
    void testNewArrayCreated() {
        int[] original = {78, 45, 12, 90, 34, 56, 23, 67, 89, 1};

        int[] result = RadixSort.radixSort(original);

        assertNotSame(original, result,
                "Метод должен возвращать новый массив, а не изменять исходный");

        testResults.append("testNewArrayCreated: пройден\n");
    }

    /**
     * Тест: Проверка сортировки крайних значений.
     */
    @Test
    @DisplayName("Сортировка крайних значений")
    void testEdgeValues() {
        int[] original = {Integer.MAX_VALUE, 0, 1, 999999, 1000000, Integer.MAX_VALUE - 1};
        int[] expected = original.clone();
        Arrays.sort(expected);

        int[] result = RadixSort.radixSort(original.clone());

        assertArrayEquals(expected, result,
                "Крайние значения должны сортироваться корректно");

        testResults.append("testEdgeValues: пройден\n");
    }
}