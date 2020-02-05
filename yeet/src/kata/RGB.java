package kata;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class RGB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int count = 0;
		StringBuilder oeis = new StringBuilder();
		for (int i = 7; i < 8; i++) {
			for (int j = 0; j < Math.pow(3, i); j++) {
				count++;
				StringBuilder number = new StringBuilder(Integer.toString(j, 3));
				while (number.length() < i) {
					number.insert(0, '0');
				}
				String test = number.toString().replace('0', 'R').replace('1', 'G').replace('2', 'B');
				oeis.append(" ");
				oeis.append(toInt(triangle(test)));
			}
		}
		System.out.println(oeis);
	}

	static int total = 0;

	public static char triangle(String rgb) {
		int result = 0;
		int num = rgb.length() - 1;
		
		Map<Integer, Integer> count = new TreeMap<Integer, Integer>();
		count.put(0, 1);
		int runningTotal = 0;
		boolean isEven = true;
		for (int i = 0; num > 0; i++) {
			for (int mod = 0; mod < num % 3; mod++) {
				isEven = !isEven;
				runningTotal += Math.pow(3, i);
				Map<Integer, Integer> countClone = new HashMap<Integer, Integer>(count);
				for (Integer key : countClone.keySet()) {
					int newKey = runningTotal - key;
					total++;
					if (countClone.containsKey(newKey)) {
						count.put(newKey, countClone.get(newKey) + countClone.get(key));
					} else {
						count.put(newKey, countClone.get(key));
					}
				}
//				System.out.println(count);
			}

			num /= 3;
		}
		for (Integer key : count.keySet()) {
			result += toInt(rgb.charAt(key)) * count.get(key);
		}
		return toChar(result, isEven);
	}

	public static int toInt(char rgb) {
		switch (rgb) {
		case 'R':
			return 0;
		case 'G':
			return 1;
		case 'B':
			return 2;
		default:
			return -1;
		}
	}

	public static char toChar(int rgb, boolean isEven) {
		switch (rgb % 3) {
		case 0:
			return 'R';
		case 1:
			return isEven ? 'G' : 'B';
		case 2:
			return isEven ? 'B' : 'G';
		default:
			return ' ';
		}
	}
}
