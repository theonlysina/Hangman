import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import dictionary.DictionaryReader;
import hangman.Hangman;
import hangman.HangmanEvil;


/**
 * Hangman Game controller: randomly plays either hangman or evil hangman
 * @author sina
 *
 */
public class HangmanGame {

	/**
	 * initialize game playing variable to True; to make sure game starts
	 */
	boolean gamePlaying = true;
	
	/**
	 * 
	 * initialize a variable for game type
	 * 0 is Traditional Hangman
	 * 1 is Evil Hangman
	 */
	static boolean evilGameType;
	
	/**
	 * name of file to read
	 */
	static String dictFileName = "src/dictionary/words.txt";

	/**
	 * number of user guesses
	 */
	static int guessCount = 0;

	/**
	 * main method -- controller
	 * @param args
	 */
	private static void run() {
		// part 1 - randomly selects a game type: Evil or Traditional

		// randomize game type according by a boolean generator
		// 0 is traditional game; 1 is Evil game
		Random randomBool = new Random();
		evilGameType = randomBool.nextBoolean();
		
		// part 2 - read dictionary words
		DictionaryReader.openDictionaryThroughBuffer(dictFileName);			
		System.out.println("Dictionary successfully imported");
		
		
		// part 3 - parse dictionary words
		try {DictionaryReader.parseDictionary(DictionaryReader.wordsmain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Dictionary successfully cleaned");
		
		// part 4 - select a random secret word
		Hangman.initializeGame(DictionaryReader.wordsCleaned);
		
		// part 4b - if playing evil hangman, computer selects the first set of possible words
		if (evilGameType == true) {
			HangmanEvil.InitializeEvil(new HashSet <String>(DictionaryReader.wordsCleaned));
		}
		
		// part 5 - start the game
		
		// while loop is the game
		boolean gamePlaying = true;
		while (gamePlaying) {
			
			// create scanner
			Scanner scanner = new Scanner(System.in);
			
			// check eligibility of user's choice
			// checks to make sure user is not repeating a guess
			// TODO: catch user input errors
			
			
			// collect user input
			boolean eligible;
			
			// perform this a first time
			// and until user enters an eligible value
			do {
				// take another input
				Hangman.userMakesGuess(scanner);
				
				// check to make  user is not repeating a guess
				eligible = !Hangman.checkUserGuess();
				
			} while (eligible == false);
			// this shows eligibility requirement
						
			// implement user guess
			// if we are playing evil hangman, evil hangman makes guessing more difficult
			if (evilGameType == true) {
				HangmanEvil.implementGuessEvil();
			} else if (evilGameType == false) {
				Hangman.implementGuess();		
			}

			// adds user's guessed letter to the list of user guesses
			Hangman.userGuessTracker();

			// and adds to total guess count
			guessCount++;
			
			// print user board
			if (evilGameType == true) {
				HangmanEvil.printHangmanSequence(guessCount);
			} else if (evilGameType == false) {
				Hangman.printHangmanSequence(guessCount);
			}
			
			// create a game-ending variable
			boolean endGame = false;
			// end game if user has won
			
			endGame = Hangman.HasUserWon();
			// if (evilGameType == true && HangmanEvil.userWon() == true) {
			// 	endGame = true;
			// }
			
			// print congrats, and end game cycle
			if (endGame == true) {
				System.out.println("You Won!");
				if (evilGameType == true) {
					System.out.println("You were playing Evil Hangman!");
				} else if (evilGameType == false) {
					System.out.println("You were playing Traditional Hangman!");
				}
				System.out.println("It took you " + guessCount + " tries to win!");
				gamePlaying = false;
			}
		}
		
	}

	/**
	 * main method
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to Hangman!");
		run();
	}
}