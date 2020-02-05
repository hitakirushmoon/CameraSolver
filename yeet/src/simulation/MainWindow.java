package simulation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class MainWindow extends JFrame implements KeyListener {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	private MainPanel panel = new MainPanel();

	public MainWindow() {
		add(panel);
		addKeyListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		if (panel.world.getTime() < panel.world.getTimeLimit()) {
			panel.world.update();
		}
		else {
			panel.world.reset();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}