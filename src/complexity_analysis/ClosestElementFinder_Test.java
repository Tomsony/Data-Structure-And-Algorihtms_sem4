package complexity_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс ClosestElementFinderTest содержит модульные тесты для проверки
 * функциональности класса ClosestElementFinder.
 *
 * Тесты покрывают следующие аспекты:
 * 1. Поиск ближайшего элемента в различных типах массивов:
 *    - Обычные массивы с разными значениями
 *    - Массивы с одним элементом
 *    - Массивы с отрицательными числами
 *    - Массивы с одинаковыми элементами
 * 2. Обработка граничных случаев (пустой массив)
 * 3. Преобразование массива в строку
 * 4. Запись результатов в файл
 *
 * @author Томских Артём ИВТ-23
 */
@DisplayName("Тестирование класса ClosestElementFinder")
class ClosestElementFinderTest {

    // ==================== ТЕСТЫ МЕТОДА findClosestElement ====================

    /**
     * Тест: Поиск ближайшего элемента в обычном массиве с различными значениями.
     *
     * Проверяемые сценарии:
     * - Число 4: ближайший элемент 3 (разница 1, элемент 5 дает разницу 1,
     *   но возвращается первый встреченный)
     * - Число 6: ближайший элемент 5 (разница 1)
     * - Число 0: ближайший элемент 1 (разница 1, все остальные дальше)
     *
     * Массив: [1, 3, 5, 7, 9]
     */
    @Test
    @DisplayName("Поиск ближайшего элемента в обычном массиве")
    void testFindClosestElementWithNormalArray() {
        int[] array = {1, 3, 5, 7, 9};

        assertEquals(3, ClosestElementFinder.findClosestElement(array, 4),
                "Для числа 4 ближайший элемент должен быть 3");
        assertEquals(5, ClosestElementFinder.findClosestElement(array, 6),
                "Для числа 6 ближайший элемент должен быть 5");
        assertEquals(1, ClosestElementFinder.findClosestElement(array, 0),
                "Для числа 0 ближайший элемент должен быть 1");
    }

    /**
     * Тест: Поиск ближайшего элемента в массиве с одним элементом.
     *
     * Проверяемый сценарий:
     * - Даже если искомое число далеко от единственного элемента,
     *   возвращается этот единственный элемент.
     *
     * Массив: [5]
     */
    @Test
    @DisplayName("Поиск ближайшего элемента в массиве из одного элемента")
    void testFindClosestElementWithSingleElement() {
        int[] array = {5};

        assertEquals(5, ClosestElementFinder.findClosestElement(array, 10),
                "В массиве с одним элементом всегда возвращается этот элемент");
    }

    /**
     * Тест: Поиск ближайшего элемента в массиве с отрицательными числами.
     *
     * Проверяемые сценарии:
     * - Число -2: ближайший элемент -3 (разница 1, 0 дает разницу 2)
     * - Число -1: ближайший элемент 0 (разница 1, -3 дает разницу 2)
     *
     * Массив: [-5, -3, 0, 1, 4]
     */
    @Test
    @DisplayName("Поиск ближайшего элемента в массиве с отрицательными числами")
    void testFindClosestElementWithNegativeNumbers() {
        int[] array = {-5, -3, 0, 1, 4};

        assertEquals(-3, ClosestElementFinder.findClosestElement(array, -2),
                "Для числа -2 ближайший элемент должен быть -3");
        assertEquals(0, ClosestElementFinder.findClosestElement(array, -1),
                "Для числа -1 ближайший элемент должен быть 0");
    }

    /**
     * Тест: Поиск ближайшего элемента в массиве с одинаковыми элементами.
     *
     * Проверяемый сценарий:
     * - Все элементы равны, поэтому любой поиск вернет это значение.
     *
     * Массив: [3, 3, 3, 3]
     */
    @Test
    @DisplayName("Поиск ближайшего элемента в массиве с одинаковыми элементами")
    void testFindClosestElementWithEqualElements() {
        int[] array = {3, 3, 3, 3};

        assertEquals(3, ClosestElementFinder.findClosestElement(array, 5),
                "В массиве с одинаковыми элементами всегда возвращается это значение");
    }

    /**
     * Тест: Проверка выброса исключения при передаче пустого массива.
     *
     * Ожидаемое поведение:
     * - Метод должен выбрасывать IllegalArgumentException
     * - Сообщение исключения должно содержать информацию о проблеме
     *
     * Массив: [] (пустой)
     */
    @Test
    @DisplayName("Проверка выброса исключения для пустого массива")
    void testFindClosestElementThrowsExceptionForEmptyArray() {
        int[] emptyArray = {};

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> ClosestElementFinder.findClosestElement(emptyArray, 5),
                "Пустой массив должен вызывать IllegalArgumentException");

