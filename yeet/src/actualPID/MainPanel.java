package actualPID;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel {
	public double scale = 1, x = MainWindow.WIDTH / 2, y = MainWindow.HEIGHT / 2;
	private Timer timer = new Timer(2, e -> {
		update();
		repaint();
	});
	Controller PID = new Controller(new PID(), 10, 0.1);

	public MainPanel() {
		timer.start();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);

		g2.fillRect(0, 0, MainWindow.WIDTH, MainWindow.HEIGHT);
		PID.render(g2);
	}

	public void update() {
		PID.update();
	}
}