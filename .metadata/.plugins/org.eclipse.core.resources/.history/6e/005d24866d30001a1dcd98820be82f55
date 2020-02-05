package controller;

import interfaces.AnimatedObject;
import objects.Coordinates;
import objects.Robot;

public class MotorController extends AnimatedObject {
	public static final Robot r = new Robot();
	public final Coordinates trackedPosition = new Coordinates(0, 0);
	public long lastLeft = r.getLeftEncoder(), lastRight = r.getRightEncoder();
	public double lastAngle = r.getAngle();

	public MotorController() {
		super();
		straight.start();
	}

	Command straight = new StraightLine(500, 5);

	public void update() {
		super.update();
	}

	void updateTrackedPosition() {
		double leftSpeed = r.getLeftEncoder() - lastLeft;
		double rightSpeed = r.getRightEncoder() - lastRight;
		lastLeft = r.getLeftEncoder();
		lastRight = r.getRightEncoder();

	}
}
