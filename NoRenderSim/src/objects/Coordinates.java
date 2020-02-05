package objects;

public class Coordinates {
	public double x, y;

	public Coordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}


	public double size() {
		return Math.sqrt(x * x + y * y);
	}

	public double distanceTo(Coordinates a) {
		return Math.sqrt((a.x - x) * (a.x - x) + (a.y - y) * (a.y - y));
	}

	public static Coordinates fromRadial(double r, double phi) {
		return new Coordinates(r * Math.cos(phi), r * Math.sin(phi));
	}

	public Coordinates add(Coordinates a) {
		x += a.x;
		y += a.y;
		return this;
	}

	public static Coordinates sum(Coordinates a, Coordinates b) {
		return new Coordinates(a.x + b.x, a.y + b.y);
	}

	public Coordinates scale(double a) {
		x *= a;
		y *= a;
		return this;
	}

	public Coordinates getVectorTo(Coordinates a) {
		return new Coordinates(a.x - x, a.y - y);
	}

	// does NOT check if a point is on a segment
	public boolean isBetween(Coordinates a, Coordinates b) {
		return (a.x - x) * (b.x - x) < 0 && (a.y - y) * (b.y - y) < 0;
	}

	public double direction() {
		return Math.atan2(y, x);
	}

	public Coordinates normalize() {
		double size = size();
		x /= size;
		y /= size;
		return this;
	}

	public boolean isZeroVector() {
		return size() < 1E-6;
	}

	@Override
	public String toString() {
		return " x: " + x + "; y: " + y;
	}

	public Coordinates clone() {
		return new Coordinates(x, y);
	}

	public static Coordinates weightedAverage(Coordinates c1, Coordinates c2, double w1, double w2) {
		return c1.clone().scale(w2).add(c2.clone().scale(w1)).scale(1 / (w1 + w2));
	}
}
