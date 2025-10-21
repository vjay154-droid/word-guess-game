package no.github.vijay.wordguessgame;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

class WordGuessGameTest {

    @Test
    void testCorrectGreenFeedback() {
        WordGuessGame game = new WordGuessGame("WATER");
        Feedback[] feedback = game.guess("Wxxxx");

        assertEquals(Feedback.GREEN, feedback[0]); // W is correct and in position
        for (int i = 1; i < Constants.WORD_LENGTH; i++) {
            assertEquals(Feedback.GRAY, feedback[i]);
        }
    }

    @Test
    void testCorrectYellowFeedback() {
        WordGuessGame game = new WordGuessGame("WATER");
        Feedback[] feedback = game.guess("Txxxx");

        assertEquals(Feedback.YELLOW, feedback[0]); // T exists but wrong position
        for (int i = 1; i < Constants.WORD_LENGTH; i++) {
            assertEquals(Feedback.GRAY, feedback[i]);
        }
    }

    @Test
    void testDuplicateLetterHandling() {
        WordGuessGame game = new WordGuessGame("WATER");
        Feedback[] feedback = game.guess("TTTTT");

        Feedback[] expected = {
                Feedback.GRAY,   // first T
                Feedback.GRAY,   // second T
                Feedback.GREEN,  // third T matches correct position
                Feedback.GRAY,   // fourth T
                Feedback.GRAY    // fifth T
            };

            assertArrayEquals(expected, feedback);
    }
    
    @Test
    void testDuplicateLettersInDifferentOrder() {
    	WordGuessGame game = new WordGuessGame("WATER");
        Feedback[] feedback = game.guess("TTBTT");
        
        assertEquals(Feedback.YELLOW, feedback[0]);
        for (int i = 1; i < Constants.WORD_LENGTH; i++) {
            assertEquals(Feedback.GRAY, feedback[i]);
        }
    }

    @Test
    void testFullCorrectWord() {
        WordGuessGame game = new WordGuessGame("WATER");
        Feedback[] feedback = game.guess("WATER");

        for (Feedback f : feedback) {
            assertEquals(Feedback.GREEN, f);
        }
    }

    @Test
    void testAllIncorrectLetters() {
        WordGuessGame game = new WordGuessGame("WATER");
        Feedback[] feedback = game.guess("PLUMB");

        for (Feedback f : feedback) {
            assertEquals(Feedback.GRAY, f);
        }
    }
    
    @Test
    void testMaxAttempts() {
        WordGuessGame game = new WordGuessGame("WATER");
        int maxGuesses = 5;

        for (int i = 0; i < maxGuesses; i++) {
            Feedback[] feedback = game.guess("PLUMB"); // wrong guess
            assertNotNull(feedback);
        }
    }
    
    @Test
    void testWordListLoading() throws IOException {
        List<String> words = WordLoader.loadWords("words.txt");
        // Filter only 5-letter words
        words.removeIf(word -> word.length() != Constants.WORD_LENGTH);
        assertFalse(words.isEmpty(), "Word list must contain at least one 5-letter word");
    }
    
    @Test
    void testInvalidGuessLength() {
        WordGuessGame game = new WordGuessGame("WATER");
        assertThrows(IllegalArgumentException.class, () -> game.guess("TOO"));
        assertThrows(IllegalArgumentException.class, () -> game.guess("TOOLONG"));
    }
}
