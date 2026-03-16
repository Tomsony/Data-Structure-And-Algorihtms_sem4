package sorts;

public class MergeSort {
    /**
     * Основная функция сортировки слиянием
     *
     * @param left первый массив
     * @param right второй массив
     * @return массив слияния left + right
     */
    public static int[] mergeSort(int[] left, int[] right) {
        int[] result = new int[left.length + right.length]; // Результирующий массив
        int l = 0, r = 0, R = 0; // Индексы для массивов

        // пока оба массива не закончились, сравниваем их текущие элементы
        // меньший элемент записываем в result, его индекс увеличивается
        while (l < left.length && r < right.length) {
            if (left[l] <= right[r]) {
                result[R++] = left[l++];
            } else {
                result[R++] = right[r++];
            }
        }
        // Если один из массивов закончился раньше другого, добиваем оставшиеся элементы
            while(l < left.length){result[R++] = left[l++];}

            while(r < right.length){result[R++] = right[r++];}
        // Вывод массива
            return result;
        }
    }
