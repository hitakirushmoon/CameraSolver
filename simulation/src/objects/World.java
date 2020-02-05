package objects;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import interfaces.Updatable;
import main.MainPanel;

public class World extends Updatable {
	double width, height;
	MainPanel panel;
	Random rand = new Random();
	List<Creature> creatures = new LinkedList<Creature>();
	List<Coordinates> food = new LinkedList<Coordinates>();
	double maxSpeed, maxEnergy;

	public World(MainPanel panel) {
		super(panel);
		this.panel = panel;
		this.width = panel.width;
		this.height = panel.height;
	}

	@Override
	public void update() {
		double maxSpeed = 0;
		double maxEnergy = 0;
		for (Creature c : creatures) {
			c.update();
			maxSpeed = Math.max(c.speed, maxSpeed);
			maxEnergy = Math.max(c.energy, maxEnergy);
		}
		this.maxSpeed = maxSpeed;
		this.maxEnergy = maxEnergy;

	}

}
