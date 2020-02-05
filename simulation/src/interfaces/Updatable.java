package interfaces;

import main.MainPanel;

public class Updatable implements IUpdatable {
	public Updatable(MainPanel main) {
		main.getUpdatables().add(this);
	}

	@Override
	public void update() {

	}

}