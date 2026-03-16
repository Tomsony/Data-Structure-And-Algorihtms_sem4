package recursion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NOD_test {

    @Test
    public void testNODWithZero() {
        assertEquals(5, NOD.nod(5, 0));  // НОД(5, 0) = 5
        assertEquals(7, NOD.nod(0, 7));  // НОД(0, 7) = 7
        assertEquals(0, NOD.nod(0, 0));  // НОД(0, 0) = 0
    }

    @Test
    public void testNODOfCoprimeNumbers() {
        assertEquals(1, NOD.nod(8, 15));
        assertEquals(1, NOD.nod(17, 25));
    }

    @Test
    public void testNODOfEqualNumbers() {
        assertEquals(12, NOD.nod(12, 12));  // НОД(12, 12) = 12
        assertEquals(100, NOD.nod(100, 100));
    }

    @Test
    public void testNODOfRegularNumbers() {
        assertEquals(14, NOD.nod(56, 98));  // Пример из main()
        assertEquals(6, NOD.nod(54, 24));   // НОД(54, 24) = 6
        assertEquals(21, NOD.nod(1071, 462)); // НОД(1071, 462) = 21
    }
}
