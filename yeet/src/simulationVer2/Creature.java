package simulationVer2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Queue;

public class Creature {
	World world;
	Coordinates position;
	Coordinates target;
	boolean isDead;
	double direction;
	double speed, size, sight;
	double energy;
	double breedingPoint, energyPassedOn;
	static final double eatingCoeffecient = 1.5;
	static final double mutationChance = 0.1;
	static final double breedingRange = 20;
	static final double directionDeviation = 1 / 36;
	static final double eatingPriority = 1;
	static final double horniness = 0.5;
	static final double runningPriority = -1;
	static final double mentabolismRate = 0.5;

	public Creature(double speed, double size, double sight, double energy, double breedingPoint,
			double energyPassedOn) {
		this.speed = speed;
		this.size = size;
		this.sight = sight * 2;
		this.energy = energy;
		this.breedingPoint = breedingPoint;
		this.energyPassedOn = energyPassedOn;
	}

	public void setWorld(World w) {
		world = w;
		w.allCreatures.add(this);
		position = Coordinates.fromRadial(w.radius, w.rand.nextDouble() * 2 * Math.PI);
		direction = w.rand.nextDouble() * 2 * Math.PI;
	}

	public void setPosition(Coordinates c) {
		this.position = c;
	}

	public void update() {
		findTarget();
		move();
		eat();
		reproduce();
		if (energy < 0) {
			die();
		}
	}

	public void findTarget() {
		Coordinates target = new Coordinates(0, 0);
		Queue<Creature> creatures = world.allCreatures;

		for (int i = 0; i < creatures.size(); i++) {
			Creature creature = creatures.poll();
			if (isInSight(creature)) {
				if (isEatable(creature)) {
					target.add(position.getVectorTo(creature.position).inverseSize()
							.scale(eatingPriority * creature.getEnergyFromBeingEaten()));
				}
				if (isBreedable(creature) && wantsToBreed()) {
					target.add(position.getVectorTo(creature.position).inverseSize().scale(horniness));
				}
				if (creature.isEatable(this)) {
					target.add(position.getVectorTo(creature.position).inverseSize().scale(runningPriority));
				}
			}
			creatures.add(creature);
		}
		Queue<Food> foodList = world.allFood;
		for (int i = 0; i < foodList.size(); i++) {
			Food food = foodList.poll();
			if (isInSight(food.position)) {
				target.add(
						position.getVectorTo(food.position).inverseSize().scale(eatingPriority * food.energyProvided));
			}
			foodList.add(food);
		}
		this.target = target;

	}

	public void move() {
		if (target != null && !target.isZeroVector()) {
			direction = target.direction();
		} else {
			if (position.size() > world.radius) {
				direction = -position.direction();
			}
			direction += world.rand.nextGaussian() * directionDeviation * 2 * Math.PI;

		}
		position.add(Coordinates.fromRadial(speed, direction));

		energy -= (size * size * size * speed * speed + sight) / 100;
	}

	public void reproduce() {
		Queue<Creature> creatures = world.allCreatures;
		for (int i = 0; i < creatures.size(); i++) {
			Creature creature = creatures.poll();
			if (isBreedable(creature)) {
				createOffspring(creature);
			}
			creatures.add(creature);
		}
	}

	public void eat() {
		Queue<Creature> creatures = world.allCreatures;
		Queue<Food> foodList = world.allFood;
		for (int i = 0; i < creatures.size(); i++) {
			Creature creature = creatures.poll();
			if (isInEatingRange(creature.position) && isEatable(creature)) {
				energy += creature.size * creature.size * creature.size;
			} else {
				creatures.add(creature);
			}
		}
		for (int i = 0; i < foodList.size(); i++) {
			Food food = foodList.poll();
			if (isInEatingRange(food.position)) {
				energy += food.energyProvided;
			} else {
				foodList.add(food);
			}
		}
	}

	public void die() {
		isDead = true;
	}

	public boolean wantsToBreed() {
		return energy > breedingPoint;
	}

	public boolean isBreedable(Creature c) {
		return position.distanceTo(c.position) < breedingRange && c.wantsToBreed() && size < c.size * 1.2
				&& size * 1.2 > c.size;
	}

	public boolean isInEatingRange(Coordinates c) {
		return position.distanceTo(c) < size;
	}

	public boolean isEatable(Creature c) {
		return c.size * eatingCoeffecient <= size;
	}

	public boolean isInSight(Creature c) {
		return position.distanceTo(c.position) - c.size < sight;
	}

	public boolean isInSight(Coordinates c) {
		return position.distanceTo(c) < sight;
	}

	public double getEnergyFromBeingEaten() {
		return size * size * size * mentabolismRate;
	}

	public Creature clone() {
		return new Creature(speed, size, sight, energy, breedingPoint, energyPassedOn);
	}

	public void createOffspring(Creature creature) {
		double newSpeed = (speed + creature.speed) / 2 + world.rand.nextGaussian() * mutationChance;
		double newSize = (size + creature.size) / 2 + world.rand.nextGaussian() * mutationChance;
		double newSight = (sight + creature.sight) / 2 + world.rand.nextGaussian() * mutationChance;
		double newEnergy = energy * energyPassedOn + energy * creature.energyPassedOn;
		double newBreedingPoint = (breedingPoint + creature.breedingPoint) / 2
				+ world.rand.nextGaussian() * mutationChance;
		double newEnergyPassedOn = 2 / (1 / energyPassedOn + 1 / creature.energyPassedOn)
				+ world.rand.nextGaussian() * mutationChance;
		Creature offspring = new Creature(newSpeed, newSize, newSight, newEnergy, newBreedingPoint, newEnergyPassedOn);
		offspring.setWorld(world);
		offspring.setPosition(Coordinates.sum(position, creature.position).scale(0.5));
		energy *= 1 - energyPassedOn;
		creature.energy *= 1 - creature.energyPassedOn;
	}

	public void drawCreature(Graphics g, double zoom) {
//		g.setColor(new Color((int) energyPassedOn, (int) breedingPoint, (int) speed));
		g.setColor(Color.white);
		g.fillOval((int) ((position.x - size / 2) * zoom), (int) ((position.y - size / 2) * zoom), (int) (size * zoom),
				(int) (size * zoom));
//		g.setColor(Color.getHSBColor(0, 0, (float) Math.min(1, energy / 300)));
		g.drawOval((int) ((position.x - sight / 2) * zoom), (int) ((position.y - sight / 2) * zoom),
				(int) (sight * zoom), (int) (sight * zoom));
	}
}
