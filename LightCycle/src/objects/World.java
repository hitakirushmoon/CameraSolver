package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import interfaces.Animatable;
import main.MainPanel;

public class World extends Animatable {
	double width, height;
	MainPanel panel;
	List<ControllablePlayer> players = new LinkedList<ControllablePlayer>();
	ControllablePlayer p1, p2;
	Coordinates food;
	final double foodSize = 5;
	Random rand = new Random();

	public World(MainPanel panel) {
		super(panel);
		this.panel = panel;
		this.width = panel.width;
		this.height = panel.height;
		p1 = new ControllablePlayer(3, 10, 0, 200, 200, this, Color.GREEN, 37, 39);
		p2 = new ControllablePlayer(3, 10, Math.PI, 600, 200, this, Color.RED, 65, 68);
		players.add(p1);
		players.add(p2);
		food = new Coordinates(rand.nextDouble() * width, rand.nextDouble() * height, this);

	}

	public void update() {
		double smallest1 = food.smallestDistanceTo(p1.position);
		if (smallest1 < foodSize + p1.trailWidth / 4) {
			food = new Coordinates(rand.nextDouble() * width, rand.nextDouble() * height, this);
			p1.trailLength++;
		}
		double smallest2 = food.smallestDistanceTo(p2.position);
		if (smallest2 < foodSize + p2.trailWidth / 4) {
			food = new Coordinates(rand.nextDouble() * width, rand.nextDouble() * height, this);
			p2.trailLength++;
		}
	}

	public void render(Graphics2D g) {
		g.fillOval((int) (food.x - foodSize), (int) (food.y - foodSize), (int) foodSize * 2 + 1,
				(int) foodSize * 2 + 1);
	}
}
