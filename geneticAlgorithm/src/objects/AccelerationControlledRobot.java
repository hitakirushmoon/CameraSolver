package objects;

public class AccelerationControlledRobot extends Robot{
	Coordinates[] accelerations;
	double leftSpeed = 0;
	double rightSpeed = 0;
	public AccelerationControlledRobot(Chromosome c) {
		super();
		accelerations = c.gene;
	}
	
	public void periodic() {
		leftSpeed += accelerations[time].x;
		rightSpeed += accelerations[time].y;
		setLeftSpeed(leftSpeed);
		setRightSpeed(rightSpeed);
//		System.out.println(position);
	}	
}
