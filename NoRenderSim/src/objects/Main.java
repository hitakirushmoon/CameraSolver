package objects;

public class Main {

	public static void main(String[] args) {
		TurningRobot r = new TurningRobot(5.624751816576687, 0.005930069706227574, 0.02235968055678472, Math.PI);
//		TurningRobot r = new TurningRobot(0.1, 0.1, 0.1, Math.PI / 2);
		long time = System.nanoTime();
		TurningRobot.test(r, 80, 5, 4, 20, 200);
		System.out.println((System.nanoTime() - time) / 1E9);
	}
}
