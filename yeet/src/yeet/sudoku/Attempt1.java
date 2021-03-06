package yeet.sudoku;

import java.util.Arrays;
import java.util.LinkedList;

import yeet.sudoku.DancingLinks.Node;

public class Attempt1 {

	public static void main(String[] args) {
		int[][] board = { { 0, 0, 3, 0, 8, 5, 4, 0, 0 }, { 0, 0, 0, 0, 0, 2, 0, 1, 0 }, { 4, 0, 0, 0, 0, 0, 3, 0, 9 },
				{ 0, 6, 1, 2, 0, 8, 0, 0, 0 }, { 5, 0, 0, 0, 4, 0, 0, 0, 6 }, { 0, 0, 0, 6, 0, 1, 7, 9, 0 },
				{ 1, 0, 9, 0, 0, 0, 0, 0, 3 }, { 0, 2, 0, 3, 0, 0, 0, 0, 0 }, { 0, 0, 4, 5, 1, 0, 2, 0, 0 }, };
//		Sudoku puzzle = new Sudoku(board);
//		puzzle.solve();
//		boolean ProbMat[][] = new boolean[7][7];
//		ProbMat[0][0] = true;
//		ProbMat[3][0] = true;
//		ProbMat[6][0] = true;
//
//		ProbMat[0][1] = true;
//		ProbMat[3][1] = true;
//
//		ProbMat[3][2] = true;
//		ProbMat[4][2] = true;
//		ProbMat[6][2] = true;
//
//		ProbMat[2][3] = true;
//		ProbMat[4][3] = true;
//		ProbMat[5][3] = true;
//		ProbMat[1][4] = true;
//		ProbMat[2][4] = true;
//		ProbMat[5][4] = true;
//		ProbMat[6][4] = true;
//		ProbMat[1][5] = true;
//		ProbMat[6][5] = true;
//		ProbMat[0][6] = true;
//		ProbMat[3][6] = true;
		Sudoku a = new Sudoku(board);
		a.solve();

	}

}

class Sudoku {
	int[][] board;
	DancingLinks solver;

	public Sudoku(int[][] board) {
		this.board = board;
		System.out.println(this);
	}

	public void solve() {
		boolean[][] matrix = new boolean[324][729];
		for (int col = 0; col < 9; col++) {
			for (int row = 0; row < 9; row++) {
				if (board[col][row] == 0) {
					for (int num = 0; num < 9; num++) {
						int index = col * 81 + row * 9 + num;
						matrix[col * 9 + row][index] = true;
						matrix[col * 9 + num + 81][index] = true;
						matrix[row * 9 + num + 162][index] = true;
						matrix[((col / 3) * 3 + row / 3) * 9 + num + 243][index] = true;
					}
				} else {
					int num = board[col][row] - 1;
					int index = col * 81 + row * 9 + num;
					matrix[col * 9 + row][index] = true;
					matrix[col * 9 + num + 81][index] = true;
					matrix[row * 9 + num + 162][index] = true;
					matrix[((col / 3) * 3 + row / 3) * 9 + num + 243][index] = true;
				}
			}
		}
		solver = new DancingLinks(matrix);
		solver.search();
		LinkedList<LinkedList<Node>> solutions = solver.solutions;
		while (!solutions.isEmpty()) {
			LinkedList<Node> solution = solutions.pop();
			int[][] solvedboard = board.clone();
			while (!solution.isEmpty()) {
				Node current = solution.pop();
				int rownum = current.row / 81 + 1;
				int colnum = (current.row / 9) % 9 + 1;
				int num = current.row % 9 + 1;
				solvedboard[rownum - 1][colnum - 1] = num;
			}
			System.out.println(toString(solvedboard));
		}
	}

	public String toString() {
		return toString(board);
	}

	public static String toString(int[][] board) {
		StringBuffer a = new StringBuffer();
		for (int i = 0; i < board.length; i++) {
			a.append(Arrays.toString(board[i]));
			a.append("\n");
		}
		return a.toString();
	}
}

class DancingLinks {
	Node header = new Node(-1, -1);
	int rowNum, colNum;

