package objects;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class TurningRobot extends Robot {
	public PIDControl controller;
	double targetAngle;
	double error;
	boolean done = false;

	public TurningRobot(double kP, double kI, double kD, double targetAngle) {
		super();
		controller = new PIDControl(kP, kI, kD);
		this.targetAngle = targetAngle;
	}

	public void periodic() {
		double errorAngle = (targetAngle - getAngle());
		if (Math.abs(errorAngle) > 1E-4) {
			double PIDangle = controller.output(errorAngle);
//			System.out.println("angle: " + PIDangle);
			setLeftSpeed(-PIDangle);
			setLeftSpeed(PIDangle);
		} else {
			done = true;
		}
	}

	public boolean isDone() {
		return done;
	}

	public double getError() {
		return Math.abs(phi - targetAngle);
	}
	public String toString() {
		return "time: " + time + "\r\nerror: "  + error+ "\r\ncontroller: " + controller;
	}
	public TurningRobot getMutation() {
		return new TurningRobot(controller.kP * (1 + rand.nextGaussian() / 20),
				controller.kI * (1 + rand.nextGaussian() / 20), controller.kD * (1 + rand.nextGaussian() / 20),
				targetAngle);
	}

	public TurningRobot clone() {
		return new TurningRobot(controller.kP, controller.kI, controller.kD, targetAngle);
	}
	
	public static void test(TurningRobot initial, double iterations, double time, double specimenNum, double newPerOld,
			double tests) {
//		LinkedList<TurningRobot> ranking = new LinkedList<TurningRobot>();
		int times = 0;
		PriorityQueue<TurningRobot> ranking = new PriorityQueue<TurningRobot>(
				(o1, o2) -> o1.time - o2.time == 0 ? (int) Math.signum(o1.error - o2.error)
						: (int) Math.signum(o1.time - o2.time));
		LinkedList<TurningRobot> list = new LinkedList<TurningRobot>();
		updateRobot(ranking, initial, time, tests);
		System.out.println(initial);
		ranking.add(initial);
		for (int i = 0; i < iterations; i++) {
			getNextGen(ranking, list, specimenNum, newPerOld);
			while (!list.isEmpty()) {
				TurningRobot specimen = list.poll();
				updateRobot(ranking, specimen, time, tests);
				times++;
			}
		}
		System.out.println(times);
		TurningRobot best = ranking.poll();
		System.out.println(best);

	}

	public static void updateRobot(PriorityQueue<TurningRobot> ranking, TurningRobot robot, double time, double tests) {
		for (int i = 0; i < tests; i++) {
			TurningRobot r = robot.clone();
			for (double j = 0; j < time / Robot.loopTime; j++) {
				r.update();
				if (r.isDone()) {
					break;
				}
			}
			robot.error += r.getError() / tests;
			robot.time += r.time / tests;
		}
		if (robot == null) {
			throw new IllegalArgumentException();
		}
		ranking.add(robot);
	}

	public static void getNextGen(PriorityQueue<TurningRobot> ranking, List<TurningRobot> list, double specimenNum,
			double newPerOld) {
		for (int i = 0; i < specimenNum; i++) {
			TurningRobot current = ranking.remove();
			for (int j = 0; j < newPerOld - 1; j++) {
				list.add(current.getMutation());
			}
			list.add(current.clone());
			if (ranking.isEmpty()) {
				ranking.add(current);
			}
		}
		ranking.clear();
	}

}
