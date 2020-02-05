package simulation;

import java.awt.image.BufferedImage;

public class WorldImage {
	World world;

	public WorldImage(World world, int width, int height) {
		this.world = world;
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	int width, height;
	private BufferedImage image;
//	World world = new World(20, 40, 40, 20, 1.5, 1.5, 3, 0.2);
	int scale = 5;

	public BufferedImage getNoiseImage() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				image.setRGB(x, y, 0);
			}
		}

		world.getCreatures().forEach(a -> {
			for (int x = (int) -a.getSize() * scale / 2; x < a.getSize() * scale / 2; x++) {
				for (int y = (int) -a.getSize() * scale / 2; y < a.getSize() * scale / 2; y++) {
					safeSetRGB(x + (int) a.getPosition().getPosX() * scale + width / 2,
							y + (int) a.getPosition().getPosY() * scale + height / 2, toRGB(a));
				}
			}
			
		});
		world.getFood().forEach(a -> safeSetRGB((int) a.getPosX() * scale + width / 2,
				(int) a.getPosY() * scale + height / 2, 0xFFFFFF));
		return image;
	}

	private int toRGB(Creature c) {
		int r = 0, g = 0, b = 0;
		r = (int) Math.min(Math.max(c.getSpeed() * 85, 0), 255);
		g = (int) Math.min(Math.max(c.getSense() * 43, 0), 255);
		b = (int) Math.min(Math.max(c.getEnergy() * 128, 0), 255);
		
		return (r << 16) + (g << 8) + b;
	}

	private void safeSetRGB(int x, int y, int rgb) {
		if (x > 0 && x < width && y > 0 && y < height) {
			image.setRGB(x, y, rgb);
		}
	}
}