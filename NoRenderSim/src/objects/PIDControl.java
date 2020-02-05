package objects;

public class PIDControl {
	double kP, kI, kD;

	public PIDControl(double kP, double kI, double kD) {
		super();
		this.kP = Math.max(kP, 0);
		this.kI = Math.max(kI, 0);
		this.kD = Math.max(kD, 0);
	}

	double I = 0;
	double previous_error = Double.POSITIVE_INFINITY;

	public double output(double error) {
		double P = error * kP;
		double I = this.I + error * kI * Robot.loopTime;
		double D = previous_error == Double.POSITIVE_INFINITY ? 0 : (error - previous_error) * kD / Robot.loopTime;
		previous_error = error;
		double output = P + I + D;
		if (Math.abs(output) < 0.5) {
			this.I = I;
		}
		return output;
	}

	public String toString() {
		return kP + ", " + kI + ", " + kD;
	}
}
