package search;

import java.io.FileWriter;
import java.io.IOException;

public class ClosestElementFinder {

    /**
     * Находит элемент в массиве, наиболее близкий к заданному числу x
     *
     * @param arr входной массив чисел
     * @param x   число, к которому ищем ближайший элемент
     * @return элемент массива, наиболее близкий к x
     * @throws IllegalArgumentException если массив пустой
     */
    public static int findClosestElement(int[] arr, int x) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Массив не может быть пустым");
        }

        int closest = arr[0];
        int minDiff = Math.abs(arr[0] - x);

        for (int i = 1; i < arr.length; i++) {
            int currentDiff = Math.abs(arr[i] - x);

            if (currentDiff < minDiff) {
                closest = arr[i];
                minDiff = currentDiff;
            }
        }

        return closest;
    }

    /**
     * Записывает результат в текстовый файл
     *
     * @param filename имя файла для записи
     */
    public static void writeResultToFile(String filename, int[] arr, int x, int result) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Массив: " + arrayToString(arr) + "\n");
            writer.write("Число x: " + x + "\n");
            writer.write("Ближайший элемент: " + result + "\n");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    /**
     * Преобразует массив в строку для вывода
     */
    public static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        // Пример использования
        int[] array = {1, 3, 5, 7, 9};
        int x = 6;

        int closest = findClosestElement(array, x);
        writeResultToFile("result.txt", array, x, closest);
    }
}
