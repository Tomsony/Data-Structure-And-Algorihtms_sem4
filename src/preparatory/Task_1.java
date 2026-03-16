package preparatory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Predicate;

/**
 * Класс Task_1 предоставляет набор утилитных методов для работы с массивами целых чисел.
 *
 * Основные возможности:
 * 1. Генерация массивов:
 *    - Случайные массивы с числами в заданном диапазоне
 *    - Монотонно возрастающие (отсортированные) массивы
 *
 * 2. Работа с массивами:
 *    - Проверка на отсортированность
 *    - Сохранение массива в файл
 *
 * 3. Поиск в массивах:
 *    - Последовательный поиск с гибким условием (Predicate)
 *    - Поиск с учётом заданной точности сравнения
 *
 * 4. Измерение производительности:
 *    - Замер времени выполнения кода
 *    - Специализированный замер времени последовательного поиска
 *
 * Все методы реализованы как статические для удобства использования
 * без необходимости создания экземпляра класса.
 *
 * @author Томских Артём ИВТ-23
 */
public class Task_1 {
    /**
     * Замер времени выполнения кода в миллисекундах.
     *
     * Алгоритм работы:
     * 1. Фиксирует начальное время через System.currentTimeMillis()
     * 2. Выполняет переданный код через метод run() интерфейса Runnable
     * 3. Фиксирует конечное время и возвращает разницу
     *
     * @param task измеряемый участок кода (объект Runnable)
     * @return время выполнения кода в миллисекундах
     */
    public static long measureTime(Runnable task) {
        long start = System.currentTimeMillis();
        task.run();
        return System.currentTimeMillis() - start;
    }

