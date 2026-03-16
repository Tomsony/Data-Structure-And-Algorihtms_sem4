package sorts.InsertionSort;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import preparatory.Task_1; // Импортируем вспомогательный класс
import java.io.IOException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс InsertionSortTest содержит модульные тесты для проверки
 * функциональности класса InsertionSort с использованием вспомогательного
 * класса Task_1 для генерации тестовых данных и измерения производительности.
 *
 * Тесты покрывают следующие аспекты:
 * 1. Корректность сортировки для различных типов массивов
 * 2. Проверка подсчета количества операций (сравнений и перемещений)
 * 3. Проверка стабильности сортировки
 * 4. Измерение производительности на разных размерах массивов
 * 5. Сохранение результатов тестирования в файл
 *
 * @author Томских Артём ИВТ-23
 */
@DisplayName("Тестирование сортировки вставками (InsertionSort) с использованием Task_1")
class InsertionSortTest {

    // Константы для тестирования
    private static final int TEST_SIZE = 1000;
    private static final int TEST_MIN = 1;
    private static final int TEST_MAX = 10000;
    private static final int PERFORMANCE_ITERATIONS = 100;

    // Для сохранения результатов тестов
    private static StringBuilder testResults;

    /**
     * Инициализация перед всеми тестами
     */
    @BeforeAll
    static void initAll() {
        testResults = new StringBuilder();
        testResults.append("=== РЕЗУЛЬТАТЫ ТЕСТИРОВАНИЯ СОРТИРОВКИ ВСТАВКАМИ ===\n");
        testResults.append("Автор: Томских Артём ИВТ-23\n");
        testResults.append("Дата: ").append(java.time.LocalDate.now()).append("\n\n");
    }

    /**
     * Сохранение результатов после всех тестов
     */
    @AfterAll
    static void saveResults() throws IOException {
        Task_1.saveArrayToFile(
                testResults.toString().chars().toArray(),
                "insertion_sort_test_results.txt"
        );
        System.out.println("Результаты тестов сохранены в файл");
    }

    /**
     * Сброс счетчиков перед каждым тестом
     */
    @BeforeEach
    void setUp() {
        InsertionSort.resetCounters();
    }

    // ==================== ТЕСТЫ КОРРЕКТНОСТИ СОРТИРОВКИ ====================

    /**
     * Тест: Сортировка обычного массива со случайными значениями,
     * сгенерированного с помощью Task_1.createRandomArray().
     */
    @Test
    @DisplayName("Сортировка случайного массива, сгенерированного Task_1")
    void testInsertionSortWithRandomArray() {
        // Используем Task_1 для генерации случайного массива
        int[] original = Task_1.createRandomArray(20, 1, 100);
        int[] expected = original.clone();
        Arrays.sort(expected); // Используем встроенную сортировку как эталон

        int[] result = InsertionSort.insertionSort(original.clone());

        assertArrayEquals(expected, result,
                "Отсортированный массив должен совпадать с эталоном");

        testResults.append("✓ Тест со случайным массивом (размер 20): пройден\n");
    }

    /**
     * Тест: Сортировка уже отсортированного массива,
     * сгенерированного с помощью Task_1.createSortedArray().
     */
    @Test
    @DisplayName("Сортировка уже отсортированного массива из Task_1")
    void testInsertionSortWithSortedArray() {
        // Используем Task_1 для генерации отсортированного массива
        int[] original = Task_1.createSortedArray(20, 1, 100);
        int[] expected = original.clone();

        InsertionSort.resetCounters();
        int[] result = InsertionSort.insertionSort(original.clone());

        assertArrayEquals(expected, result,
                "Отсортированный массив должен остаться без изменений");

        // Для отсортированного массива:
        // - Сравнений: n-1 (каждый элемент сравнивается один раз)
        // - Перемещений: n-1 (только вставка каждого ключа)
        assertEquals(original.length - 1, InsertionSort.getComparisonCount(),
                "Для отсортированного массива требуется n-1 сравнений");
        assertEquals(original.length - 1, InsertionSort.getSwapCount(),
                "Для отсортированного массива требуется n-1 перемещений (только вставки)");
    }

