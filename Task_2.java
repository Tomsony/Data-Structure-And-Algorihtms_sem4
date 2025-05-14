public class Task_2 {
    /**
     * Бинарный поиск
     *
     * @param sortedArray отсортированный массив из Task_1
     * @param element     искомый элемент
     * @return -1 если элемент не найден
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
     * Измеряет время бинарного поиска случайных элементов в массиве
     *
     * @param array      массив для поиска
     * @param min        минимальное значение элемента
     * @param max        максимальное значение элемента
     * @param iterations количество итераций поиска
     * @return время выполнения всех итераций поиска в миллисекундах
     */
    public static long binarySearchTime(int[] array, int min, int max, int iterations) {
        return Task_1.measureTime(() -> {
            for (int k = 0; k < iterations; k++) {
                // Math.random() генерирует случайное число типа double в диапазоне [0.0, 1.0)
                int randomValue = (int) (Math.random() * (max - min + 1)) + min;
                int foundIndex = binaryOfIndex(array, randomValue);
            }
        });
    }

    /**
     * Интерполяционный поиск
     *
     * @param array отсортированный массив из Task_1
     * @param key   искомый элемент
     * @return -1 если элемент не найден
     */
    public static int interpolationSearch(int[] array, int key) {
        int low = 0;
        int high = array.length - 1;

        // Быстрая проверка граничных значений
        if (array[low] == key) return low;
        if (array[high] == key) return high;

        while (low <= high && key >= array[low] && key <= array[high]) {
            // Защита от деления на ного и одинаковых элементов
            if (array[high] == array[low]) {
                return (array[low] == key) ? low : -1;
            }

            // Оптимизированная формула интерполяции
            int pos = low + (((key - array[low]) * (high - low)) / (array[high] - array[low]));

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

    /**
     * Измеряет время интерполяционного поиска случайных элементов в массиве
     *
     * @param array      массив для поиска
     * @param min        минимальное значение элемента
     * @param max        максимальное значение элемента
     * @param iterations количество итераций поиска
     * @return время выполнения всех итераций поиска в миллисекундах
     */
    public static long interpolationSearchTime(int[] array, int min, int max, int iterations) {
        return Task_1.measureTime(() -> {
            for (int k = 0; k < iterations; k++) {
                // Math.random() генерирует случайное число типа double в диапазоне [0.0, 1.0)
                int randomValue = (int) (Math.random() * (max - min + 1)) + min;
                int foundIndex = interpolationSearch(array, randomValue);
            }
        });
    }

    public static void main(String[] args) {
            int size = 800_000_000; // Размер массива
            int min = 1; // Минимальное значение элемента массива
            int max = size; // Максимальное значение элемента массива
            int searchIncrement = 10_000_000; // Количество итераций в циклах поиска

            // Создаем отсортированный массив один раз
            System.out.println("Создание отсортированного массива...");
            int[] sortedArray = Task_1.createSortedArray(size, min, max);
            System.out.println("Массив создан, размер: " + sortedArray.length);

            // Бинарный поиск
            System.out.println("Идёт бинарный поиск...");
            long binarySearchRandomElementTime = Task_2.binarySearchTime(sortedArray, min, max, searchIncrement);
            // Результат бинарного поиска
            System.out.println("Время поиска случайного элемента (" + searchIncrement + " итераций): "
                    + binarySearchRandomElementTime + " мс");
        }
    }

