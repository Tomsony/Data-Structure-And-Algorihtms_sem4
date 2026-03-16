package dynamic_alg;

import java.util.Scanner;

public class KnightPhoneNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите желаемую длину телефонного номера");
        int N = scanner.nextInt();
        long result = calculateValidNumbers(N);
        System.out.println(result);
    }

    public static long calculateValidNumbers(int N) {
        if (N <= 0 || N > 100) {
            System.out.println("Неверное значение N");
            return -1;
        }

        // Для каждой цифры (0-9) храним массив цифр, на которые можно попасть ходом коня
        int[][] moves = {
                {4, 6},    // 0
                {6, 8},    // 1
                {7, 9},    // 2
                {4, 8},    // 3
                {0, 3, 9}, // 4
                {},        // 5 (не используется, так как с 5 нельзя начать и на нее нельзя попасть)
                {0, 1, 7}, // 6
                {2, 6},    // 7
                {1, 3},    // 8
                {2, 4}     // 9
        };

        // Инициализация DP таблицы
        long[][] dp = new long[N + 1][10];

        // Базовый случай: для N=1 каждая допустимая начальная цифра дает 1 вариант
        // Номер не может начинаться с 0 или 8
        for (int digit = 0; digit <= 9; digit++) {
            if (digit != 0 && digit != 8 && digit != 5) {
                dp[1][digit] = 1;
            }
        }

        // Заполнение DP таблицы
        for (int length = 2; length <= N; length++) { // Перебираем все возможные длины номеров
            // Для каждой цифры digit (от 0 до 9) мы хотим узнать, сколько номеров длины length
            // заканчиваются на эту цифру
            for (int digit = 0; digit <= 9; digit++) {
                if (digit == 5) continue; // цифра 5 не используется
                //  перебираем все цифры, из которых можно прийти в digit ходом коня
                // например, если digit = 0, то moves[0] = {4, 6}
                for (int prevDigit : moves[digit]) {
                    dp[length][digit] += dp[length - 1][prevDigit];
                }
            }
        }

        // Суммируем все варианты для длины N
        long total = 0;
        for (int digit = 0; digit <= 9; digit++) {
            if (digit != 5) {
                total += dp[N][digit];
            }
        }

        return total;}
}