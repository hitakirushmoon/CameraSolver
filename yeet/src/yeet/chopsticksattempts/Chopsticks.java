package yeet.chopsticksattempts;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Chopsticks {
	static int[] database = new int[225];
	static boolean[] visited = new boolean[225];

	public static int negamax(Deque<Integer> path) {
		int key = path.peekLast();
		int[] hands = getHands(key);
		visited[key] = true;
		if (hands[0] == 0 && hands[1] == 0) {
			database[key] = -1;
			return -1;
		}
		Queue<Integer> children = genChildren(hands);
		int value = -1;
		while (!children.isEmpty()) {
			int nextkey = children.poll();
			if (path.contains(nextkey)) {
				value = Math.max(value, 0);
				continue;
			} else if (database[nextkey] != 0) {
				value = Math.max(value, -database[nextkey]);
				continue;
			} else {
				path.addLast(nextkey);
				int nega = -negamax(path);
				path.removeLast();
				value = Math.max(value, nega);
			}
		}
		database[key] = value;
		return value;
	}
	
	public static void main(String[] args) {
		int[] hands = { 1, 1, 1, 1 };
		Queue<Integer> children = genChildren(hands);
//		children.forEach(x -> System.out.println(Arrays.toString(getHands(x))));
		Deque<Integer> path = new LinkedList<Integer>();
		path.add(getKey(hands));
		negamax(path);
		int count = 0;
		for (int i = 0; i < database.length; i++) {
			if (visited[i] != true) {
				count++;
				System.out.println(Arrays.toString(getHands(i)) + " " + database[i] + " ");
			}
		}
		System.out.println(count);
	}

	final static int[] factorialconst = { 1, 1, 2, 6, 24, 120, 720 };

	public static Queue<Integer> genChildren(int key) {
		return genChildren(getHands(key));
	}

	public static Queue<Integer> genChildren(int[] hands) {
		Queue<Integer> children = new LinkedList<Integer>();
		for (int i = 0; i < 2; i++) {
			if (hands[i] == 0) {
				continue;
			}
			for (int j = 0; j < 4; j++) {
				if (i == j || hands[j] == 0) {
					continue;
				}
				int[] nexthand = hands.clone();
				nexthand[j] = (nexthand[j] + nexthand[i]) % 5;
				int key = getKey(nexthand);
				if (!children.contains(key)) {
					children.add(key);
				}
			}
		}
//		int sum = hands[0] + hands[1];
//		for (int i = 0; i <= sum / 2; i++) {
//			int[] nexthand = hands.clone();
//			nexthand[0] = i;
//			nexthand[1] = sum - i;
//			if (nexthand[1] >= 5) {
//				continue;
//			}
//			int key = getKey(nexthand);
//			if (!children.contains(key) && getKey(hands) != key) {
//				children.add(key);
//			}
//		}
		return children;
	}

	public static int getKey(int[] hands) {
		int[] p1hands = new int[2];
		int[] p2hands = new int[2];
		p1hands[0] = Math.min(hands[0], hands[1]);
		p1hands[1] = Math.max(hands[0], hands[1]) + 1;
		p2hands[0] = Math.min(hands[2], hands[3]);
		p2hands[1] = Math.max(hands[2], hands[3]) + 1;
		return cHasher(p2hands) * 15 + cHasher(p1hands);
	}

	public static int[] getHands(int key) {
		int hand1 = key / 15;
		int hand2 = key % 15;
		int[] hands = new int[4];
		for (int i = 0; i < 7; i++) {
			if (hand1 < nCk(i, 2)) {
				hands[1] = i - 2;
				hands[0] = hand1 - nCk(i - 1, 2);
				break;
			}
		}
		for (int i = 0; i < 7; i++) {
			if (hand2 < nCk(i, 2)) {
				hands[3] = i - 2;
				hands[2] = hand2 - nCk(i - 1, 2);
				break;
			}
		}
		return hands;
	}

	protected static int cHasher(int[] arr) {
		int a = 0;
		for (int i = 0; i < arr.length; i++) {
			a += nCk(arr[i], i + 1);
		}
		return a;
	}

	private static int nCk(int n, int k) {
		if (n < k) {
			return 0;
		}
		return factorialconst[n] / (factorialconst[k] * factorialconst[n - k]);
	}

}
