package objects;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import interfaces.Renderable;

public class QuadraticSpline implements Renderable {
	double a, b, c, d, e, f, t;
	double tStart;
	List<Coordinates> coords = new LinkedList<Coordinates>();
	Coordinates end;
	QuadraticSpline next;
	public QuadraticSpline(Coordinates start, Coordinates end, Coordinates dStart, double tStart, double t) {
		this.t = t;
		c = start.x;
		b = dStart.x;
		f = start.y;
		e = dStart.y;
		a = (end.x - b * t - c) / (t * t);
		d = (end.y - e * t - f) / (t * t);
		this.tStart = tStart;
		this.end = end;
	}

	public Coordinates getPos(double t) {
		double x = a * (t - tStart) * (t - tStart) + b * (t - tStart) + c;
		double y = d * (t - tStart) * (t - tStart) + e * (t - tStart) + f;
		return new Coordinates(x, y);
	}

	public Coordinates getDerrivative(double t) {
		double x = 2 * a * (t - tStart) + b;
		double y = 2 * d * (t - tStart) + e;
		return new Coordinates(x, y);
	}

	@Override
	public void render(Graphics2D g) {
		for (double t = 0; t < this.t; t += this.t / 50) {
			coords.add(getPos(t + tStart));
		}
		for (Coordinates coord : coords) {
			coord.render(g);
		}
	}

}
