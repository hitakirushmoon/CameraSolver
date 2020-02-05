package robot;

import java.awt.Color;
import java.awt.Graphics2D;

public class APPC implements Renderable {
	public Circle robot;
	public Polyline polyline;
	public Coordinates target;
	public Segment currentSeg;
	public Path path;
	public double speed;
	public boolean shouldBeUpdated = false;

	public APPC(double x, double y) {
		this(new Coordinates(x, y));
	}

	public APPC(Coordinates start) {
		polyline = new Polyline(start.clone());
		robot = new Circle(start.clone(), 1);
		path = new Path(start.clone());
		currentSeg = polyline.start;
		speed = 1;
	}

	public void update() {
		if(shouldBeUpdated && polyline.start != null) {
			moveToTarget();
		}
	}
	public void moveToTarget() {
		if (!reachedEnd()) {
			findTarget();
		}
		if (target != null) {
			robot.center.add(robot.center.getVectorTo(target).normalize().scale(speed));
			path.addCoordinates(robot.center.clone());
		}
	}

	public boolean reachedEnd() {
		if (target != null) {
			return target.getVectorTo(polyline.end.end).size() < 1;
		}
		return false;
	}

	public void findTarget() {
		if (currentSeg == null) {
			currentSeg = polyline.start;
		}
		if (currentSeg.nextSegment != null) {
			Coordinates intersection2 = intersection(robot, currentSeg.nextSegment);
			if (intersection2 != null) {
				target = intersection2;
				currentSeg = currentSeg.nextSegment;
				return;
			}
		}
		Coordinates intersection = intersection(robot, currentSeg);
		target = intersection;

	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(255, 255, 255, 128));
		polyline.render(g);
		g.setColor(Color.GREEN);
		robot.render(g);
		if (target != null) {
			g.setColor(Color.RED);
			new Segment(robot.center, target).render(g);
			;
		}
		g.setColor(Color.BLUE);
		path.render(g);
	}

	public Coordinates intersection(Circle c, Segment s) {
		double y0 = s.start.y;
		double y1 = s.end.y;
		double x0 = s.start.x;
		double x1 = s.end.x;

		// A(x - x0) + B(y - y0) = 0
		double A = y1 - y0, B = x0 - x1, C = x1 * y0 - x0 * y1 + c.center.x * A + c.center.y * B;
		double squaredVector = A * A + B * B;
		double squaredDis = c.radius * c.radius - C * C / squaredVector;
		if (squaredDis > 0) {
			double m = Math.sqrt(squaredDis / squaredVector);
			double x = -A * C / squaredVector, y = -B * C / squaredVector;
			Coordinates target1 = new Coordinates(x + B * m, y - A * m).add(c.center);
			Coordinates target2 = new Coordinates(x - B * m, y + A * m).add(c.center);
			boolean target1inRange = target1.isBetween(s.start, s.end);
			boolean target2inRange = target2.isBetween(s.start, s.end);
			if (target1inRange && target2inRange) {
				return target1.distanceTo(s.end) > target2.distanceTo(s.end) ? target2 : target1;
			} else if (target1inRange) {
				return target1;
			} else if (target2inRange) {
				return target2;
			} else {
				return null;
			}
		}
		return null;
	}
}
