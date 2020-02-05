package simulation;

import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel {
	World world = new World(50, 100, 100, 20, 1.5, 1.5, 3, 0.2);

	private Timer timer = new Timer(1, e -> repaint());

	public MainPanel() {
		timer.start();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		WorldImage world = new WorldImage(this.world, MainWindow.WIDTH, MainWindow.HEIGHT);
		g.drawImage(world.getNoiseImage(), 0, 0, this);
	}

}