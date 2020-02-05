package actualPID;

public class PID {
	double target = -1;
	double P, I, D;
	double culmativeError = 0;
	double lastError = 0;
	double kP = 0, kI = 0, kD = 0;

	public PID(double kP, double kI, double kD) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
	}

	public PID(double target, double kP, double kI, double kD) {
		this.target = target;
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
	}

	public PID() {
	}

	public double getPID(double current) {
		double e = target - current;
		culmativeError += e;
		P = e * kP * 0.1;
		I = culmativeError * kI * 0.0001;
		if (lastError == 0) {
			D = 0;
		} else {
			D = (e - lastError) * kD;
		}
		lastError = e;
		return P + I + D;
	}

}
