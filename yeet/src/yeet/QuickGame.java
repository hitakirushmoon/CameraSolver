package yeet;

import java.util.Arrays;

public class QuickGame {

	public static void main(String[] args) {
		int[] choices = { 1, 2, 4, 6 };
		int[] choices2 = { 1, 4, 7, 8 };

		solveGame(52, choices);
		solveGame(52, choices2);
	}

	public static void solveGame(int length, int[] choices) {
		int[] winningMove = new int[length];
		for (int i = 0; i < winningMove.length; i++) {
			if (winningMove[i] == 0) {
				for (int choice : choices) {
					if (i + choice >= winningMove.length)
						break;
					winningMove[i + choice] = choice;
				}
//				winningMove[i] = -1;
			}
		}
		System.out.println(Arrays.toString(winningMove));

	}
}
