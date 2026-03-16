package sorts.RadixSort;

import preparatory.Task_1;
import sorts.SearchUtility;

import java.util.Arrays;

/// O(n * k) - стандартная сложность алгоритма поразрядной сортировки по возрастанию, k - количество разрядов
public class RadixSort {

    /**
     * Реализация поразрядной сортировки по возрастанию для целых чисел
     *
     * @param arr массив для сортировки
     * @return отсортированный массив
     */
    public static int[] radixSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return arr;  // Возвращаем массив как есть
        }

        // Находим максимальное число для определения количества разрядов
        int max = Arrays.stream(arr).max().getAsInt();

        // Применяем сортировку подсчетом для каждого разряда
        for (int exp = 1; max / exp > 0; exp *= 10) {
            arr = countingSortByDigit(arr, exp);  // Обновляем массив после каждой итерации
        }

        return arr;
    }

    /**
     * Вспомогательная функция для сортировки подсчетом по возрастанию по определенному разряду
     *
     * @param arr массив для сортировки
     * @param exp текущий разряд (1 для единиц, 10 для десятков и т.д.)
     * @return частично отсортированный массив
     */
    private static int[] countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n]; // выходной массив
        int[] count = new int[10]; // счетчики для цифр 0-9

        // Инициализируем счетчики
        Arrays.fill(count, 0);

        // Считаем количество вхождений каждой цифры в текущем разряде
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }

        // Изменяем count[i] так, чтобы он содержал фактическую позицию цифры в output
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Строим выходной массив
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        return output;  // Возвращаем новый отсортированный массив
    }


    public static void main(String[] args) {
        int size = 2000000;
        int iterations = 1000;
        int max = 999_999;

        // Создаем случайный массив
        int[] arrRand1 = Task_1.createSortedArray(size,0,max);

        System.out.println("Выполняется поразрядная сортировка: ");

        // Замеряем время
        long duration = SearchUtility.measureRadixSortTime(arrRand1,iterations, (arr) -> radixSort(arr));

        System.out.println("Время выполнения: " + duration + " мс");
    }
}
