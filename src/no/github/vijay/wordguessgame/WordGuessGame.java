package no.github.vijay.wordguessgame;

public class WordGuessGame {

	private final String answer;
	public WordGuessGame(String answer) {
		this.answer = answer.toUpperCase();
		if(this.answer.length() != Constants.WORD_LENGTH) {
			throw new IllegalArgumentException("Answer must be " + Constants.WORD_LENGTH + " letters.");
		}
	}

	public Feedback[] guess(String guess) {
		guess = guess.toUpperCase();
		
		if(guess.length() != Constants.WORD_LENGTH) {
			throw new IllegalArgumentException("Guess must be " + Constants.WORD_LENGTH + " letters.");
		}
		
		Feedback[] feedbacks = new Feedback[Constants.WORD_LENGTH];
		boolean[] usedLetters = new boolean[Constants.WORD_LENGTH]; 
		
		for(int i = 0; i < Constants.WORD_LENGTH; i++) {
			if(guess.charAt(i) == answer.charAt(i)) {
				feedbacks[i] = Feedback.GREEN;
				usedLetters[i] = true;
			}
		}
		
		for(int i = 0; i < Constants.WORD_LENGTH; i++) {
			if(feedbacks[i] == null) {
				char ch = guess.charAt(i);
				boolean found = false;
				for(int j = 0; j< Constants.WORD_LENGTH; j++) {
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
