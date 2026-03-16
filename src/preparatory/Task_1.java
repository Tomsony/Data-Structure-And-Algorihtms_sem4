package preparatory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Predicate;


public class Task_1 {
    /**
     * Замер времени выполнения кода в наносекундах
     * @param task измеряемый участок кода
     * @return время выполнения кода
     */
    public static long measureTime(Runnable task) {
        // start содержит начальное время измерения
        long start = System.currentTimeMillis();
        // run - метод Runnable, который запускает поток
        task.run();
        return System.currentTimeMillis() - start;
    }

    /**
     * Создаёт массив заданного размера со случайными целыми числами в указанном диапазоне
     * @param size определяет размер массива
     * @param min  нижняя граница диапазона
     * @param max  верхняя граница диапазона
     */
    public static int[] createRandomArray(int size, int min, int max) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            // Math.random() возвращает случайное число от 0.0 (включительно) до 1.0 (исключительно)
            // (max - min + 1) определяет количество возможных значений в диапазоне
            // Приведение к (int) отбрасывает дробную часть
            arr[i] = (int) (Math.random() * (max - min + 1)) + min;
        }
        return arr;
    }

    /**
     * Заполнение монотонно возрастающего массива
     * @param size - определяет размер массива
     * @param min  - нижняя граница диапазона
     * @param max  - верхняя граница диапазона
     */
    public static int[] createSortedArray(int size, int min, int max) {
        int[] arr = new int[size]; // объявление массива размера size
        arr[0] = min; // Первый элемент
        int remaining = max - min; // оставшаяся разница между текущим значением и максимальным
        int availableIncrements = size - 1; // количество шагов

        for (int i = 1; i < size; i++) {
            int maxIncrement = remaining - (availableIncrements - 1); // Максимальное возможное приращение на этом шаге
            int increment = 1 + (int)(Math.random() * maxIncrement);
            arr[i] = arr[i - 1] + increment;
            remaining -= increment; // Обновляем оставшееся пространство
            availableIncrements--;
        }
        return arr;
    }

    /**
     *  Сохранение данных массива в файл
     * @param arr - выводимый массив
     * @param filename - имя файла для записи данных
     */
    public static void saveArrayToFile(int[] arr, String filename) throws IOException {
        // BufferWriter - буферизирует запись данных
        // FileWriter(filename) - открывает файл для записи
        // try-with-resources автоматически закрывает ресурсы writer, даже при возникновении исключения
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            for (int num : arr){
                writer.write(Integer.toString(num)); // Запись числа как строки
                writer.newLine(); // Переход на новую строку
            }
        } catch (IOException e){
            System.err.println("Ошибка записи в файл: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     *  Проверка сортировки массива по возрастанию
     * @param arr проверяемый массив
     */
    public static boolean isSorted(int[] arr){
        for(int i = 1; i < arr.length; i++){
            if (arr[i] < arr[i-1]) return false;
        }
        return true;
    }

    /**
     *  Последовательный поиск с гибким пользовательским условием.
     * @param arr массив в котором выполняется поиск.
     * @param condition условие для проверки элементов.
     *
     * Predicate — это функциональный интерфейс из Java, который представляет условие (логическую функцию). Он имеет один метод:
     * boolean test(T t);
     * @return
     */
    public static int sequentialSearch(int[] arr,  Predicate<Integer> condition) {
        for (int i = 0; i < arr.length; i++) {
            // Для каждого элемента arr[i] вызывается метод condition.test(arr[i]).
            // Если условие возвращает true (элемент удовлетворяет условию), метод немедленно возвращает индекс i.
            // Если ни один элемент не удовлетворяет условию, возвращается -1.
            if (condition.test(arr[i])) {
                return i+1;
            }
        }
        return -1;
    }

    /**
     * Измеряет время последовательного поиска случайных элементов в массиве
     * @param array массив для поиска
     * @param min минимальное значение элемента
     * @param max максимальное значение элемента
     * @param iterations количество итераций поиска
     * @return время выполнения всех итераций поиска в миллисекундах
     */
    public static long sequentialSearchTime(int[] array, int min, int max, int iterations, double precision) {
        return measureTime(() -> {
            for (int k = 0; k < iterations; k++) {
                // Math.random() генерирует случайное число типа double в диапазоне [0.0, 1.0)
                int randomValue = (int) (Math.random() * (max - min + 1)) + min;
                // Добавляем небольшое случайное отклонение для демонстрации точности
                double noisyValue = randomValue + (Math.random() - 0.5) * 2 * precision;
                int foundIndex = sequentialSearch(array, n -> compareWithPrecision(n, noisyValue, precision));
            }
        });
    }

    /**
     *  Сравнивает два числа с заданной точностью
     * @param a первое число
     * @param b второе число
     * @param precision точность для сравнения
     * @return true, если числа равны с заданной точностью
     */
    public static boolean compareWithPrecision(double a, double b, double precision){
        return Math.abs(a - b) <= precision;
    }
}
