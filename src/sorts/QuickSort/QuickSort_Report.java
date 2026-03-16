package sorts.QuickSort;

import preparatory.Task_1;

import java.util.Arrays;
import java.util.Random;

/**
 * Класс QuickSort_Report демонстрирует работу алгоритма быстрой сортировки
 * с пошаговым выводом информации о процессе сортировки.
 *
 * Особенности реализации:
 * 1. Используется оптимизированный выбор опорного элемента (pivot) по методу медианы из трёх
 * 2. Выводится информация о каждом шаге: индекс опорного элемента, разделение на левую и правую части
 * 3. Используется генератор случайных чисел с фиксированным seed для воспроизводимости результатов
 * 4. Для создания исходного массива используется вспомогательный класс Task_1
 *
 * Цель: проиллюстрировать процесс быстрой сортировки и показать,
 * как рекурсивно разделяется массив относительно опорных элементов.
 *
 * @author Томских Артём ИВТ-23
 */
public class QuickSort_Report {

    /**
     * Генератор случайных чисел с фиксированным сидом (21 * 42 = 882).
     */
    private static final Random random = new Random(21 * 42);

    /**
     * Точка входа в программу.
     *
     * Алгоритм работы:
     * 1. Генерируется случайный размер массива от 10 до 19 элементов
     * 2. С помощью Task_1.createRandomArray() создается массив случайных чисел от 0 до 11
     * 3. Выводится исходный массив
     * 4. Выполняется быстрая сортировка с подробным логированием
     * 5. Выводится отсортированный массив
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        // === ШАГ 1: Определение размера массива ===
        // Генерируем размер от 10 до 19
        int size = 10 + random.nextInt(10);
        System.out.println("Размер массива: " + size);

        // === ШАГ 2: Создание и заполнение массива случайными числами ===
        // Используем Task_1 для создания массива со значениями от 0 до 11
        int[] array = Task_1.createRandomArray(size, 0, 11);
        System.out.println("Исходный массив: " + Arrays.toString(array));

        // === ШАГ 3: Быстрая сортировка с пошаговым выводом ===
        System.out.println("\n=== НАЧАЛО БЫСТРОЙ СОРТИРОВКИ ===\n");
        int[] result = quickSort(array, 0, array.length - 1);

        // === ШАГ 4: Вывод отсортированного массива ===
        System.out.println("\n=== РЕЗУЛЬТАТ СОРТИРОВКИ ===");
        System.out.println("Отсортированный массив: " + Arrays.toString(result));
    }

    /**
     * Рекурсивная функция быстрой сортировки с выводом отладочной информации.
     *
     * Алгоритм работы с выводом:
     * 1. Проверяет корректность входных индексов
     * 2. Если длина части массива больше 1 (low < high):
     *    a. Выводит сообщение о поиске опорного элемента
     *    b. Вызывает partition() для разделения массива и получения индекса pivot
     *    c. Выводит полученный индекс опорного элемента
     *    d. Выводит сообщение о рекурсивной сортировке левой части
     *    e. Рекурсивно сортирует левую часть (до pivot-1)
     *    f. Выводит сообщение о рекурсивной сортировке правой части
     *    g. Рекурсивно сортирует правую часть (от pivot+1)
     * 3. Возвращает отсортированный массив
     *
     * @param arr  массив для сортировки
     * @param low  левая граница сортируемой части (включительно)
     * @param high правая граница сортируемой части (включительно)
     * @return отсортированный массив
     */
    public static int[] quickSort(int[] arr, int low, int high) {
        // Проверка корректности индексов
        if (low < 0 || high >= arr.length) {
            System.out.println("  Некорректные индексы: low=" + low + ", high=" + high);
            return arr;
        }

        // Рекурсивно сортируем, пока длина части больше 1
        if (low < high) {
            System.out.println("\n  --- Сортировка части массива с индексами [" + low + ".." + high + "] ---");

            // Получаем индекс опорного элемента после разделения
            System.out.println("  Находим индекс опорного элемента (pivot)");
            int pivotIndex = partition(arr, low, high);
            System.out.println("  Индекс опорного элемента = " + pivotIndex + " (значение " + arr[pivotIndex] + ")");

            // Рекурсивно сортируем левую часть (элементы меньше или равные pivot)
            if (low < pivotIndex - 1) {
                System.out.println("  Рекурсивно сортируем ЛЕВУЮ часть [" + low + ".." + (pivotIndex - 1) + "]");
            } else {
                System.out.println("  Левая часть пуста или содержит один элемент");
            }
            quickSort(arr, low, pivotIndex - 1);

            // Рекурсивно сортируем правую часть (элементы больше pivot)
            if (pivotIndex + 1 < high) {
                System.out.println("  Рекурсивно сортируем ПРАВУЮ часть [" + (pivotIndex + 1) + ".." + high + "]");
            } else {
                System.out.println("  Правая часть пуста или содержит один элемент");
            }
            quickSort(arr, pivotIndex + 1, high);
        } else if (low == high) {
            System.out.println("  Часть с одним элементом (индекс " + low + "), не требует сортировки");
        }

        return arr;
    }

