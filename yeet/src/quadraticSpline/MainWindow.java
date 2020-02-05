package quadraticSpline;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

public class MainWindow extends JFrame implements KeyListener, MouseInputListener {
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
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
		mouseClickCoords.x = e.getX();
		mouseClickCoords.y = e.getY();
		clicked = true;
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

	static final int SPACE = 32, UP = 38, DOWN = 40, A = 65, P = 80, I = 73, C = 67, D = 68, LSHIFT = 16, EQUAL = 61,
			MINUS = 45, LCTRL = 17, BSPACE = 8;
	static final boolean[] input = new boolean[1000];
	static final Coordinates mouseClickCoords = new Coordinates(0, 0);
	static boolean clicked = false;

	@Override
	public void keyPressed(KeyEvent e) {
		input[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		input[e.getKeyCode()] = false;
	}
}