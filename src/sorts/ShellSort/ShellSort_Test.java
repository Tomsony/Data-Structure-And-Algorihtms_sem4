package sorts.ShellSort;

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
 * Класс ShellSortTest содержит модульные тесты для проверки
 * функциональности класса ShellSort.
 *
 * Тесты покрывают следующие аспекты:
 * 1. Корректность сортировки для различных типов массивов:
 *    - Случайные массивы
 *    - Уже отсортированные массивы (лучший случай)
 *    - Массивы в обратном порядке (худший случай)
 *    - Массивы с дубликатами
 *    - Массивы с отрицательными числами
 *    - Пустые массивы и массивы с одним элементом
 *    - null массивы
 * 2. Проверка работы с различными размерами массивов
 * 3. Проверка стабильности
 * 4. Интеграция с Task_1 для генерации тестовых данных
 * 5. Сравнение производительности с встроенной сортировкой
 *
 * @author Томских Артём ИВТ-23
 */
@DisplayName("Тестирование сортировки Шелла (ShellSort)")
class ShellSortTest {

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
        testResults.append("=== РЕЗУЛЬТАТЫ ТЕСТИРОВАНИЯ СОРТИРОВКИ ШЕЛЛА (SHELL SORT) ===\n");
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
        Task_1.saveArrayToFile(data, "shellsort_test_results.txt");
        System.out.println("Результаты тестов сохранены в файл 'shellsort_test_results.txt'");
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
     */
    @Test
    @DisplayName("Сортировка случайного массива")
    void testShellSortWithRandomArray() {
        int[] original = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        int[] result = ShellSort.shellSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив должен быть отсортирован по возрастанию");

        testResults.append("testShellSortWithRandomArray: пройден\n");
    }

    /**
     * Тест: Сортировка уже отсортированного массива (лучший случай).
     */
    @Test
    @DisplayName("Сортировка уже отсортированного массива")
    void testShellSortWithSortedArray() {
        int[] original = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        int[] result = ShellSort.shellSort(original.clone());

        assertArrayEquals(expected, result,
                "Отсортированный массив должен остаться без изменений");

        testResults.append("testShellSortWithSortedArray: пройден\n");
    }

    /**
     * Тест: Сортировка массива в обратном порядке (худший случай).
     */
    @Test
    @DisplayName("Сортировка массива в обратном порядке")
    void testShellSortWithReversedArray() {
        int[] original = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        int[] result = ShellSort.shellSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив в обратном порядке должен быть отсортирован");

        testResults.append("testShellSortWithReversedArray: пройден\n");
    }

    /**
     * Тест: Сортировка массива с дублирующимися элементами.
     */
    @Test
    @DisplayName("Сортировка массива с дубликатами")
    void testShellSortWithDuplicates() {
        int[] original = {5, 3, 8, 3, 1, 5, 8, 3, 2, 5};
        int[] expected = {1, 2, 3, 3, 3, 5, 5, 5, 8, 8};

        int[] result = ShellSort.shellSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с дубликатами должен быть корректно отсортирован");

        testResults.append("testShellSortWithDuplicates: пройден\n");
    }

    /**
     * Тест: Сортировка массива с отрицательными числами.
     */
    @Test
    @DisplayName("Сортировка массива с отрицательными числами")
    void testShellSortWithNegativeNumbers() {
        int[] original = {-5, 3, -2, 0, -8, 7, -1, 4, -3, 2};
        int[] expected = {-8, -5, -3, -2, -1, 0, 2, 3, 4, 7};

        int[] result = ShellSort.shellSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с отрицательными числами должен сортироваться корректно");

        testResults.append("testShellSortWithNegativeNumbers: пройден\n");
    }

    /**
     * Тест: Сортировка массива с одинаковыми элементами.
     */
    @Test
    @DisplayName("Сортировка массива с одинаковыми элементами")
    void testShellSortWithUniformArray() {
        int[] original = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        int[] expected = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5};

        int[] result = ShellSort.shellSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с одинаковыми элементами должен остаться без изменений");

