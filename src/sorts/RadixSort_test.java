package sorts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RadixSort_test {

    @Test
    public void testRadixSortWithRegularArray() {
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66};
        int[] expected = {2, 24, 45, 66, 75, 90, 170, 802};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr, "Массив должен быть отсортирован по возрастанию");
    }

    @Test
    public void testRadixSortWithDuplicateElements() {
        int[] arr = {121, 432, 564, 23, 1, 45, 121, 45};
        int[] expected = {1, 23, 45, 45, 121, 121, 432, 564};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr, "Массив с повторяющимися элементами должен быть отсортирован");
    }

    @Test
    public void testRadixSortWithAlreadySortedArray() {
        int[] arr = {5, 10, 15, 20, 25};
        int[] expected = {5, 10, 15, 20, 25};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr, "Уже отсортированный массив должен остаться без изменений");
    }

    @Test
    public void testRadixSortWithSingleElement() {
        int[] arr = {42};
        int[] expected = {42};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr, "Массив с одним элементом должен остаться без изменений");
    }

    @Test
    public void testRadixSortWithEmptyArray() {
        int[] arr = {};
        int[] expected = {};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr, "Пустой массив должен остаться без изменений");
    }

    @Test
    public void testRadixSortWithLargeNumbers() {
        int[] arr = {1000000, 999999, 1234567, 987654};
        int[] expected = {987654, 999999, 1000000, 1234567};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr, "Массив с большими числами должен быть отсортирован");
    }

    @Test
    public void testRadixSortWithSameDigitNumbers() {
        int[] arr = {111, 11, 1, 1111};
        int[] expected = {1, 11, 111, 1111};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr, "Числа с одинаковыми цифрами должны быть отсортированы по значению");
    }

    @Test
    public void testRadixSortWithNullInput() {
        int[] arr = null;
        assertDoesNotThrow(() -> RadixSort.radixSort(arr),
                "Метод должен корректно обрабатывать null вход без исключений");
    }
}
