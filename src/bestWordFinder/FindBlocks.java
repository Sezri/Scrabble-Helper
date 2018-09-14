package bestWordFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class FindBlocks {
	
	static int bestScore, bestFocusScore;
	static ArrayList<String> wordListLong = new ArrayList<String>();
	static HashMap<Character, Integer> scrabbleLetters;
	static final int SQUARES = 15;
	static HashSet<String> allWords = new HashSet<String>();
	static int[] indicies;
	
	public static void findBlocks(String word, int[] focusIndexes) {
		
		scrabbleLetters = scrabbleChars();
		char[] letters = word.toLowerCase().toCharArray();
		char[][] board = new char[SQUARES][word.length()];
		board[0] = letters;
		allWords = new HashSet<String>();
		bestScore = 0;
		bestFocusScore = 0;
		indicies = focusIndexes;
		
		try(Scanner scanner = new Scanner(new File("src/LongMatches.txt")) ) {
			while(scanner.hasNextLine()) {
				wordListLong.add(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		try(Scanner scanner = new Scanner(new File("src/wordlist.txt.txt")) ) {
			while(scanner.hasNextLine()) {
				allWords.add(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		fillBoard(board, 0);
	}
	
	
	private static void fillBoard(char[][] board, int i) {
		int index = firstWordWithLetter(board[0][i]), endIndex;
		if(board[0][i] == 'z')
			endIndex = wordListLong.size() - 1;
		else endIndex = firstWordWithLetter((char)(board[0][i] + 1));
		
		while(index < endIndex) {
			addInColumn(wordListLong.get(index++), board, i);
			if(i < board[0].length - 1)
				fillBoard(board, i + 1);
			if(rowsAreWords(board)) {
				if(isGoodBoard(board)) {
					printBoard(board);
				}
			}
		}
	}

	private static boolean isGoodBoard(char[][] board) {
		int score = boardScore(board);
		int focusScore = focusScore(board);
		
		if(focusScore > bestFocusScore) {
			bestFocusScore = focusScore;
			System.out.println(score + " , " + focusScore);
			return true;
		}
			
		else if(score >= bestScore) {
			bestScore = score;
			if(focusScore == bestFocusScore) {
				System.out.println(score + " , " + focusScore);
			} else {
				System.out.println(score);
			}
			return true;
		}
		
		else
			return false;
	}


	private static int focusScore(char[][] board) {
		int score = 0;
		for(int index : indicies)
			for(int i = 0; i < SQUARES; i++)
				score += scrabbleLetters.get(board[i][index]);
		return score;
	}


	private static boolean rowsAreWords(char[][] board) {
		
		for(char[] row : board) {
			StringBuilder str = new StringBuilder();
			for(char letter : row)
				str.append(letter);
			
			for(String word : str.toString().split(" ")) {
				if(word != null && word.length() > 1)
					if(!allWords.contains(word))
						return false;
			}
		}		
		return true;
	}

//	private static String findBestWord(char c) {
//		int index = firstWordWithLetter(c), endIndex, bestScore = 0, score;
//		String bestWord = "", currentWord;
//		if(c == 'z')
//			endIndex = wordListLong.size() - 1;
//		else endIndex = firstWordWithLetter((char)(c + 1));
//		while(index < endIndex) {
//			currentWord = wordListLong.get(index);
//			score = wordScore(currentWord);
//			if(score > bestScore) {
//				bestWord = currentWord;
//				bestScore = score;
//			}
//			index += 1;
//		}
//		return bestWord;
//	}

	private static void addInColumn(String bestWord, char[][] board, int column) {
		char[] word = bestWord.toCharArray();
		for(int i = 0; i < SQUARES; i++) {
			if(i < bestWord.length())
				board[i][column] = word[i];
			else
				board[i][column] = ' ';
		}
	}

	private static void printBoard(char[][] board) {
		for(char[] array : board) {
			for(char letter : array) {
				System.out.print(letter);
				System.out.print(' ');
			}
			System.out.println();
		}
	}

	private static int boardScore(char[][] board) {
		int score = 0;
		for(char[] word : board)
			for(char i : word)
				score += scrabbleLetters.get(i);
		return score;
	}


//	private static int wordScore(String word) {
//		int score = 0;
//		for(char i : word.toCharArray()) {
//			score += scrabbleLetters.get(i);
//		}
//		return score;
//	}

	private static int firstWordWithLetter(char letter) {
		if(letter == 'a') return 0;
		int low = 0, mid = wordListLong.size()/2, high = wordListLong.size() - 1;
		while(!checkIfFirst(mid)) {
			if(wordListLong.get(mid).charAt(0) >= letter) {
				high = mid;
				mid = (low + high)/2;
			} else if(wordListLong.get(mid).charAt(0) < letter) {
				low = mid;
				mid = (low + high)/2;
			}
		}
		return mid;
	}
	/**
	 * 
	 * @param index
	 * @return -1 if first is before index, 0 if is index, 1 if after index
	 */
	private static boolean checkIfFirst(int index) {
		if(wordListLong.get(index).charAt(0) != wordListLong.get(index - 1).charAt(0)) 
			return true;
		return false;
	}
	
	private static HashMap<Character, Integer> scrabbleChars() {
		HashMap<Character, Integer> scrabbleLetters = new HashMap<Character, Integer>();
		scrabbleLetters.put('a', 1);
		scrabbleLetters.put('b', 4);
		scrabbleLetters.put('c', 4);
		scrabbleLetters.put('d', 2);
		scrabbleLetters.put('e', 1);
		scrabbleLetters.put('f', 4);
		scrabbleLetters.put('g', 3);
		scrabbleLetters.put('h', 3);
		scrabbleLetters.put('i', 1);
		scrabbleLetters.put('j', 10);
		scrabbleLetters.put('k', 5);
		scrabbleLetters.put('l', 2);
		scrabbleLetters.put('m', 4);
		scrabbleLetters.put('n', 2);
		scrabbleLetters.put('o', 1);
		scrabbleLetters.put('p', 4);
		scrabbleLetters.put('q', 10);
		scrabbleLetters.put('r', 1);
		scrabbleLetters.put('s', 1);
		scrabbleLetters.put('t', 1);
		scrabbleLetters.put('u', 2);
		scrabbleLetters.put('v', 5);
		scrabbleLetters.put('w', 4);
		scrabbleLetters.put('x', 8);
		scrabbleLetters.put('y', 3);
		scrabbleLetters.put('z', 10);
		scrabbleLetters.put(' ', 0);
		return scrabbleLetters;
	}
}
