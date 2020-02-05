package kata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CakeCutter {

	String[] slices;
	int count;

	public CakeCutter(String cake) {
		slices = cake.split("\n");
		count = cake.length() - cake.replace("o", "").length();
	}

	List<Integer> cakeSizes = new ArrayList<Integer>();
	int[][] position;
	int sizeOfRects;
	int m, n;

	public List<String> cut() {
		// Coding and coding... again!
		m = slices.length;
		n = slices[0].length();
		position = new int[count][2];

		sizeOfRects = m * n / count;
		for (int i = 1; i <= Math.sqrt(sizeOfRects); i++) {
			if (sizeOfRects % i == 0) {
				cakeSizes.add(i);
				if (i * i != sizeOfRects) {
					cakeSizes.add(sizeOfRects / i);
				}
			}
		}
		Collections.sort(cakeSizes);
		int raisin = 0;
		for (int x = 0; x < m && raisin < count; x++) {
			for (int y = 0; y < n && raisin < count; y++) {
				if (slices[x].charAt(y) == 'o') {
					position[raisin][0] = x;
					position[raisin][1] = y;
					raisin++;
				}
			}
		}
		return search(slices, new LinkedList<String>());
	}

	public List<String> search(String[] cake, List<String> solution) {
		int[] nextCorner = getNextCorner(cake);

		if (nextCorner == null) {
			return solution;
		}
		int x = nextCorner[0];
		int y = nextCorner[1];

		hLoop: for (Integer h : cakeSizes) {
			int w = sizeOfRects / h;
			if (x + h > m || y + w > n) {
				continue;
			}

			boolean found = false;
			for (int[] pos : position) {
				if (pos[0] >= x && pos[0] < x + h && pos[1] >= y && pos[1] < y + w) {
					if (found) {
						continue hLoop;
					}
					found = true;
				}
			}
			if (!found) {
				continue;
			}
			String[] cakeClone = cake.clone();
			String slice = cut(cakeClone, x, y, w, h);
			if (slice == null) {
				continue;
			}
			solution.add(slice);
			List<String> result = search(cakeClone, solution);
			if (!result.isEmpty()) {
				return result;
			}
			solution.remove(slice);
		}
		return new LinkedList<String>();
	}

	public String cut(String[] cake, int x, int y, int w, int h) {
		if (h == 1)
			System.out.println();
		List<String> slice = new LinkedList<String>();
		String whiteSpace = "";
		for (int i = 0; i < w; i++) {
			whiteSpace += ' ';
		}
		for (int i = x; i < x + h; i++) {
			if (cake[i].substring(y, y + w).contains(" ")) {
				return null;
			}
			slice.add(cake[i].substring(y, y + w));
			cake[i] = cake[i].substring(0, y) + whiteSpace + cake[i].substring(y + w);
		}
		return String.join("\n", slice);
	}

	public static int[] getNextCorner(String[] cake) {
		for (int x = 0; x < cake.length; x++) {
			for (int y = 0; y < cake[x].length(); y++) {
				if (cake[x].charAt(y) != ' ') {
					return new int[] { x, y };
				}
			}
		}
		return null;
	}
}
