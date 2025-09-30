package no.github.vijay.wordguessgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Game {
	
	private static final String GREEN_SYMBOL = "[ %s ]";
    private static final String YELLOW_SYMBOL = "( %s )";
    private static final String GRAY_SYMBOL = " %s ";
    
	public static void main(String[] args) throws IOException {
		List<String> words = loadWords("words.txt");
		String answer = pickRandomWord(words);

        WordGuessGame game = new WordGuessGame(answer);

        printIntroduction();
        playGame(game, answer);
	}
	
	private static String pickRandomWord(List<String> words) {
        return words.get(new Random().nextInt(words.size()));
    }
	
	private static List<String> loadWords(String resourcePath) throws IOException {
		try (InputStream is = Game.class.getClassLoader().getResourceAsStream(resourcePath)) {
	        if (is == null) {
	            throw new IllegalStateException("Resource file not found: " + resourcePath);
	        }

	        List<String> words = new BufferedReader(new InputStreamReader(is))
	                .lines()
	                .collect(Collectors.toList());

	        words.removeIf(word -> word.length() != Constants.WORD_LENGTH);

	        if (words.isEmpty()) {
	            throw new IllegalStateException("Word list is empty after filtering!");
	        }

	        return words;
	    }
	}
	
	private static void playGame(WordGuessGame game, String answer) {
        try (Scanner sc = new Scanner(System.in)) {
            int attempts = 0;

            while (attempts < Constants.MAX_GUESS) {
                System.out.print("Enter guess (" + (Constants.MAX_GUESS - attempts) + " left): ");
                String guess = sc.nextLine().trim();

                try {
                    Feedback[] feedback = game.guess(guess);
                    System.out.println(renderFeedback(guess, feedback));
                    attempts++;

                    if (guess.equalsIgnoreCase(answer)) {
                        System.out.println("🎉 Congratulations! You guessed the word!");
                        return;
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("❌ Invalid guess: " + ex.getMessage());
                }
            }
        }
        System.out.println("❌ Out of guesses! The word was: " + answer);
    }
	
	private static String renderFeedback(String guess, Feedback[] feedback) {
		StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Constants.WORD_LENGTH; i++) {
            char ch = Character.toUpperCase(guess.charAt(i));
            switch (feedback[i]) {
                case GREEN:
                    sb.append(String.format(GREEN_SYMBOL, ch));
                    break;
                case YELLOW:
                    sb.append(String.format(YELLOW_SYMBOL, ch));
                    break;
                case GRAY:
                    sb.append(String.format(GRAY_SYMBOL, ch));
                    break;
            }
        }
        return sb.toString();
	}

	private static void printIntroduction() {
		System.out.println("Welcome to the Word Guess Game!");
        System.out.println("Rules:");
        System.out.println(" - You have " + Constants.MAX_GUESS + "guesses.");
        System.out.println(" - All words are "+Constants.WORD_LENGTH+ " letters.");
        System.out.println(" - GREEN = correct letter & position.");
        System.out.println(" - YELLOW = letter exists but wrong position.");
        System.out.println(" - GRAY = letter not in the word.");
        System.out.println("Let's begin!\n");
	}
}
