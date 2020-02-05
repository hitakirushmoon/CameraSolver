package kata;

public class Immortal2 {
	public static void main(String[] args) {
		int m = 9, n = 19, l = 3;
		int[][][] debug = genXor(m, n, l);
		int[][][] debug2 = genXor(m, n, 0);

		for (int i = 0; i < debug.length; i++) {
			int[][] table = debug[i];
			int sum1 = 0;
			int sum2 = 0;

			for (int j = 0; j < table.length; j++) {
				int[] row = table[j];
				for (int cell : row) {
					System.out.print(cell + "" + cell + " ");
					sum1 += cell;
				}
				System.out.print("       ");
				for (int cell : debug2[i][j]) {
					System.out.print(cell + "" + cell + " ");
					sum2 += cell;
				}
				System.out.println();
			}
			System.out.println(sum2 - sum1);
		}
//		System.out.println((double)(test(m, n) - sum)/l);
		System.out.println(test(m, n, l));
		System.out.println(sum);

	}

	static int sum = 0;

	public static int[][][] genXor(int m, int n, int l) {
		sum = 0;
		int[][][] table = new int[6][m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				int cell = Math.max((i ^ j) - l, 0);
				sum += cell;

				for (int k = 0; k < 6; k++) {
					table[k][i][j] = cell % 2;
					cell /= 2;
//				}
				}
			}
		}
		return table;
	}

	public static int test(int m, int n, int l) {
		int sum = 0;
		for (int i = 1; i < m || i < n; i *= 2) {
			int m1 = m % i;
			int n1 = n % i;
			int m2 = m / i;
			int n2 = n / i;
			int firstQuad = (m2 * n2) / 2 * i * i * i;
			int lastQuad = (m2 + n2) % 2 * i * m1 * n1;
			int secondQuad = (m2 % 2 + n2) / 2 * i * i * m1;
			int thridQuad = (n2 % 2 + m2) / 2 * i * i * n1;
			sum += firstQuad + secondQuad + thridQuad + lastQuad;
		}
		return sum;
	}
}
