package sorts;

import preparatory.Task_1;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс SearchUtility предоставляет утилиты для измерения производительности
 * различных алгоритмов поиска и сортировки.
 *
 * Основные возможности:
 * 1. Измерение времени выполнения алгоритмов поиска (бинарный, интерполяционный)
 * 2. Измерение времени выполнения алгоритмов сортировки (вставками, быстрая, поразрядная)
 * 3. Измерение времени слияния массивов
 *
 * Для каждого типа алгоритма определен соответствующий функциональный интерфейс,
 * что позволяет передавать ссылки на методы и использовать лямбда-выражения.
 *
 * Все методы измерения используют Task_1.measureTime() для замера времени
 * и выполняют множественные итерации для получения статистически значимых результатов.
 *
 * @author Томских Артём ИВТ-23
 * @version 1.0
 * @see preparatory.Task_1
 */
public class SearchUtility {

    // ==================== ФУНКЦИОНАЛЬНЫЕ ИНТЕРФЕЙСЫ ====================

    @FunctionalInterface
    /**
     * Функциональный интерфейс для алгоритмов поиска в отсортированном массиве.
     * Позволяет передавать ссылки на методы бинарного и интерполяционного поиска.
     *
     * @param array отсортированный массив для поиска
     * @param key   искомый элемент
     * @return индекс найденного элемента или -1, если элемент не найден
     */
    public interface IntArraySearch {
        int search(int[] array, int key);
    }


    @FunctionalInterface
    /**
     * Функциональный интерфейс для алгоритмов слияния двух массивов.
     * Используется для измерения производительности слияния в сортировке слиянием.
     *
     * @param left  первый отсортированный массив
     * @param right второй отсортированный массив
     * @return результат слияния (новый отсортированный массив)
     */
    public interface MergeFunction {
        int[] merge(int[] left, int[] right);
    }


    @FunctionalInterface
    /**
     * Функциональный интерфейс для алгоритмов сортировки вставками.
     *
     * @param array массив для сортировки
     * @return отсортированный массив
     */
    public interface InsertionFunction {
        int[] insertion(int[] array);
    }


    @FunctionalInterface
    /**
     * Функциональный интерфейс для алгоритмов быстрой сортировки.
     *
     * @param arr массив для сортировки
     * @param low левая граница сортировки (включительно)
     * @param high правая граница сортировки (включительно)
     * @return отсортированный массив
     */
    public interface QuickSortFunction {
        int[] quick(int[] arr, int low, int high);
    }


    @FunctionalInterface
    /**
     * Функциональный интерфейс для алгоритмов поразрядной сортировки (Radix Sort).
     *
     * @param arr массив для сортировки
     * @return отсортированный массив
     */
    public interface RadixSortFunction {
        int[] radix(int[] arr);
    }


    // ==================== МЕТОДЫ ИЗМЕРЕНИЯ ====================

    /**
     * Универсальный метод для измерения времени выполнения алгоритмов поиска
     * (бинарного, интерполяционного и др.).
     *
     * Алгоритм работы:
     * 1. Генерируется случайное значение в заданном диапазоне для каждого поиска
     * 2. Выполняется поиск с использованием переданного алгоритма
     * 3. Процесс повторяется указанное количество итераций
     * 4. Измеряется общее время выполнения всех итераций
     *
     * Преимущества использования ThreadLocalRandom:
     * - Более высокая производительность в многопоточной среде
     * - Не требует создания нового экземпляра Random
     *
     * @param array      отсортированный массив, в котором выполняется поиск
     * @param min        минимальное значение для генерации случайных чисел (включительно)
     * @param max        максимальное значение для генерации случайных чисел (включительно)
     * @param iterations количество итераций поиска для усреднения результатов
     * @param searchFunc ссылка на метод с алгоритмом поиска (IntArraySearch)
     * @return общее время выполнения всех итераций поиска в миллисекундах
     *
     * @throws NullPointerException если array или searchFunc равен null
     */
    public static long measureSearchTime(int[] array,
                                         int min,
                                         int max,
                                         int iterations,
                                         IntArraySearch searchFunc) {
        // Проверка входных параметров
        if (array == null || searchFunc == null) {
            throw new NullPointerException("Массив и функция поиска не могут быть null");
        }

        return Task_1.measureTime(() -> {
            ThreadLocalRandom random = ThreadLocalRandom.current(); // ThreadLocalRandom работает быстрее Math.random()
            for (int k = 0; k < iterations; k++) {
                // Генерируем случайное значение в заданном диапазоне
                int randomValue = random.nextInt(min, max + 1);
                // Выполняем поиск (результат нас не интересует, только время)
                searchFunc.search(array, randomValue);
            }
        });
    }

