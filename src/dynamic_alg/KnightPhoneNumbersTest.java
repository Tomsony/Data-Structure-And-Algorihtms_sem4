package dynamic_alg;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KnightPhoneNumbersTest {

    @Test
    public void testN1() {
        // Тест для длины 1 (7 допустимых номеров: 1,2,3,4,6,7,9)
        assertEquals(7, KnightPhoneNumbers.calculateValidNumbers(1));
    }

    @Test
    public void testN2() {
        // Тест для длины 2 (16 допустимых комбинаций)
        assertEquals(16, KnightPhoneNumbers.calculateValidNumbers(2));
    }

    @Test
    public void testN3() {
        // Тест для длины 3 (36 допустимых комбинаций)
        assertEquals(36, KnightPhoneNumbers.calculateValidNumbers(3));
    }

    @Test
    public void testN4() {
        // Тест для длины 4 (82 допустимых комбинации)
        assertEquals(82, KnightPhoneNumbers.calculateValidNumbers(4));
    }

    @Test
    public void testN10() {
        // Тест для длины 4 (82 допустимых комбинации)
        assertEquals(11728, KnightPhoneNumbers.calculateValidNumbers(10));
    }

    @Test
    public void testInvalidN0() {
        // Тест недопустимого ввода (N=0)
        assertEquals(-1, KnightPhoneNumbers.calculateValidNumbers(0));
    }

    @Test
    public void testInvalidN101() {
        // Тест недопустимого ввода (N=101)
        assertEquals(-1, KnightPhoneNumbers.calculateValidNumbers(101));
    }
}