package kata;

import java.util.Arrays;
import java.util.Random;

public class Roll {
	public static void main(String[] args) {
		Random dice = new Random();
		int m = 5, n = 10, t = 1000000, j = 4;
		int[] result = new int[(int) m];
		double sum = 0;
		double lowguess = 0;
		double highguess = 0;
		double probsum = 0;
		for (int k = 0; k < n; k++) {
//			System.out.println(k);
//			System.out.println(((Math.pow(k + 1, m) - Math.pow(k, m)) / Math.pow(n, m)));

//			System.out.println(((Math.pow(k + 1, m) - Math.pow(k, m)) / Math.pow(n, m)));
			highguess += k * (Math.pow(k + 1, j + 1) - Math.pow(k, j + 1)) / Math.pow(n, j + 1) * (Math.pow(n - k, m - j) - Math.pow(n - (k + 1), m - j)) / Math.pow(n, m - j);
			probsum += (Math.pow(k + 1, m) - Math.pow(k, m)) / Math.pow(n, m);
		}
		System.out.println(probsum);
		System.out.println(lowguess);
		System.out.println(highguess);
		for (int i = 0; i < t; i++) {
			for (int trial = 0; trial < m; trial++) {
				result[trial] = dice.nextInt((int) n);
			}
			Arrays.sort(result);
			sum += result[j];
		}

		System.out.println(sum / t);
//		System.out.println(guess - sum / t);
	}
}
