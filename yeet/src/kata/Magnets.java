package kata;

class Magnets {
	public static void main(String[] args) {
		System.out.println(doubles(1,10));
	}
	public static double doubles(int maxk, int maxn) {
		double sum = 0;
		for (double k = 1; k <= maxk; k++) {
			for (double n = 1; n <= maxn; n++) {
				sum += v(k,n);
			}
		}
		return sum;
	}

	public static double v(double k, double n) {
		return 1 / (k * Math.pow(n + 1, 2 * k));
	}
}