        assertTrue(exception.getMessage().contains("пуст"),
                "Сообщение исключения должно указывать на проблему с пустым массивом");
    }


    // ==================== ТЕСТЫ МЕТОДА arrayToString ====================

    /**
     * Тест: Проверка преобразования массива в строку.
     *
     * Проверяемые сценарии:
     * 1. Обычный массив с несколькими элементами
     * 2. Пустой массив
     * 3. Массив с одним элементом
     *
     * Ожидаемый формат: [элемент1, элемент2, ..., элементN]
     */
    @Test
    @DisplayName("Проверка преобразования массива в строку")
    void testArrayToString() {
        // Обычный массив
        int[] array = {1, 2, 3};
        assertEquals("[1, 2, 3]", ClosestElementFinder.arrayToString(array),
                "Массив из трех элементов должен быть отформатирован как [1, 2, 3]");

        // Пустой массив
        int[] emptyArray = {};
        assertEquals("[]", ClosestElementFinder.arrayToString(emptyArray),
                "Пустой массив должен быть представлен как []");

        // Массив с одним элементом
        int[] singleElementArray = {5};
        assertEquals("[5]", ClosestElementFinder.arrayToString(singleElementArray),
                "Массив с одним элементом должен быть представлен как [5]");
    }


    // ==================== ТЕСТЫ МЕТОДА writeResultToFile ====================

    /**
     * Тест: Проверка записи результатов поиска в файл.
     *
     * Использует временную директорию (@TempDir) для создания временного файла,
     * который автоматически удаляется после завершения теста.
     *
     * Проверяемые аспекты:
     * 1. Файл успешно создается
     * 2. Содержимое файла соответствует ожидаемому формату
     * 3. Все данные (массив, x, результат) корректно записаны
     *
     * @param tempDir временная директория, предоставляемая JUnit
     * @throws IOException если возникает ошибка при чтении файла
     */
    @Test
    @DisplayName("Проверка записи результатов в файл")
    void testWriteResultToFile(@TempDir Path tempDir) throws IOException {
        // Создаем путь к временному файлу
        String filename = tempDir.resolve("test_result.txt").toString();

        // Тестовые данные
        int[] array = {1, 3, 5};
        int x = 4;
        int result = 3;

        // Выполняем запись в файл
        ClosestElementFinder.writeResultToFile(filename, array, x, result);

        // Проверяем содержимое файла
        String content = Files.readString(Path.of(filename));

        assertTrue(content.contains("Массив: [1, 3, 5]"),
                "Файл должен содержать строку с массивом");
        assertTrue(content.contains("Число x: 4"),
                "Файл должен содержать строку с искомым числом");
        assertTrue(content.contains("Ближайший элемент: 3"),
                "Файл должен содержать строку с результатом");

        // Дополнительная проверка: убеждаемся, что файл не пустой
        assertFalse(content.isEmpty(), "Файл не должен быть пустым");
    }


    // ==================== ДОПОЛНИТЕЛЬНЫЕ ТЕСТЫ ====================

    /**
     * Тест: Проверка поиска при наличии равноудаленных элементов.
     *
     * Сценарий:
     * - Число 5, массив содержит 3 и 7 (оба на расстоянии 2)
     * - Ожидается возврат первого встреченного (3)
     *
     * Это важно для документирования поведения метода.
     */
    @Test
    @DisplayName("Поиск при наличии равноудаленных элементов")
    void testFindClosestElementWithEquidistantValues() {
        int[] array = {3, 7, 10};

        assertEquals(3, ClosestElementFinder.findClosestElement(array, 5),
                "При равноудаленных элементах должен возвращаться первый встреченный");
    }

    /**
     * Тест: Проверка поиска при точном совпадении с элементом массива.
     *
     * Сценарий:
     * - Искомое число точно равно одному из элементов
     * - Должен вернуться этот элемент (разница 0)
     */
    @Test
    @DisplayName("Поиск при точном совпадении с элементом массива")
    void testFindClosestElementWithExactMatch() {
        int[] array = {1, 2, 3, 4, 5};

        assertEquals(3, ClosestElementFinder.findClosestElement(array, 3),
                "При точном совпадении должен возвращаться соответствующий элемент");
    }
}