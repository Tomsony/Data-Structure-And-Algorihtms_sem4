package greedy_alg;

import java.util.ArrayList;
import java.util.List;

///  Алгоритм реализует Жадный алгоритм размена монет
/// общая сложность алгоритма составляет O(k), где k - количество различных номиналов монет
/// Если бы монеты не были отсортированы, сложность могла бы быть выше, так как потребовался бы их предварительный сортировка (O(k log k))
public class GreedyCoinChange {

    public static void main(String[] args) {
        int[] coins = {25, 10, 5, 1}; // Монеты должны быть отсортированы в убывающем порядке
        int amount = 63;

        List<Integer> result = greedyCoinChange(coins, amount);
        System.out.println("Монеты для размена: " + result);
        System.out.println("Количество монет: " + result.size());
    }

    /**
     * Жадный алгоритм размена монет
     * @param coins - массив монет, отсортированный в убывающем порядке
     * @param amount - сумма для размена
     * @return список использованных монет
     */
    public static List<Integer> greedyCoinChange(int[] coins, int amount) {
        List<Integer> change = new ArrayList<>();

        for (int coin : coins) {
            while (amount >= coin) {
                change.add(coin);
                amount -= coin;
            }
        }

        if (amount != 0) {
            throw new RuntimeException("Невозможно разменять сумму с данными монетами");
        }

        return change;
    }
}