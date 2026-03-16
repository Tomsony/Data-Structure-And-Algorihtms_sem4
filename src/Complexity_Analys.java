import preparatory.Task_1;
import sorts.SearchUtility;
import search.Task_2;

import java.io.IOException;
/**
 *  Основные функции:
 *   1) Создание массива определенного размера со случайными элементами
 *      + измерение времени многократного создания таких массивов.
 *   2) Создание монотонно возрастающего массива определенного размера со случайными элементами
 *      + измерение времени многократного создания таких массивов.
 *   3) Проверка на сортировку массива.
 *   4) Гибкий последовательный поиск элементов массива
 *      + измерение времени многократного поиска.
 *   5) Бинарный поиск случайных элементов массива
 *      + измерение времени многократного поиска.
 *   6) Интерполяционный поиск случайных элементов массива
 *      + измерение времени многократного поиска.
 */
public class Complexity_Analys {
    public static void main(String[] args) throws IOException {
        int size = 1000000; // Размер массива
        int min = 1; // Минимальное значение элемента массива
        int max = size; // Максимальное значение элемента массива
        int increment = 100; // Количество итераций в циклах многократного увеличения времени создания массивов
        int maxIterations = 10_000_000; // Количество итераций в циклах поиска

        System.out.println("Идёт замер времени создания RandomArray..."); // Статус работы программы
        //Создание и замер времени для RandomArray
        /**
         Первым шагом создаём двумерный массив (контейнер для хранения будущего массива) randomArrayHolder длиной 1
         Этот массив объявлен как final, что означает, что сама ссылка на массив не может быть изменена
         (в лямбда-выражениях нельзя менять локальные переменные, а final - константа)
         Массив будет использоваться для хранения результата работы метода createRandomArray
         */
        final int[][] randomArrayHolder = new int[1][];
        // Вызывается метод measureTime, который измеряет время выполнения лямбда-выражения
        // Результат выполнения в наносекундах сохраняется в переменную randomCreationTime
        long randomCreationTime = Task_1.measureTime(() -> {
            for (int j = 0; j < increment; j++){
                randomArrayHolder[0] = Task_1.createRandomArray(size,min,max);
            }
        });
        Task_1.saveArrayToFile(randomArrayHolder[0], "Random_Array"); // Сохраняем массив в файл Random_Array
        System.out.println("Время создания Random_Array: " + randomCreationTime + " мс"); // Результат создания

        // Создание и замер времени для SortedArray (алгоритм почти идентичен, как и для RandomArray
        System.out.println("Идёт замер времени создания SortedArray...");
        final int[][] sortedArrayHolder = new int[1][];
        long sortedCreationTime = Task_1.measureTime(() -> {
            for (int j = 0; j < increment; j++){
                sortedArrayHolder[0] = Task_1.createSortedArray(size,min,max);
            }
        });
        Task_1.saveArrayToFile(sortedArrayHolder[0], "Sorted_Array.txt");
        System.out.println("Время создания Sorted_Array: " + sortedCreationTime + " мс");
        System.out.println(Task_1.isSorted(sortedArrayHolder[0])); // Проверка отсортирован ли массив

       // Последовательный поиск
       System.out.println("Идёт последовательный поиск...");
        int[] randomArray = randomArrayHolder[0]; // создаем новый одномерный массив и записываем в него первый элемент
       // Вызов метода для замера времени последовательного поиска записывается в отдельную переменную
       double precision = 0.5; // Точность сравнения в последовательном поиске
        long searchRandomElementTime = Task_1.sequentialSearchTime(randomArray, min,max, maxIterations,precision);
        // Результат последовательного поиска
        System.out.println("Время поиска случайного элемента (" + maxIterations + " итераций): "
                + searchRandomElementTime + " мс");

        int[] sortedArray = sortedArrayHolder[0];

        // Бинарный поиск
        System.out.println("Идёт бинарный поиск...");
        long binaryTime = SearchUtility.measureSearchTime(
                sortedArray,
                min,
                max,
                maxIterations,
                Task_2::binaryOfIndex
        );
        // Результат бинарного поиска
        System.out.println("Время поиска случайного элемента (" + maxIterations + " итераций): "
                + binaryTime + " мс");


        // Интерполяционный поиск
        System.out.println("Идёт интерполяционный поиск...");
        long interpolationTime = SearchUtility.measureSearchTime(
                sortedArray,
                min,
                max,
                maxIterations,
                Task_2::interpolationSearch
        );
        // Результат интерполяционного поиска
        System.out.println("Время поиска случайного элемента (" + maxIterations + " итераций): "
                + interpolationTime + " мс");
    }
}
