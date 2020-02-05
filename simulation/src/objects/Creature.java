package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import interfaces.Animatable;

public class Creature extends Animatable {

	Coordinates position;
	final double speed, size, sense;
	double energy = 100;
	double direction;
	World world;

	public Creature(Coordinates position, double speed, double size, double sense, World world) {
		super(world.panel);
		this.position = position;
		this.speed = speed;
		this.size = size;
		this.sense = sense;
		this.world = world;
		direction = world.rand.nextGaussian() * Math.PI;
	}

	@Override
	public void update() {
		Coordinates target = getTarget();
		move(target);
	}

	void move(Coordinates target) {
		if (target == null) {
			direction += world.rand.nextGaussian() * 0.2;
		} else {
			direction = position.getClosestVectorTo(target).slope();
		}
		position.add(Coordinates.fromRadial(speed, direction, world));
	}

	Coordinates getTarget() {
		Coordinates current = null;
		double smallest_distance = sense;
		for (Coordinates food : world.food) {
			if (current == null) {
				current = food;
				continue;
			}
			if (smallest_distance > position.smallestDistanceTo(food)) {
				current = food;
			}
		}
		return current;
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color((int) (255 * speed / world.maxSpeed), 0, (int) (255 * energy / world.maxEnergy)));
		g.fillOval((int) (position.x - size / 2), (int) (position.y - size / 2), (int) size, (int) size);
		g.drawOval((int) (position.x - sense / 2), (int) (position.y - sense / 2), (int) sense, (int) sense);
		// TODO Auto-generated method stub
	}

}