    /**
     * Измеряет время выполнения алгоритмов слияния двух массивов.
     *
     * Алгоритм работы:
     * 1. Выполняется слияние двух массивов с использованием переданного алгоритма
     * 2. Процесс повторяется указанное количество итераций
     * 3. Измеряется общее время выполнения всех итераций
     *
     * Используется для оценки производительности различных реализаций слияния,
     * например, в сортировке слиянием (Merge Sort).
     *
     * @param left       первый массив для слияния
     * @param right      второй массив для слияния
     * @param iterations количество итераций слияния
     * @param mergeFunc  ссылка на метод с алгоритмом слияния
     * @return общее время выполнения всех итераций слияния в миллисекундах
     *
     * @throws NullPointerException если left, right или mergeFunc равен null
     */
    public static long measureMergeTime(int[] left,
                                        int[] right,
                                        int iterations,
                                        MergeFunction mergeFunc) {
        // Проверка входных параметров
        if (left == null || right == null || mergeFunc == null) {
            throw new NullPointerException("Массивы и функция слияния не могут быть null");
        }

        return Task_1.measureTime(() -> {
            for (int i = 0; i < iterations; i++) {
                // Выполняем слияние (результат нас не интересует, только время)
                mergeFunc.merge(left, right);
            }
        });
    }

    /**
     * Измеряет время выполнения сортировки вставками.
     *
     * ВНИМАНИЕ: Этот метод изменяет исходный массив!
     * Если необходимо сохранить исходный массив, передавайте его клон.
     *
     * @param array             массив для сортировки
     * @param iterations        количество итераций сортировки
     * @param insertionFunction ссылка на метод сортировки вставками
     * @return общее время выполнения всех итераций сортировки в миллисекундах
     *
     * @throws NullPointerException если array или insertionFunction равен null
     */
    public static long measureInsSortTime(int[] array,
                                          int iterations,
                                          InsertionFunction insertionFunction) {
        // Проверка входных параметров
        if (array == null || insertionFunction == null) {
            throw new NullPointerException("Массив и функция сортировки не могут быть null");
        }

        return Task_1.measureTime(() -> {
            for (int i = 0; i < iterations; i++) {
                // Выполняем сортировку вставками
                insertionFunction.insertion(array);
            }
        });
    }

    /**
     * Измеряет время выполнения быстрой сортировки с восстановлением исходного массива.
     *
     * Особенности реализации:
     * 1. Создается копия исходного массива для восстановления
     * 2. На каждой итерации массив восстанавливается из копии
     * 3. Выполняется быстрая сортировка
     *
     * Такой подход позволяет многократно сортировать один и тот же массив,
     * сохраняя исходное состояние между итерациями.
     *
     * @param array              исходный массив для сортировки
     * @param low                левая граница сортировки
     * @param high               правая граница сортировки
     * @param iterations         количество итераций сортировки
     * @param quickSortFunction  ссылка на метод быстрой сортировки
     * @return общее время выполнения всех итераций сортировки в миллисекундах
     *
     * @throws NullPointerException если array или quickSortFunction равен null
     */
    public static long measureQuickSortTime(int[] array,
                                            int low,
                                            int high,
                                            int iterations,
                                            QuickSortFunction quickSortFunction) {
        // Проверка входных параметров
        if (array == null || quickSortFunction == null) {
            throw new NullPointerException("Массив и функция сортировки не могут быть null");
        }

        // Создаем копию исходного массива для восстановления
        int[] copy = Arrays.copyOf(array, array.length);

        return Task_1.measureTime(() -> {
            for (int i = 0; i < iterations; i++) {
                // Восстанавливаем исходный массив из копии
                System.arraycopy(copy, 0, array, 0, array.length);
                // Выполняем быструю сортировку
                quickSortFunction.quick(array, low, high);
            }
        });
    }

    /**
     * Измеряет время выполнения поразрядной сортировки (Radix Sort).
     *
     * Этот метод изменяет исходный массив!
     * Если необходимо сохранить исходный массив, передавайте его клон.
     *
     * @param array              массив для сортировки
     * @param iterations         количество итераций сортировки
     * @param radixSortFunction  ссылка на метод поразрядной сортировки
     * @return общее время выполнения всех итераций сортировки в миллисекундах
     *
     * @throws NullPointerException если array или radixSortFunction равен null
     */
    public static long measureRadixSortTime(int[] array,
                                            int iterations,
                                            RadixSortFunction radixSortFunction) {
        // Проверка входных параметров
        if (array == null || radixSortFunction == null) {
            throw new NullPointerException("Массив и функция сортировки не могут быть null");
        }

        return Task_1.measureTime(() -> {
            for (int i = 0; i < iterations; i++) {
                // Выполняем поразрядную сортировку
                radixSortFunction.radix(array);
            }
        });
    }
}