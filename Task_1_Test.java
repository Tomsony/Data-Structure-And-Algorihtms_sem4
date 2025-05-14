import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task_1_Test {
    private static final int TEST_SIZE = 10000; // размер массива
    private static final int TEST_MIN = 10; // минимальное значение
    private static final int TEST_MAX = 1000; // максимальное значение
    private int[] randomArray;
    private int[] sortedArray;

    // Перед началом тестов создадим проверяемые массивы
    @BeforeEach // этот метод выполняется перед каждым тестом в классе
    void setUp() {
        randomArray = Task_1.createRandomArray(TEST_SIZE, TEST_MIN, TEST_MAX); // заполненный случайными числами (через Task_1.createRandomArray)
        sortedArray = Task_1.createSortedArray(TEST_SIZE, TEST_MIN, TEST_MAX); // отсортированный по возрастанию (через Task_1.createSortedArray)
    }

        @Test // помечает метод как тестовый случай
        @DisplayName("Проверка метода isSorted") // задаёт читаемое название теста
        // TestInfo позволяет получить информацию о тесте
        void isSortedTest(TestInfo testInfo) {
            System.out.println("Запущен тест: " + testInfo.getDisplayName());

            int[] unsortedArray = {1,4,2,9};
            int[] sortedArray = {1,2,3,4};

            boolean unsortedResult = Task_1.isSorted(unsortedArray);
            boolean sortedResult = Task_1.isSorted(sortedArray);
            // Вывод результатов
            System.out.println("Результат для неотсортированного массива: " + unsortedResult);
            System.out.println("Результат для отсортированного массива: " + sortedResult);
            // Если isSorted(unsortedArray) вернёт true, тест упадёт с ошибкой
            assertFalse(unsortedResult);
            // Если isSorted(sortedArray) вернёт false, тест упадёт
            assertTrue(sortedResult);
        }
        @Test
        void testRandomArrayNotSorted() {
            assertFalse(Task_1.isSorted(randomArray));
        }

        @Test
        void testSortedArrayIsSorted() {
            assertTrue(Task_1.isSorted(sortedArray));
        }
}


