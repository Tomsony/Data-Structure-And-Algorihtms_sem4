package sorts;

public class QuickSort {
    /** Основная функция быстрой сортировки
     * @param arr исходный массив
     * @param low левая часть
     * @param high правая часть
     * @return отсортированный массив
     * */
    public static int[] quickSort(int[] arr, int low, int high){
        if(low < 0 || high >= arr.length){
            return arr;
        }
        if (low < high){
            // Находим индекс опорного элемента
            int pivotIndex = partition(arr,low,high);

            // Рекурсивно сортируем левую и правую части
            quickSort(arr, low, pivotIndex - 1);
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
