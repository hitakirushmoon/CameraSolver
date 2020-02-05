package rubiks;

public class CornersOnly extends Rubiks {

	public CornersOnly() {
		corners = new byte[] { 0, 3, 6, 9, 12, 15, 18, 21, -1, -1 };
//		corners = new byte[] { 23, 20, 17, 14, 11, 8, 5, 2, -1, -1 };
	}

	public CornersOnly(byte[] corners) {
		this.corners = corners.clone();
	}
	
	public void rotate(byte a) {
		super.rotate(a);
		corners[8] = (byte) (corners[9] % 3);
		corners[9] = (byte) (a / 3);
	}

	@Override
	protected void swapedges(EP d, EP c, EP b, EP a) {
	}

	

	@Override
	protected void swapedges2(EP d, EP c, EP b, EP a) {
	}

}