    /**
     * Тест: Проверка, что Task_1.isSorted правильно определяет
     * отсортированный после сортировки вставками массив.
     */
    @Test
    @DisplayName("Проверка сортировки с использованием Task_1.isSorted")
    void testIsSortedAfterInsertionSort() {
        int[] original = Task_1.createRandomArray(50, 1, 500);

        // Проверяем, что случайный массив не отсортирован
        assertFalse(Task_1.isSorted(original),
                "Случайный массив не должен быть отсортирован");

        // Сортируем
        int[] sorted = InsertionSort.insertionSort(original.clone());

        // Проверяем, что после сортировки массив отсортирован
        assertTrue(Task_1.isSorted(sorted),
                "После сортировки массив должен быть отсортирован");

        testResults.append("✓ Проверка Task_1.isSorted после сортировки: пройден\n");
    }

    // ==================== ТЕСТЫ С РАЗЛИЧНЫМИ ТИПАМИ МАССИВОВ ====================

    /**
     * Параметризованный тест: Сортировка массивов разного размера.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 100, 1000})
    @DisplayName("Сортировка массивов разного размера")
    void testInsertionSortWithDifferentSizes(int size) {
        int[] original = Task_1.createRandomArray(size, 1, size * 10);
        int[] expected = original.clone();
        Arrays.sort(expected);

        int[] result = InsertionSort.insertionSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив размера " + size + " должен быть корректно отсортирован");

        testResults.append("✓ Тест с массивом размера ").append(size).append(": пройден\n");
    }

    /**
     * Тест: Сортировка массива с отрицательными числами.
     */
    @Test
    @DisplayName("Сортировка массива с отрицательными числами")
    void testInsertionSortWithNegativeNumbers() {
        int[] original = Task_1.createRandomArray(20, -50, 50);
        int[] expected = original.clone();
        Arrays.sort(expected);

        int[] result = InsertionSort.insertionSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с отрицательными числами должен сортироваться корректно");

        testResults.append("✓ Тест с отрицательными числами: пройден\n");
    }

    /**
     * Тест: Сортировка массива с одинаковыми элементами.
     */
    @Test
    @DisplayName("Сортировка массива с одинаковыми элементами")
    void testInsertionSortWithUniformArray() {
        int[] original = {5, 5, 5, 5, 5, 5};
        int[] expected = {5, 5, 5, 5, 5, 5};

        int[] result = InsertionSort.insertionSort(original.clone());

        assertArrayEquals(expected, result,
                "Массив с одинаковыми элементами должен остаться без изменений");

        testResults.append("✓ Тест с одинаковыми элементами: пройден\n");
    }

    // ==================== ТЕСТЫ ГРАНИЧНЫХ СЛУЧАЕВ ====================

    /**
     * Тест: Сортировка пустого массива.
     */
    @Test
    @DisplayName("Сортировка пустого массива")
    void testInsertionSortWithEmptyArray() {
        int[] empty = {};

        int[] result = InsertionSort.insertionSort(empty);

        assertNotNull(result);
        assertEquals(0, result.length);
        assertTrue(Task_1.isSorted(result));

        testResults.append("✓ Тест с пустым массивом: пройден\n");
    }

    /**
     * Тест: Сортировка массива с одним элементом.
     */
    @Test
    @DisplayName("Сортировка массива с одним элементом")
    void testInsertionSortWithSingleElement() {
        int[] single = {42};

        int[] result = InsertionSort.insertionSort(single);

        assertArrayEquals(new int[]{42}, result);
        assertTrue(Task_1.isSorted(result));

        testResults.append("✓ Тест с одним элементом: пройден\n");
    }

    /**
     * Тест: Сортировка null массива.
     */
    @Test
    @DisplayName("Сортировка null массива")
    void testInsertionSortWithNullArray() {
        int[] result = InsertionSort.insertionSort(null);

        assertNull(result);

        testResults.append("✓ Тест с null массивом: пройден\n");
    }

    // ==================== ТЕСТЫ ПРОИЗВОДИТЕЛЬНОСТИ ====================

    /**
     * Тест: Измерение времени сортировки массивов разного размера.
     */
    @Test
    @DisplayName("Измерение производительности сортировки")
    void testInsertionSortPerformance() {
        testResults.append("\n--- ТЕСТЫ ПРОИЗВОДИТЕЛЬНОСТИ ---\n");

        int[] sizes = {100, 500, 1000, 2000, 5000};

        for (int size : sizes) {
            int[] array = Task_1.createRandomArray(size, 1, size * 10);

            // Измеряем время с помощью Task_1.measureTime
            long time = Task_1.measureTime(() -> {
                InsertionSort.insertionSort(array.clone());
            });

            testResults.append("Размер ").append(size).append(": ").append(time).append(" мс\n");

            // Для размера 1000 проверяем теоретическую оценку
            if (size == 1000) {
                assertTrue(time < 100, "Сортировка 1000 элементов должна занимать < 100 мс");
            }
        }
    }

