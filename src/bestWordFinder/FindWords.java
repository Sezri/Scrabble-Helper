package bestWordFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;

public class FindWords {	
	
	public static void findWords(String filename, String newFile) {
		
		HashSet<String> wordList = new HashSet<String>();
		ArrayList<String> sortedList = new ArrayList<String>();
		
		try(Scanner scanner = new Scanner(new File(filename)) ) {
			while(scanner.hasNextLine()) {
				wordList.add(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String wordShort;
		
		try(FileWriter fw = new FileWriter(new File(newFile))) {
			for(String word:wordList) {
				wordShort = word.substring(1);
				if(wordList.contains(wordShort) && !sortedList.contains(wordShort)) {
					sortedList.add(wordShort);
				}
			}
			sortedList.sort(new LetterThenLength());
			for(String word:sortedList)
				fw.write(word + '\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		findWords("wordlist.txt.txt", "LongMatches.txt");
	}
	
	private static class LetterThenLength implements Comparator<String> {

		@Override
		public int compare(String arg0, String arg1) {
			if(arg0.charAt(0) > arg1.charAt(0))
				return 1;
			else if(arg0.charAt(0) < arg1.charAt(0))
				return -1;
			else if(arg0.length() > arg1.length()) 
				return -1;
			else if(arg0.length() < arg1.length())
				return 1;
			else
				return arg0.compareTo(arg1);
		}
		
	}
}
