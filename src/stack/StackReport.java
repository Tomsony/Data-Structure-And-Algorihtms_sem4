package stack;

public class StackReport {
        private static int depth = 0;

        public static void recursiveCall() {
            depth++;
            recursiveCall(); // Бесконечная рекурсия
        }

        public static void main(String[] args) {
            try {
                recursiveCall();
            } catch (StackOverflowError e) { // Стек переполняется
                // depth здесь - это количество вызовов, уместившихся в стек до переполнения
                // переполнение стека - фиксированный объем памяти выделенный JVM для стека вызова первышен
                System.out.println("Max depth: " + depth);
            }
        }
}
