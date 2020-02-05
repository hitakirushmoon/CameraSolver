package simulationVer2;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	Creature c = new Creature(2, 2, 2, 50, 100, 0.5);
	World w = new World(100, 10, 400, 0.5, c);
	private MainPanel panel = new MainPanel(w);
	
	public MainWindow() {
		add(panel);
		addKeyListener(w);
		addMouseListener(w);
		addMouseMotionListener(w);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
	}

}