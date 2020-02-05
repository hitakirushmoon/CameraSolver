package kata;

import java.math.BigInteger;

public class Fibonacci {


	public static BigInteger fib(BigInteger n) {
		int nint = n.intValue();
		return fib(nint);
	}

	static BigInteger fib(int n) {
		if (n == 0) {
			return BigInteger.ZERO;
		}
		if (n == 1 || n == 2) {
			return BigInteger.ONE;
		}

		if (n % 2 == 0) {
			if (n < 0) {
				return fib(-n).negate();
			} else {
				BigInteger halfFib = fib(n / 2);
				return halfFib.multiply(fib(n / 2 + 1).multiply(BigInteger.TWO).subtract(halfFib));
			}
		} else {
			if (n < 0) {
				return fib(-n);
			} else {
				BigInteger halfFib = fib(n / 2);
				BigInteger halfFibMore = fib(n / 2 + 1);
				halfFib = halfFib.multiply(halfFib);
				halfFibMore = halfFibMore.multiply(halfFibMore);
				return halfFib.add(halfFibMore);
			}
		}
	}

}