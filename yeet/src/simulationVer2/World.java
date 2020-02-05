package simulationVer2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.event.MouseInputListener;

public class World implements KeyListener, MouseInputListener {
	int time = 0;
	final double radius;
	final double foodChance;
	final Queue<Creature> allCreatures = new LinkedList<Creature>();
	final Queue<Food> allFood = new LinkedList<Food>();
	final Random rand = new Random();
	final static double foodSizeLimit = 10;

	public World(double radius, int initialCreatures, int initialFood, double foodChance, Creature a) {
		this.radius = radius;
		this.foodChance = foodChance;
		for (int i = 0; i < initialCreatures; i++) {
			Creature creature = a.clone();
			creature.setWorld(this);
		}
		for (int i = 0; i < initialFood; i++) {
			generateFood();
		}
	}

	public void update() {
		time++;
		if (rand.nextDouble() < foodChance) {
			generateFood();
		}
		for (int i = 0; i < allCreatures.size(); i++) {
			Creature creature = allCreatures.poll();
			creature.update();
			if (!creature.isDead) {
				allCreatures.add(creature);
			}
		}
	}

	public void generateFood() {
		allFood.add(new Food(rand.nextDouble() * foodSizeLimit / 2 + foodSizeLimit / 2,
				Coordinates.fromRadial(rand.nextDouble() * radius, rand.nextDouble() * 2 * Math.PI)));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		update();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
