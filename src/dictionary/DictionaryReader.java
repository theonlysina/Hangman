package dictionary;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * Utility class for reading the dictionary files
 * 
 * @author sina
 *
 */
public class DictionaryReader {

	/**
			 * gets info from dictionary file
			 * 
			 * 
			 * 				
			 *  * public static void openDictionary (String dictFileName) throws
				 * FileNotFoundException {
				 * 
				 * // create file File file = new File(dictFileName);
				 * 
				 * // create scanner with given file Scanner scanner = new Scanner(file);
				 * 
				 * // while scanner has another line while (scanner.hasNextLine()) { // print
				 * the next line System.out.println(scanner.nextLine()); }
				 * 
				 * scanner.close(); }
				 * 
			 * @throws FileNotFoundException if file is not found
			 * 
			 */
	
	
	// create a set for dictionary words
	/**
	 * wordsmain Set<String> dictionary words before cleaning
	 */
	public static Set<String> wordsmain = new HashSet<String>();
	
	// create a set for dictionary words
	/**
	 * wordsCleaned Set<String> dictionary words after cleaning
	 */
	public static Set<String> wordsCleaned = new HashSet<String>();

	/**
	 * gets words from dictionary file, but does not clean
	 * @param dictFileName
	 */
	public static void openDictionaryThroughBuffer(String dictFileName) {

		// create ArrayList to store lines
		Set<String> lines = new HashSet<String>();

		// create file object
		File dictFile = new File(dictFileName);

		// define file reader
		FileReader fr = null;

		// define buffered reader
		BufferedReader br = null;

		try {
			// create FileReader
			fr = new FileReader(dictFile);

			// create Buffered Reader
			br = new BufferedReader(fr);

			// define a line
			String line;

			// read each line
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}

		} catch (FileNotFoundException e) {
			// gets and prints file name not found
			System.out.println("File, " + dictFile.getName() + " not found.");
			e.printStackTrace();
		} catch (IOException e) {
			// prints the error message and info about which line
			System.out.println("IO Exception... perhaps a read-line error");
			e.printStackTrace();
		} finally {

			// regardless of what happens, we close file objects
			try {
				fr.close();
				br.close();
			} catch (final IOException e) {
				// catch IOException
				e.printStackTrace();
			} finally {
				// print a success message that dictionary was imported
				System.out.println("Dictionary imported.");
			}

			// save file content into a set
			setWordsmain(lines);
		}
	}


	/**
	 * getter for wordsmain
	 * @return wordsmain
	 */
	public static Set<String> getWordsmain() {
		return wordsmain;
	}

	/**
	 * getter for wordsCleaned
	 * @return the wordsCleaned
	 */
	public static Set<String> getWordsCleaned() {
		return wordsCleaned;
	}

	/**
	 * setter for wordsmain
	 * @param wordsmain
	 */
	public static void setWordsmain(Set<String> wordsmain) {
		DictionaryReader.wordsmain = wordsmain;
	}

	/**
	 * setter for wordsCleaned
	 * @param wordsCleaned
	 */
	public static void setWordsCleaned(Set<String> wordsCleaned) {
		DictionaryReader.wordsCleaned = wordsCleaned;
	}

	/**
	 * parseDictionary - parses raw dictionary set (wordsmain) to clean dictionary set (wordsCleaned)
	 * saves wordsCleaned using setWordsCleaned
	 * @param wordsmain
	 * @throws Exception
	 */
	public static void parseDictionary(Set<String> wordsmain) throws Exception {

		// create a HashSet variable
		Set<String> wordsCleaned = new HashSet<String>(wordsmain);

		// create an iterator for to iterate through Hashset
		Iterator<String> it = wordsCleaned.iterator();

		// while loop that ends with last String in HashSet
		while (it.hasNext()) {

			// look at the next string
			String next = it.next();

			// if we find one of more of these patterns
			if (next.contains(".") // contains a dot
					|| next.contains("\'") // contains an apostrophe
					|| next.contains("-") // contains a hyphen
					|| next.matches(".*[A-Z].*") // contains a capital letter; using regex
					|| next.matches("(.*)(\\s+)(.*)") // contains a compound word; uses regex
					|| next.matches(".*[0-9].*") // contains a digit; uses regex
			) {// then we remove the word and put in a notice to user
				it.remove();
				// System.out.println("unwanted word removed.");
			}
		}

		// return cleaned version of the words
		setWordsCleaned(wordsCleaned);
	}
}
