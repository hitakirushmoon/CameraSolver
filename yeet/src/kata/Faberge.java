package kata;

import java.math.BigInteger;

public class Faberge {
	public static void main(String[] args) {
//		System.out.println(degree(BigInteger.valueOf(2),BigInteger.valueOf(3)));
		System.out.println(height(BigInteger.valueOf(40000), BigInteger.valueOf(80000)));
	}

	private static BigInteger p = BigInteger.valueOf(998244353);
	static int pp = 998244353;
	static int[] remainder = new int[998244353];

	public static BigInteger height(BigInteger n, BigInteger m) {
		BigInteger h = BigInteger.ZERO;
		BigInteger a = BigInteger.ONE;
		for (BigInteger e = BigInteger.ONE; e.compareTo(n) <= 0; h = h.add(a), e = e.add(BigInteger.ONE)) {
			a = a.multiply(m.add(BigInteger.ONE.subtract(e)).mod(p)).multiply(degree(e.mod(p), pp - 2)).mod(p);
		}
		return h.mod(p);
	}

	public static BigInteger degree(BigInteger a, int k) {
		long res = 1;
		long cur = a.intValue();
		if (cur == 0)
			return BigInteger.ZERO;
		if (remainder[a.intValue()] != 0) {
			return BigInteger.valueOf(remainder[a.intValue()]);
		}
		while (k > 0) {
			if (k % 2 == 1) {
				res = (res * cur) % pp;
			}
			k /= 2;
			cur = (cur * cur) % pp;
		}
		remainder[a.intValue()] = (int) res;
		return BigInteger.valueOf(res);
	}

}