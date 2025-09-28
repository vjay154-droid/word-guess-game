package no.github.vijay.wordguessgame;

public class WordGuessGame {

	private final String answer;
	public WordGuessGame(String answer) {
		this.answer = answer.toUpperCase();
		if(this.answer.length() != 5) {
			throw new IllegalArgumentException("Answer must be 5 letters.");
		}
	}

	public Feedback[] guess(String guess) {
		guess = guess.toUpperCase();
		
		if(guess.length() != 5) {
			throw new IllegalArgumentException("Guess must be 5 letters.");
		}
		
		Feedback[] feedbacks = new Feedback[5];
		boolean[] usedLetters = new boolean[5]; 
		
		for(int i = 0; i < 5; i++) {
			if(guess.charAt(i) == answer.charAt(i)) {
				feedbacks[i] = Feedback.GREEN;
				usedLetters[i] = true;
			}
		}
		
		for(int i = 0; i < 5; i++) {
			if(feedbacks[i] == null) {
				char ch = guess.charAt(i);
				boolean found = false;
				for(int j = 0; j< 5; j++) {
					if(!usedLetters[j] && answer.charAt(j) == ch) {
						usedLetters[j] = true;
						found = true;
						break;
					}
				}
				feedbacks[i] = found ? Feedback.YELLOW : Feedback.GRAY;
			}
		}
		return feedbacks;
	}

}
