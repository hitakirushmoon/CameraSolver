package actualPID;

import java.awt.Color;
import java.awt.Graphics2D;

public class Controller implements Renderable, Updateable {
	PID control;
	double startingCurrent = -1;
	double current = -1;
	double maxSpeed;
	double lastSpeed = 0;
	double maxAcceleration;
	Path path = new Path();
	boolean shouldBeUpdated = false;
	double yValue = 200;
	double weightConst = -0.3;
	double t = 0;
	Path P = new Path(), I = new Path(), D = new Path();

	public Controller(PID control, double maxSpeed, double maxAcceleration) {
		this.control = control;
		this.maxSpeed = maxSpeed;
		this.maxAcceleration = maxAcceleration;
	}

	public void setTarget(double target) {
		control.target = target;
	}

	public void setCurrent(double current) {
		this.current = current;
		this.startingCurrent = current;
		path.addCoordinates(new Coordinates(yValue + t, current));
	}

	public void update() {
		if (control.target != -1 && current != -1 && shouldBeUpdated()) {
			t++;
			double speed = control.getPID(current);
			speed = Math.min(lastSpeed + maxAcceleration, Math.max(speed, lastSpeed - maxAcceleration));
			current += Math.min(maxSpeed, Math.max(speed, -maxSpeed)) - weightConst;
			path.addCoordinates(new Coordinates(yValue + t, current));

			lastSpeed = speed;
		}
	}

	@Override
	public boolean shouldBeUpdated() {
		return shouldBeUpdated;
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawString("kP: " + control.kP, 0, 20);
		g.drawString("kI: " + control.kI, 0, 30);
		g.drawString("kD: " + control.kD, 0, 40);
		g.drawString("max speed: " + maxSpeed, 0, 50);
		g.drawString("max acceleration: " + maxAcceleration, 0, 60);

		if (control.target != -1 && current != -1) {
			g.drawString("pos: " + current, 0, 100);
			g.drawString("lastError: " + control.lastError, 0, 110);
			g.drawString("last speed: " + lastSpeed, 0, 160);
			P.addCoordinates(new Coordinates(yValue + t,
					control.P * 40 + startingCurrent));
			I.addCoordinates(new Coordinates(yValue + t,
					control.I * 40 + startingCurrent));
			D.addCoordinates(new Coordinates(yValue + t,
					control.D * 40 + startingCurrent));
			g.setColor(Color.GRAY);
			Coordinates target = new Coordinates(yValue + t, control.target);
			target.render(g);
			g.setColor(Color.WHITE);
			path.render(g);
			g.setColor(Color.RED);
			g.drawString("P: " + control.P, 0, 120);
			P.render(g);
			g.setColor(Color.GREEN);
			g.drawString("I: " + control.I, 0, 130);
			I.render(g);
			g.setColor(Color.BLUE);
			g.drawString("D: " + control.D, 0, 140);
			D.render(g);

//			new Segment(target, current).render(g);
		}
	}

}
