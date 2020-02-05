package bezier;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Queue;

public class Bezier {
	Path curve;
	Polyline first;
	double t = 0;
	double speed = 0.005;
	boolean shouldBeUpdated = false;
	Queue<Polyline> lines = new LinkedList<Polyline>();

	public Bezier(double x, double y) {
		this(new Coordinates(x, y));
	}

	public Bezier(Coordinates start) {
		first = new Polyline(start.clone());
		curve = new Path(start.clone());
	}

	public void update() {
		double tStart = t;
		while (shouldBeUpdated && t - tStart < speed && t < 1) {
			lines.removeAll(lines);
			t += 0.001;
			if (first.startingCoords.next == null) {
				return;
			}
			lines.add(first);
			Polyline current = first;
			for (int i = 0; i < first.size; i++) {
				Polyline next = new Polyline(
						Coordinates.weightedAverage(current.startingCoords, current.startingCoords.next, t, 1 - t));
				Coordinates currentCoord = current.startingCoords.next;
				while (currentCoord.next != null) {
					next.addCoordinates(Coordinates.weightedAverage(currentCoord, currentCoord.next, t, 1 - t));
					currentCoord = currentCoord.next;
				}
				lines.add(next);

				current = next;
			}
			curve.addCoordinates(current.startingCoords);
		}
	}

	public void render(Graphics2D g) {
		double color = 0;
		g.setColor(Color.WHITE);
		g.drawString("speed: " + speed, 100, 100);

		Queue<Polyline> lines = new LinkedList<Polyline>(this.lines);
		while (!lines.isEmpty()) {
			color++;
			g.setColor(Color.getHSBColor((float) (color / (first.size + 1)), 0.75f, 1));
			lines.poll().render(g);
		}
		g.setColor(new Color(255, 255, 255, 128));
		first.render(g);
		g.setColor(Color.getHSBColor((float) 1, 1, 1));
		curve.render(g);

	}
}
