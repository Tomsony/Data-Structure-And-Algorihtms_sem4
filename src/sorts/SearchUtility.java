package sorts;

import preparatory.Task_1;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class SearchUtility {

    // Для избежания конфликта типов в лямбда-выражении, создан функциональный интерфейс
    @FunctionalInterface
            // @param array отсортированный массив
            // @param key искомый элемент поиска
    public interface IntArraySearch {
        int search(int[] array, int key);
    }

    /**
     * Универсальный метод для измерения работы бинарного и интерполяционного поиска
     *
     * @param array      отсортированный массив
     * @param min        первый элемент
     * @param max        последний элемент
     * @param iterations количество итераций для увеличения времени работы
     * @param searchFunc ссылка на метод с алгоритмом поиска
     */
    public static long measureSearchTime(int[] array,
                                         int min,
                                         int max,
                                         int iterations,
                                         IntArraySearch searchFunc) {
        return Task_1.measureTime(() -> {
            ThreadLocalRandom random = ThreadLocalRandom.current(); // ThreadLocalRandom работает быстрее Math.random()
            for (int k = 0; k < iterations; k++) {
                int randomValue = random.nextInt(min, max + 1); // Искомый элемент поиска
                searchFunc.search(array, randomValue);
            }
        });
    }

    // Функциональный интерфейс для слияния массивов
    @FunctionalInterface
    public interface MergeFunction {
        int[] merge(int[] left, int[] right);
    }

    /** Метод для измерения слияния двух массивов*/
    public static long measureMergeTime(int[] left,
                                        int[] right,
                                        int iterations,
                                        MergeFunction mergeFunc) {
        return Task_1.measureTime(() -> {
            for (int i = 0; i < iterations; i++) {
                mergeFunc.merge(left, right);
                }
            });
        }

    // Функциональный интерфейс для сортировки вставками
    @FunctionalInterface
    public interface InsertionFunction {
        int [] insertion(int[] sort);
    }

    public static long measureInsSortTime(int[] array, int iterations, InsertionFunction insertionFunction){
        return Task_1.measureTime(() -> {
            for (int i = 0; i < iterations; i++){
                insertionFunction.insertion(array);
            }
        });
    }

    // Функциональный интерфейс для быстрой сортировки
    @FunctionalInterface
    public interface QuickSortFunction {
        int[] quick(int[] arr, int low, int high);
    }

    public static long measureQuickSortTime(int[] array, int low, int high,
                                            int iterations, QuickSortFunction quickSortFunction) {
        int[] copy = Arrays.copyOf(array, array.length); // создали один раз
        return Task_1.measureTime(() -> {
            for (int i = 0; i < iterations; i++) {
                System.arraycopy(copy, 0, array, 0, array.length); // восстановление
                quickSortFunction.quick(array, low, high);
            }
        });
    }

    // Функциональный интерфейс для поразрядной сортировки
    @FunctionalInterface
    public interface RadixSortFunction {
        int[] radix(int[] arr);
    }
    public static long measureRadixSortTime(int[] array, int iterations, RadixSortFunction radixSortFunction) {
        return Task_1.measureTime(() -> {
            for (int i = 0; i < iterations; i++) {
                radixSortFunction.radix(array);
            }
        });
    }
}

