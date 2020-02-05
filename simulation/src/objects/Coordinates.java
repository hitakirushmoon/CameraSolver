package objects;

public class Coordinates {
	double x, y;
	World world;

	public Coordinates(double x, double y, World world) {
		this.x = x;
		this.y = y;
		this.world = world;
	}

	public Coordinates add(Coordinates a) {
		x = mod(a.x + x, world.width);
		y = mod(a.y + y, world.height);
		return this;
	}

	public double mod(double a, double b) {
		return (a % b + b) % b;
	}

	public double slope() {
		return Math.atan2(y, x);
	}

	public Coordinates getClosestVectorTo(Coordinates c) {

		return new Coordinates(
				Math.abs(c.x - x) < world.width / 2 ? c.x - x
						: -Math.signum(c.x - x) * (world.width - Math.abs(c.x - x)),
				Math.abs(c.y - y) < world.height / 2 ? c.y - y
						: -Math.signum(c.y - y) * (world.height - Math.abs(c.y - y)),
				world);
	}

	public boolean wrapAround(Coordinates c) {
		return Math.abs(c.x - x) > world.width / 2 || Math.abs(c.y - y) > world.height / 2;
	}

	public Coordinates rotate(double angle, Coordinates center) {
		double normalizedX = x - center.x;
		double normalizedY = y - center.y;
		double sine = Math.sin(angle);
		double cosine = Math.cos(angle);
		return new Coordinates(normalizedX * cosine - normalizedY * sine, normalizedY * cosine + normalizedX * sine,
				world).add(center);
	}

	public static Coordinates fromRadial(double r, double phi, World world) {
		return new Coordinates(r * Math.cos(phi), r * Math.sin(phi), world);
	}

	public double smallestDistanceTo(Coordinates c) {
		return Math.sqrt(Math.min(Math.abs(c.x - x), world.width - Math.abs(c.x - x))
				* Math.min(Math.abs(c.x - x), world.width - Math.abs(c.x - x))
				+ Math.min(Math.abs(c.y - y), world.height - Math.abs(c.y - y))
				+ Math.min(Math.abs(c.y - y), world.height - Math.abs(c.y - y)));
	}

	public Coordinates clone() {
		return new Coordinates(x, y, world);
	}

	public String toString() {
		return " x: " + x + "; y: " + y;
	}

	public boolean checkCollisionWithSegment(Coordinates c1, Coordinates c2, double width) {
		Coordinates center = new Coordinates(c1.x + c2.x, c1.y + c2.y, world).scale(0.5);
		Coordinates rotated = rotate(Math.atan2(c2.y - c1.y, c2.x - c1.x), center);
		double height = c1.smallestDistanceTo(c2);
		if (Math.abs(rotated.x - center.x) < height / 2 && Math.abs(rotated.y - center.y) < width / 2) {
			System.out.println("oofs");
		}
		return Math.abs(rotated.x - center.x) < height / 2 && Math.abs(rotated.y - center.y) < width / 2;
	}

	public Coordinates scale(double a) {
		x *= a;
		y *= a;
		return this;
	}

	static boolean onSegment(Coordinates p, Coordinates q, Coordinates r) {
		if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && q.y <= Math.max(p.y, r.y)
				&& q.y >= Math.min(p.y, r.y))
			return true;

		return false;
	}

	// To find orientation of ordered triplet (p, q, r).
	// The function returns following values
	// 0 --> p, q and r are colinear
	// 1 --> Clockwise
	// 2 --> Counterclockwise
	static int orientation(Coordinates p, Coordinates q, Coordinates r) {
		// See https://www.geeksforgeeks.org/orientation-3-ordered-Coordinatess/
		// for details of below formula.

		return (int) Math.signum((q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y));
	}

	// The main function that returns true if line segment 'p1q1'
	// and 'p2q2' intersect.
	static boolean doIntersect(Coordinates p1, Coordinates p2, Coordinates p3, Coordinates p4) {
		// Find the four orientations needed for general and
		// special cases
		Coordinates c2 = p1.getClosestVectorTo(p2);
		Coordinates c3 = p1.getClosestVectorTo(p3);
		Coordinates c4 = p3.getClosestVectorTo(p4);
		Coordinates d2 = add(c2, p1);
		Coordinates d3 = add(c3, p1);
		Coordinates d4 = add(p1, add(c4, c3));

		int o1 = orientation(p1, d2, d3);
		int o2 = orientation(d2, d3, d4);
		int o3 = orientation(d3, d4, p1);
		int o4 = orientation(d4, p1, d2);
		// General case
		if (o1 * o3 + o2 * o4 == -2 && o1 * o2 + o3 * o4 == 2) {
			System.out.println();
		}
//		if (do1 * do3 + do2 * do4 == -2 && do1 * do2 + do3 * do4 == 2) {
//			System.out.println();
//		}
		return o1 * o3 + o2 * o4 == -2 && o1 * o2 + o3 * o4 == 2;

	}

	static Coordinates add(Coordinates a, Coordinates b) {
		return new Coordinates(a.x + b.x, a.y + b.y, a.world);
	}
}