package objects;

import java.awt.Graphics2D;
import java.util.List;

import interfaces.Renderable;

public class CubicSpline implements Renderable {
	double a, b, c, d, e, f, g, h;
	List<Coordinates> coords;

	public CubicSpline(Coordinates start, Coordinates end, Coordinates dStart, Coordinates dEnd, double T) {
		d = start.x;
		c = dStart.x;
		h = start.y;
		g = dStart.y;
		a = (T * dEnd.x - 2 * end.x + c * T + 2 * d) / T * T * T;
		b = (3 * end.x - T * dEnd.x - 2 * c * T - 3 * d) / T * T;
		e = (T * dEnd.y - 2 * end.y + g * T + 2 * h) / T * T * T;
		f = (3 * end.y - T * dEnd.y - 2 * g * T - 3 * h) / T * T;
		for (double t = 0; t < T; t += 0.02) {
			coords.add(getPos(t));
		}
		
	}

	public Coordinates getPos(double t) {
		double x = a * t * t * t + b * t * t + c * t + d;
		double y = e * t * t * t + f * t * t + g * t + h;
		return new Coordinates(x, y);
	}

	@Override
	public void render(Graphics2D g) {
		for(Coordinates coord : coords) {
			coord.render(g);
		}
	}

}
