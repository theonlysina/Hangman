/**
 * 
 */
package hangman;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * 
 * subclass of Hangman.java -- provides methods for evil hangman
 * @author sina
 */
public class HangmanEvil extends Hangman {

	
	/**
	 * HashMap - contains various possible word families for computer's Evil Implementations
	 */
	private static HashMap<String, HashSet<String>> possibleWordFamilies;
	
	/**
	 * HashSet - contains currently defined word family
	 */
	private static Set<String> currentWordFamily = new HashSet<String>();
	
	/**
	 * StringBuilder - the word that would appear if/when that family was chosen
	 */
	private static StringBuilder familyDescriptor = new StringBuilder();

	/**
	 * StringBuilder - the order of letters that have been guessed correctly
	 */
	private static StringBuilder correctGuessesList = new StringBuilder();

	/**
	 * sets HashMap<String, HashSet<String>> getPossibleWordFamilies
	 * @return HashMap<String, HashSet<String>> getPossibleWordFamilies
	 */
	@SuppressWarnings("unused")
	private static HashMap<String, HashSet<String>> getPossibleWordFamilies() {
		return possibleWordFamilies;
	}

	/**
	 * gets Set<String> CurrentWordFamily
	 * @return the currentWordFamily
	 */
	private static Set<String> getCurrentWordFamily() {
		return currentWordFamily;
	}



	/**
	 * gets StringBuilder getFamilyDescriptor()
	 * @return the familyDescriptor
	 */
	@SuppressWarnings("unused")
	private static StringBuilder getFamilyDescriptor() {
		return familyDescriptor;
	}


	/**
	 * sets HashMap<String, HashSet<String>> possibleWordFamilies
	 * @param possibleWordFamilies
	 */
	@SuppressWarnings("unused")
	private static void setPossibleWordFamilies(HashMap<String, HashSet<String>> possibleWordFamilies) {
		HangmanEvil.possibleWordFamilies = possibleWordFamilies;
	}


	/**
	 * sets Set<String> currentWordFamily
	 * @param currentWordFamily
	 */
	private static void setCurrentWordFamily(Set<String> currentWordFamily) {
		HangmanEvil.currentWordFamily = currentWordFamily;
	}


	/**
	 * sets String familyKey
	 * @param familyKey
	 */
	private static void setFamilyDescriptor(String familyKey) {
		HangmanEvil.familyDescriptor.replace(0, familyKey.length(), familyKey.toString());
	}


	/**
	 * Initializes Evil Hangman for first round
	 * @param wordsCleaned
	 */
	public static void InitializeEvil(HashSet<String> wordsCleaned) {
		
		// get variables
		// define secretWord for function
		HangmanEvil.secretWord = getSecretWord();
		
		// creates an empty word of the determined size
		for (int i=0; i < secretWord.length() ; i++){
			familyDescriptor.append('_');
		}
		
		// define secretWord for function
		HangmanEvil.currentWordFamily = getCurrentWordFamily();
	
		// iterate through the HashSet for cleaned words
		for (String word : wordsCleaned) {
			// if the word matches the length of the randomly selected word if
			if (word.length() == secretWord.length()) {
				// add the word to the current word family
				currentWordFamily.add(word); }
		}
	
		// set word family to function
		setCurrentWordFamily(currentWordFamily);
		
		// DEBUGGING
		// System.out.println("famdesc after init: " + familyDescriptor);
	}
	
	
	/**
	 * Implements user's guess in the Evil Hangman game
	 */
	public static void implementGuessEvil() {
		
		// adds user's guessed letter to the list of user guesses
		userLetterGuessSet.add(guessedLetter);
		
		// create possible word families
		createPossibleWordFamilies(guessedLetter);
		
		// choose the word family with most words
		chooseWordFamily();
		
		// before we get to the final word, we keep narrowing options;
		if (getCurrentWordFamily().size() != 1) {
		
				// checks to see if largest family does have the guessed letter
				// check to see if user guessed correct or incorrect letter
				if (familyDescriptor.toString().contains(String.valueOf(guessedLetter))) {
					// if guess proved to be fruitful, and computer could not escape fully
					
					//add to list of good guesses
					correctLetterList.add(guessedLetter);

					// print incorrect guess
					System.out.println("Correct Guess");
					
				} else if (familyDescriptor.toString().contains(String.valueOf(guessedLetter)) == false) {
					// otherwise; if letter was not found and the computer managed to escape
					
					// print correct guess
					System.out.println("Incorrect Guess");
					
					// add to list of bad guesses
					incorrectLetterList.add(guessedLetter);;
				}
		}
		
		// if there is no word left (this should not happen), user wins the game
		else if (getCurrentWordFamily().size() == 0) {
			userWon();
		}
		
		// when we get to the final word, we will start playing traditional hangman
		else if (getCurrentWordFamily().size() == 1) {
			
			// find secret word to feed to Hangman Traditional;
			Iterator<String> it = currentWordFamily.iterator();
			String word = it.next();
			secretWord = word;
			
			// debugging
			// System.out.println("family size = 1");

			// uses traditional hangman method for implementing guess 
			Hangman.implementGuess();
		}
	}



