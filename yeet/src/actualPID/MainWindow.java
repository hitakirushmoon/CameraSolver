package actualPID;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

public class MainWindow extends JFrame implements KeyListener, MouseInputListener {
	public static final int WIDTH = 1920 ;
	public static final int HEIGHT = 1080 ;
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
//		double x = panel.x + e.getX() - WIDTH / 2 * panel.scale;
//		double y = panel.y + e.getY() - HEIGHT / 2 * panel.scale;
//		if (panel.test == null) {
//			panel.test = new Bezier(x, y);
//		} else {
//
//			panel.test.first.addCoordinates(new Coordinates(x, y));
//		}
		if (panel.PID.current == -1) {
			panel.PID.yValue = e.getX();
			panel.PID.setCurrent(e.getY());
			return;
		}
		panel.PID.setTarget(e.getY());
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
	boolean shifted;

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 27:
			System.exit(0);
			break;
		case LCTRL:
			shifted = true;
			break;
		case LSHIFT:
			setLocation(MouseInfo.getPointerInfo().getLocation().x - WIDTH / 2,
					MouseInfo.getPointerInfo().getLocation().y - HEIGHT / 2);
			break;
		case C:
			panel.PID.current = panel.PID.startingCurrent;
			panel.PID.path.empty();
			panel.PID.P.empty();
			panel.PID.I.empty();
			panel.PID.D.empty();
			panel.PID.lastSpeed = 0;
			panel.PID.control = new PID(panel.PID.control.target, panel.PID.control.kP, panel.PID.control.kI,
					panel.PID.control.kD);
			panel.PID.t = 0;
			break;
		case P:
			if (shifted) {
				panel.PID.control.kP -= 0.1;
			} else {
				panel.PID.control.kP += 0.1;
			}
			break;
		case I:
			if (shifted) {
				panel.PID.control.kI -= 0.1;
			} else {
				panel.PID.control.kI += 0.1;
			}
			break;
		case D:
			if (shifted) {
				panel.PID.control.kD -= 0.1;
			} else {
				panel.PID.control.kD += 0.1;
			}
			break;
		case A:
			if (shifted) {
				panel.PID.maxAcceleration -= 0.1;
			} else {
				panel.PID.maxAcceleration += 0.1;
			}
			break;
		case UP:
			if (shifted) {
				panel.PID.maxSpeed = Double.POSITIVE_INFINITY;
			} else {
				panel.PID.maxSpeed++;
			}
			break;
		case DOWN:
			if (shifted) {
				panel.PID.maxSpeed = 10;
			} else {
				panel.PID.maxSpeed--;
			}
			break;
		case BSPACE:
			panel.PID = new Controller(new PID(panel.PID.control.kP, panel.PID.control.kI, panel.PID.control.kD),
					panel.PID.maxSpeed, panel.PID.maxAcceleration);
			break;
		case SPACE:
			panel.PID.shouldBeUpdated = !panel.PID.shouldBeUpdated;
			break;
		default:
			System.out.println(e.getKeyCode());

		}

//		if (panel.test != null) {
//			if (e.getKeyCode() == BSPACE) {
//				panel.test = null;
//				return;
//			}
//			if (e.getKeyCode() == SPACE) {
//				panel.test.shouldBeUpdated = !panel.test.shouldBeUpdated;
//			}
//			if (e.getKeyCode() == C) {
//				panel.test.t = 0;
//				panel.test.curve.start.next = null;
//				panel.test.curve.last = panel.test.curve.start;
//				panel.test.lines.removeAll(panel.test.lines);
//			}
//			if (e.getKeyCode() == UP) {
//				panel.test.speed += 0.001;
//			}
//			if (e.getKeyCode() == DOWN) {
//				panel.test.speed -= 0.001;
//			}
//		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == LCTRL) {
			shifted = false;
		}
	}
}