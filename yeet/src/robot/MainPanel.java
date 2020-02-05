package robot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel {
	public double scale = 1, x = MainWindow.WIDTH / 2, y = MainWindow.HEIGHT / 2;
	private Timer timer = new Timer(1, e -> {
		repaint();
		update();
	});
	APPC test = null;

	public MainPanel() {
		timer.start();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
//		if (MainWindow.LCTRLED && MainWindow.EQUALED) {
//			if (scale >= 1) {
//				x = MouseInfo.getPointerInfo().getLocation().x - this.getLocationOnScreen().x;
//				y = MouseInfo.getPointerInfo().getLocation().y - this.getLocationOnScreen().y;
//			}
//			scale += 0.1;
//		} else if (MainWindow.LCTRLED && MainWindow.MINUSED) {
//			scale -= 0.1;
//			if (scale < 1) {
//				x = MainWindow.WIDTH / 2;
//				y = MainWindow.HEIGHT / 2;
//				scale = 1;
//			}
//		}
//		g2.translate(x, y);
//		g2.scale(scale, scale);
//		g2.translate(-x, -y);

		g2.fillRect(0, 0, MainWindow.WIDTH, MainWindow.HEIGHT);
		if (test != null) {

			test.render(g2);
		}
	}

	public void update() {
		if (test != null) {
			test.update();
		}
	}
}