	/**
	 * searches current word family for different word possibilities resulting from give guess (guessedLetter)
	 * creates a HashMap containing all the possible sets
	 * @param guessedLetter
	 */
	public static void createPossibleWordFamilies (char guessedLetter) {
		
		// initialize the hashmap of word families
		// this hashmap pairs a possible string with a word family
		possibleWordFamilies = new HashMap <String, HashSet<String>>();
		
		// iterate through current word family
		for (String word : currentWordFamily) {
			
			// create a temporary string builder
			StringBuilder sb = new StringBuilder();
			
			// add family descriptor (previously guessed data) to temporary stringbuilder
			sb.append(familyDescriptor.toString());
			
			// create a key for the word --> over iteration this creates all possible families
			String mapKey = lookForLetters(word, guessedLetter, sb);
			
			// if map doesn't have key for this word, create a new map entry
			if (possibleWordFamilies.get(mapKey) == null) {
				
				// create a new family set
				HashSet<String> newFamily = new HashSet<String>();
				// add the word to the word family (word)
				newFamily.add(word);
				// add key pair (word family and word) to map (mapkey, new set) of word families
				possibleWordFamilies.put(mapKey,newFamily);
	
			} else {
				// otherwise, if map does have key for this word, just add the word to word family
				possibleWordFamilies.get(mapKey).add(word);
			}
		}
	}
	
	/**
	 * @param word String word that could contain the given letter
	 * @param guessedLetter String letter that could be contained in the given word
	 * @param String word that will be displayed if this word fits in the currentSet
	 * @return StringfamilyDescriptor after finding all the occurrences of the given letter
	 */
	public static String lookForLetters (String word, char guessedLetter, StringBuilder sb){
		
		// define index for finding location of next instance of letter in word
		int index = word.indexOf(guessedLetter);
		
		// get index of letter in word
		index = word.indexOf(guessedLetter);
		
		// if you don't find the letter in word
		// return the same descriptor as HashKey
		if (index == -1) {
			
			// return key
			return sb.toString();

		// if letter is found in word at least once,
		} else {
				
			// for each occurrence of letter, the letter is put into family descriptor
			sb.setCharAt(index, guessedLetter);
				
			// word is amended for next iteration
			// first index is replaced with "." because it is a letter that is in no words
			// prepares word to searching for next instance of given letter in the next loop
			word = word.substring(0, index) + "." + word.substring(index + 1);
			
			// repeat the function with reduced word word
			return lookForLetters (word, guessedLetter, sb);
		}			
	}
	
	/**
	 * chooses the word family with the largest set of words as possibilities
	 */
	public static void chooseWordFamily(){
		
		// get family descriptor
		familyDescriptor = getFamilyDescriptor();
		
		// debugging
		// System.out.println("famdesk beginning of choose family");
		// System.out.println(familyDescriptor.toString());
		
		// define a new set (extractable variable) for largest family
		HashSet<String> largestFamily = new HashSet<String>();
		
		// define a new key for largest family
		String largestFamilyKey = null;
		
		// iterates through each family of words
		for (String familyKey : possibleWordFamilies.keySet()){
			
			// create a temporary variable set
			// fetches the family's set from the family's descriptor
			HashSet<String> familySet = possibleWordFamilies.get(familyKey);
			
			// finds largest set by comparing the set with the next set
			if (familySet.size() > largestFamily.size()){
				
				// replaces previously-defined family with new one
				largestFamily = familySet;
				largestFamilyKey = familyKey;
				
				// update family descriptor (for printing)
				setFamilyDescriptor(familyKey);
				
				// DEBUGGING
				// System.out.println("famdesc after choosing family:");
				// System.out.println(familyDescriptor.toString());
			}
		}
		
		// updates current word family // replaces with with the largest future family
		currentWordFamily = largestFamily;
		correctGuessesList = correctGuessesList.append(largestFamilyKey);
	
	}
	
	
	/**
	 * this funtion is called when computer has no other word choice.
	 * returns true if user has won; false if user has not won
	 * @return boolean 
	 */
	public static boolean userWon() {
		if (getCurrentWordFamily().size() == 0) {
			return true;
		}
		return false;
	}
	

	/**
	 * prints hangman platform for user to see
	 * @param guessCount
	 */
	public static void printHangmanSequence(int guessCount) {
		
		// print hangman string
		String familyDescriptorString = getFamilyDescriptor().toString();
		System.out.println (familyDescriptorString);
		
		// print correct guesses, incorrect guesses, guess count
		System.out.println ("Incorrect Guesses: " + incorrectLetterList);
		System.out.println ("Correct Guesses: " + correctLetterList);
		System.out.println ("Guess Count: " + guessCount);
	
	}
}