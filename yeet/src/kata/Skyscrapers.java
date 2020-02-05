package kata;

import java.util.Arrays;
import java.util.LinkedList;

public class Skyscrapers {
	public static void main(String[] args) {
		int[][] result = solvePuzzle(
				new int[] { 7, 0, 0, 0, 2, 2, 3, 0, 0, 3, 0, 0, 0, 0, 3, 0, 3, 0, 0, 5, 0, 0, 0, 0, 0, 5, 0, 4 });

	}

	static int[][] solvePuzzle(int[] clues) {
		System.out.println(Arrays.toString(clues));
		Solver solve = new Solver(clues);
		solve.solve();
//		solve.printClues();
		return solve.returnAns();
	}

}

class Solver {
	static int size;
	int largestNum;
	int[] clues;
	LinkedList<Integer>[][] units;

	public Solver(int[] clues) {
		this.clues = clues;
		size = clues.length / 4;
		largestNum = size - 1;
		LinkedList<Integer>[][] values = new LinkedList[size][size];
		units = new LinkedList[4 * size][size];
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				values[row][col] = new LinkedList<Integer>();
				for (int num = 0; num < size; num++) {
					values[row][col].add(num);
				}
			}
		}
		for (int col = 0; col < size; col++) {
			for (int row = 0; row < size; row++) {
				units[col][row] = values[row][col];
			}
		}
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				units[row + size][largestNum - col] = values[row][col];
			}
		}
		for (int col = 0; col < size; col++) {
			for (int row = 0; row < size; row++) {
				units[3 * size - 1 - col][largestNum - row] = values[row][col];
			}
		}
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				units[4 * size - 1 - row][col] = values[row][col];
			}
		}
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				for (int num = 0; num < size; num++) {
					if (quickElim(row, col, num)) {
						eliminate(units, col, row, num);
					}
				}
			}
		}
	}

	void solve() {
		dfs(units);
	}

	boolean dfs(LinkedList<Integer>[][] units) {
		int minUnit = -1, minIndex = -1, minSize = size + 1;
		for (int unit = 0; unit < size; unit++) {
			for (int index = 0; index < size; index++) {
				if (units[unit][index].size() > 1 && units[unit][index].size() < minSize) {
					minUnit = unit;
					minIndex = index;
					minSize = units[unit][index].size();
				}
			}
		}
		if (minSize == size + 1) {
			this.units = units;
			return true;
		}
		if (minSize == 0) {
			return false;
		}
		LinkedList<Integer> cell = units[minUnit][minIndex];
		for (Integer possibility : new LinkedList<Integer>(cell)) {
			debug(minIndex, minUnit, possibility + 1, 1, 3, 1);
			LinkedList<Integer>[][] unitsClone = clone(units);
			if (!assign(unitsClone, minUnit, minIndex, possibility)) {

				if (!eliminate(units, minUnit, minIndex, possibility)) {
					return false;
				}
				continue;
			}
			if (!dfs(unitsClone)) {
				if (!eliminate(units, minUnit, minIndex, possibility)) {
					return false;
				}
				continue;
			}
			return true;
		}
		return false;
	}

	LinkedList<Integer>[][] clone(LinkedList<Integer>[][] units) {
		LinkedList<Integer>[][] values = new LinkedList[size][size];
		LinkedList<Integer>[][] unitsCloned = new LinkedList[4 * size][size];

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				values[row][col] = new LinkedList<Integer>(units[col][row]);
			}
		}
		for (int col = 0; col < size; col++) {
			for (int row = 0; row < size; row++) {
				unitsCloned[col][row] = values[row][col];
			}
		}
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				unitsCloned[row + size][largestNum - col] = values[row][col];
			}
		}
		for (int col = 0; col < size; col++) {
			for (int row = 0; row < size; row++) {
				unitsCloned[3 * size - 1 - col][largestNum - row] = values[row][col];
			}
		}
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				unitsCloned[4 * size - 1 - row][col] = values[row][col];
			}
		}
		return unitsCloned;
	}

	boolean eliminate(LinkedList<Integer>[][] units, int unit, int index, Integer num) {
		LinkedList<Integer> cell = units[unit][index];
		if (!cell.contains(num)) {
			return true;
		}
		cell.remove(num);
		if (cell.size() == 0) {
			return false;
		}
		if (cell.size() == 1) {
			Integer onlyNum = cell.peek();
			for (int i = 0; i < size; i++) {
				if (i == index)
					continue;
				if (!eliminate(units, unit, i, onlyNum)) {
					return false;
				}
			}
			for (int i = 0; i < size; i++) {
				if (i == unit % size)
					continue;
				if (!eliminate(units, unit - unit % size + i, index, onlyNum)) {
					return false;
				}
			}
		}

		int occurencesI = 0;
		int occurencesU = 0;
		int occurenceIndex = -1;
		int occurenceUnit = -1;
		for (int i = 0; i < size; i++) {
			if (units[unit][i].contains(num)) {
				occurencesI++;
				occurenceIndex = i;
			}
			if (units[unit - unit % size + i][index].contains(num)) {
				occurencesU++;
				occurenceUnit = unit - unit % size + i;
			}
		}
		if (occurencesI == 0 || occurencesU == 0) {
			return false;
		}
		if (occurencesI == 1) {
			if (!assign(units, unit, occurenceIndex, num)) {
				return false;
			}
		}
		if (occurencesU == 1) {
			if (!assign(units, occurenceUnit, index, num)) {
				return false;
			}
		}
		int[] whereToBound = unitsAndIndexes(unit, index);
		for (int i = 0; i < whereToBound.length; i++) {
			if (clues[whereToBound[i]] == 0) {
				continue;
			}
			for (int ind = 0; ind < size; ind++) {
				for (Integer num2 : new LinkedList<Integer>(units[whereToBound[i]][ind])) {
					int[] bounds = getClueBounds(units, whereToBound[i], ind, num2);
					if (clues[whereToBound[i]] < bounds[0] || clues[whereToBound[i]] > bounds[1]) {
						if (!eliminate(units, whereToBound[i], ind, num2)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	int[] unitsAndIndexes(int unit, int index) {
		int[] result = new int[4];
		int col = (unit / size) % 2 == 0 ? unit % size : index;
		int row = (unit / size) % 2 == 0 ? index : unit % size;
		result[0] = col;
		result[1] = row + size;
		result[2] = 3 * size - 1 - col;
		result[3] = 4 * size - 1 - row;
		return result;
	}

	boolean assign(LinkedList<Integer>[][] units, int unit, int index, Integer num) {
		LinkedList<Integer> cell = units[unit][index];
		if (!cell.contains(num)) {
			return false;
		}
		for (Integer eliminated : new LinkedList<Integer>(cell)) {
			if (eliminated == num) {
				continue;
			}
			if (!eliminate(units, unit, index, eliminated)) {
				return false;
			}
		}
		return true;
	}

	void debug(int row, int col, int num, int tarRow, int tarCol, int tarNum) {
		if (row == tarRow && col == tarCol && num == tarNum) {
			System.out.println();
		}
	}

//	check if maximum possible clue is smaller than current clue
	boolean quickElim(int row, int col, int num) {

		if (row + size - num < clues[col] && clues[col] != 0) {
			return true;
		}
		if (col + size - num < clues[4 * size - 1 - row] && clues[4 * size - 1 - row] != 0) {
			return true;
		}
		if (largestNum - row + size - num < clues[3 * size - 1 - col] && clues[3 * size - 1 - col] != 0) {
			return true;
		}
		if (largestNum - col + size - num < clues[size + row] && clues[size + row] != 0) {
			return true;
		}
		return false;
	}

	int[] getClueBounds(LinkedList<Integer>[][] units, int unit, int index, int num) {

		int min = -1, max = -1, resultMin = 0, resultMax = 0;
		LinkedList<Integer>[] cell = units[unit];
		for (int i = 0; i < size; i++) {
			if (i == index) {
				if (min < num) {
					min = num;
					resultMax++;
				}
				if (max < num) {
					max = num;
					resultMin++;
				}
			} else {
				if (cell[i].getFirst() == num && cell[i].size() == 1) {
					return new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE };
				}
				int minPossible = cell[i].getFirst() == num ? cell[i].get(1) : cell[i].getFirst();
				int maxPossible = cell[i].getLast() == num ? cell[i].get(cell[i].size() - 2) : cell[i].getLast();
				if (maxPossible > min) {
					resultMax++;
				}
				if (minPossible > max) {
					resultMin++;
				}
				if (minPossible > min) {
					min = minPossible;
				}
				if (maxPossible > max) {
					max = maxPossible;
				}
			}
		}
		return new int[] { resultMin, resultMax };
	}

//	public String toString() {
//		StringBuilder s = new StringBuilder();
//		for (LinkedList<Integer>[] row : values) {
//			s.append(Arrays.toString(row)).append("\n");
//		}
//		return s.toString();
//
//	}

//	void printClues() {
//		String whiteSpace = "";
//		for (int i = 0; i < size; i++) {
//			whiteSpace += " ";
//		}
//		System.out.print(" ");
//		for (int i = 0; i < size; i++) {
//			System.out.print(clues[i]);
//		}
//		System.out.println(" ");
//		for (int i = 0; i < size; i++) {
//			System.out.println(clues[4 * size - i - 1] + whiteSpace + clues[i + size]);
//		}
//		System.out.print(" ");
//		for (int i = 0; i < size; i++) {
//			System.out.print(clues[3 * size - i - 1]);
//		}
//		System.out.println(" ");
//
//	}

	int[][] returnAns() {
		int[][] ans = new int[size][size];
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				ans[row][col] = units[col][row].peek() + 1;
			}
//			System.out.println(Arrays.toString(ans[row]));
		}
		return ans;
	}

}