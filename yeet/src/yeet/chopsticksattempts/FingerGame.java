package yeet.chopsticksattempts;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class FingerGame {
	public static void main(String[] args) {
		int[] fingers = new int[] { 1, 1, 1, 1 };
		Deque<Integer> path = new LinkedList<Integer>();
		path.add(key(fingers));
		negamax(fingers.clone(), path);
		int count = 0;
		for (int i = 0; i < database.length; i++) {

			if (visited[i] == true && database[i] == 0) {
				count++;
				System.out.println(("0000" + Integer.toString(i, 5)).substring(Integer.toString(i, 5).length()) + " "
						+ database[i] + " " + i);
			}

		}
		System.out.println(count);
		Scanner in = new Scanner(System.in);
		System.out.println("your hands:           " + fingers[0] + " " + fingers[1]);
		System.out.println("the computer's hands: " + fingers[2] + " " + fingers[3]);
		game: while (true) {
			int yourhand = 0;
			while (true) {
				System.out.println("which of your hands?");
				int input = in.nextInt();
				if (input < 1 || input > 2) {
					System.out.println("invalid input: choose either 1 or 2");
					continue;
				}
				if (fingers[input - 1] == 0) {
					System.out.println("invalid input: empty hand");
					continue;
				} else {
					yourhand = input - 1;
					break;
				}
			}
			int comphand = 0;
			while (true) {
				System.out.println("which of the computer's hands?");
				int input = in.nextInt();
				if (input < 1 || input > 2) {
					System.out.println("invalid input: choose either 1 or 2");
					continue;
				}
				if (fingers[input + 1] == 0) {
					System.out.println("invalid input: empty hand");
					continue;
				} else {
					comphand = input + 1;
					break;
				}
			}
			fingers[comphand] = (fingers[comphand] + fingers[yourhand]) % 5;
			System.out.println("your hands:           " + fingers[0] + " " + fingers[1]);
			System.out.println("the computer's hands: " + fingers[2] + " " + fingers[3]);
			if (path.contains(key(fingers))) {
				System.out.println("draw: repetition");
				break game;
			}
			path.add(key(fingers));
			if (fingers[2] == 0 && fingers[3] == 0) {
				System.out.println("you win!");
				break game;
			}

			int value = Integer.MIN_VALUE;
			for (int i = 0; i < 2; i++) {
				// ignore case if empty
				if (fingers[i + 2] == 0) {
					continue;
				}
				// loop through p2's hands
				for (int j = 0; j < 2; j++) {
					// ignore case if empty
					if (fingers[j] == 0) {
						continue;
					}
					// child node
					int[] nextfin = fingers.clone();
					nextfin[j] = (nextfin[j] + nextfin[i + 2]) % 5;
					int key = key(nextfin);
					if (path.contains(key) && 0 > value) {
						value = 0;
						yourhand = j;
						comphand = i + 2;
					} else if (-database[key] > value) {
						value = -database[key];
						yourhand = j;
						comphand = i + 2;
					}
				}
			}
			System.out.println();
			System.out.println("the computer used its hand #" + (comphand - 1) + " on your hand #" + (yourhand + 1));
			fingers[yourhand] = (fingers[comphand] + fingers[yourhand]) % 5;
			System.out.println("your hands:           " + fingers[0] + " " + fingers[1]);
			System.out.println("the computer's hands: " + fingers[2] + " " + fingers[3]);
			if (path.contains(key(fingers))) {
				System.out.println("draw: repetition");
				break game;
			}
			path.add(key(fingers));
			if (fingers[0] == 0 && fingers[1] == 0) {
				System.out.println("you lost!");
				break game;
			}
		}
		in.close();
	}

	static int[] database = new int[625];
	static boolean[] visited = new boolean[625];

	public static int negamax(int[] fingers, Deque<Integer> path) {
		// hash position (not optimal at all but i'm lazy)
		int finkey = key(fingers);
		visited[finkey] = true;
		// losing condition
		if (fingers[0] == 0 && fingers[1] == 0) {
			database[finkey] = -1;
			return -1;
		} else {
			// swap p1 and p2
			int temp = fingers[0];
			fingers[0] = fingers[2];
			fingers[2] = temp;
			temp = fingers[1];
			fingers[1] = fingers[3];
			fingers[3] = temp;
			// min value
			int value = -1;
			// loop through p1's hands
			for (int i = 0; i < 2; i++) {
				// ignore case if empty
				if (fingers[i + 2] == 0) {
					continue;
				}
				// loop through p2's hands
				for (int j = 0; j < 2; j++) {
					// ignore case if empty
					if (fingers[j] == 0) {
						continue;
					}
					// child node
					int[] nextfin = fingers.clone();

					nextfin[j] = (nextfin[j] + nextfin[i + 2]) % 5;
					int key = key(nextfin);
					// check visited
					if (path.contains(key)) {
						value = Math.max(value, 0);
						continue;
					} else if (database[key] != 0) {
						// grab value from cache
						value = Math.max(value, -database[key]);
						continue;
					} else {
						path.addLast(key);
						int nega = -negamax(nextfin, path);
						path.removeLast();
						value = Math.max(value, nega);
					}
				}
			}
//			System.out.println(Integer.toString(finkey, 5) + " " + value);
			database[finkey] = value;
			return value;
		}
	}

	public static int key(int[] fingers) {
		int sum = 0;
		for (int i = 0; i < fingers.length; i++) {
			sum += fingers[i] * Math.pow(5, fingers.length - i - 1);
		}
		return sum;
	}
}
