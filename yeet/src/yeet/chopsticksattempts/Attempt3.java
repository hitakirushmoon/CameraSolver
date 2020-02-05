package yeet.chopsticksattempts;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Attempt3 {
	static int[] database = new int[225];
	static boolean[] visited = new boolean[225];

	public static void main(String[] args) {
		int[] fingers = { 1, 1, 1, 1 };
		NodeV1 root = new NodeV1(fingers, false);
//		root.genChildren();
//		root.children.forEach(x -> System.out.println(Arrays.toString(x.hands)));
		Deque<NodeV1> path = new LinkedList<NodeV1>();
		path.add(root);

		negamax(path);
		int count = 0;
		for (int i = 0; i < database.length; i++) {
			NodeV1 a = new NodeV1(NodeV1.getHands(i), false);
			a.genChildren();
			Queue<Integer> score = new LinkedList<Integer>();
			Queue<Integer> keys = new LinkedList<Integer>();
			a.getChildren().forEach(x -> score.add(database[x.getKey()]));
			a.getChildren().forEach(x -> keys.add(x.getKey()));
			System.out.println(a + " " + database[i]);
			System.out.println(score);
			System.out.println(a.getChildren());
			System.out.println();
			if (database[i] == 0) {
				count++;
			}
		}
		System.out.println(count);
		game(root, path);

	}

	public static void game(NodeV1 node, Deque<NodeV1> path) {
		Scanner in = new Scanner(System.in);

		in.close();
	}

	public static int negamax(Deque<NodeV1> path) {

		NodeV1 current = path.peekLast();

		int key = current.getKey();

		int[] hands = current.hands;
		visited[key] = true;
		if (hands[0] == 0 && hands[1] == 0) {
			database[key] = -1;
			return -1;
		}
		current.genChildren();
		Queue<NodeV1> children = current.getChildren();
		int value = -1;
		while (!children.isEmpty()) {
			NodeV1 next = children.poll();
			int nextkey = next.getKey();
			if (path.contains(next)) {
				continue;
			}
			if (database[nextkey] != 0) {
				value = Math.max(value, -database[nextkey]);
				continue;
			}
			path.addLast(next);
			int nega = -negamax(path);
			path.removeLast();
			value = Math.max(value, nega);

		}
		database[key] = value;
		return value;
	}

}

class NodeV1 {
	int[] hands;
	private Queue<NodeV1> children;
	private Queue<NodeV1> parents;

	public NodeV1(int[] hands, boolean isSplit) {
		this.hands = hands;
		children = new LinkedList<NodeV1>();
	}

	public void genChildren() {
		transferChildren();
		tapChildren();
	}

	public Queue<NodeV1> getChildren() {
		return new LinkedList<NodeV1>(children);
	}

	public void transferChildren() {
		int sum = hands[0] + hands[1];
		for (int i = 0; i <= sum / 2; i++) {
			int[] nexthand = hands.clone();
			nexthand[0] = i;
			nexthand[1] = sum - i;
			if (nexthand[1] >= 5) {
				continue;
			}
			NodeV1 child = new NodeV1(nexthand, true);
			if (this.equals(child)) {
				continue;
			}
			swap(nexthand);
			if (!children.contains(child)) {
				children.add(child);
			}
		}
	}

	public void tapChildren() {
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
				swap(nexthand);
				NodeV1 child = new NodeV1(nexthand, false);

				if (!children.contains(child)) {
					children.add(child);
				}
			}
		}
	}

	public static void swap(int[] hands) {
		int temp = hands[0];
		hands[0] = hands[2];
		hands[2] = temp;
		temp = hands[1];
		hands[1] = hands[3];
		hands[3] = temp;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof NodeV1)) {
			return false;
		}
		NodeV1 r = (NodeV1) o;
		return r.getKey() == this.getKey();
	}

	public String toString() {
		return Arrays.toString(hands);
	}

	public int getKey() {
		int[] p1hands = new int[2];
		int[] p2hands = new int[2];
		p1hands[0] = Math.min(hands[0], hands[1]);
		p1hands[1] = Math.max(hands[0], hands[1]) + 1;
		p2hands[0] = Math.min(hands[2], hands[3]);
		p2hands[1] = Math.max(hands[2], hands[3]) + 1;
		return cHasher(p1hands) * 15 + cHasher(p2hands);
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

	final static int[] factorialconst = { 1, 1, 2, 6, 24, 120, 720 };

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
