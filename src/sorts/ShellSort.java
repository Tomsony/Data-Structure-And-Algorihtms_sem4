package sorts;

public class ShellSort {
    // Сортировка Шелла
    public static int[] shellSort (int[] arr) {
        int n = arr.length;

        // Начинаем с большого интервала и уменьшаем его
        for (int gap = n / 2; gap > 0; gap /= 2) {

            // Применяем сортировку вставками для этого интервала
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;

                // Сдвигаем элементы, пока не найдем правильное место для arr[i]
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }

                // Вставляем сохраненный элемент на правильное место
                arr[j] = temp;
            }
        }
        return arr;
    }
}
