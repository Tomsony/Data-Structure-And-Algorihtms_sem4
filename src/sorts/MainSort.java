package sorts;

import preparatory.Task_1;

public class MainSort {
    public static void main(String[] args) {
        int size = 10000;
        int max = 69696;
        int min = 0;
        int[] arr1 = Task_1.createSortedArray(size,0,max);
        int[] arr2 = Task_1.createSortedArray(size,0,max);
        int iterations = 1000; // Количество повторений для точности замера

        System.out.println("Выполняется слияние массивов: ");
        long duration = SearchUtility.measureMergeTime(arr1,arr2,iterations, MergeSort::mergeSort);
        System.out.println("Время выполнения: " + duration + " мс");

        int[] arr3 = Task_1.createRandomArray(size,0,max);
        System.out.println("Выполняется сортировка вставками: ");
        // Замер времени
        long duration2 = SearchUtility.measureInsSortTime(arr3, iterations, InsertionSort::insertionSort);
        System.out.println("Время выполнения: " + duration2 + " мс");

        int[] arrRand = Task_1.createRandomArray(size,0,max);
        System.out.println("Выполняется быстрая сортировка: ");
        long duration3 = SearchUtility.measureQuickSortTime(arrRand,0,arrRand.length - 1,iterations, QuickSort::quickSort);
        System.out.println("Время выполнения: " + duration3 + " мс");

        int[] arrRand1 = Task_1.createSortedArray(size,0,max);
        System.out.println("Выполняется сортировка Шелла: ");
        long duration4 = SearchUtility.measureInsSortTime(arrRand1,iterations, ShellSort::shellSort);
        System.out.println("Время выполнения: " + duration4 + " мс");
    }
}
