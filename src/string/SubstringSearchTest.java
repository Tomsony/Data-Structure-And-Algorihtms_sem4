package string;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class SubstringSearchTest {

    private static List<Character> longText;
    private static List<Character> existingPattern;
    private static List<Character> nonExistingPattern;
    private static List<Character> patternAtStart;
    private static List<Character> patternAtEnd;
    private static List<Character> emptyPattern;

    @BeforeAll
    static void setUp() {
        // Подготовка тестовых данных один раз для всех тестов
        String textStr = "abcdefghijklmnopqrstuvwxyaabcdefghijklmnopqrstuvwxyz";
        longText = textStr.chars().mapToObj(c -> (char)c).collect(Collectors.toList());

        existingPattern = "def".chars().mapToObj(c -> (char)c).collect(Collectors.toList());
        nonExistingPattern = "xyz123".chars().mapToObj(c -> (char)c).collect(Collectors.toList());
        patternAtStart = "abc".chars().mapToObj(c -> (char)c).collect(Collectors.toList());
        patternAtEnd = "yz".chars().mapToObj(c -> (char)c).collect(Collectors.toList());
        emptyPattern = "".chars().mapToObj(c -> (char)c).collect(Collectors.toList());
    }

    @Test
    void testSearchExistingPattern() {
        int index = NaiveSubstringSearch.search(longText, existingPattern);
        assertEquals(3, index); // Первое вхождение "def" на позиции 3
    }

    @Test
    void testSearchNonExistingPattern() {
        int index = NaiveSubstringSearch.search(longText, nonExistingPattern);
        assertEquals(-1, index); // Паттерн не должен находиться
    }

    @Test
    void testSearchPatternAtStart() {
        int index = NaiveSubstringSearch.search(longText, patternAtStart);
        assertEquals(0, index); // Паттерн в начале текста
    }

    @Test
    void testSearchPatternAtEnd() {
        int index = NaiveSubstringSearch.search(longText, patternAtEnd);
        assertEquals(50, index); // "yz" в конце текста (позиция 50)
    }

    @Test
    void testSearchEmptyPattern() {
        int index = NaiveSubstringSearch.search(longText, emptyPattern);
        assertEquals(0, index); // Пустой паттерн должен находиться в начале
    }

    @Test
    void testSearchWithMultipleOccurrences() {
        List<Character> text = "abababab".chars().mapToObj(c -> (char)c).collect(Collectors.toList());
        List<Character> pattern = "aba".chars().mapToObj(c -> (char)c).collect(Collectors.toList());
        int index = NaiveSubstringSearch.search(text, pattern);
        assertEquals(0, index); // Должен вернуть первое вхождение
    }

    @Test
    void testSearchWithDifferentTypes() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> pattern = Arrays.asList(3, 4, 5);
        int index = NaiveSubstringSearch.search(numbers, pattern);
        assertEquals(2, index); // Проверяем работу с другим типом данных
    }

    @Test
    void testSearchPatternLongerThanText() {
        List<Character> shortText = "abc".chars().mapToObj(c -> (char)c).collect(Collectors.toList());
        List<Character> longPattern = "abcdef".chars().mapToObj(c -> (char)c).collect(Collectors.toList());
        int index = NaiveSubstringSearch.search(shortText, longPattern);
        assertEquals(-1, index); // Паттерн длиннее текста
    }

    @Test
    void testFunctionalInterface() {
        SubstringSearchFunction<Character> searchFunc = NaiveSubstringSearch::search;
        int index = searchFunc.search(longText, existingPattern);
        assertEquals(3, index); // Проверяем работу функционального интерфейса
    }
}