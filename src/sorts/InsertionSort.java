package sorts;

public class InsertionSort {
    // Алгоритм сортировки вставками
    public static int[] insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++){
            int key = arr[i]; // Текущий элемент, который нужно вставить
            int j = i - 1; // Индекс последнего элемента отсортированной части

            // Сдвигаем элементы вправо, пока не найдём место для key
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j]; // Сдвигаем элемент вправо
                j--;
            }
            arr[j + 1] = key; // Вставляем key в правильную позицию
        }
        return arr;
    }
}
