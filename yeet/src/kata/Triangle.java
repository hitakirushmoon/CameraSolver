package kata;

import java.util.Random;

public class Triangle {
	public static void main(String[] args) {
		StringBuilder test = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < Math.pow(3, 18); i++) {
			test.append(toChar(r.nextInt(3)));
		}
		System.out.println("done!");
		long time = System.nanoTime();
		System.out.println(triangle(test.toString()));
		System.out.println((System.nanoTime() - time) / 1E9);
//		System.out.println(RGB.triangle(test.toString()));
	}

	public static char triangle(String rgb) {

		return toChar(lmao(rgb, rgb.length() - 1, 1, 0, rgb.length() % 2 == 1));
	}

	public static int lmao(String rgb, int num, int current_three_pow, int x_degree, boolean multiplier) {
		if (num == 0) {
			return toInt(rgb.charAt(x_degree)) * (multiplier ? 1 : -1);
		}
		int mod = num % 3;
		int next_num = num / 3;
		if (mod == 0) {
			return lmao(rgb, next_num, current_three_pow * 3, x_degree, multiplier);
		}
		if (mod == 1) {
			return lmao(rgb, next_num, current_three_pow * 3, x_degree, multiplier)
					+ lmao(rgb, next_num, current_three_pow * 3, x_degree + current_three_pow, multiplier);
		}
		return lmao(rgb, next_num, current_three_pow * 3, x_degree, multiplier)
				+ lmao(rgb, next_num, current_three_pow * 3, x_degree + current_three_pow, !multiplier)
				+ lmao(rgb, next_num, current_three_pow * 3, x_degree + 2 * current_three_pow, multiplier);

	}

	public static int toInt(char rgb) {
		switch (rgb) {
		case 'R':
			return 0;
		case 'G':
			return 1;
		case 'B':
			return -1;
		default:
			return 0;
		}
	}

	public static char toChar(int rgb) {
		switch ((rgb % 3 + 3) % 3) {
		case 0:
			return 'R';
		case 1:
			return 'G';
		case 2:
			return 'B';
		default:
			return ' ';
		}
	}
}
