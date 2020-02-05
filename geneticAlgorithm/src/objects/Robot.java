package objects;

import java.util.Random;

public class Robot {
	public static final double width = 55;
	public static final double loopTime = 0.02;
	public int time = 0;
	protected static final Random rand = new Random();
	// unit: cm
	public Coordinates position = new Coordinates(0, 0);
	// unit: raidans
	double phi = 0;

	private final Motor left = new Motor(10, 0.7, new Encoder()), right = new Motor(20, 1, new Encoder());

	private double gyro;

	public Robot() {
		super();
	}

	public void update() {
		periodic();
		time++;
		double leftSpeed = left.output();
		double rightSpeed = right.output();
		position.add(Coordinates.fromRadial(leftSpeed + rightSpeed, phi).scale(0.5).scale(loopTime));
		phi += (leftSpeed - rightSpeed) * loopTime / width;
		gyro = phi + rand.nextGaussian() * 0.001;

	}

	public void periodic() {

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
