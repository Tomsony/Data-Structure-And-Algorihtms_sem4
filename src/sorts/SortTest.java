package sorts;

import org.junit.jupiter.api.Test;
import preparatory.Task_1;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SortTest {
    private static final int TEST_SIZE = 1000;

    // ========== Общие тестовые данные ==========
    private int[] generateRandomArray(int size) {
        int[] randArr = Task_1.createRandomArray(size,0,10_000);
        return randArr;
    }

    private int[] generateSortedArray(int size) {
        int[] arr = Task_1.createSortedArray(size,0,10000);
        return arr;
    }


    // ========== Вспомогательные методы ==========
    private void assertSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            assertTrue(array[i] <= array[i + 1],
                    "Array is not sorted at index " + i + ": " + array[i] + " > " + array[i + 1]);
        }
    }

    // ========== Тесты для sorts.MergeSort ==========
    @Test
    void mergeSort_EmptyArray() {
        int[] empty = {};
        int[] result = MergeSort.mergeSort(empty, empty);
        assertArrayEquals(empty, result);
    }


    @Test
    void mergeSort_AlreadySorted() {
        int[] sorted = generateSortedArray(TEST_SIZE);
        int[] left = Arrays.copyOfRange(sorted, 0, sorted.length / 2);
        int[] right = Arrays.copyOfRange(sorted, sorted.length / 2, sorted.length);

        int[] result = MergeSort.mergeSort(left, right);
        assertArrayEquals(sorted, result);
    }

    // ========== Тесты для sorts.QuickSort ==========
    @Test
    void quickSort_EmptyArray() {
        int[] empty = {};
        int[] result = QuickSort.quickSort(empty, 0, empty.length - 1);
        assertArrayEquals(empty, result);
    }

    @Test
    void quickSort_SingleElement() {
        int[] single = {5};
        int[] result = QuickSort.quickSort(single, 0, single.length - 1);
        assertArrayEquals(single, result);
    }

    @Test
    void quickSort_RandomArray() {
        int[] arr = generateRandomArray(TEST_SIZE);
        int[] result = QuickSort.quickSort(arr, 0, arr.length - 1);
        assertSorted(result);
    }

    @Test
    void quickSort_AlreadySorted() {
        int[] sorted = generateSortedArray(TEST_SIZE);
        int[] result = QuickSort.quickSort(sorted, 0, sorted.length - 1);
        assertArrayEquals(sorted, result);
    }

    // ========== Тесты для sorts.InsertionSort ==========
    @Test
    void insertionSort_EmptyArray() {
        int[] empty = {};
        int[] result = InsertionSort.insertionSort(empty);
        assertArrayEquals(empty, result);
    }

    @Test
    void insertionSort_SingleElement() {
        int[] single = {5};
        int[] result = InsertionSort.insertionSort(single);
        assertArrayEquals(single, result);
    }

    @Test
    void insertionSort_RandomArray() {
        int[] arr = generateRandomArray(TEST_SIZE);
        int[] result = InsertionSort.insertionSort(arr);
        assertSorted(result);
    }

    @Test
    void insertionSort_AlreadySorted() {
        int[] sorted = generateSortedArray(TEST_SIZE);
        int[] result = InsertionSort.insertionSort(sorted);
        assertArrayEquals(sorted, result);
    }

    // ========== Тесты для sorts.ShellSort ==========
    @Test
    void shellSort_EmptyArray() {
        int[] empty = {};
        ShellSort.shellSort(empty);
        assertArrayEquals(empty, empty);
    }

    @Test
    void shellSort_SingleElement() {
        int[] single = {5};
        ShellSort.shellSort(single);
        assertArrayEquals(single, single);
    }

    @Test
    void shellSort_RandomArray() {
        int[] arr = generateRandomArray(TEST_SIZE);
        ShellSort.shellSort(arr);
        assertSorted(arr);
    }

    @Test
    void shellSort_AlreadySorted() {
        int[] sorted = generateSortedArray(TEST_SIZE);
        int[] expected = Arrays.copyOf(sorted, sorted.length);
        ShellSort.shellSort(sorted);
        assertArrayEquals(expected, sorted);
    }
}