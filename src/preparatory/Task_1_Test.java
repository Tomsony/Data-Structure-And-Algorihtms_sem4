package preparatory;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Данный класс является тестовым для класса Task_1
 * Содержит комплексное тестирование всех методов класса Task_1:
 * - Генерация массивов (случайные и отсортированные)
 * - Проверка сортировки
 * - Последовательный поиск
 * - Сравнение с точностью
 *
 * @author Томских Артём ИВТ-23
 */
class Task_1_Test {
    // Константы для тестирования - используются во всех тестах для единообразия
    private static final int TEST_SIZE = 1000;  // Стандартный размер тестовых массивов
    private static final int TEST_MIN = 10;      // Минимальное значение для генерации
    private static final int TEST_MAX = 2000;    // Максимальное значение для генерации

    private int[] randomArray;  // Массив со случайными значениями для каждого теста
    private int[] sortedArray;  // Отсортированный массив для каждого теста

    /**
     * Инициализация тестовых данных перед каждым тестом.
     * Создаёт случайный и отсортированный массивы фиксированного размера.
     * Выполняется перед каждым тестом для обеспечения изоляции тестов.
     */
    @BeforeEach
    void setUp() {
        randomArray = Task_1.createRandomArray(TEST_SIZE, TEST_MIN, TEST_MAX);
        sortedArray = Task_1.createSortedArray(TEST_SIZE, TEST_MIN, TEST_MAX);
    }

    /**
     * ТЕСТ: Базовое тестирование метода isSorted
     *
     * Что проверяет:
     * 1. Явно заданный неотсортированный массив должен определяться как false
     * 2. Явно заданный отсортированный массив должен определяться как true
     *
     * Использует ручные тестовые данные для гарантии результата.
     */
    @Test
    @DisplayName("Проверка isSorted на явно заданных массивах")
    void testIsSortedWithExplicitArrays() {
        int[] unsortedArray = {1, 4, 2, 9};
        int[] sortedArray = {1, 2, 3, 4};

        assertFalse(Task_1.isSorted(unsortedArray));
        assertTrue(Task_1.isSorted(sortedArray));
    }

    /**
     * ТЕСТ: Проверка работы isSorted с пустым массивом
     *
     * Что проверяет:
     * Пустой массив должен считаться отсортированным (граничный случай)
     *
     * Пустой массив технически является отсортированным,
     * так как в нём нет пар элементов, нарушающих порядок.
     */
    @Test
    @DisplayName("Проверка isSorted на пустом массиве")
    void testIsSortedWithEmptyArray() {
        int[] emptyArray = {};
        assertTrue(Task_1.isSorted(emptyArray), "Пустой массив считается отсортированным");
    }

    /**
     * ТЕСТ: Проверка работы isSorted с массивом из одного элемента
     *
     * Что проверяет:
     * Массив с одним элементом всегда считается отсортированным
     * (также граничный случай)
     */
    @Test
    @DisplayName("Проверка isSorted на массиве с одним элементом")
    void testIsSortedWithSingleElement() {
        int[] singleElementArray = {42};
        assertTrue(Task_1.isSorted(singleElementArray));
    }

    /**
     * ТЕСТ: Проверка работы isSorted с массивом одинаковых элементов
     *
     * Что проверяет:
     * Массив с равными элементами считается отсортированным,
     * так как условие arr[i] < arr[i-1] никогда не выполняется
     */
    @Test
    @DisplayName("Проверка isSorted на массиве с одинаковыми элементами")
    void testIsSortedWithEqualElements() {
        int[] equalElementsArray = {5, 5, 5, 5};
        assertTrue(Task_1.isSorted(equalElementsArray),
                "Массив с равными элементами считается отсортированным");
    }

    /**
     * ТЕСТ: Проверка работы isSorted с отрицательными числами
     *
     * Что проверяет:
     * 1. Отсортированный массив с отрицательными числами
     * 2. Неотсортированный массив с отрицательными числами
     *
     * Отрицательные числа не должны влиять на логику сортировки
     */
    @Test
    @DisplayName("Проверка isSorted на массиве с отрицательными числами")
    void testIsSortedWithNegativeNumbers() {
        int[] sortedNegatives = {-5, -3, -1, 0, 2};
        int[] unsortedNegatives = {-1, -5, 0, 3};

        assertTrue(Task_1.isSorted(sortedNegatives));
        assertFalse(Task_1.isSorted(unsortedNegatives));
    }

    /**
     * ТЕСТ: Параметризованная проверка отсортированных массивов разного размера
     *
     * Что проверяет:
     * Метод createSortedArray создает отсортированные массивы для разных размеров:
     * - 100 элементов (multiplier = 1)
     * - 200 элементов (multiplier = 2)
     * - 500 элементов (multiplier = 5)
     * - 1000 элементов (multiplier = 10)
     *
     * Параметризованный тест позволяет избежать дублирования кода
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 10})
    @DisplayName("Проверка, что sortedArray отсортирован при разных размерах")
    void testSortedArrayIsSortedForDifferentSizes(int multiplier) {
        int size = 100 * multiplier;
        int[] testArray = Task_1.createSortedArray(size, TEST_MIN, TEST_MAX);
        assertTrue(Task_1.isSorted(testArray));
    }

    /**
     * ТЕСТ: Проверка sequentialSearch при отсутствии элемента
     *
     * Что проверяет:
     * При поиске элемента, которого гарантированно нет в массиве,
     * метод должен вернуть -1
     *
     * Используем Integer.MIN_VALUE, так как это значение заведомо вне диапазона
     */
    @Test
    @DisplayName("Проверка sequentialSearch когда элемент не найден")
    void testSequentialSearchElementNotFound() {
        int result = Task_1.sequentialSearch(
                sortedArray,
                n -> Task_1.compareWithPrecision(n, Integer.MIN_VALUE, 0)
        );
        assertEquals(-1, result, "Должен вернуть -1 для отсутствующего элемента");
    }

