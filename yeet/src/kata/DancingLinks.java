//package kata;
//
//import java.util.LinkedList;
//
//public class DancingLinks {
//	Node header = new Node(-1, -1);
//	int rowNum, colNum;
//
//	public DancingLinks(boolean[][] problem) {
//		Node current = header;
//		header.column = header;
//		colNum = problem.length;
//		rowNum = problem[0].length;
//		for (int i = 0; i < colNum; i++) {
//			current.right = new Node(i, -1);
//			current.right.left = current;
//			current = current.right;
//			current.column = current;
//
//		}
//		current.right = header;
//		header.left = current;
//		current = header;
//		Node[][] matrix = new Node[colNum][rowNum];
//		for (int i = 0; i < colNum; i++) {
//			for (int j = 0; j < rowNum; j++) {
//				if (problem[i][j]) {
//					matrix[i][j] = new Node(i, j);
//				}
//			}
//		}
//
//		for (int i = 0; i < colNum; i++) {
//			current = current.right;
//			Node last = current;
//			for (int j = 0; j < rowNum; j++) {
//				if (problem[i][j]) {
//					current.count++;
//					Node cur = matrix[i][j];
//					int a, b;
//					last = cur;
//					cur.column = current;
//					a = i;
//					b = j;
//					do {
//						a = mod(a + 1, colNum);
//					} while (!problem[a][b]);
//					cur.right = matrix[a][b];
//
//					a = i;
//					b = j;
//					do {
//						a = mod(a - 1, colNum);
//					} while (!problem[a][b]);
//					cur.left = matrix[a][b];
//
//					a = i;
//					b = j;
//					do {
//						b = mod(b + 1, rowNum);
//					} while (!problem[a][b]);
//					cur.down = matrix[a][b];
//
//					a = i;
//					b = j;
//					do {
//						b = mod(b - 1, rowNum);
//					} while (!problem[a][b]);
//					cur.up = matrix[a][b];
//
//				}
//			}
//			if (last == current) {
//				current.up = last;
//				current.down = last;
//			} else {
//				current.up = last;
//				current.down = last.down;
//				current.down.up = current;
//				current.up.down = current;
//			}
//		}
//	}
//
//	LinkedList<Node> solution = new LinkedList<Node>();
//	LinkedList<LinkedList<Node>> solutions = new LinkedList<LinkedList<Node>>();
//
//	public void search() {
////		System.out.println(this);
//		if (header.right == header) {
//			LinkedList<Node> solutionClone = new LinkedList<Node>(solution);
//			solutionClone.sort((Node a, Node b) -> a.row - b.row);
//			solutions.add(solutionClone);
//			return;
//		} else {
//			Node column = chooseNextNode();
//			column.cover();
//			for (Node rowNode = column.down; rowNode != column; rowNode = rowNode.down) {
//				solution.addLast(rowNode);
//
//				for (Node rightNode = rowNode.right; rightNode != rowNode; rightNode = rightNode.right) {
//					rightNode.cover();
//				}
//				search();
//
//				solution.removeLast();
//				for (Node leftNode = rowNode.left; leftNode != rowNode; leftNode = leftNode.left) {
//					leftNode.uncover();
//				}
//			}
//
//			column.uncover();
//		}
//	}
//
//	public Node chooseNextNode() {
//		Node choice = header;
//		for (Node current = header.right; current != header; current = current.right) {
//			if (choice.count == 0 || (current.count < choice.count && current.count != 0)) {
//				choice = current;
//			}
//		}
//		return choice;
//	}
//
//	public static int mod(int a, int b) {
//		return (a % b + b) % b;
//	}
//
//	public String toString() {
//		StringBuilder a = new StringBuilder();
//		a.append(header);
//		Node currentCol = header;
//		for (int i = 0; i < colNum; i++) {
//			if (currentCol.right.col == i) {
//				currentCol = currentCol.right;
//				a.append("\r\n");
//				a.append(currentCol.col);
//				Node currentRow = currentCol;
//				for (int j = 0; j < rowNum; j++) {
//					a.append(" ");
//					if (currentRow.down.row == j) {
//						currentRow = currentRow.down;
//						a.append(currentRow.row);
//					} else {
//						a.append(" ");
//					}
//				}
//			}
//		}
//		a.append("\r\n");
//		a.append("end");
//		a.append("\r\n");
//		return a.toString();
//	}
//}
//
//class Node {
//	Node left, right, up, down;
//	Node column;
//	int col, row;
//	int count;
//	
//	public Node(int col, int row) {
//		this.col = col;
//		this.row = row;
//	}
//
//	public void cover() {
//		Node column = this.column;
//
//		column.right.left = column.left;
//		column.left.right = column.right;
//
//		for (Node row = column.down; row != column; row = row.down) {
//			for (Node rightNode = row.right; rightNode != row; rightNode = rightNode.right) {
//				rightNode.up.down = rightNode.down;
//				rightNode.down.up = rightNode.up;
//			}
//		}
//	}
//
//	public void uncover() {
//		Node column = this.column;
//
//		for (Node row = column.up; row != column; row = row.up) {
//			for (Node leftNode = row.left; leftNode != row; leftNode = leftNode.left) {
//				leftNode.up.down = leftNode;
//				leftNode.down.up = leftNode;
//			}
//		}
//		column.right.left = column;
//		column.left.right = column;
//	}
//
//	public String toString() {
////			return "C" + rownum + "R" + colnum + "N" + num + "Condition: " + col;
//		return row + " " + col;
//	}
//}