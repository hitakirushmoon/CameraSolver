package kata;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class CountBinary {
	public static void main(String[] args) {
		int base = 4;
		Set<String> stable = new TreeSet<String>(
				(String a, String b) -> new BigInteger(a).subtract(new BigInteger(b)).signum());
		for (int i = 0; i < 1000000; i++) {
			stable.add(iterateCount(i, base));
		}
		System.out.println(stable);
		stable.forEach(x -> iterateCount(x, base));
	}

	public static String iterateCount(String s, int base) {
		LinkedList<String> found = new LinkedList<String>();
		while (!found.contains(s)) {
			found.add(s);
			s = count(s, base);
		}
//		found.add(s);
		System.out.println(found);
		return s;
	}

	public static String iterateCount(int number, int base) {
		String s = Integer.toString(number, base);
		return iterateCount(s, base);
	}

	public static String count(String s, int n) {
		LinkedList<String> counts = new LinkedList<String>();
		for (int digit = 0; digit < n; digit++) {
			int count = s.length() - s.replace(Integer.toString(digit, n), "").length();
			if (count != 0) {
				counts.add(Integer.toString(count, n) + digit);
			}
		}
		return String.join("", counts);
	}
}
