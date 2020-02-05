package simulation;

public class Place {
	double posX, posY;

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public Place(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public double getDistance(Place target) {
		return Math.sqrt((this.posX - target.posX) * (this.posX - target.posX)
				+ (this.posY - target.posY) * (this.posY - target.posY));
	}

	public void add(Place a) {
		posX += a.posX;
		posY += a.posY;
	}

	public Place to(Place a) {
		return new Place(a.posX - this.posX, a.posY - this.posY);
	}

	public double size() {
		return Math.sqrt(posX * posX + posY * posY);
	}

	public Place normalize(double factor) {
		if (!isNull()) {
			double size = size();
			posX *= factor / size;
			posY *= factor / size;
		}
		return this;
	}

	public boolean isNull() {
		return size() < 1E-4;
	}

	public String toString() {
		return " - position: " + (double) Math.round(posX * 100) / 100 + "; " + (double) Math.round(posY * 100) / 100;
	}
}
