package simulationVer2;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel {

	private Timer timer = new Timer(30, e -> repaint());
	World w;

	public MainPanel(World w) {
		this.w = w;
		timer.start();
	}

	protected void paintComponent(Graphics g) {
		double zoom = 3;
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, MainWindow.WIDTH, MainWindow.HEIGHT);
		g.translate(MainWindow.WIDTH / 2, MainWindow.HEIGHT / 2);
		w.allCreatures.forEach(x -> x.drawCreature(g, zoom));
		w.allFood.forEach(x -> x.drawFood(g, zoom));
	}

}