package preparatory;

import java.util.Random;
import java.util.Arrays;

/**
 * Класс Task_1_Report демонстрирует работу бинарного поиска на отсортированном массиве.
 *
 * Цель: проиллюстрировать процесс бинарного поиска с пошаговым выводом информации
 * о состоянии границ поиска и количестве итераций.
 *
 * Для воспроизводимости результатов используется генератор случайных чисел
 * с фиксированным начальным значением (seed = 21 * 42).
 *
 * @author Томских Артём ИВТ-23
 */
public class Task_1_Report {
    // Генератор случайных чисел с фиксированным зерном для воспроизводимости
    private static final Random random = new Random(21 * 42);

    public static void main(String[] args) {
        // === Этап 1: определение размера массива ===
        int size = 10 + random.nextInt(11); // случайное число от 10 до 20 включительно
        System.out.println("Размер массива: " + size);

        // === Этап 2: создание отсортированного массива с числами от 0 до 30 ===
        // Используем готовый метод из Task_1, гарантирующий монотонное возрастание
        int[] sortedArray = Task_1.createSortedArray(size, 0, 30);
        System.out.println("Отсортированный массив: " + Arrays.toString(sortedArray));

        // === Этап 3: генерация искомого числа===
        int target = random.nextInt(31); // случайное число от 0 до 30
        System.out.println("Ищем число: " + target);

        // === Этап 4: выполнение бинарного поиска с детальным логом ===
        int result = binarySearchWithLog(sortedArray, target);
        if (result >= 0) {
            System.out.println("Найдено на позиции: " + result);
        } else {
            System.out.println("Число не найдено");
        }
    }

    /**
     * Выполняет бинарный поиск элемента в отсортированном массиве
     * с выводом информации о каждом шаге.
     *
     * Алгоритм:
     * 1. Устанавливаем левую (left) и правую (right) границы поиска.
     * 2. Пока left <= right:
     *    - Вычисляем средний индекс mid.
     *    - Если array[mid] равен искомому, возвращаем mid.
     *    - Если array[mid] меньше искомого, сдвигаем left = mid + 1.
     *    - Иначе сдвигаем right = mid - 1.
     * 3. Если элемент не найден, возвращаем -1.
     *
     * На каждой итерации выводятся значения left, right, mid и соответствующие
     * элементы массива, а также направление сдвига границ.
     *
     * @param array  отсортированный массив целых чисел
     * @param target значение, которое необходимо найти
     *
     * @return индекс найденного элемента, либо -1, если элемент отсутствует
     */
    public static int binarySearchWithLog(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int iteration = 1;

        while (left <= right) {
            int mid = left + (right - left) / 2; // предотвращает возможное переполнение
            System.out.println("Итерация " + iteration++ + ":");
            System.out.println("  left = " + left + " (" + array[left] + ")");
            System.out.println("  right = " + right + " (" + array[right] + ")");
            System.out.println("  mid = " + mid + " (" + array[mid] + ")");

            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                left = mid + 1;
                System.out.println("  Искомое число больше, сдвигаем left -> " + left);
            } else {
                right = mid - 1;
                System.out.println("  Искомое число меньше, сдвигаем right -> " + right);
            }
        }
        return -1;
    }
}