package string;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class SubstringDNAtest {

    public static void main(String[] args) {
        String filename = "dna_sequence.txt";
        String pattern = "AGATA"; // Пример паттерна для поиска

        try {
            // Читаем DNA-последовательность из файла
            String dnaSequence = readDNAFromFile(filename);
            System.out.println("Длина DNA-последовательности: " + dnaSequence.length());

            // Преобразуем строки в списки символов
            List<Character> dnaList = stringToList(dnaSequence);
            List<Character> patternList = stringToList(pattern);

            // Выполняем поиск
            int index = NaiveSubstringSearch.search(dnaList, patternList);

            if (index != -1) {
                System.out.println("Паттерн найден на позиции: " + index);
            } else {
                System.out.println("Паттерн не найден в последовательности");
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    /**
     * Читает DNA-последовательность из файла, удаляя переносы строк
     */
    public static String readDNAFromFile(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    /**
     * Преобразует строку в список символов
     */
    public static List<Character> stringToList(String str) {
        List<Character> list = new ArrayList<>();
        for (char c : str.toCharArray()) {
            list.add(c);
        }
        return list;
    }
}