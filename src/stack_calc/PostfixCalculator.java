package stack_calc;

import stack.Stack;

public class PostfixCalculator {
    private Stack<Double> stack;

    public PostfixCalculator() {
        stack = new Stack<>();
    }

    /**
     * Вычисляет результат постфиксного выражения
     * @param expression строка с постфиксным выражением (токены разделены пробелами)
     * @return результат вычисления
     * @throws IllegalArgumentException при неверном формате выражения
     */
    public double evaluate(String expression) {
        stack.clear(); // Очищаем стек перед вычислением

        String[] tokens = expression.split(" ");
        for (String token : tokens) {
            if (isNumber(token)) {
                // Если токен - число, помещаем в стек
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                // Если токен - оператор, выполняем операцию
                performOperation(token.charAt(0));
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression format");
        }

        return stack.pop();
    }

    /**
     * Проверяет, является ли строка числом
     */
    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Проверяет, является ли строка оператором
     */
    private boolean isOperator(String str) {
        return str.length() == 1 && "+-*/".contains(str);
    }

    /**
     * Выполняет арифметическую операцию
     */
    private void performOperation(char operator) {
        if (stack.size() < 2) {
            throw new IllegalArgumentException("Not enough operands for operation " + operator);
        }

        double b = stack.pop();
        double a = stack.pop();
        double result;

        switch (operator) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                result = a / b;
                break;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }

        stack.push(result);
    }
}