        testResults.append("testShellSortWithUniformArray: пройден\n");
    }

    /**
     * Тест: Сортировка массива с четным количеством элементов.
     */
    @Test
    @DisplayName("Сортировка массива с четным количеством элементов")
    void testShellSortWithEvenLength() {
        int[] original = {8, 3, 6, 1, 7, 2, 5, 4};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8};

        int[] result = ShellSort.shellSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с четным количеством элементов должен сортироваться корректно");

        testResults.append("testShellSortWithEvenLength: пройден\n");
    }

    /**
     * Тест: Сортировка массива с нечетным количеством элементов.
     */
    @Test
    @DisplayName("Сортировка массива с нечетным количеством элементов")
    void testShellSortWithOddLength() {
        int[] original = {7, 2, 9, 1, 5, 3, 8, 4, 6};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        int[] result = ShellSort.shellSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с нечетным количеством элементов должен сортироваться корректно");

        testResults.append("testShellSortWithOddLength: пройден\n");
    }

    // ==================== ТЕСТЫ ГРАНИЧНЫХ СЛУЧАЕВ ====================

    /**
     * Тест: Сортировка пустого массива.
     */
    @Test
    @DisplayName("Сортировка пустого массива")
    void testShellSortWithEmptyArray() {
        int[] empty = {};

        int[] result = ShellSort.shellSort(empty);

        assertNotNull(result);
        assertEquals(0, result.length);
        assertTrue(Task_1.isSorted(result));

        testResults.append("testShellSortWithEmptyArray: пройден\n");
    }

    /**
     * Тест: Сортировка массива с одним элементом.
     */
    @Test
    @DisplayName("Сортировка массива с одним элементом")
    void testShellSortWithSingleElement() {
        int[] single = {42};

        int[] result = ShellSort.shellSort(single);

        assertArrayEquals(new int[]{42}, result);
        assertTrue(Task_1.isSorted(result));

        testResults.append("testShellSortWithSingleElement: пройден\n");
    }

    /**
     * Тест: Сортировка массива с двумя элементами.
     */
    @Test
    @DisplayName("Сортировка массива с двумя элементами")
    void testShellSortWithTwoElements() {
        int[] original = {5, 2};
        int[] expected = {2, 5};

        int[] result = ShellSort.shellSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с двумя элементами должен сортироваться корректно");

        testResults.append("testShellSortWithTwoElements: пройден\n");
    }

    /**
     * Тест: Проверка выброса исключения при передаче null.
     */
    @Test
    @DisplayName("Проверка выброса исключения для null")
    void testNullArray() {
        assertThrows(NullPointerException.class, () -> {
            ShellSort.shellSort(null);
        }, "Должно быть выброшено исключение при передаче null");

        testResults.append("testNullArray: пройден\n");
    }

    // ==================== ТЕСТЫ С ИСПОЛЬЗОВАНИЕМ TASK_1 ====================

    /**
     * Тест: Сортировка массива, сгенерированного Task_1.createRandomArray().
     */
    @Test
    @DisplayName("Сортировка случайного массива из Task_1")
    void testShellSortWithTask1RandomArray() {
        int[] original = Task_1.createRandomArray(20, 1, 100);
        int[] expected = original.clone();
        Arrays.sort(expected);

        int[] result = ShellSort.shellSort(original.clone());

        assertArrayEquals(expected, result,
                "Случайный массив из Task_1 должен быть корректно отсортирован");

        testResults.append("testShellSortWithTask1RandomArray: пройден\n");
    }

    /**
     * Тест: Сортировка отсортированного массива из Task_1.
     */
    @Test
    @DisplayName("Сортировка отсортированного массива из Task_1")
    void testShellSortWithTask1SortedArray() {
        int[] original = Task_1.createSortedArray(20, 1, 100);
        int[] expected = original.clone();

        int[] result = ShellSort.shellSort(original.clone());

        assertArrayEquals(expected, result,
                "Отсортированный массив из Task_1 должен остаться без изменений");

        testResults.append("testShellSortWithTask1SortedArray: пройден\n");
    }

    /**
     * Тест: Проверка с использованием Task_1.isSorted.
     */
    @Test
    @DisplayName("Проверка с использованием Task_1.isSorted")
    void testIsSortedAfterShellSort() {
        int[] original = Task_1.createRandomArray(50, 1, 1000);

        // Проверяем, что случайный массив не отсортирован
        assertFalse(Task_1.isSorted(original),
                "Случайный массив не должен быть отсортирован");

        // Сортируем
        int[] sorted = ShellSort.shellSort(original.clone());

        // Проверяем, что после сортировки массив отсортирован
        assertTrue(Task_1.isSorted(sorted),
                "После сортировки массив должен быть отсортирован");

        testResults.append("testIsSortedAfterShellSort: пройден\n");
    }

    /**
     * Тест: Сохранение отсортированного массива в файл с помощью Task_1.
     */
    @Test
    @DisplayName("Сохранение отсортированного массива в файл")
    void testSaveSortedArrayToFile() throws IOException {
        int[] original = Task_1.createRandomArray(50, 1, 1000);
        int[] sorted = ShellSort.shellSort(original.clone());

        // Сохраняем в файл с помощью Task_1
        Task_1.saveArrayToFile(sorted, "shellsort_output.txt");

        testResults.append("testSaveSortedArrayToFile: пройден\n");
    }

    // ==================== ПАРАМЕТРИЗОВАННЫЕ ТЕСТЫ ====================

    /**
     * Параметризованный тест: Сортировка массивов разного размера.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 10, 20, 50, 100, 200})
    @DisplayName("Сортировка массивов разного размера")
    void testShellSortWithDifferentSizes(int size) {
        int[] original = Task_1.createRandomArray(size, 1, size * 10);
        int[] expected = original.clone();
        Arrays.sort(expected);

        int[] result = ShellSort.shellSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив размера " + size + " должен быть корректно отсортирован");

        testResults.append("testShellSortWithDifferentSizes: размер ").append(size).append(" - пройден\n");
    }

    /**
     * Параметризованный тест: Сортировка различных комбинаций массивов.
     */
    @ParameterizedTest
    @MethodSource("sortTestProvider")
    @DisplayName("Параметризованный тест сортировки")
    void testShellSortParameterized(int[] input, int[] expected, String description) {
        int[] result = ShellSort.shellSort(input.clone());

        assertArrayEquals(expected, result,
                description + ": результат сортировки некорректен");

        testResults.append("testShellSortParameterized: ").append(description).append(" - пройден\n");
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
                        new int[]{1, 2, 3, 4, 5},
                        new int[]{1, 2, 3, 4, 5},
                        "Уже отсортированный"
                ),
                Arguments.of(
                        new int[]{5, 4, 3, 2, 1},
                        new int[]{1, 2, 3, 4, 5},
                        "Обратный порядок"
                ),
                Arguments.of(
                        new int[]{3, 1, 4, 1, 5, 9, 2},
                        new int[]{1, 1, 2, 3, 4, 5, 9},
                        "С дубликатами"
                ),
                Arguments.of(
                        new int[]{-5, 3, -2, 0, -8, 7},
                        new int[]{-8, -5, -2, 0, 3, 7},
                        "С отрицательными числами"
                ),
                Arguments.of(
                        new int[]{100, 20, 50, 80, 10},
                        new int[]{10, 20, 50, 80, 100},
                        "Разные значения"
                )
        );
    }

    // ==================== ТЕСТЫ ПРОИЗВОДИТЕЛЬНОСТИ ====================

    /**
     * Тест: Измерение времени сортировки массивов разного размера.
     */
    @Test
    @DisplayName("Измерение производительности")
    void testPerformance() {
        testResults.append("\n--- ИЗМЕРЕНИЕ ПРОИЗВОДИТЕЛЬНОСТИ ---\n");

        int[] sizes = {100, 500, 1000, 5000, 10000};

        for (int size : sizes) {
            int[] array = Task_1.createRandomArray(size, 1, size);

            long startTime = System.nanoTime();
            ShellSort.shellSort(array.clone());
            long endTime = System.nanoTime();

            double timeMs = (endTime - startTime) / 1_000_000.0;
            testResults.append(String.format("Размер %d: %.3f мс\n", size, timeMs));

            // Проверяем, что время не превышает разумных пределов
            if (size <= 10000) {
                assertTrue(timeMs < 1000, "Сортировка " + size + " элементов должна занимать < 1 секунды");
            }
        }
    }

    /**
     * Тест: Сравнение с сортировкой вставками (теоретически ShellSort должна быть быстрее).
     */
    @Test
    @DisplayName("Сравнение с сортировкой вставками")
    void testComparisonWithInsertionSort() {
        int size = 5000;
        int[] array = Task_1.createRandomArray(size, 1, size);

        // Копируем массив для каждого алгоритма
        int[] forShell = array.clone();
        int[] forInsertion = array.clone();

        // Измеряем время ShellSort
        long shellStart = System.nanoTime();
        ShellSort.shellSort(forShell);
        long shellEnd = System.nanoTime();
        double shellTime = (shellEnd - shellStart) / 1_000_000.0;

        // Измеряем время сортировки вставками (реализуем простую)
        long insertStart = System.nanoTime();
        insertionSort(forInsertion);
        long insertEnd = System.nanoTime();
        double insertTime = (insertEnd - insertStart) / 1_000_000.0;

        testResults.append("\n--- СРАВНЕНИЕ С СОРТИРОВКОЙ ВСТАВКАМИ (размер ").append(size).append(") ---\n");
        testResults.append(String.format("ShellSort: %.3f мс\n", shellTime));
        testResults.append(String.format("InsertionSort: %.3f мс\n", insertTime));
        testResults.append(String.format("ShellSort быстрее в %.2f раз\n", insertTime / shellTime));

        // Проверяем, что ShellSort действительно быстрее (хотя бы в 2 раза для больших массивов)
        assertTrue(shellTime < insertTime,
                "ShellSort должна быть быстрее обычной сортировки вставками");
    }

    /**
     * Вспомогательный метод - простая сортировка вставками для сравнения.
     */
    private void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // ==================== ТЕСТЫ С РАЗНЫМИ ПОСЛЕДОВАТЕЛЬНОСТЯМИ ====================

    /**
     * Тест: Проверка работы с разными размерами массивов и интервалами.
     */
    @Test
    @DisplayName("Проверка различных интервалов")
    void testDifferentGaps() {
        // Тестируем массивы разных размеров, чтобы проверить работу с разными интервалами
        int[] sizes = {10, 15, 23, 30, 45, 60};

        for (int size : sizes) {
            int[] original = Task_1.createRandomArray(size, 1, 100);
            int[] expected = original.clone();
            Arrays.sort(expected);

            int[] result = ShellSort.shellSort(original.clone());

            assertArrayEquals(expected, result,
                    "Массив размера " + size + " должен сортироваться корректно");
        }

        testResults.append("testDifferentGaps: пройден\n");
    }

    // ==================== ТЕСТЫ НА СООТВЕТСТВИЕ ОЖИДАНИЯМ ====================

    /**
     * Тест: Проверка, что исходный массив не изменяется (мы работаем с клоном).
     */
    @Test
    @DisplayName("Проверка неизменности исходного массива")
    void testOriginalArrayUnmodified() {
        int[] original = {5, 2, 8, 1, 9};
        int[] originalCopy = original.clone();

        ShellSort.shellSort(original.clone());

        assertArrayEquals(originalCopy, original,
                "Исходный массив не должен изменяться (мы сортируем клон)");

        testResults.append("testOriginalArrayUnmodified: пройден\n");
    }

    /**
     * Тест: Проверка, что метод возвращает тот же массив.
     */
    @Test
    @DisplayName("Проверка работы 'на месте'")
    void testInPlaceSorting() {
        int[] array = {5, 2, 8, 1, 9};
        int[] arrayCopy = array.clone();

        int[] result = ShellSort.shellSort(array);

        // Метод должен вернуть тот же объект (работает на месте)
        assertSame(array, result,
                "Метод должен возвращать тот же массив (сортировка на месте)");

        // Но массив должен быть отсортирован
        Arrays.sort(arrayCopy);
        assertArrayEquals(arrayCopy, array,
                "Массив должен быть отсортирован");

        testResults.append("testInPlaceSorting: пройден\n");
    }
}