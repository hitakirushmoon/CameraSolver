package simulationVer2;

import java.awt.Color;
import java.awt.Graphics;

public class Food {
	double energyProvided;
	Coordinates position;

	public Food(double energyProvided, Coordinates position) {
		this.energyProvided = energyProvided;
		this.position = position;
	}

	public void drawFood(Graphics g, double zoom) {
//		g.setColor(Color.getHSBColor(0, 0, (float) (energyProvided / 10)));
		g.setColor(Color.white);
		g.fillRect((int) (position.x * zoom), (int) (position.y * zoom), 1, 1);
	}

}
