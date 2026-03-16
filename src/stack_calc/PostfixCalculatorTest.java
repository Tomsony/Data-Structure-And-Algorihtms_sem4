package stack_calc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PostfixCalculatorTest {
    private PostfixCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new PostfixCalculator();
    }

    // Тесты простых операций
    @Test
    void testAddition() {
        assertEquals(5, calculator.evaluate("2 3 +"));
    }

    @Test
    void testSubtraction() {
        assertEquals(1, calculator.evaluate("3 2 -"));
    }

    @Test
    void testMultiplication() {
        assertEquals(6, calculator.evaluate("2 3 *"));
    }

    @Test
    void testDivision() {
        assertEquals(2.5, calculator.evaluate("5 2 /"));
    }

    // Тесты сложных выражений
    @Test
    void testComplexExpression1() {
        assertEquals(14, calculator.evaluate("5 1 2 + 4 * + 3 -"));
    }

    @Test
    void testComplexExpression2() {
        assertEquals(6.5, calculator.evaluate("15 7 1 1 + - / 3 * 2 1 1 + + -"));
    }
}

// Тесты обработки ошибок