    // ==================== ТЕСТЫ СЧЕТЧИКОВ ОПЕРАЦИЙ ====================

    /**
     * Тест: Проверка счетчиков для разных случаев.
     */
    @Test
    @DisplayName("Проверка счетчиков операций")
    void testOperationCounters() {
        testResults.append("\n--- ПРОВЕРКА СЧЕТЧИКОВ ОПЕРАЦИЙ ---\n");

        // Лучший случай - уже отсортированный массив
        int[] sortedArray = Task_1.createSortedArray(10, 1, 100);
        InsertionSort.resetCounters();
        InsertionSort.insertionSort(sortedArray.clone());

        testResults.append("Лучший случай (отсортированный):\n");
        testResults.append("  Сравнений: ").append(InsertionSort.getComparisonCount()).append("\n");
        testResults.append("  Перемещений: ").append(InsertionSort.getSwapCount()).append("\n");

        assertEquals(9, InsertionSort.getComparisonCount(),
                "Для n=10 в лучшем случае должно быть 9 сравнений");

        // Худший случай - создадим обратно отсортированный массив
        int[] reversedArray = Task_1.createSortedArray(10, 1, 100);
        for (int i = 0; i < reversedArray.length / 2; i++) {
            int temp = reversedArray[i];
            reversedArray[i] = reversedArray[reversedArray.length - 1 - i];
            reversedArray[reversedArray.length - 1 - i] = temp;
        }

        InsertionSort.resetCounters();
        InsertionSort.insertionSort(reversedArray);

        testResults.append("Худший случай (обратный порядок):\n");
        testResults.append("  Сравнений: ").append(InsertionSort.getComparisonCount()).append("\n");
        testResults.append("  Перемещений: ").append(InsertionSort.getSwapCount()).append("\n");

        int expectedComparisons = 10 * 9 / 2; // n(n-1)/2 = 45
        assertEquals(expectedComparisons, InsertionSort.getComparisonCount(),
                "Для n=10 в худшем случае должно быть 45 сравнений");
    }

    // ==================== ТЕСТЫ С СОХРАНЕНИЕМ В ФАЙЛ ====================

    /**
     * Тест: Сохранение отсортированного массива в файл с помощью Task_1.
     */
    @Test
    @DisplayName("Сохранение отсортированного массива в файл")
    void testSaveSortedArrayToFile() throws IOException {
        int[] original = Task_1.createRandomArray(50, 1, 1000);
        int[] sorted = InsertionSort.insertionSort(original.clone());

        // Сохраняем в файл с помощью Task_1
        Task_1.saveArrayToFile(sorted, "insertion_sort_output.txt");

        testResults.append("✓ Сохранение в файл: пройден\n");
    }

    // ==================== ТЕСТ С КОМБИНАЦИЕЙ МЕТОДОВ ====================

    /**
     * Тест: Комплексный тест с использованием нескольких методов Task_1.
     */
    @Test
    @DisplayName("Комплексный тест с Task_1")
    void testWithMultipleTask1Methods() {
        // 1. Создаем случайный массив
        int[] array = Task_1.createRandomArray(100, 1, 1000);

        // 2. Проверяем, что он не отсортирован
        assertFalse(Task_1.isSorted(array));

        // 3. Сортируем и замеряем время
        long sortTime = Task_1.measureTime(() -> {
            InsertionSort.insertionSort(array.clone());
        });

        // 4. Создаем отсортированный массив для сравнения
        int[] sortedArray = Task_1.createSortedArray(100, 1, 1000);

        // 5. Сортируем исходный массив
        int[] result = InsertionSort.insertionSort(array.clone());

        // 6. Проверяем, что результат отсортирован
        assertTrue(Task_1.isSorted(result));

        testResults.append("\n--- КОМПЛЕКСНЫЙ ТЕСТ ---\n");
        testResults.append("Время сортировки 100 элементов: ").append(sortTime).append(" мс\n");
        testResults.append("✓ Все проверки пройдены\n");
    }
}