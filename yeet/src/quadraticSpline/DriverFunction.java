package quadraticSpline;

import java.awt.Graphics2D;

public class DriverFunction {
	static MultiQuadraticSpline mqs = new MultiQuadraticSpline();
	static double t = 0;
	static Path curve;
	
	public static void update() {
		t++;
		if (MainWindow.clicked) {
			mqs.addCoordinates(MainWindow.mouseClickCoords);
		}
		mqs.getCoords(t);
	}

	public static void render(Graphics2D g) {
		mqs.render(g);
	}
}
