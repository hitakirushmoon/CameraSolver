//package kata;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Pattern;
//
//public class GraphTest {
//	public static void main(String[] args) {
//		int mod = 18, base = 4, remainder = 0;
//		Graph g = new Graph(mod, base, remainder);
//		String solution = g.getSol();
////		solution = solution.substring(0,5) + "*" + solution.substring(5, solution.length());
////		System.out.println(solution);
//		for (int i = 0; i < 300; i++) {
//			System.out.println(i);
//			System.out.println(Integer.toString(i, base));
//			System.out.println(Pattern.compile(solution).matcher(Integer.toString(i, base)).matches());
//		}
//	}
//}
//
//class Graph {
//	Node[] nodes;
//	Node root = new Node(-1);
//	Node end;
//
//	// modulo m, base n and remainder k
//	Graph(int m, int n, int k) {
//		nodes = new Node[m];
//		for (int i = 0; i < nodes.length; i++) {
//			nodes[i] = new Node(i);
//		}
//		for (Node node : nodes) {
//			for (int b = 0; b < n; b++) {
//				int child = (node.value * n + b) % m;
//				if (child == node.value) {
//					if (node.self.isEmpty()) {
//						node.self = Integer.toString(b, n);
//					} else {
//						node.self = Node.paren(node.self + "|" + Integer.toString(b, n));
//					}
//				} else {
//					if (!node.children.containsKey(nodes[child])) {
//						nodes[child].parents.add(node);
//						node.children.put(nodes[child], Integer.toString(b, n));
//					} else {
//						node.children.put(nodes[child],
//								Node.paren(node.children.get(nodes[child]) + "|" + Integer.toString(b, n)));
//					}
//				}
//			}
//		}
//		nodes[0].parents.add(root);
//		root.children.put(nodes[0], "");
//
//		end = new Node(m);
//		nodes[k].children.put(end, "");
//		end.parents.add(nodes[k]);
//		for (int i = m - 1; i > -1; i--) {
//			nodes[i].removeSelf();
//		}
//	}
//
//	String getSol() {
//		return root.children.get(end);
//	}
//}
//
//class Node {
//	int value;
//	Map<Node, String> children = new HashMap<Node, String>();
//	List<Node> parents = new ArrayList<Node>();
//	String self = "";
//
//	public Node(int value) {
//		super();
//		this.value = value;
//	}
//
//	void removeSelf() {
//		if (!self.isEmpty()) {
//			self += "*";
//		}
//		for (Node parent : parents) {
//			for (Node child : children.keySet()) {
//				child.parents.remove(this);
//				String parentsToThis = parent.children.get(this);
//				String thisToChild = children.get(child);
//				String sol;
//
//				sol = paren(concat(concat(parentsToThis, self), thisToChild));
//				if (parent == child) {
//					if (!parent.self.isEmpty()) {
//						parent.self = paren(parent.self + "|" + sol);
//					} else {
//						parent.self = sol;
//					}
//					continue;
//				}
//				if (parent.children.containsKey(child)) {
//					sol = paren(parent.children.get(child) + "|" + sol);
//				} else {
//					child.parents.add(parent);
//				}
//				parent.children.put(child, sol);
//			}
//			parent.children.remove(this);
//
//		}
//	}
//
//	static boolean connectedParen(String a) {
//		if (!a.startsWith("(") || !a.endsWith(")")) {
//			return false;
//		}
//		int right = 0, left = 0;
//		for (int i = 0; i < a.length(); i++) {
//			if (a.charAt(i) == '(') {
//				right++;
//			} else if (a.charAt(i) == ')') {
//				left++;
//			}
//			if (right == left && i != a.length() - 1) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	static String paren(String a) {
//		if (a.length() == 1 || connectedParen(a)) {
//			return a;
//		}
//		return "(" + a + ")";
//	}
//
//	static String concat(String a, String b) {
//		return a + b;
//	}
//
//	public String toString() {
//		return " " + value;
//	}
//}