    /**
     * ТЕСТ: Проверка sequentialSearch при наличии элемента
     *
     * Что проверяет:
     * При поиске первого элемента массива метод должен вернуть индекс 0 + 1
     *
     * В текущей реализации sequentialSearch возвращает индекс + 1,
     * что нужно учитывать при использовании метода
     */
    @Test
    @DisplayName("Проверка sequentialSearch когда элемент найден")
    void testSequentialSearchElementFound() {
        int expectedIndex = 0; // Первый элемент отсортированного массива
        int result = Task_1.sequentialSearch(
                sortedArray,
                n -> Task_1.compareWithPrecision(n, sortedArray[0], 0)
        );
        assertEquals(expectedIndex + 1, result,
                "Обратите внимание: метод возвращает индекс + 1");
    }

    /**
     * ТЕСТ: Параметризованная проверка метода compareWithPrecision
     *
     * Что проверяет:
     * различные сценарии сравнения с точностью
     * 1. Точное равенство (5 == 5, точность 0) -> true
     * 2. В пределах точности (5 и 6, точность 1) -> true
     * 3. За пределами точности (5 и 7, точность 1) -> false
     * 4. Отрицательные числа (-5 и -5, точность 0) -> true
     * 5. Граничное значение (0 и 0.1, точность 0.1) -> true
     * 6. Чуть больше точности (0 и 0.1, точность 0.09) -> false
     *
     * @param a первое сравниваемое число
     * @param b второе сравниваемое число
     * @param precision точность сравнения
     * @param expected ожидаемый результат
     */
    @ParameterizedTest
    @CsvSource({
            "5, 5, 0, true",     // Точное равенство
            "5, 6, 1, true",      // В пределах точности
            "5, 7, 1, false",     // За пределами точности
            "-5, -5, 0, true",    // Отрицательные числа
            "0, 0.1, 0.1, true",  // Граничное значение
            "0, 0.1, 0.09, false" // Чуть больше точности
    })
    @DisplayName("Параметризованная проверка compareWithPrecision")
    void testCompareWithPrecision(double a, double b, double precision, boolean expected) {
        assertEquals(expected, Task_1.compareWithPrecision(a, b, precision));
    }

    /**
     * ТЕСТ: Проверка, что createRandomArray генерирует числа в заданном диапазоне
     *
     * Что проверяет:
     * Все элементы случайного массива должны находиться в интервале [TEST_MIN, TEST_MAX]
     *
     * Проходим по всем элементам массива и проверяем каждый
     */
    @Test
    @DisplayName("Проверка createRandomArray на попадание в диапазон")
    void testCreateRandomArrayValuesInRange() {
        int[] array = Task_1.createRandomArray(1000, TEST_MIN, TEST_MAX);
        for (int value : array) {
            assertTrue(value >= TEST_MIN && value <= TEST_MAX,
                    String.format("Значение %d вне диапазона [%d, %d]", value, TEST_MIN, TEST_MAX));
        }
    }

    /**
     * ТЕСТ: Проверка монотонности отсортированного массива
     *
     * Что проверяет:
     * Каждый следующий элемент отсортированного массива должен быть
     * больше или равен предыдущему (неубывающая последовательность)
     *
     * Проходим по массиву и проверяем условие arr[i] >= arr[i-1]
     */
    @Test
    @DisplayName("Проверка createSortedArray на монотонность")
    void testCreateSortedArrayIsMonotonic() {
        int[] array = Task_1.createSortedArray(100, 1, 1000);
        for (int i = 1; i < array.length; i++) {
            assertTrue(array[i] >= array[i-1],
                    String.format("Массив не монотонен на индексе %d: %d < %d",
                            i, array[i-1], array[i]));
        }
    }

    /**
     * ТЕСТ: Проверка, что последний элемент не превышает максимальное значение
     *
     * Что проверяет:
     * При генерации отсортированного массива последний элемент
     * должен быть меньше или равен заданному максимуму
     *
     * Это важно, так как алгоритм распределяет приращения так,
     * чтобы не выйти за пределы диапазона
     */
    @Test
    @DisplayName("Проверка createSortedArray: последний элемент не превышает max")
    void testCreateSortedArrayLastElementNotExceedMax() {
        int[] array = Task_1.createSortedArray(100, 1, 1000);
        assertTrue(array[array.length - 1] <= 1000,
                "Последний элемент превышает максимальное значение");
    }

    /**
     * ТЕСТ: Проверка производительности sequentialSearch
     *
     * Что проверяет:
     * Время выполнения sequentialSearch для разных размеров массива
     * Это не столько тест корректности, сколько тест производительности
     *
     * Время должно расти линейно с увеличением размера массива O(n)
     *
     * Примечание: Этот тест может выполняться долго, поэтому может быть
     * помечен @Tag("performance") для отдельного запуска
     */
    @Test
    @DisplayName("Проверка производительности sequentialSearch")
    void testSequentialSearchPerformance() {
        int[] sizes = {100, 1000, 10000, 100000};

        for (int size : sizes) {
            int[] testArray = Task_1.createRandomArray(size, TEST_MIN, TEST_MAX);

            long startTime = System.nanoTime();

            // Выполняем 1000 поисков для усреднения
            for (int i = 0; i < 1000; i++) {
                int randomValue = TEST_MIN + (int)(Math.random() * (TEST_MAX - TEST_MIN));
                Task_1.sequentialSearch(testArray, n -> n == randomValue);
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000; // в миллисекундах

            System.out.printf("Размер %d: %d мс%n", size, duration);
        }
    }
}