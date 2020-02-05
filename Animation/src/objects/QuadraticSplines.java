package objects;

import java.awt.Graphics2D;
import java.util.LinkedList;

import interfaces.Renderable;
import main.MainPanel;

public class QuadraticSplines implements Renderable {

	LinkedList<QuadraticSpline> splines;
	Coordinates start;

	public QuadraticSplines(MainPanel p) {
		p.getRenderables().add(this);
		splines = new LinkedList<QuadraticSpline>();
	}

	public void addPoints(Coordinates point) {
		if (start == null) {
			start = point;
		} else if (splines.isEmpty()) {
			splines.add(new QuadraticSpline(start, point, new Coordinates(0, 0), 0, 1));
		} else {
			QuadraticSpline spline = splines.peekLast();
			QuadraticSpline newSpline = new QuadraticSpline(spline.end, point,
					spline.getDerrivative(spline.tStart + spline.t), spline.tStart + spline.t, 1);
			spline.next = newSpline;
			splines.addLast(newSpline);
		}
	}

	@Override
	public void render(Graphics2D g) {

		for (QuadraticSpline spline : splines) {
			spline.render(g);
		}
	}

}
