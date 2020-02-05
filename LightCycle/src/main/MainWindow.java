package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

public class MainWindow extends JFrame implements KeyListener, MouseInputListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public static final boolean[] pressed = new boolean[128];

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 27) {
			System.exit(0);
		} else if (e.getKeyCode() < pressed.length) {
			pressed[e.getKeyCode()] = true;
			System.out.println(e.getKeyCode());
		} else {
			System.out.println("key " + e.getKeyCode() +  " out of range!");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() < pressed.length) {
			pressed[e.getKeyCode()] = false;
		} else {
			System.out.println("key out of range!");
		}
	}
}