package search;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class ClosestElementFinderTest {

    @Test
    void testFindClosestElementWithNormalArray() {
        int[] array = {1, 3, 5, 7, 9};
        assertEquals(3, ClosestElementFinder.findClosestElement(array, 4));
        assertEquals(5, ClosestElementFinder.findClosestElement(array, 6));
        assertEquals(1, ClosestElementFinder.findClosestElement(array, 0));
    }

    @Test
    void testFindClosestElementWithSingleElement() {
        int[] array = {5};
        assertEquals(5, ClosestElementFinder.findClosestElement(array, 10));
    }

    @Test
    void testFindClosestElementWithNegativeNumbers() {
        int[] array = {-5, -3, 0, 1, 4};
        assertEquals(-3, ClosestElementFinder.findClosestElement(array, -2));
        assertEquals(0, ClosestElementFinder.findClosestElement(array, -1));
    }

    @Test
    void testFindClosestElementWithEqualElements() {
        int[] array = {3, 3, 3, 3};
        assertEquals(3, ClosestElementFinder.findClosestElement(array, 5));
    }

    @Test
    void testFindClosestElementThrowsExceptionForEmptyArray() {
        int[] emptyArray = {};
        assertThrows(IllegalArgumentException.class, () -> {
            ClosestElementFinder.findClosestElement(emptyArray, 5);
        });
    }

    @Test
    void testArrayToString() {
        int[] array = {1, 2, 3};
        assertEquals("[1, 2, 3]", ClosestElementFinder.arrayToString(array));

        int[] emptyArray = {};
        assertEquals("[]", ClosestElementFinder.arrayToString(emptyArray));

        int[] singleElementArray = {5};
        assertEquals("[5]", ClosestElementFinder.arrayToString(singleElementArray));
    }

    @Test
    void testWriteResultToFile(@TempDir Path tempDir) throws IOException {
        String filename = tempDir.resolve("test_result.txt").toString();
        int[] array = {1, 3, 5};
        int x = 4;
        int result = 3;

        ClosestElementFinder.writeResultToFile(filename, array, x, result);

        String content = Files.readString(Path.of(filename));
        assertTrue(content.contains("Массив: [1, 3, 5]"));
        assertTrue(content.contains("Число x: 4"));
        assertTrue(content.contains("Ближайший элемент: 3"));
    }
}