    /**
     * Создаёт массив заданного размера со случайными целыми числами.
     *
     * Алгоритм генерации:
     * Для каждого индекса i от 0 до size-1:
     *   1. Генерируется случайное число в диапазоне [0.0, 1.0)
     *   2. Масштабируется до размера диапазона: (max - min + 1)
     *   3. Приводится к целому числу
     *   4. Смещается на min для попадания в нужный диапазон
     *
     * @param size определяет размер массива (должен быть >= 0)
     * @param min  нижняя граница диапазона (включительно)
     * @param max  верхняя граница диапазона (включительно)
     * @return массив случайных целых чисел в заданном диапазоне
     *
     * @throws IllegalArgumentException если size < 0 или min > max
     */
    public static int[] createRandomArray(int size, int min, int max) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * (max - min + 1)) + min;
        }
        return arr;
    }

    /**
     * Создаёт монотонно возрастающий (отсортированный) массив.
     *
     * Алгоритм равномерного распределения:
     * 1. Первый элемент устанавливается равным min
     * 2. Оставшийся диапазон (max - min) распределяется между size-1 шагами
     * 3. На каждом шаге случайно выбирается приращение, но так,
     *    чтобы оставшихся шагов хватило для достижения max
     *
     * Математическое обоснование:
     *   remaining = max - min (общий доступный диапазон)
     *   availableIncrements = size - 1 (количество шагов)
     *   На каждом шаге максимальное приращение = remaining - (availableIncrements - 1)
     *   Это гарантирует, что после этого шага останется достаточно места
     *   для минимальных приращений на оставшихся шагах
     *
     * @param size определяет размер массива (должен быть >= 1)
     * @param min  нижняя граница диапазона (первый элемент)
     * @param max  верхняя граница диапазона (последний элемент <= max)
     * @return монотонно возрастающий массив
     *
     * @throws IllegalArgumentException если size < 1 или min >= max
     */
    public static int[] createSortedArray(int size, int min, int max) {
        int[] arr = new int[size];
        arr[0] = min;
        int remaining = max - min;
        int availableIncrements = size - 1;

        for (int i = 1; i < size; i++) {
            int maxIncrement = remaining - (availableIncrements - 1);
            int increment = 1 + (int)(Math.random() * maxIncrement);
            arr[i] = arr[i - 1] + increment;
            remaining -= increment;
            availableIncrements--;
        }
        return arr;
    }

    /**
     * Сохраняет массив целых чисел в файл.
     *
     * Формат сохранения:
     * - Каждое число записывается в отдельной строке
     * - Файл создаётся или перезаписывается
     *
     * Особенности реализации:
     * - Используется BufferedWriter для оптимизации записи
     * - Применяется try-with-resources для автоматического закрытия
     * - В случае ошибки программа завершается с кодом 1
     *
     * @param arr      выводимый массив
     * @param filename имя файла для записи данных
     * @throws IOException если возникает ошибка ввода-вывода
     */
    public static void saveArrayToFile(int[] arr, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            for (int num : arr){
                writer.write(Integer.toString(num));
                writer.newLine();
            }
        } catch (IOException e){
            System.err.println("Ошибка записи в файл: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Проверяет, отсортирован ли массив по возрастанию.
     *
     * Алгоритм:
     * Последовательно сравниваются пары соседних элементов.
     * Если находится пара, где предыдущий элемент больше следующего,
     * массив считается неотсортированным.
     *
     * Особые случаи:
     * - Пустой массив считается отсортированным
     * - Массив с одним элементом считается отсортированным
     * - Массив с равными элементами считается отсортированным
     *
     * @param arr проверяемый массив
     * @return true если массив отсортирован по неубыванию, иначе false
     */
    public static boolean isSorted(int[] arr){
        for(int i = 1; i < arr.length; i++){
            if (arr[i] < arr[i-1]) return false;
        }
        return true;
    }

    /**
     * Последовательный поиск элемента, удовлетворяющего заданному условию.
     *
     * Алгоритм:
     * 1. Проходит по массиву от начала до конца
     * 2. Для каждого элемента применяет переданное условие (Predicate)
     * 3. Если условие выполняется, возвращает индекс элемента + 1
     * 4. Если ни один элемент не подошёл, возвращает -1
     *
     * ВАЖНО: Метод возвращает индекс + 1 для найденного элемента,
     * что позволяет отличить первый элемент (индекс 0) от отсутствия элемента (-1)
     *
     * @param arr       массив в котором выполняется поиск
     * @param condition условие для проверки элементов (Predicate)
     *
     * @return индекс + 1 если элемент найден, -1 если не найден
     */
    public static int sequentialSearch(int[] arr,  Predicate<Integer> condition) {
        for (int i = 0; i < arr.length; i++) {
            if (condition.test(arr[i])) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Измеряет общее время выполнения множественных операций последовательного поиска.
     *
     * Алгоритм:
     * 1. Для каждого из iterations:
     *    a. Генерируется случайное значение в заданном диапазоне
     *    b. Добавляется небольшой шум для демонстрации работы с точностью
     *    c. Выполняется поиск с учётом заданной точности
     * 2. Измеряется общее время всех итераций
     *
     * Назначение:
     * Позволяет получить статистически значимые измерения времени поиска,
     * усредняя результаты по множеству случайных запросов.
     *
     * @param array      массив для поиска
     * @param min        минимальное значение элемента (для генерации)
     * @param max        максимальное значение элемента (для генерации)
     * @param iterations количество итераций поиска
     * @param precision  точность сравнения
     *
     * @return общее время выполнения всех итераций поиска в миллисекундах
     */
    public static long sequentialSearchTime(int[] array, int min, int max, int iterations, double precision) {
        return measureTime(() -> {
            for (int k = 0; k < iterations; k++) {
                int randomValue = (int) (Math.random() * (max - min + 1)) + min;
                double noisyValue = randomValue + (Math.random() - 0.5) * 2 * precision;
                int foundIndex = sequentialSearch(array, n -> compareWithPrecision(n, noisyValue, precision));
            }
        });
    }

    /**
     * Сравнивает два числа с заданной точностью (эпсилон).
     *
     * Математическая формула:
     * |a - b| <= precision
     *
     * Используется для сравнения чисел с плавающей точкой,
     * где прямое равенство может быть ненадёжным из-за погрешностей вычислений.
     *
     * @param a         первое число
     * @param b         второе число
     * @param precision точность сравнения (неотрицательное число)
     * @return true, если числа равны с заданной точностью, иначе false
     *
     * @throws IllegalArgumentException если precision < 0
     */
    public static boolean compareWithPrecision(double a, double b, double precision){
        return Math.abs(a - b) <= precision;
    }
}