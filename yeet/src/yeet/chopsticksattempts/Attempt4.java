package yeet.chopsticksattempts;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import yeet.chopsticksattempts.Graph.Node;

public class Attempt4 {
	public static void main(String[] args) {
		Graph g = new Graph(new int[] { 1, 1, 1, 1 });
		Node start = new Node(new int[] { 1, 1, 1, 1 });
		Scanner in = new Scanner(System.in);
		game: while (true) {
		}
//		in.close();

	}

}

class Graph {

	final int nodes = 225;
	Node[] graph = new Node[nodes];

	public Graph(int[] root) {
		for (int i = 0; i < nodes; i++) {
			graph[i] = new Node(i);
		}
		for (int i = 0; i < nodes; i++) {
			genChildren(graph[i]);
		}
		Deque<Node> path = new LinkedList<Node>();
		path.add(graph[getKey(root)]);
		negamax(path);
	}

	public void negamax(Deque<Node> path) {

		Node current = path.peekLast();
		int[] hands = current.hands;
		if (hands[0] == 0 && hands[1] == 0) {
			current.value = -1;
		}
		if (current.getChildren().isEmpty()) {
			genChildren(current);
		}
		Queue<Node> children = current.getChildren();
		int value = -1;
		while (!children.isEmpty()) {
			Node next = children.poll();
			if (path.contains(next)) {
				continue;
			}
			if (next.value != 0) {
				value = Math.max(value, -next.value);
				continue;
			}
			path.addLast(next);
			negamax(path);
			path.removeLast();
			value = Math.max(value, -next.value);

		}
		current.value = value;
	}

	public void genChildren(Node n) {
		for (int i = 0; i < 2; i++) {
			if (n.hands[i] == 0) {
				continue;
			}
			for (int j = 0; j < 4; j++) {
				if (i == j || n.hands[j] == 0) {
					continue;
				}
				int[] nexthand = n.hands.clone();
				nexthand[j] = (nexthand[j] + nexthand[i]) % 5;
				swap(nexthand);
				Node child = graph[getKey(nexthand)];
				if (!n.children.contains(child)) {
					n.children.add(child);
				}
			}
		}
		int sum = n.hands[0] + n.hands[1];
		for (int i = 0; i <= sum / 2; i++) {
			int[] nexthand = n.hands.clone();
			nexthand[0] = i;
			nexthand[1] = sum - i;
			if (nexthand[1] >= 5) {
				continue;
			}
			if (n == graph[getKey(nexthand)]) {
				continue;
			}
			swap(nexthand);
			Node child = graph[getKey(nexthand)];
			if (!n.children.contains(child)) {
				n.children.add(child);
			}
//			if (!child.parents.contains(n)) {
//				child.parents.add(n);
//			}
		}
	}

	static class Node {
		int[] hands;
		private Queue<Node> children;
		int value;
		boolean visited;

		public Node(int hands) {
			this(getHands(hands));
		}

		public Node(int[] hands) {
			this.hands = hands;
			children = new LinkedList<Node>();
		}

		public Queue<Node> getChildren() {
			return new LinkedList<Node>(children);
		}

//		public boolean equals(Object o) {
//			if (o == this) {
//				return true;
//			}
//			if (!(o instanceof Node)) {
//				return false;
//			}
//			Node r = (Node) o;
//			return getKey(r.hands) == getKey(hands);
//		}

		public String toString() {
			return  "your hand: " + hands[0] + " " + hands[1] + "\r\n"
					+ "opponents' hand: " + hands[2] + " " + hands[3] + "\r\n"
					;
		}
	}

	public static int getKey(int[] hands) {
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

	public static void swap(int[] hands) {
		int temp = hands[0];
		hands[0] = hands[2];
		hands[2] = temp;
		temp = hands[1];
		hands[1] = hands[3];
		hands[3] = temp;
	}

}