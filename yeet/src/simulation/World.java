package simulation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class World {
	double radius;
	int generation = 0;
	int time = 0;
	final int timeLimit;
	final double foodCount;
	final Queue<Creature> creatures = new LinkedList<Creature>();
	final Queue<Place> food = new LinkedList<Place>();
	final Random rand = new Random();
	final Queue<String> file = new LinkedList<String>();

	public static void main(String[] args) {

//		world.toFile();
	}

	public World(double radius, int timeLimit, double foodCount, int initialCreatureCount, double speed, double size,
			double sense, double randomCoefficient) {
		this.radius = radius;
		this.timeLimit = timeLimit;
		this.foodCount = foodCount;
		for (int i = 0; i < initialCreatureCount; i++) {
			creatures.add(new Creature(this, 0, 0, speed + rand.nextGaussian() * randomCoefficient,
					size + rand.nextGaussian() * randomCoefficient, sense + rand.nextGaussian() * randomCoefficient));
		}
		reset();
	}

	public double getRadius() {
		return radius;
	}

	public int getGeneration() {
		return generation;
	}

	public int getTime() {
		return time;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public double getFoodCount() {
		return foodCount;
	}

	public Queue<Creature> getCreatures() {
		return creatures;
	}

	public Queue<Place> getFood() {
		return food;
	}

	public Random getRand() {
		return rand;
	}

	public Queue<String> getFile() {
		return file;
	}

	public void update() {
		time++;
		for (int i = 0; i < creatures.size(); i++) {
			Creature creature = creatures.poll();
			creature.update();
			if (!creature.died) {
				creatures.add(creature);
			}
		}
		file.add(this.toString());
	}

	public void day() {
		while (time < timeLimit) {
			if (creatures.isEmpty()) {
				System.out.println("oops all dead");
				break;
			}
			update();
		}
		reset();
	}

	public void toFile() {
		Path file = Paths.get("D:\\Users\\Minh\\Desktop\\test.txt");
		try {
			Files.write(file, this.file, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void toFile(String a) {
		Path file = Paths.get("D:\\Users\\Minh\\Desktop\\test.txt");
		try {
			Files.writeString(file, a, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String toString() {
		StringBuilder a = new StringBuilder();
		creatures.forEach(x -> a.append(x).append("\r\n"));
		food.forEach(x -> a.append(x).append("\r\n"));
		return a.toString();
	}

	public void reset() {
		time = 0;
		generation++;
		double averageSize = 0;
		double averageSense = 0;
		double averageSpeed = 0;
		for (int i = 0; i < creatures.size(); i++) {
			Creature creature = creatures.poll();
			creature.reset();
			creatures.add(creature);
		}

		for (int i = 0; i < creatures.size(); i++) {
			Creature creature = creatures.poll();
			double angle = Math.PI * 2 * (double) i / (creatures.size() + 1);
			creature.position.posX = radius * Math.cos(angle);
			creature.position.posY = radius * Math.sin(angle);
			averageSize += creature.size / creatures.size();
			averageSpeed += creature.speed / creatures.size();
			averageSense += creature.sense / creatures.size();
			creatures.add(creature);
		}
		for (int i = 0; i < foodCount - food.size(); i++) {
			double r = rand.nextDouble() * radius;
			double phi = rand.nextDouble() * 2 * Math.PI;
			food.add(new Place(r * Math.cos(phi), r * Math.sin(phi)));
		}

		file.add(this.toString());
		file.add(" - average speed: " + averageSpeed + " - average size: " + averageSize + " - average sense: "
				+ averageSense);
		toFile(this.toString() + "/r/n" + " - average speed: " + averageSpeed + " - average size: " + averageSize
				+ " - average sense: " + averageSense);
	}

}
