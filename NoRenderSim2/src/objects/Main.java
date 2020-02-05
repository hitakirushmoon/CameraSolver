package objects;

public class Main {

	public static void main(String[] args) {
		long time = System.nanoTime();
		Robot r = new Robot(new Motor(1, 1, 1, 0, 1, new Encoder()), new Motor(1, 1, 1, 0, 1, new Encoder()));
		r.setLeftSpeed(1);
		r.setRightSpeed(0.9);
		for (int i = 0; i < 1000; i++) {
			r.update();
			System.out.println(r.position);
		}
	}
}
