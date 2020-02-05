package yeet;

import java.util.Arrays;

public class MCQ {
	public static void main(String[] args) {
		int[][] a = { { 1, -3, 0 }, { 4, 5, 1 }, { 3, 8, 0 } };
		int[][] b = { { 1, 1, -2 }, { 3, 0, -4 }, { -1, 3, 2 } };
		int[][] ab = multi(a, b);
		int[][] c = { { 2, 0, -2 }, { 4, 7, -5 }, { 1, 0, -1 } };
		int[][] cc = multi(c, c);
		System.out.println(Arrays.deepToString(add(scale(ab, 2), cc)));
		int[][] a1 = { { 1, 2 }, { 3, -4 } };
		int[][] b1 = { { 5, 6 }, { -6, 7 } };
		int[][] c1 = { { 1, -3, 4 }, { 2, 6, -5 } };
		System.out.println(Arrays.deepToString(multi(multi(a1,b1),c1)))	;
		
		
	}

	public static int[][] multi(int[][] a, int[][] b) {
		int[][] c = new int[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				for (int k = 0; k < a[0].length; k++) {
					c[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return c;
	}

	public static int[][] add(int[][] a, int[][] b) {
		int[][] c = new int[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				c[i][j] = a[i][j] + b[i][j];
			}
		}
		return c;
	}

	public static int[][] scale(int[][] a, int b) {
		int[][] c = new int[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				c[i][j] = a[i][j] * b;
			}
		}
		return c;
	}
}