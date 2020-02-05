package yeet.rubiks;

import java.util.Arrays;

public class CubeUtils {
	final static int[] factorialconst = { 1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600 };

	protected static int nonConsecutiveFHasher(byte[] arr) {
		byte[] clone = arr.clone();
		byte[] translatearr = arr.clone();
		Arrays.sort(clone);
		for (int i = 0; i < clone.length; i++) {
			translatearr[i] = (byte) Arrays.binarySearch(clone, translatearr[i]);
		}
		return fHasher(translatearr);
	}

	protected static int fHasher(byte arr[]) {
		int a = 0;
		byte[] translatearr = arr.clone();
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				if (translatearr[j] > translatearr[i]) {
					translatearr[j]--;
				}
			}
		}
		for (int i = 0; i < arr.length; i++) {
			a += translatearr[i] * factorialconst[arr.length - i - 1];
		}

		return a;
	}

	protected static int cHasher(byte[] arr) {
		int a = 0;
		byte[] clone = arr.clone();
		Arrays.sort(clone);
		for (int i = 0; i < clone.length; i++) {
			a += nCk(clone[i], i + 1);
		}
		return a;
	}

	private static int nCk(int n, int k) {
		if (n < k) {
			return 0;
		}
		return factorialconst[n] / (factorialconst[k] * factorialconst[n - k]);
	}

	protected static int bHasher(byte[] arr, int base, int offsetend) {
		int a = 0;
		for (int i = 0; i < arr.length - offsetend; i++) {
			a += Math.pow(base, i) * arr[i];
		}
		return a;
	}

}
