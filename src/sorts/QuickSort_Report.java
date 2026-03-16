package sorts;

import preparatory.Task_1;

import java.util.Arrays;
import java.util.Random;

public class QuickSort_Report {
    private static final Random random = new Random(21 * 42);

    public static void main(String[] args) {
        // 1. Определение размера массива (10-20 элементов)
        int size = 10 + random.nextInt(10); // random.nextInt(11)= 1  => size = 11
        System.out.println("Размер массива: " + size);

        // 2. Заполнение массива случайными числами (0-30)
        int[] array = Task_1.createRandomArray(size,0,11);
        System.out.println("Массив: " + Arrays.toString(array));

        // 3. Быстрая сортировка с выводом итераций
        int[] result = quickSort(array,0,array.length-1);

        // 4. Вывод массива
        System.out.println("Отсортированный массив: " + Arrays.toString(result));
    }

    public static int[] quickSort(int[] arr, int low, int high){
        if(low < 0 || high >= arr.length){
            return arr;
        }
        if (low < high){
            System.out.println("Находим индекс опорного элемента");
            int pivotIndex = partition(arr,low,high);
            System.out.println("Индекс опорного элемента = " + pivotIndex);


            System.out.println("Рекурсивно сортируем левую часть");
            quickSort(arr, low, pivotIndex - 1);
            System.out.println("Рекурсивно сортируем правую часть");
            quickSort(arr, pivotIndex + 1, high);
        }

        return arr;
    }

    /** Функция для разделения массива относительно опорного элемента
     * @return индекс pivot - опорный элемент
     * */
    public static int partition(int[] arr, int low, int high) {
        // Выбираем pivot как медиану трёх (первый, средний, последний)
        int mid = low + (high - low) / 2;
        if (arr[low] > arr[mid]) swap(arr, low, mid);
        if (arr[low] > arr[high]) swap(arr, low, high);
        if (arr[mid] > arr[high]) swap(arr, mid, high);
        // Теперь pivot = arr[mid], перемещаем его в конец
        swap(arr, mid, high);
        int pivot = arr[high];

        int i = low;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, high); // Возвращаем pivot на место
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
