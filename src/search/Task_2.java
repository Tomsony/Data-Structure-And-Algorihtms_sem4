package search;

///  Класс отвечает за реализацию и замер времени выполнения бинарного и интерполяционного поиска
public class Task_2 {
    /**
     * Бинарный поиск
     * @param sortedArray отсортированный по возрастанию массив из preparatory.Task_1
     * @param element     искомый элемент
     * @return -1 если элемент не найден или искомое число
     */
    public static int binaryOfIndex(int[] sortedArray, int element) {
        int left = 0; // левая граница диапазона
        int right = sortedArray.length - 1; // правая

        while (left <= right) {
            int middle = left + (right - left) / 2; // середина массива
            int current = sortedArray[middle]; // текущий элемент

            if (current == element) { // Середина массива - искомое число?
                return middle; // Если да - возвращаем
            } else if (current < element) { // Середина массива - меньше искомого числа?
                left = middle + 1; // Если да - увеличиваем левую границу на 1
            } else if (current > element) {
                right = middle - 1; // Если да - уменьшаем правую границу на 1
            }
        }

        return -1;
    }

    /**
     * Интерполяционный поиск
     * @param array отсортированный по возрастанию массив из preparatory.Task_1
     * @param key   искомый элемент
     * @return -1 если элемент не найден
     */
    public static int interpolationSearch(int[] array, int key) {
        int low = 0;
        int high = array.length - 1;

        if (array.length == 0) return -1;
        // Быстрая проверка граничных значений
        if (array[low] == key) return low;
        if (array[high] == key) return high;

        while (low <= high && key >= array[low] && key <= array[high]) {
            // Защита от деления на ного и одинаковых элементов
            if (array[high] == array[low]) {
                return (array[low] == key) ? low : -1;
            }

            // Оптимизированная формула интерполяции
            // Исправление: использование long для избежания переполнения
            int pos = low + (int)( ((long)(key - array[low]) * (high - low) / (array[high] - array[low]) ));

            // Защита от выхода за границы
            pos = Math.max(low, Math.min(high, pos));

            if (array[pos] == key) {
                return pos;
            } else if (array[pos] < key) {
                low = pos + 1;
            } else {
                high = pos - 1;
            }
        }
        return -1;
    }
}

