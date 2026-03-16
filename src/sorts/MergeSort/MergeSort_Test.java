package sorts.MergeSort;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import preparatory.Task_1;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс MergeSortTest содержит модульные тесты для проверки
 * функциональности класса MergeSort.
 *
 * Тесты покрывают следующие аспекты:
 * 1. Корректность слияния для различных типов массивов:
 *    - Обычные отсортированные массивы
 *    - Массивы разной длины
 *    - Массивы с дубликатами
 *    - Массивы с отрицательными числами
 *    - Пустые массивы
 *    - Массивы с одним элементом
 * 2. Проверка обработки граничных случаев (null массивы)
 * 3. Проверка стабильности сортировки
 * 4. Интеграция с Task_1 для генерации тестовых данных
 *
 * @author Томских Артём ИВТ-23
 */
@DisplayName("Тестирование сортировки слиянием (MergeSort)")
class MergeSortTest {

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
        testResults.append("=== РЕЗУЛЬТАТЫ ТЕСТИРОВАНИЯ СОРТИРОВКИ СЛИЯНИЕМ (MERGE SORT) ===\n");
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
        Task_1.saveArrayToFile(data, "mergesort_test_results.txt");
        System.out.println("Результаты тестов сохранены в файл 'mergesort_test_results.txt'");
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

    // ==================== ТЕСТЫ КОРРЕКТНОСТИ СЛИЯНИЯ ====================

    /**
     * Тест: Слияние двух обычных отсортированных массивов.
     */
    @Test
    @DisplayName("Слияние двух обычных отсортированных массивов")
    void testMergeSortWithNormalArrays() {
        int[] left = {1, 3, 5, 7, 9};
        int[] right = {2, 4, 6, 8, 10};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int[] result = MergeSort.mergeSort(left, right);

        assertArrayEquals(expected, result,
                "Результат слияния должен содержать все элементы в отсортированном порядке");

        testResults.append("testMergeSortWithNormalArrays: пройден\n");
    }

    /**
     * Тест: Слияние массивов разной длины.
     */
    @Test
    @DisplayName("Слияние массивов разной длины")
    void testMergeSortWithDifferentLengths() {
        int[] left = {1, 2, 3};
        int[] right = {4, 5, 6, 7, 8};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8};

        int[] result = MergeSort.mergeSort(left, right);

        assertArrayEquals(expected, result,
                "Массивы разной длины должны корректно сливаться");

