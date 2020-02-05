package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import main.MainWindow;

public class ControllablePlayer extends Player {
	int left, right;

	public ControllablePlayer(double normalSpeed, double direction, double trailLength, double x, double y, World world, Color color,
			int left, int right) {
		super(normalSpeed, direction, trailLength, new Coordinates(x, y, world), world, color);
		this.left = left;
		this.right = right;
	}

	boolean turnedRight = false;
	boolean turnedLeft = false;

	public void update() {
//		if ((MainWindow.pressed[39] || MainWindow.pressed[68]) && !turnedRight) {
		if (MainWindow.pressed[right] && !turnedRight) {
			direction += Math.PI / 30;
//			turnedRight = true;
		}
//		if ((MainWindow.pressed[37] || MainWindow.pressed[65]) && !turnedLeft) {		
		if (MainWindow.pressed[left] && !turnedLeft) {
			direction -= Math.PI / 30;
//			turnedLeft = true;
		}
//		if (!MainWindow.pressed[39] && turnedRight) {
//			turnedRight = false;
//		}
//		if (!MainWindow.pressed[37] && turnedLeft) {
//			turnedLeft = false;
//		}
//		if(MainWindow.pressed[32]) {
		super.update();
//		}
	}

	public void render(Graphics2D g) {
//		g.setColor(new Color(255, 255, 0));
		super.render(g);
	}
}