    /**
     * Функция разделения массива относительно опорного элемента с выводом промежуточных результатов.
     *
     * Оптимизация: выбор опорного элемента по методу "медиана из трёх"
     * (первый, средний и последний элементы). Это значительно уменьшает
     * вероятность худшего случая O(n²).
     *
     * Алгоритм работы с выводом:
     * 1. Вычисляет средний индекс mid
     * 2. Сортирует три элемента (low, mid, high) так, чтобы медиана оказалась в mid
     * 3. Перемещает опорный элемент (медиану) в конец (индекс high)
     * 4. Проходит по всем элементам от low до high-1:
     *    - Если элемент <= pivot, меняет его с элементом на позиции i и увеличивает i
     * 5. Возвращает опорный элемент на позицию i
     * 6. Возвращает индекс опорного элемента
     *
     * @param arr  массив для разделения
     * @param low  левая граница разделяемой части
     * @param high правая граница разделяемой части
     * @return индекс опорного элемента после разделения
     */
    public static int partition(int[] arr, int low, int high) {
        System.out.println("    Разделение части [" + low + ".." + high + "]: " + arrayToString(arr, low, high));

        // === ШАГ 1: Вычисление среднего индекса ===
        int mid = low + (high - low) / 2;
        System.out.println("    Выбраны три элемента: low=" + low + "(" + arr[low] + "), mid=" + mid + "(" + arr[mid] + "), high=" + high + "(" + arr[high] + ")");

        // === ШАГ 2: Медиана из трёх (сортируем три элемента) ===
        if (arr[low] > arr[mid]) {
            swap(arr, low, mid);
            System.out.println("      swap(low, mid): " + arrayToString(arr, low, high));
        }
        if (arr[low] > arr[high]) {
            swap(arr, low, high);
            System.out.println("      swap(low, high): " + arrayToString(arr, low, high));
        }
        if (arr[mid] > arr[high]) {
            swap(arr, mid, high);
            System.out.println("      swap(mid, high): " + arrayToString(arr, low, high));
        }

        // Теперь медиана находится в mid, а наибольший элемент - в high
        System.out.println("    После выбора медианы: pivot = " + arr[mid] + " (на индексе " + mid + ")");

        // === ШАГ 3: Перемещаем опорный элемент в конец ===
        swap(arr, mid, high);
        int pivot = arr[high];
        System.out.println("    Перемещаем pivot в конец: теперь pivot = " + pivot + " на индексе " + high);

        // === ШАГ 4: Основное разделение ===
        int i = low; // i - граница для элементов <= pivot
        System.out.println("    Начинаем разделение (i = " + i + "):");

        for (int j = low; j < high; j++) {
            System.out.print("      j=" + j + " (" + arr[j] + "): ");
            if (arr[j] <= pivot) {
                if (i != j) {
                    swap(arr, i, j);
                    System.out.print("меняем с индексом i=" + i + " → ");
                } else {
                    System.out.print("оставляем на месте → ");
                }
                i++;
                System.out.println("массив: " + arrayToString(arr, low, high) + ", i=" + i);
            } else {
                System.out.println("элемент > pivot, пропускаем");
            }
        }

        // === ШАГ 5: Возвращаем pivot на место ===
        if (i != high) {
            swap(arr, i, high);
            System.out.println("    Возвращаем pivot на место (меняем индексы " + i + " и " + high + "): " + arrayToString(arr, low, high));
        } else {
            System.out.println("    Pivot уже на месте");
        }

        System.out.println("    Разделение завершено, pivot на индексе " + i + ", значение " + arr[i]);
        return i;
    }

    /**
     * Вспомогательный метод для обмена двух элементов в массиве.
     *
     * @param arr массив, в котором производится обмен
     * @param i   индекс первого элемента
     * @param j   индекс второго элемента
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Вспомогательный метод для преобразования части массива в строку.
     *
     * @param arr  массив
     * @param low  левая граница (включительно)
     * @param high правая граница (включительно)
     * @return строковое представление указанной части массива
     */
    private static String arrayToString(int[] arr, int low, int high) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = low; i <= high; i++) {
            sb.append(arr[i]);
            if (i < high) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Вспомогательный метод для преобразования всего массива в строку.
     *
     * @param arr массив
     * @return строковое представление массива
     */
    private static String arrayToString(int[] arr) {
        if (arr == null) return "null";
        if (arr.length == 0) return "[]";

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}