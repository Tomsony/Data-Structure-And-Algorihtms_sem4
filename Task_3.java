public class Task_3 {
        private static int depth = 0;

        public static void recursiveCall() {
            depth++;
            recursiveCall(); // Бесконечная рекурсия
        }

        public static void main(String[] args) {
            try {
                recursiveCall();
            } catch (StackOverflowError e) {
                System.out.println("Max depth: " + depth);
            }
        }
}
