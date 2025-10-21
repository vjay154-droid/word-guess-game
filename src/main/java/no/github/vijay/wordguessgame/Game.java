package no.github.vijay.wordguessgame;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
	
	public static final String GREEN_SYMBOL = "\u001B[32m";
	public static final String YELLOW_SYMBOL = "\u001B[33m";
	public static final String GRAY_SYMBOL = "\u001B[37m";
    
	public static void main(String[] args) throws IOException {
		List<String> words = WordLoader.loadWords("words.txt");
		String answer = pickRandomWord(words);

        WordGuessGame game = new WordGuessGame(answer);

        printIntroduction();
        playGame(game, answer);
	}
	
	private static String pickRandomWord(List<String> words) {
        return words.get(new Random().nextInt(words.size()));
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
                    sb.append(GREEN_SYMBOL).append(ch).append(GREEN_SYMBOL);
                    break;
                case YELLOW:
                    sb.append(YELLOW_SYMBOL).append(ch).append(YELLOW_SYMBOL);
                    break;
                case GRAY:
                    sb.append(GRAY_SYMBOL).append(ch).append(GRAY_SYMBOL);
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
