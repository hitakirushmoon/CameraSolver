package simulation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Creature {
	double direction;
	double speed;
	double size;
	double sense;
	double energy = 1;
	World world;
	Place position;
	Place target;
	Queue<Creature> creatures;
	Queue<Place> food;
	Queue<Place> eatenFood = new LinkedList<Place>();
	Place actualTarget;
	double mutateConst = 0.3;
	Random rand;
	boolean died;

	public double getDirection() {
		return direction;
	}

	public double getSpeed() {
		return speed;
	}

	public double getSize() {
		return size;
	}

	public double getSense() {
		return sense;
	}

	public double getEnergy() {
		return energy;
	}

	public World getWorld() {
		return world;
	}

	public Place getPosition() {
		return position;
	}

	public Place getTarget() {
		return target;
	}

	public boolean isDead() {
		return died;
	}


	public Creature(World world, double posX, double posY, double speed, double size, double sense) {
		this.position = new Place(posX, posY);
		this.world = world;
		this.speed = speed;
		this.size = size;
		this.sense = sense;
		creatures = world.creatures;
		food = world.food;
		rand = world.rand;
	}

	public Creature clone() {
		return new Creature(world, position.posX, position.posY, speed, size, sense);
	}

	void update() {
		updateTarget();
		move();
		eat();
		if (energy <= 0) {
			die();
		}
	}

	public void reset() {
		this.direction = Math.atan2(-position.posY, -position.posX);
		if (energy > 1) {
			reproduce();
			this.energy = 1;
		}
	}

	void reproduce() {
		double excessEnergy = energy - 1;
		while (excessEnergy > 1) {
			creatures.add(new Creature(world, 0, 0, speed + rand.nextGaussian() * mutateConst,
					size + rand.nextGaussian() * mutateConst, sense + rand.nextGaussian() * mutateConst));
			excessEnergy--;
		}
		if (rand.nextDouble() <= excessEnergy) {
			creatures.add(new Creature(world, 0, 0, speed + rand.nextGaussian() * mutateConst,
					size + rand.nextGaussian() * mutateConst, sense + rand.nextGaussian() * mutateConst));
		}
	}

	void updateTarget() {
		Place totalTarget = null;
		Place realTarget = null;
		double closestTarget = sense;
		for (int i = 0; i < creatures.size(); i++) {
			Creature creature = creatures.poll();
			if (position.getDistance(creature.position) < sense && creature.size >= size * 1.2) {
				closestTarget = -position.getDistance(creature.position);
				if (totalTarget == null) {
					totalTarget = position.to(creature.position).normalize(-1);
				} else {
					totalTarget.add(position.to(creature.position).normalize(-1));
				}
			}
			if (position.getDistance(creature.position) < closestTarget && creature.size * 1.2 <= size) {
				totalTarget = position.to(creature.position);
				realTarget = creature.position;
			}
			creatures.add(creature);
		}
		for (int i = 0; i < food.size(); i++) {
			Place targetFood = food.poll();
			if (position.getDistance(targetFood) < closestTarget) {
				totalTarget = position.to(targetFood);
				realTarget = targetFood;
			}
			food.add(targetFood);
		}
		actualTarget = realTarget;
		target = totalTarget;
	}

	void move() {
		if (target == null || target.isNull()) {
			if (position.size() > world.radius) {
				direction = Math.atan2(-position.posY, -position.posX);
			}
			direction += rand.nextGaussian() * 1 / 6;
			Place movement = new Place(speed * Math.cos(direction), speed * Math.sin(direction));
			position.add(movement);
		} else {
			target.normalize(speed);
			direction = Math.atan2(target.posY, target.posX);
			position.add(target);
		}
		energy -= (speed * speed * size * size * size + sense / 2) / 250;
	}

	void eat() {
		for (int i = 0; i < creatures.size(); i++) {
			boolean eaten = false;
			Creature creature = creatures.poll();
			if (position.getDistance(creature.position) <= size && creature.size * 1.2 <= size) {
				energy += creature.size * creature.size * creature.size;
				eaten = true;
			}
			if (!eaten) {
				creatures.add(creature);
			}
		}
		for (int i = 0; i < food.size(); i++) {
			Place food = this.food.poll();
			if (position.getDistance(food) <= size) {
				energy += 1;
				eatenFood.add(food);
			} else {
				this.food.add(food);

			}
		}
	}

	void die() {
		died = true;
	}

	public String toString() {
		return " - energy: " + Math.round(energy * 100) + " - speed: " + (double) Math.round(speed * 100) / 100
				+ " - size: " + (double) Math.round(size * 100) / 100 + " - sense: "
				+ (double) Math.round(sense * 100) / 100 + position + "\r\n - target: " + target + " - actual target: "
				+ actualTarget + "\r\n - eaten food: " + eatenFood + "\r\n";
	}
}