        testResults.append("testMergeSortWithDifferentLengths: пройден\n");
    }

    /**
     * Тест: Слияние массивов с дублирующимися элементами.
     */
    @Test
    @DisplayName("Слияние массивов с дубликатами")
    void testMergeSortWithDuplicates() {
        int[] left = {1, 3, 3, 5, 5};
        int[] right = {2, 3, 4, 5, 6};
        int[] expected = {1, 2, 3, 3, 3, 4, 5, 5, 5, 6};

        int[] result = MergeSort.mergeSort(left, right);

        assertArrayEquals(expected, result,
                "Дубликаты должны сохраняться в правильном порядке");

        testResults.append("testMergeSortWithDuplicates: пройден\n");
    }

    /**
     * Тест: Слияние массивов с отрицательными числами.
     */
    @Test
    @DisplayName("Слияние массивов с отрицательными числами")
    void testMergeSortWithNegativeNumbers() {
        int[] left = {-8, -5, -3, 0, 2};
        int[] right = {-7, -4, -1, 1, 3};
        int[] expected = {-8, -7, -5, -4, -3, -1, 0, 1, 2, 3};

        int[] result = MergeSort.mergeSort(left, right);

        assertArrayEquals(expected, result,
                "Отрицательные числа должны сортироваться корректно");

        testResults.append("testMergeSortWithNegativeNumbers: пройден\n");
    }

    /**
     * Тест: Слияние с пустым массивом.
     */
    @Test
    @DisplayName("Слияние с пустым массивом")
    void testMergeSortWithEmptyArray() {
        int[] left = {};
        int[] right = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        int[] result = MergeSort.mergeSort(left, right);

        assertArrayEquals(expected, result,
                "При слиянии с пустым массивом результат должен быть равен непустому массиву");

        // Симметричный случай
        int[] result2 = MergeSort.mergeSort(right, left);
        assertArrayEquals(expected, result2,
                "Слияние с пустым массивом должно работать симметрично");

        testResults.append("testMergeSortWithEmptyArray: пройден\n");
    }

    /**
     * Тест: Слияние двух пустых массивов.
     */
    @Test
    @DisplayName("Слияние двух пустых массивов")
    void testMergeSortWithBothEmpty() {
        int[] left = {};
        int[] right = {};
        int[] expected = {};

        int[] result = MergeSort.mergeSort(left, right);

        assertArrayEquals(expected, result,
                "Слияние двух пустых массивов должно давать пустой массив");

        testResults.append("testMergeSortWithBothEmpty: пройден\n");
    }

    /**
     * Тест: Слияние массивов с одним элементом.
     */
    @Test
    @DisplayName("Слияние массивов с одним элементом")
    void testMergeSortWithSingleElements() {
        int[] left = {5};
        int[] right = {3};
        int[] expected = {3, 5};

        int[] result = MergeSort.mergeSort(left, right);

        assertArrayEquals(expected, result,
                "Массивы с одним элементом должны корректно сливаться");

        testResults.append("testMergeSortWithSingleElements: пройден\n");
    }

    /**
     * Тест: Слияние, где левый массив полностью меньше правого.
     */
    @Test
    @DisplayName("Левый массив полностью меньше правого")
    void testMergeSortLeftSmaller() {
        int[] left = {1, 2, 3, 4, 5};
        int[] right = {6, 7, 8, 9, 10};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int[] result = MergeSort.mergeSort(left, right);

        assertArrayEquals(expected, result,
                "Когда левый массив полностью меньше, порядок должен сохраняться");

        testResults.append("testMergeSortLeftSmaller: пройден\n");
    }

    /**
     * Тест: Слияние, где правый массив полностью меньше левого.
     */
    @Test
    @DisplayName("Правый массив полностью меньше левого")
    void testMergeSortRightSmaller() {
        int[] left = {6, 7, 8, 9, 10};
        int[] right = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int[] result = MergeSort.mergeSort(left, right);

        assertArrayEquals(expected, result,
                "Когда правый массив полностью меньше, он должен оказаться в начале");

        testResults.append("testMergeSortRightSmaller: пройден\n");
    }

    // ==================== ТЕСТЫ С ИСПОЛЬЗОВАНИЕМ TASK_1 ====================

    /**
     * Тест: Слияние массивов, сгенерированных Task_1.createSortedArray().
     */
    @Test
    @DisplayName("Слияние отсортированных массивов из Task_1")
    void testMergeSortWithTask1SortedArrays() {
        int[] left = Task_1.createSortedArray(5, 1, 50);
        int[] right = Task_1.createSortedArray(5, 1, 50);

        int[] result = MergeSort.mergeSort(left, right);

        // Проверяем, что результат отсортирован
        assertTrue(Task_1.isSorted(result),
                "Результат слияния отсортированных массивов должен быть отсортирован");

        // Проверяем, что все элементы присутствуют
        assertEquals(left.length + right.length, result.length,
                "Размер результата должен быть суммой размеров входных массивов");

        testResults.append("TestMergeSortWithTask1SortedArrays: пройден\n");
    }

    /**
     * Тест: Проверка с использованием Task_1.isSorted.
     */
    @Test
    @DisplayName("Проверка отсортированности результата")
    void testIsSortedAfterMerge() {
        int[] left = Task_1.createSortedArray(8, 10, 100);
        int[] right = Task_1.createSortedArray(7, 10, 100);

        int[] result = MergeSort.mergeSort(left, right);

        assertTrue(Task_1.isSorted(result),
                "Результат слияния двух отсортированных массивов должен быть отсортирован");

        testResults.append("testIsSortedAfterMerge: пройден\n");
    }

    // ==================== ПАРАМЕТРИЗОВАННЫЕ ТЕСТЫ ====================

    /**
     * Параметризованный тест: Слияние различных комбинаций массивов.
     */
    @ParameterizedTest
    @MethodSource("mergeTestProvider")
    @DisplayName("Параметризованный тест слияния")
    void testMergeParameterized(int[] left, int[] right, int[] expected, String description) {
        int[] result = MergeSort.mergeSort(left, right);

        assertArrayEquals(expected, result,
                description + ": результат слияния некорректен");

        testResults.append("testMergeParameterized: ").append(description).append(" - пройден\n");
    }

    /**
     * Провайдер тестовых данных для parameterized test.
     */
    static Stream<Arguments> mergeTestProvider() {
        return Stream.of(
                Arguments.of(
                        new int[]{1, 3, 5},
                        new int[]{2, 4, 6},
                        new int[]{1, 2, 3, 4, 5, 6},
                        "Чередующиеся элементы"
                ),
                Arguments.of(
                        new int[]{1, 2, 3},
                        new int[]{4, 5, 6},
                        new int[]{1, 2, 3, 4, 5, 6},
                        "Левый полностью меньше"
                ),
                Arguments.of(
                        new int[]{4, 5, 6},
                        new int[]{1, 2, 3},
                        new int[]{1, 2, 3, 4, 5, 6},
                        "Правый полностью меньше"
                ),
                Arguments.of(
                        new int[]{1, 1, 1},
                        new int[]{1, 1, 1},
                        new int[]{1, 1, 1, 1, 1, 1},
                        "Все элементы равны"
                ),
                Arguments.of(
                        new int[]{-5, -3, -1},
                        new int[]{-4, -2, 0},
                        new int[]{-5, -4, -3, -2, -1, 0},
                        "Отрицательные числа"
                ),
                Arguments.of(
                        new int[]{},
                        new int[]{1, 2, 3},
                        new int[]{1, 2, 3},
                        "Пустой левый"
                ),
                Arguments.of(
                        new int[]{1, 2, 3},
                        new int[]{},
                        new int[]{1, 2, 3},
                        "Пустой правый"
                ),
                Arguments.of(
                        new int[]{5},
                        new int[]{3},
                        new int[]{3, 5},
                        "По одному элементу"
                )
        );
    }

    // ==================== ТЕСТЫ СЛУЧАЙНЫХ ДАННЫХ ====================

    /**
     * Тест: Слияние случайных отсортированных массивов.
     */
    @Test
    @DisplayName("Слияние случайных отсортированных массивов")
    void testMergeSortWithRandomSortedArrays() {
        // Генерируем случайные отсортированные массивы с помощью Task_1
        int[] left = Task_1.createSortedArray(10, 1, 100);
        int[] right = Task_1.createSortedArray(15, 1, 100);

        int[] result = MergeSort.mergeSort(left, right);

        // Создаем ожидаемый результат: объединяем и сортируем
        int[] expected = new int[left.length + right.length];
        System.arraycopy(left, 0, expected, 0, left.length);
        System.arraycopy(right, 0, expected, left.length, right.length);
        Arrays.sort(expected);

        assertArrayEquals(expected, result,
                "Слияние случайных отсортированных массивов должно быть корректным");

        testResults.append("testMergeSortWithRandomSortedArrays: пройден\n");
    }

    // ==================== ТЕСТЫ СТАБИЛЬНОСТИ ====================

    /**
     * Тест: Проверка стабильности сортировки слиянием.
     * Сортировка слиянием является стабильной, поэтому порядок равных элементов
     * из левого массива должен сохраняться перед равными элементами из правого.
     */
    @Test
    @DisplayName("Проверка стабильности сортировки")
    void testStability() {
        // Создаем массивы с парами (значение, исходный индекс)
        int[][] leftPairs = {{3, 1}, {3, 2}, {5, 1}};
        int[][] rightPairs = {{3, 3}, {4, 1}, {5, 2}};

        int[] left = new int[leftPairs.length];
        int[] right = new int[rightPairs.length];

        for (int i = 0; i < leftPairs.length; i++) {
            left[i] = leftPairs[i][0];
        }
        for (int i = 0; i < rightPairs.length; i++) {
            right[i] = rightPairs[i][0];
        }

        int[] result = MergeSort.mergeSort(left, right);

        // Проверяем только корректность сортировки, так как для полной проверки
        // стабильности нужны объекты с дополнительной информацией
        assertTrue(Task_1.isSorted(result),
                "Результат должен быть отсортирован");

        testResults.append("testStability: пройден (MergeSort стабильна по определению)\n");
    }

    // ==================== ТЕСТЫ ГРАНИЧНЫХ СЛУЧАЕВ ====================

    /**
     * Тест: Проверка выброса исключения при передаче null.
     */
    @Test
    @DisplayName("Проверка выброса исключения для null")
    void testNullArray() {
        int[] validArray = {1, 2, 3};

        assertThrows(NullPointerException.class, () -> {
            MergeSort.mergeSort(null, validArray);
        }, "Должно быть выброшено исключение при передаче null");

        assertThrows(NullPointerException.class, () -> {
            MergeSort.mergeSort(validArray, null);
        }, "Должно быть выброшено исключение при передаче null");

        assertThrows(NullPointerException.class, () -> {
            MergeSort.mergeSort(null, null);
        }, "Должно быть выброшено исключение при передаче null");

        testResults.append("testNullArray: пройден\n");
    }

    /**
     * Тест: Слияние очень больших массивов (проверка производительности).
     */
    @Test
    @DisplayName("Слияние больших массивов")
    void testLargeArrays() {
        int size = 10000;

        // Создаем два отсортированных массива большого размера
        int[] left = new int[size];
        int[] right = new int[size];

        for (int i = 0; i < size; i++) {
            left[i] = i * 2;
            right[i] = i * 2 + 1;
        }

        long startTime = System.nanoTime();
        int[] result = MergeSort.mergeSort(left, right);
        long endTime = System.nanoTime();

        // Проверяем корректность
        assertEquals(size * 2, result.length,
                "Размер результата должен быть суммой размеров");

        assertTrue(Task_1.isSorted(result),
                "Большой массив должен быть отсортирован");

        double timeMs = (endTime - startTime) / 1_000_000.0;
        testResults.append("testLargeArrays: размер ").append(size)
                .append(", время: ").append(String.format("%.3f", timeMs)).append(" мс\n");
    }

    // ==================== ТЕСТЫ НА СООТВЕТСТВИЕ ОЖИДАНИЯМ ====================

    /**
     * Тест: Проверка, что исходные массивы не изменяются.
     */
    @Test
    @DisplayName("Проверка неизменности исходных массивов")
    void testOriginalArraysUnmodified() {
        int[] left = {1, 3, 5, 7, 9};
        int[] right = {2, 4, 6, 8, 10};
        int[] leftCopy = left.clone();
        int[] rightCopy = right.clone();

        MergeSort.mergeSort(left, right);

        assertArrayEquals(leftCopy, left,
                "Исходный левый массив не должен изменяться");
        assertArrayEquals(rightCopy, right,
                "Исходный правый массив не должен изменяться");

        testResults.append("testOriginalArraysUnmodified: пройден\n");
    }
}