package hangman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * Class for playing traditional hangman
 * @author sina
 *
 */
public abstract class Hangman {
	
	// variables
	/**
	 * String for storing the secret word user is trying to guess
	 */
	protected static String secretWord;

	/**
	 * char for storing the letter user has last guessed
	 */
	protected static char guessedLetter;

	/**
	 * <ArrayList<Character> list of characters, representing user-viewable hangman sequence
	 */
	protected static ArrayList<Character> hangman = new ArrayList<Character>();
	
	/**
	 * Set<Character> list of user's guesses
	 */
	protected static Set<Character> userLetterGuessSet = new HashSet<Character>();
	
	/**
	 * Set<Character> list of user's correct guesses
	 */
	protected static Set<Character> correctLetterList = new HashSet<Character>();
	
	/**
	 * Set<Character> list of user's correct guesses
	 */
	protected static Set<Character> incorrectLetterList = new HashSet<Character>();

	// getters and setters

	/**
	 * gets String secretWord
	 * @return the secretWord
	 */
	protected static String getSecretWord() {
		return secretWord;
	}

	/**
	 * sets String secretWord
	 * @param secretWord the secretWord to set
	 */
	protected static void setSecretWord(String secretWord) {
		Hangman.secretWord = secretWord;
	}

	/**
	 * gets char guessedLetter
	 * @return the guessedLetter
	 */
	protected static char getGuessedLetter() {
		return guessedLetter;
	}

	/**
	 * sets char guessedLetter
	 * @param guessedLetter the guessedLetter to set
	 */
	protected static void setGuessedLetter(char guessedLetter) {
		Hangman.guessedLetter = guessedLetter;
	}
	
	/**
	 * gets ArrayList<Character> hangman
	 * @return the hangman
	 */
	protected static ArrayList<Character> getHangman() {
		return hangman;
	}

	/**
	 * sets ArrayList<Character> hangman
	 * @param hangman the hangman to set
	 */
	protected static void setHangman(ArrayList<Character> hangman) {
		Hangman.hangman = hangman;
	}

	/**
	 * gets Set<Character> userLetterGuessSet
	 * @return the userLetterGuessSet
	 */
	protected static Set<Character> getUserLetterGuessSet() {
		return userLetterGuessSet;
	}

	/**
	 * sets Set<Character> userLetterGuessSet
	 * @param userLetterGuessSet the userLetterGuessSet to set
	 */
	protected static void setUserLetterGuessSet(Set<Character> userLetterGuessSet) {
		Hangman.userLetterGuessSet = userLetterGuessSet;
	}

	/**
	 * gets Set<Character> correctLetterList
	 * @return the correctLetterList
	 */
	protected static Set<Character> getCorrectLetterList() {
		return correctLetterList;
	}

	/**
	 * sets Set<Character> correctLetterList
	 * @param correctLetterList
	 */
	protected static void setCorrectLetterList(Set<Character> correctLetterList) {
		Hangman.correctLetterList = correctLetterList;
	}


	/**
	 * gets Set<Character> incorrectLetterList
	 * @return
	 */
	protected static Set<Character> getIncorrectLetterList() {
		return incorrectLetterList;
	}


	/**
	 * sets Set<Character> incorrectLetterList
	 * @param incorrectLetterList
	 */
	protected static void setIncorrectLetterList(Set<Character> incorrectLetterList) {
		Hangman.incorrectLetterList = incorrectLetterList;
	}
	

	// methods
	
	
	/**
	 * initializes game by picking a random word from dictionary
	 * @param wordsmain
	 */
	public static void initializeGame(Set<String> wordsmain) {
		
		// get variables
		// this.secretWord = getSecretWord();
		
		// Pick a secret word for ArrayList
		  
		// create a random generator
		Random rand = new Random();
		
		// generate a random number between 0 and Hashset size - 1
		int randNum = rand.nextInt(wordsmain.size());
		
		// create an iterator for to iterate through list of cleaned words
		Iterator<String> it = wordsmain.iterator();
		
		// creates a current index
		int i = 0;
		
		// while loop that ends with last String in Set
		
		while (it.hasNext()) {
		  
			// define variable next to look at the next string String next = it.next();
			String word = it.next();
			
			// if current index is equal to random number
			if (i == randNum) {
				secretWord = word;
				break;
			}
			
			// increse current index
			i++;
		}
		
		// define a letter placeholder
		Character letterPlaceholder = '_';		
		
		// add as many letter place holders to hangman printout as there are letters in secret word
		int j;
		for (j = 0; j < secretWord.length(); j++) {
			hangman.add(letterPlaceholder);
			System.out.print("_");
		}
		
		// break line
		System.out.println();

		// set secret word
		setSecretWord(secretWord);
	}
	
