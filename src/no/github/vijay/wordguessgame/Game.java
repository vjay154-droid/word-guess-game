package no.github.vijay.wordguessgame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
	public static void main(String[] args) throws IOException {
		List<String> words = Files.readAllLines(Paths.get("words.txt"));
		
		Iterator<String> iterator = words.iterator();
		while(iterator.hasNext()) {
			if(iterator.next().length() != Constants.WORD_LENGTH) {
				iterator.remove();
			}
		}
		
		
		Random random = new Random();
		if (words.isEmpty()) {
		    throw new IllegalStateException("Word list is empty! Please check words.txt.");
		}
		String answer = words.get(random.nextInt(words.size()));
		WordGuessGame game = new WordGuessGame(answer);
		printIntroduction();

		try (Scanner sc = new Scanner(System.in)) {
			int attempts = 0;
			
			while(attempts < Constants.MAX_GUESS) {
				System.out.println("Enter guess (" + (Constants.MAX_GUESS - attempts) + " left): ");
				String guess = sc.nextLine().trim();
				
				try {
					Feedback[] feedback = game.guess(guess); 
					printFeedback(guess, feedback);
					
					attempts++;
					
					if(guess.equalsIgnoreCase(answer)) {
						System.out.println("Congratulations! You guessed the word!");
			            return;
					}
					
				}catch (IllegalArgumentException ex) {
					 System.out.println("Invalid guess: " + ex.getMessage());
				}
			}
		}
		System.out.println("Out of guesses! The word was: " + answer);
	}

	private static void printFeedback(String guess, Feedback[] feedback) {
		for(int i = 0; i < Constants.MAX_GUESS; i++) {
			char ch = guess.toUpperCase().charAt(i);
			
			switch(feedback[i]) {
				case GREEN:
					System.out.print("[ " + ch + " ]");
                    break;
				case YELLOW: 
					System.out.print("( " + ch + " )");
                    break;
				case GRAY:
					System.out.print(" " + ch + " ");
                    break;
			}
		}
		System.out.println();
	}

	private static void printIntroduction() {
		System.out.println("Welcome to the Word Guess Game!");
        System.out.println("Rules:");
        System.out.println(" - You have 5 guesses.");
        System.out.println(" - All words are 5 letters.");
        System.out.println(" - GREEN = correct letter & position.");
        System.out.println(" - YELLOW = letter exists but wrong position.");
        System.out.println(" - GRAY = letter not in the word.");
        System.out.println("Let's begin!\n");
	}
}
