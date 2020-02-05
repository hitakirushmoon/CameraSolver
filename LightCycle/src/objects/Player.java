package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import interfaces.Animatable;

public class Player extends Animatable {
	final double normalSpeed;
	final double normalTrailLength;
	double direction;
	double speed;
	double trailLength;
	final double segmentLength = 5;
	final double trailWidth = 5;
	final Coordinates originalPos;
	Coordinates position;
	LinkedList<Coordinates> trail = new LinkedList<Coordinates>();
	World world;
	Color color;
	int tick = 0;

	public Player(double normalSpeed, double normalTrailLength, double direction, Coordinates position, World world,
			Color color) {
		super(world.panel);
		this.normalSpeed = normalSpeed;
		this.direction = direction;
		this.speed = normalSpeed;
		this.position = position;
		this.normalTrailLength = normalTrailLength;
		this.trailLength = normalTrailLength;
		this.world = world;
		this.originalPos = position.clone();
		this.color = color;
	}

	@Override
	public void render(Graphics2D g) {
		g.setStroke(new BasicStroke((float) trailWidth * 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		Coordinates lastCoordinates = null;
		int layer = 0;
		for (Coordinates c : trail) {
			layer++;
			if (lastCoordinates != null) {
				if (layer == 2) {
					lastCoordinates.add(lastCoordinates.getClosestVectorTo(c).scale(tick / segmentLength));
				}
				g.setColor(color);
				wraparoundLine(g, c, lastCoordinates);
			}
			lastCoordinates = c;
		}
		if (lastCoordinates != null) {
			wraparoundLine(g, lastCoordinates, position);
		}
		g.setColor(Color.getHSBColor(0.5f, 1, 1));
		g.fillOval((int) (position.x - trailWidth), (int) (position.y - trailWidth), (int) trailWidth * 2 + 1,
				(int) trailWidth * 2 + 1);
		g.drawString("score: " + (int) Math.max(0, trail.size() - normalTrailLength), 10, 10);

	}

	public void wraparoundLine(Graphics2D g, Coordinates c, Coordinates lastCoordinates) {

		if (lastCoordinates.wrapAround(c)) {
			Coordinates closest = lastCoordinates.getClosestVectorTo(c);
			g.drawLine((int) lastCoordinates.x, (int) lastCoordinates.y, (int) (lastCoordinates.x + closest.x),
					(int) (lastCoordinates.y + closest.y));
			g.drawLine((int) (c.x - closest.x), (int) (c.y - closest.y), (int) c.x, (int) c.y);

		} else {
			g.drawLine((int) lastCoordinates.x, (int) lastCoordinates.y, (int) c.x, (int) c.y);
		}
	}

	@Override
	public void update() {
		if (!trail.isEmpty()) {
			for (Player p : world.players) {
				if (!p.trail.isEmpty()) {
					Coordinates lastPosition = trail.removeLast();
					position.add(Coordinates.fromRadial(normalSpeed, direction, world));
					Coordinates lastCoordinates = null;
					Coordinates position = this.position;
					for (Coordinates c : p.trail) {
						if (lastCoordinates != null) {
//					if (position.checkCollisionWithSegment(lastCoordinates, c, trailWidth)
							if (Coordinates.doIntersect(c, lastCoordinates, position, lastPosition)) {
								trailLength = normalTrailLength;
								trail.removeAll(trail);
								position.x = originalPos.x;
								position.y = originalPos.y;
								return;
							}

						}

						lastCoordinates = c;
					}
					trail.add(lastPosition);
				}
			}
		}
		tick++;
		if (tick == segmentLength) {
			tick = 0;
			trail.add(position.clone());
			if (trail.size() > trailLength) {
				trail.remove();
			}
		}

	}

}
