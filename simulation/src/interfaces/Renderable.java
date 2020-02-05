package interfaces;

import java.awt.Graphics2D;

import main.MainPanel;

public class Renderable implements IRenderable {
	public Renderable(MainPanel main) {
		main.getRenderables().add(this);
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

}