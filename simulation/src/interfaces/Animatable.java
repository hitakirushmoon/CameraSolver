package interfaces;

import java.awt.Graphics2D;

import main.MainPanel;

public class Animatable implements IRenderable, IUpdatable {

	public Animatable(MainPanel main) {
		main.getUpdatables().add(this);
		main.getRenderables().add(this);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub

	}

}
