package bezier;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

public class MainWindow extends JFrame implements KeyListener, MouseInputListener {
	public static final int WIDTH = 1920 / 2;
	public static final int HEIGHT = 1080 / 2;
	private MainPanel panel = new MainPanel();

	public MainWindow() {
		add(panel);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setUndecorated(true);
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		double x = panel.x + e.getX() - WIDTH / 2 * panel.scale;
		double y = panel.y + e.getY() - HEIGHT / 2 * panel.scale;
		if (panel.test == null) {
			panel.test = new Bezier(x, y);
		} else {

			panel.test.first.addCoordinates(new Coordinates(x, y));
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	static final int SPACE = 32, UP = 38, DOWN = 40, S = 83, T = 84, C = 67, LSHIFT = 16, EQUAL = 61, MINUS = 45,
			LCTRL = 17, BSPACE = 8;

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 27) {
			System.exit(0);
		}
		if (e.getKeyCode() == LSHIFT) {
			setLocation(MouseInfo.getPointerInfo().getLocation().x - WIDTH / 2,
					MouseInfo.getPointerInfo().getLocation().y - HEIGHT / 2);
		}

		if (panel.test != null) {
			if (e.getKeyCode() == BSPACE) {
				panel.test = null;
				return;
			}
			if (e.getKeyCode() == SPACE) {
				panel.test.shouldBeUpdated = !panel.test.shouldBeUpdated;
			}
			if (e.getKeyCode() == C) {
				panel.test.t = 0;
				panel.test.curve.start.next = null;
				panel.test.curve.last = panel.test.curve.start;
				panel.test.lines.removeAll(panel.test.lines);
			}
			if (e.getKeyCode() == UP) {
				panel.test.speed += 0.001;
			}
			if (e.getKeyCode() == DOWN) {
				panel.test.speed -= 0.001;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}