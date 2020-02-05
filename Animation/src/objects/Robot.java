package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;

import interfaces.Animatable;
import main.MainPanel;

public class Robot implements Animatable {
	Coordinates pos;
	Coordinates speed;
	double t = 0;
	static final double dt = 0.02;
	public QuadraticSplines path;
	QuadraticSpline current;

	public Robot(MainPanel p) {
		p.getUpdatables().add(this);
		p.getRenderables().add(this);
		path = new QuadraticSplines(p);
	}

	@Override
	public void render(Graphics2D g) {
		if (pos != null) {
			g.setColor(Color.RED);
			g.drawRect((int) pos.x - 2, (int) pos.y - 2, 5, 5);
		}
	}

	@Override
	public void update() {
		if (!path.splines.isEmpty()) {
			if (current == null) {
				current = path.splines.peekFirst();
			} else {
				t += dt;
				if (t > current.tStart + current.t) {
					current = current.next;
				}
				speed = current.getDerrivative(t);
				pos.add(speed.scale(dt));
			}
		} else {
			if (pos == null && path.start != null) {
				pos = path.start;
			}
		}
	}
}
