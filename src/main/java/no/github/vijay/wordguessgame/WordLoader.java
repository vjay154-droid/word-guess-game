package no.github.vijay.wordguessgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class WordLoader {

    public static List<String> loadWords(String resourcePath) throws IOException {
        InputStream is = WordLoader.class.getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new IllegalStateException("Resource not found: " + resourcePath);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            List<String> words = br.lines()
                    .filter(word -> word.length() == 5) // only 5-letter words
                    .collect(Collectors.toList());

            if (words.isEmpty()) {
                throw new IllegalStateException("No 5-letter words found in: " + resourcePath);
            }
            return words;
        }
    }
}
