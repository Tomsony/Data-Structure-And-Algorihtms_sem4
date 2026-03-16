package greedy_alg;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class GreedyCoinChangeTest {

    @Test
    public void testExactChange() {
        int[] coins = {25, 10, 5, 1};
        int amount = 63;
        List<Integer> expected = Arrays.asList(25, 25, 10, 1, 1, 1);
        assertEquals(expected, GreedyCoinChange.greedyCoinChange(coins, amount));
    }

    @Test
    public void testZeroAmount() {
        int[] coins = {10, 5, 1};
        int amount = 0;
        List<Integer> expected = Arrays.asList(); // Пустой список
        assertEquals(expected, GreedyCoinChange.greedyCoinChange(coins, amount));
    }

    @Test(expected = RuntimeException.class)
    public void testNoSolution() {
        int[] coins = {5, 3}; // Невозможно разменять 7
        int amount = 7;
        GreedyCoinChange.greedyCoinChange(coins, amount);
    }

    @Test
    public void testSingleCoin() {
        int[] coins = {10, 5, 1};
        int amount = 10;
        List<Integer> expected = Arrays.asList(10);
        assertEquals(expected, GreedyCoinChange.greedyCoinChange(coins, amount));
    }

    @Test
    public void testLargeAmount() {
        int[] coins = {50, 25, 10, 1};
        int amount = 99;
        List<Integer> expected = Arrays.asList(50, 25, 10, 10, 1, 1, 1, 1);
        assertEquals(expected, GreedyCoinChange.greedyCoinChange(coins, amount));
    }
}