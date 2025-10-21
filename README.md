# Word Guess Game (Assessment Solution)

## Introduction

This is a command-line word guessing game inspired by *Wordle*.
The player has **5 attempts** to guess a hidden **5-letter word**.

### Rules

* You have 5 guesses to find the word.
* Words must be exactly 5 letters long.
* Feedback for each guess:

  * **\[X]** = correct letter in the correct position (GREEN)
  * **(X)** = correct letter but wrong position (YELLOW)
  * **X** = letter not in the word (**GRAY**, chosen to match Wordle conventions; gray is neutral and avoids implying error)
* Duplicate handling: if a guess has more occurrences of a letter than the answer, the extras are marked **GRAY**.

---

## Implementation

* **`WordGuessGame`** → Core logic for comparing guesses.
* **`Feedback`** → Enum for GREEN, YELLOW, GRAY.
* **`Game`** → Main class that runs the game loop and user interaction.
* **`words.txt`** → Simple word list (one word per line).

A random 5-letter word is chosen from `words.txt` when the game starts.

---

## Unit Tests

* Implemented with **JUnit 5**.
* Tests cover:

  * Correct GREEN/YELLOW behavior.
  * Duplicate letter handling (e.g., `WATER` vs `OTTER`).
  * Fully correct and fully incorrect guesses for completeness.

---

## Requirements

* Java 17 (tested; should also work on Java 11+)
* Eclipse, IntelliJ, or any Java IDE with **JUnit 5**

---

## How to Run

1. Open the project in **Eclipse** (or any Java IDE).
2. Ensure **Java 17** is installed.
3. Place the `words.txt` file in the resource folder.
4. Run `Game.java` as a **Java Application**.

### Running Tests

1. Make sure **JUnit 5** is added to your project.
2. Right-click `WordGuessGameTest.java` → **Run As → JUnit Test**.
