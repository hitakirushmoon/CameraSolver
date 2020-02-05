package kata;

public class Immortal {
	/** set true to enable debug */
	static boolean debug = false;

	static long elderAge(long largerSide, long smallerSide, long decrement, long mod) {
		if (largerSide == 0 || smallerSide == 0) {
			return 0;
		}
		if (smallerSide > largerSide) {
			long temp = largerSide;
			largerSide = smallerSide;
			smallerSide = temp;
		}
		if (smallerSide == 1) {
			return consec(0, largerSide - 1, decrement, mod);
		}

		long largestPowerOf2 = 1;
		while (largestPowerOf2 * 2 <= largerSide) {
			largestPowerOf2 = largestPowerOf2 * 2;
		}
		if (smallerSide > largestPowerOf2) {
			long firstQuarter = largestPowerOf2 % mod * consec(0, largestPowerOf2 - 1, decrement, mod);
			long secondAndThirdQuarter = ((smallerSide + largerSide - largestPowerOf2 * 2)) % mod
					* consec(0, largestPowerOf2 - 1, decrement - largestPowerOf2, mod);
			long fourthQuarter = elderAge(smallerSide - largestPowerOf2, largerSide - largestPowerOf2, decrement, mod);
			return (firstQuarter + secondAndThirdQuarter + fourthQuarter) % mod;
		} else {
			long firstHalf = smallerSide % mod * consec(0, largestPowerOf2 - 1, decrement, mod);
			long secondHalf = elderAge(smallerSide, largerSide - largestPowerOf2, decrement - largestPowerOf2, mod);
			return (firstHalf + secondHalf) % mod;
		} // do it!
	}

	static long consec(long start, long end, long k, long p) {
		start -= k;
		end -= k;
		if (start < 0) {
			start = 0;
		}
		if (end < start) {
			return 0;
		}
		long sum = (start + end);
		long num = (end + 1 - start);
		long res = 0;
		if ((sum & 1) == 0) {
			res = ((sum / 2) % p * (num % p)) % p;
		} else {
			res = ((num / 2) % p * (sum % p)) % p;
		}
		return res;
	}
}
