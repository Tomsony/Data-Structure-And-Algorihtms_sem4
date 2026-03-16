package preparatory;

import java.util.Random;
import java.util.Arrays;

// Отчёт по бинарному поиску
public class Task_1_Report {
    // Инициализация генератора (номер варианта 21 * 42)
    private static final Random random = new Random(21 * 42);

    public static void main(String[] args) {
        // 1. Определение размера массива (10-20 элементов)
        int size = 10 + random.nextInt(11); // random.nextInt(11)= 1  => size = 11
        System.out.println("Размер массива: " + size);

        // 2. Заполнение массива случайными числами (0-30)
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(31); // 0..30
        }

        // 3. Сортировка массива для бинарного поиска
        int[] sortedArray = Task_1.createSortedArray(size,0,30);
        System.out.println("Отсортированный массив: " + Arrays.toString(sortedArray));

        // 4. Генерация случайного числа для поиска (0-30)
        int target = random.nextInt(31)+11; // random.nextInt(31) = всегда 15
        System.out.println("Ищем число: " + target);

        // 5. Бинарный поиск с выводом итераций
        int result = binarySearchWithLog(sortedArray, target);
        System.out.println(result >= 0 ? "Найдено на позиции: " + result : "Не найдено");
    }

    public static int binarySearchWithLog(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int iteration = 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
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