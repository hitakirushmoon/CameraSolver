package kata;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Loopover {
	public static void main(String[] args) {
		char[][] solvedBoard = new char[][] { { 'A', 'B', 'C', 'D', 'E' }, { 'F', 'G', 'H', 'I', 'J' },
				{ 'K', 'L', 'M', 'N', 'O' }, { 'P', 'Q', 'R', 'S', 'T' }, { 'U', 'V', 'W', 'X', 'Y' } };
//		char[][] testBoard = new char[][] { { 'C', 'W', 'M', 'F', 'J' }, { 'O', 'R', 'D', 'B', 'A' },
//			{ 'N', 'K', 'G', 'L', 'Y' }, { 'P', 'H', 'S', 'V', 'E' }, { 'X', 'T', 'Q', 'U', 'I' } };
		char[][] testBoard = new char[][] { { 'A', 'C', 'D', 'B', 'E' }, { 'F', 'G', 'H', 'I', 'J' },
				{ 'K', 'L', 'M', 'N', 'O' }, { 'P', 'Q', 'R', 'S', 'T' }, { 'U', 'V', 'W', 'X', 'Y' } };
		Loopover test = new Loopover(testBoard, solvedBoard);
		System.out.println(test.solve().size());
	
	}

	int[] normalizedBoard;
	int w, h;

	public Loopover(char[][] mixedUpBoard, char[][] solvedBoard) {
		w = mixedUpBoard.length;
		h = mixedUpBoard[0].length;
		normalizedBoard = new int[w * h];
		for (int x = 0; x < solvedBoard.length; x++) {
			for (int y = 0; y < solvedBoard[x].length; y++) {
				char current = solvedBoard[x][y];
				innerLoop: for (int x0 = 0; x0 < mixedUpBoard.length; x0++) {
					for (int y0 = 0; y0 < mixedUpBoard[x0].length; y0++) {
						if (current == mixedUpBoard[x0][y0]) {
							normalizedBoard[x0 * solvedBoard.length + y0] = x * mixedUpBoard.length + y;
							break innerLoop;
						}
					}
				}
			}
		}
		System.out.println(Arrays.toString(normalizedBoard));
	}

	LinkedList<String> solution = new LinkedList<String>();

	public List<String> solve() {
		for (int i = 0; i < w * (h - 1); i++) {
			int current = 0;
			for (int a = 0; a < normalizedBoard.length; a++) {
				if (i == normalizedBoard[a]) {
					current = a;
					break;
				}
			}
			solve_wh_1(current);
			System.out.println(this);
		}
		for (int i = w * (h - 1); i < w * h; i++) {
			int current = 0;
			for (int a = 0; a < normalizedBoard.length; a++) {
				if (i == normalizedBoard[a]) {
					current = a;
					break;
				}
			}
			solveLastLayer(current);
			System.out.println(this);

		}
		return solution;
	}

	public void solveLastLayer(int current) {
		int target = normalizedBoard[current];
		if (target == current)
			return;

		if (target == w * (h - 1)) {
			int i = y(current);
			while (i != y(target)) {
				R(x(target));
				i--;
			}
			return;
		} else {
			permutate(current);
		}
	}

	public void permutate(int current) {
		int target = normalizedBoard[current];
		int buffer = target + 1;
		if (buffer == current) {
			U(y(current));
			L(x(current));
			D(y(current));
			R(x(current));
			R(x(current));
			U(y(current));
			L(x(current));
			D(y(current));

		} else {
			U(y(target));
			R(x(target));
			D(y(target));
			for (int i = y(current) - 1; i > y(target); i--) {
				R(x(target));
			}
			U(y(target));
			for (int i = 0; i < y(current) - y(target); i++) {
				L(x(target));
			}
			D(y(target));
		}
	}

	public void solve_wh_1(int current) {
		int target = normalizedBoard[current];
		if (target == current)
			return;

		if (x(target) == x(current)) {
			D(y(current));
			D(y(target));
			int i = y(current);
			while (i != y(target)) {
				R(x(current) + 1);
				i = (i + h - 1) % h;
			}
			U(y(target));
			U(y(current));
		} else if (y(target) == y(current)) {
			L(x(current));
			int i = x(target);
			while (i != x(current)) {
				D(y(target));
				i++;
			}
			R(x(current));
			while (i != x(target)) {
				U(y(target));
				i = (i + h - 1) % h;
			}
		} else {
			int i = x(target);
			while (i != x(current)) {
				D(y(target));
				i++;
			}
			i = y(current);
			while (i != y(target)) {
				R(x(current));
				i = (i + h - 1) % h;
			}
			i = x(current);
			while (i != x(target)) {
				U(y(target));
				i--;
			}
		}
	}

	int x(int pos) {
		return pos / 5;
	}

	int y(int pos) {
		return pos % 5;
	}

	public void L(int row) {
		int temp = normalizedBoard[row * 5 + w - 1];
		for (int i = w - 1; i > 0; i--) {
			normalizedBoard[row * 5 + i] = normalizedBoard[row * 5 + i - 1];
		}
		solution.add("L" + row);
		normalizedBoard[row * 5] = temp;
	}

	public void R(int row) {
		int temp = normalizedBoard[row * 5];
		for (int i = 0; i < w - 1; i++) {
			normalizedBoard[row * 5 + i] = normalizedBoard[row * 5 + i + 1];
		}
		solution.add("R" + row);
		normalizedBoard[row * 5 + w - 1] = temp;
	}

	public void D(int col) {
		int temp = normalizedBoard[col + (h - 1) * 5];
		for (int i = h - 1; i > 0; i--) {
			normalizedBoard[col + i * 5] = normalizedBoard[col + (i - 1) * 5];
		}
		solution.add("D" + col);
		normalizedBoard[col] = temp;
	}

	public void U(int col) {
		int temp = normalizedBoard[col];
		for (int i = 0; i < h - 1; i++) {
			normalizedBoard[col + i * 5] = normalizedBoard[col + (i + 1) * 5];
		}
		solution.add("U" + col);
		normalizedBoard[col + (h - 1) * 5] = temp;
	}

	public String toString() {
		StringBuilder a = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				String num = "0" + normalizedBoard[i * 5 + j];
				a.append(num.substring(num.length() - 2) + " ");
			}
			a.append("\n");
		}
		return a.toString();
	}
}