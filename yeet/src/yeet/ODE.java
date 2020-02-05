package yeet;

public class ODE {
	public static void main(String[] args) {
		final double k = 9E9;
		final double episilon = 2;
		final double u = 1;
		final double q1 = 6E-6, q2 = 6E-6;
		double t = 0;
		double r = 1;
		double a = 0;
		double v = 0.2;
		double dt = 0.00001;
		double limit = 0.1;
		System.out.println(" a: " + a + " v: " + v + " r: " + r + " t: " + t);
		while (true) {
			t += dt;
			a = q1 * q2 * k / (r * r * episilon) - u * v * v;
			v += a * dt;
			r += v * dt;
			if (v < limit) {
				limit -= limit * 0.1;
				System.out.println(" a: " + a + " v: " + v + " r: " + r + " t: " + t);
			}
		}
	}
}
