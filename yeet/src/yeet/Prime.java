package yeet;

import java.math.BigInteger;
import java.util.LinkedList;

public class Prime {
	public static void main(String[] args) {
//		String a ="11111112222222111112222221111111" 
//				+ "11111220000002211220000002211111"
//				+ "11122002222220022002222220022111"
//				+ "12200221111112200221111112200221"
//				+ "20022111111111122111111111122002"
//				+ "20021111111111111111111111112002"
//				+ "20021111111111111111111111112002"
//				+ "20022111111111111111111111122002"
//				+ "12200211111111111111111111200221"
//				+ "11200221111111111111111112200211"
//				+ "11122002211111111111111220022111"
//				+ "11111220022111111111122002211111"
//				+ "11111112200221111112200221111111"
//				+ "11111111122002211220022111111111"
//				+ "11111111111220022002211111111111"
//				+ "11111111111112200221111111111111"
//				+ "11111111111111122111111111111111"

		String a = "11111220000002211220000002211111" + "11122002222220022002222220022111"
				+ "12200221111112200221111112200221" + "20022111111111122111111111122002"
				+ "20021111111111111111111111112002" + "20021111111111111111111111112002"
				+ "20022111111111111111111111122002" + "12200211111111111111111111200221"
				+ "11200221111111111111111112200211" + "11122002211111111111111220022111"
				+ "11111220022111111111122002211111" + "11111112200221111112200221111111"
				+ "11111111122002211220022111111111" + "11111111111220022002211111111111"
				+ "11111111111112200221111111111111"

		;
//		for (int x = 1; x < 17; x++) {
//			for (int y = 1; y < 31; y++) {
//				if (a.charAt(x * 32 + y) == '0') {
//					boolean problem = false;
//					if (a.charAt((x + 1) * 32 + y) == '1') {
//						System.out.println("down");
//						problem = true;
//					}
//					if (a.charAt((x - 1) * 32 + y) == '1') {
//						System.out.println("up");
//						problem = true;
//					}
//					if (a.charAt(x * 32 + y + 1) == '1') {
//						System.out.println("left");
//						problem = true;
//					}
//					if (a.charAt(x * 32 + y - 1) == '1') {
//						System.out.println("right");
//						problem = true;
//					}
//					if (problem) {
//						System.out.println(x + " " + y);
//						System.out.println();
//					}
//				}
//			}
//		}
		boolean found = false;
		int i = 2;
		while (!found) {
			found = addNoise(a, 2, i);
			System.out.println(i);
			i++;
		}
		System.out.println(a.length());
	}

	public static boolean addNoise(String a, int layer, int chunk) {
		return addNoise(a, layer, chunk, 0, new LinkedList<Integer>());
	}

	public static boolean addNoise(String a, int layer, int chunk, int startingNum, LinkedList<Integer> history) {
		if (layer == 0) {
			BigInteger b = new BigInteger(a);
//			BigInteger e = new BigInteger(a, 3);
			if (b.isProbablePrime(10)
//					&& e.isProbablePrime(10)
			) {
				for (int i = 0; i < 15; i++) {
					System.out.println(b.toString().substring(i * 32, (i + 1) * 32));
				}
				System.out.println(b);
				System.out.println(history);
//				System.out.println(e);
				return true;

			}
			return false;
		}
		boolean found = false;
		for (int i = startingNum; i < a.length() - layer - chunk + 1; i++) {
			boolean all1s = true;
			String all2s = "";
			for (int index = 0; index < chunk; index++) {
				all2s += "2";
				if (a.charAt(i + index) != '1') {
					all1s = false;
				}
			}
			if (all1s) {
				String d = a.substring(0, i) + all2s + a.substring(i + chunk, a.length());
				history.addLast(i);
				found = addNoise(d, layer - 1, chunk, i + chunk, history) || found;
				history.removeLast();
			}
		}
		return found;
	}
}