	public DancingLinks(boolean[][] problem) {
		Node current = header;
		header.column = header;
		colNum = problem.length;
		rowNum = problem[0].length;
		for (int i = 0; i < colNum; i++) {
			current.right = new Node(i, -1);
			current.right.left = current;
			current = current.right;
			current.column = current;

		}
		current.right = header;
		header.left = current;
		current = header;
		Node[][] matrix = new Node[colNum][rowNum];
		for (int i = 0; i < colNum; i++) {
			for (int j = 0; j < rowNum; j++) {
				if (problem[i][j]) {
					matrix[i][j] = new Node(i, j);
				}
			}
		}

		for (int i = 0; i < colNum; i++) {
			current = current.right;
			Node last = current;
			for (int j = 0; j < rowNum; j++) {
				if (problem[i][j]) {
					current.count++;
					Node cur = matrix[i][j];
					int a, b;
					last = cur;
					cur.column = current;
					a = i;
					b = j;
					do {
						a = mod(a + 1, colNum);
					} while (!problem[a][b]);
					cur.right = matrix[a][b];

					a = i;
					b = j;
					do {
						a = mod(a - 1, colNum);
					} while (!problem[a][b]);
					cur.left = matrix[a][b];

					a = i;
					b = j;
					do {
						b = mod(b + 1, rowNum);
					} while (!problem[a][b]);
					cur.down = matrix[a][b];

					a = i;
					b = j;
					do {
						b = mod(b - 1, rowNum);
					} while (!problem[a][b]);
					cur.up = matrix[a][b];

				}
			}
			if (last == current) {
				current.up = last;
				current.down = last;
			} else {
				current.up = last;
				current.down = last.down;
				current.down.up = current;
				current.up.down = current;
			}
		}
	}

	LinkedList<Node> solution = new LinkedList<Node>();
	LinkedList<LinkedList<Node>> solutions = new LinkedList<LinkedList<Node>>();

	public void search() {
//		System.out.println(this);
		if (header.right == header) {
			solutions.add(new LinkedList<Node>(solution));
			return;
		} else {
			Node column = chooseNextNode();
			column.cover();
			for (Node rowNode = column.down; rowNode != column; rowNode = rowNode.down) {
				solution.addLast(rowNode);

				for (Node rightNode = rowNode.right; rightNode != rowNode; rightNode = rightNode.right) {
					rightNode.cover();
				}
				search();

				solution.removeLast();
				for (Node leftNode = rowNode.left; leftNode != rowNode; leftNode = leftNode.left) {
					leftNode.uncover();
				}
			}

			column.uncover();
		}
	}

	public Node chooseNextNode() {
		Node choice = header;
		for (Node current = header.right; current != header; current = current.right) {
			if (choice.count == 0 || (current.count < choice.count && current.count != 0)) {
				choice = current;
			}
		}
		return choice;
	}

	public static int mod(int a, int b) {
		return (a % b + b) % b;
	}

	public String toString() {
		StringBuilder a = new StringBuilder();
		a.append(header);
		Node currentCol = header;
		for (int i = 0; i < colNum; i++) {
			if (currentCol.right.col == i) {
				currentCol = currentCol.right;
				a.append("\r\n");
				a.append(currentCol.col);
				Node currentRow = currentCol;
				for (int j = 0; j < rowNum; j++) {
					a.append(" ");
					if (currentRow.down.row == j) {
						currentRow = currentRow.down;
						a.append(currentRow.row);
					} else {
						a.append(" ");
					}
				}
			}
		}
		a.append("\r\n");
		a.append("end");
		a.append("\r\n");
		return a.toString();
	}

	static class Node {
		Node left, right, up, down;
		Node column;
		int col, row;
		int count;

		public Node(int col, int row) {
			this.col = col;
			this.row = row;
		}

		public void cover() {
			Node column = this.column;

			column.right.left = column.left;
			column.left.right = column.right;

			for (Node row = column.down; row != column; row = row.down) {
				for (Node rightNode = row.right; rightNode != row; rightNode = rightNode.right) {
					rightNode.up.down = rightNode.down;
					rightNode.down.up = rightNode.up;
				}
			}
		}

		public void uncover() {
			Node column = this.column;

			for (Node row = column.up; row != column; row = row.up) {
				for (Node leftNode = row.left; leftNode != row; leftNode = leftNode.left) {
					leftNode.up.down = leftNode;
					leftNode.down.up = leftNode;
				}
			}
			column.right.left = column;
			column.left.right = column;
		}

		public String toString() {
//			return "C" + rownum + "R" + colnum + "N" + num + "Condition: " + col;
			return row + " " + col;
		}
	}

}