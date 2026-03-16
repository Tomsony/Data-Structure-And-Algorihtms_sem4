package string;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/// Класс заполняет файл случайной последовательностью нуклеотидов
public class DNAGenerator {

    public static void main(String[] args) {
        int length = 1_000_000;
        String dna = generateRandomDNA(length);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dna_sequence.txt"))) {
            writer.write(dna);
            System.out.println("Последовательность успешно записана в файл dna_sequence.txt");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public static String generateRandomDNA(int length) {
        char[] nucleotides = {'A', 'T', 'C', 'G'};
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            char nucleotide = nucleotides[random.nextInt(4)];
            sb.append(nucleotide);

            // Добавляем перенос строки каждые 100 символов
            if ((i + 1) % 100 == 0 && i != length - 1) {
                sb.append('\n');
            }
        }

        return sb.toString();
    }
}