package objects;

import java.util.Random;

public class Robot {
	public static final double width = 55;
	public static final double loopTime = 0.02;
	public double time = 0;
	protected static final Random rand = new Random();
	PIDControl turn;
	// unit: cm
	public Coordinates position = new Coordinates(0, 0);
	// unit: raidans
	double phi = 0;

	private final Motor left, right;

	private double gyro;

	public Robot(Motor left, Motor right) {
		this.left = left;
		this.right = right;
	}

	public Robot() {
		super();
		left = new Motor(10, 0.7, new Encoder());
		right = new Motor(20, 1, new Encoder());
	}

	public void update() {
		periodic();
		updatePosition();
		time++;
		gyro = phi + rand.nextGaussian() * 0.001;
	}

	public void periodic() {

	}

	private void updatePosition() {
		double leftSpeed = left.output();
		double rightSpeed = right.output();
		double r = width * (rightSpeed + leftSpeed) / (rightSpeed - leftSpeed);
		System.out.println(r);
		Coordinates c = Coordinates.fromRadial(r, phi + Math.PI / 2);
		System.out.println(c);
		phi += (rightSpeed - leftSpeed) * loopTime / width;
		System.out.println(Coordinates.fromRadial(r, phi + Math.PI / 2));
		position.add(c.getVectorTo(Coordinates.fromRadial(r, phi + Math.PI / 2)));
	}

	public long getLeftEncoder() {
		return left.encoder.position;
	}

	public long getRightEncoder() {
		return right.encoder.position;
	}

	public void setLeftSpeed(double speed) {
		left.setSpeed(speed);
	}

	public void setRightSpeed(double speed) {
		right.setSpeed(speed);
	}

	public double getAngle() {
		return gyro;
	}

	public String toString() {
		return left.encoder.position + " " + right.encoder.position + " " + gyro;
	}
}
