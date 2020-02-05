package yeet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class CodeForce {
	public static void main(String[] args) {
//		System.out.println(Arrays.toString(A(3, 5, 2, 2, 2, 1, 3)));
//		System.out.println(Arrays.toString(A(6, 8, 3, 2, 5, 1, 1)));
//		System.out.println(Arrays.toString(A(100, 200, 13, 45, -20, 111, 9969)));
		System.out.println(B(3, 2, 2, 2, 3));
	}

	public static int[] A(int N, int M, int x0, int y0, int vx, int vy, int T) {
		int x1, y1;
		x1 = x0 + vx * T;
		y1 = y0 + vy * T;
		int x2, y2;
		x2 = mod((x1 / N), 2);
		y2 = mod((y1 / M), 2);
		x1 = (int) (N * x2 - mod(x1, N) * Math.pow(-1, x2 + 1));
		y1 = (int) (M * y2 - mod(y1, M) * Math.pow(-1, y2 + 1));
		return new int[] { x1, y1 };
	}

	public static int B(int... arr) {
		int n = arr.length;
		int sum = n * (n + 1) * (n + 2) / 6;
		Queue<int[]> dupind = new LinkedList<int[]>();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < n; i++) {
			if (!map.containsKey(arr[i])) {
				map.put(arr[i], i);
			} else {
				dupind.add(new int[] { map.get(arr[i]), i });
				map.put(arr[i], i);
			}
		}
		while (!dupind.isEmpty()) {
			int[] ij = dupind.remove();
			sum -= (ij[0] + 1) * (n - ij[1]);
		}
		return sum;
	}

	public static int mod(int a, int b) {
		return Math.abs(a) % b;
	}
}

class Graph {
	public Node[] nodes;
	public int root, goal;

	public Graph(int length) {
		for (int i = 0; i < length; i++) {
			nodes[i] = new Node(i);
		}
	}

	public void setRoot(int root) {
		this.root = root;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public void setRestores(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			nodes[i].restore = true;
		}
	}

	static class Node {
		boolean restore;
		final int id;
		Queue<Node> children;

		public Node(int id) {
			this.id = id;
			restore = false;
			children = new LinkedList<Node>();
		}

		public void addChildren(Node n) {
			children.add(n);
		}

		public Queue<Node> getChildern() {
			return children;
		}
	}
}