	/**
	 * collects user's input (guess of a letter) from keyboard scanner
	 * @param scanner
	 */
	public final static void userMakesGuess(Scanner scanner) {			
		System.out.println("Please guess a letter.");

		// get user input
		try {
			// try getting a single char input from user
			guessedLetter = scanner.next().charAt(0);
		} catch (Exception e) {
			// if not, print a friendly message
			System.out.println("Please enter valid inputs.");
			// and print stack trace
			e.printStackTrace();
		}
		
		System.out.println("You guessed: " + guessedLetter);
		
		// set guessed letter
		setGuessedLetter(guessedLetter);
	}
	

	/**
	 * checks to make sure user guess (guessedLetter) is not repetitive
	 * @return boolean eligible - is user's guess repetitive
	 */
	public static boolean checkUserGuess() {
		
		// define a boolean to represent whether a guess is repetitive
		boolean repetitive = false;
		
		// check to see if guessed letters set is empty
		if (!userLetterGuessSet.isEmpty()) {
			// if set is not empty, look for guessed letter inside the secret word
			if (userLetterGuessSet.contains(Character.valueOf(guessedLetter))) {
				// if character is repetitive, politely remind the user
				System.out.println("Sorry. Looks like you guessed this letter before.");
				// an indicate it is repetitive
				repetitive = true;
			} else {
				// otherwise, indicate that guessed letter is not repetitive
				repetitive = false;
			}
		}
		
		// return if test passed of railed
		return repetitive;
	}

	/**
	 * Adds user's guessed letter to the list of user guesses
	 */
	public static void userGuessTracker() {
		userLetterGuessSet.add(guessedLetter);
	}
	
	/**
	 * Implements user's guess by performing necessary comparisons with secret word
	 */
	public static void implementGuess() {
		
		// and then adds to either the list of correct guesses, or the list of incorrect guesses
		// check to see if user guessed correct or incorrect letter
		
		if (secretWord.contains(String.valueOf(guessedLetter))) {
			
			// if guess is good, add to list of good guesses
			correctLetterList.add(guessedLetter);
			
			// inform user
			System.out.println("Correct Guess");
		
		} else if (secretWord.contains(String.valueOf(guessedLetter)) == false) {
			
			// if guess is bad, add to list of bad guesses
			incorrectLetterList.add(guessedLetter);
			
			// inform user
			System.out.println("Inorrect Guess");
		}
		
	}
	
	/**
	 * prints visual output for hangman platform
	 * @param guessCount
	 */
	public static void printHangmanSequence(int guessCount) {
		
		// iterate through length of word with variable i
		int i;
		for (i = 0; i < secretWord.length(); i++) {
			
			// set up a bool to indicate if i = j
			boolean found = false;
			
			// iterate through each of the user's right guesses with variable j
			for (Character j : correctLetterList) {
				
				if (secretWord.charAt(i) == j) {
					// if secretWord's letter is already guessed, print the letter
					System.out.print(j);
				
					// set a variable called found to true
					found = true;
					
					// and move onto the next character in the word (i)
					break;
				} 
			}
			
			// if secretWord's letter is not found print "_"
			if (found == false) {
				System.out.print("_");
			}
		}
		
		// print break line
		System.out.println();
		
		// print correct guesses, incorrect guesses, guess count
		System.out.println ("Incorrect Guesses: " + incorrectLetterList);
		System.out.println ("Correct Guesses: " + correctLetterList);
		System.out.println ("Guess Count: " + guessCount);
		
	}
			

	/**
	 * @return boolean userWon to indicate if user has won
	 */
	public static boolean HasUserWon() {
		
		// create a variable to indicate if user has won
		boolean userWon = false;
		
		// create an int for iterating through length of word
		int i;
		
		// create an int for counting number of letter-spaces guessed
		int k = 0;
		
		// iterate through length of word
		for (i = 0; i < secretWord.length(); i++) {
			// inside first loop, iterate through correct guesses
			for (Character j : correctLetterList) {
				// if letters match, increase count of letter-spaces guessed
				if (secretWord.charAt(i) == j) {
					k += 1;
					}
			}
		}
		
		// if all letter-spaces are guessed, user wins
		if (k >= secretWord.length()) userWon = true;
		
		// returns boolean indicate of whether user has won
		return userWon;
				
	}
}
