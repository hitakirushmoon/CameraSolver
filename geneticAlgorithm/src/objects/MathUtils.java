package objects;

public class MathUtils {
	public static double limit(double arg, double min, double max) {
		return Math.max(Math.min(arg, max), min);
	}

	public static double limitChange(double current, double last, double changeLimit) {
		return limit(current, last - changeLimit, last + changeLimit);
	}

	public static double limitChange(double current, double last, double upLimit, double downLimit) {
		if (current > 0)
			return limit(current, last - downLimit, last + upLimit);
		else
			return limit(current, last - upLimit, last + downLimit);
	}
}
