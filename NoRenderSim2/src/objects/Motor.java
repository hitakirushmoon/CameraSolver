package objects;

public class Motor {
	// unit : -1.0 to 1.0 of unknown measure
	private double speed = 0;

	// unit : cm/s
	private double lastOutput = 0;

	// unit : cm/s
	public final double maxSpeed;
	public final double maxAcc;
	public final double drift;

	public final double variability;
	public final double bias;

	public final Encoder encoder;

	public Motor(Encoder encoder) {
		this(10, 1, encoder);
	} 
	
	public Motor(double variability, double bias, Encoder encoder) {
		this(300, 50, 15, variability, bias, encoder);
	}

	public Motor(double maxSpeed, double maxAcc, double drift, double variability, double bias, Encoder encoder) {
		this.maxSpeed = maxSpeed;
		this.maxAcc = maxAcc;
		this.drift = drift;
		this.variability = variability;
		this.bias = bias;
		this.encoder = encoder;
	}

	

	double output() {
		lastOutput = MathUtils.limitChange(speed * maxSpeed * bias + Robot.rand.nextGaussian() * variability * speed,
				lastOutput, maxAcc, drift);

		encoder.position += lastOutput * Robot.loopTime * 4096 / maxSpeed;
		return lastOutput;
	}

	// unit : -1.0 to 1.0 of unknown measure
	public void setSpeed(double speed) {
		this.speed = MathUtils.limit(speed, -1, 1);
	}

}
