package recursion;

public class NOD {
    // Алгоритм найти НОД (наибольший общий делитель) двух натуральных чисел
    public static void main(String[] args) {
        int a = 56;
        int b = 98;
        System.out.println("НОД чисел " + a + " и " + b + " = " + nod(a, b));
    }

    /** Рекурсивная функция для нахождения НОД
     * @return a возвращает первое число, если второе число нулевое
     * @return nod(a,b) рекурсивно возвращает метод, передавая в аргументах второе число и остаток от деления первого числа на второе
    */
    public static int nod(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return nod(b, a % b);
        }
    }
}