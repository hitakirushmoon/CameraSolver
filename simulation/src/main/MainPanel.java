package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import interfaces.IUpdatable;
import interfaces.IRenderable;
import objects.World;

public class MainPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1267434478501521763L;
	List<IUpdatable> updatables = new LinkedList<IUpdatable>();
	List<IRenderable> renderables = new LinkedList<IRenderable>();

	public List<IUpdatable> getUpdatables() {
		return updatables;
	}

	public List<IRenderable> getRenderables() {
		return renderables;
	}

	final double start = System.nanoTime();
	double current_time, current_frame;
//	public double scale = 1, x = MainWindow.WIDTH / 2, y = MainWindow.HEIGHT / 2;
	private Timer timer = new Timer(10, e -> {
		repaint();
		update();
		current_time += System.nanoTime() - start;
		current_frame++;
	});

	public final double width, height;

	public MainPanel() {
		timer.start();
		width = MainWindow.WIDTH;
		height = MainWindow.HEIGHT;
		new World(this);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, (int) width, (int) height);
		g2.setColor(Color.WHITE);
		for (IRenderable renderable : renderables) {
			renderable.render(g2);
		}
		g2.drawString("fps: " + (current_time / 1000000000 / current_frame), 10, 20);
	}

	public void update() {
		for (IUpdatable updatable : updatables) {
			updatable.update();
		}
	}
}