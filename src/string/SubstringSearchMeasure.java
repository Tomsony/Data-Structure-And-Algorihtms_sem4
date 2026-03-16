package string;

import java.util.List;

/**
 * Функциональный интерфейс для поиска подпоследовательности в списке элементов.
 * @param <T> тип элементов в списке
 */
@FunctionalInterface
interface SubstringSearchFunction<T> {
    /**
     * Поиск подпоследовательности в списке
     * @param text список, в котором ищем
     * @param pattern подпоследовательность для поиска
     * @return индекс начала подпоследовательности или -1 если не найдено
     */
    int search(List<T> text, List<T> pattern);
}

/// Класс для измерения времени выполнения поиска подпоследовательности
/// @author Артём
public class SubstringSearchMeasure {

    /**
     * Метод для измерения времени выполнения поиска подпоследовательности
     * @param <T> тип элементов в списках
     * @param text список, в котором ищем
     * @param pattern подпоследовательность для поиска
     * @param searchFunc функция поиска
     * @return время выполнения в наносекундах
     */
    public static <T> long measureSubstringSearchTime(
            List<T> text,
            List<T> pattern,
            SubstringSearchFunction<T> searchFunc) {
        long startTime = System.nanoTime();  // Фиксируем начальное время
        searchFunc.search(text, pattern);   // Выполняем поиск
        return System.nanoTime() - startTime; // Возвращаем затраченное время
    }
}

/**
 * Класс, реализующий наивный алгоритм поиска подпоследовательности
 */
/// @author Артём
class NaiveSubstringSearch {
    /**
     * Наивный алгоритм поиска подпоследовательности в списке
     * @param <T> тип элементов списка
     * @param text список, в котором ищем
     * @param pattern подпоследовательность для поиска
     * @return индекс начала подпоследовательности или -1 если не найдено
     */
    public static <T> int search(List<T> text, List<T> pattern) {
        int n = text.size();    // Длина текста
        int m = pattern.size(); // Длина образца

        // Перебираем все возможные начальные позиции в тексте
        for (int i = 0; i <= n - m; i++) {
            int j;  // Счетчик для сравнения элементов образца

            // Последовательно сравниваем элементы образца с элементами текста
            for (j = 0; j < m; j++) {
                // Если элементы не совпадают - прерываем сравнение
                if (!text.get(i + j).equals(pattern.get(j))) {
                    break;
                }
            }

            // Если весь образец пройден - совпадение найдено
            if (j == m) {
                return i;  // Возвращаем начальную позицию
            }
        }
        return -1;  // Совпадение не найдено
    }
}