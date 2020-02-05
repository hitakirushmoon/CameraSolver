package rubiks;

public class EdgesOnly extends Rubiks {

	public EdgesOnly(byte[] edges) {
		this.edges = edges.clone();
	}

	public EdgesOnly() {
		edges = new byte[] { 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, -1, -1 };
//		edges = new byte[] { 23, 21, 19, 17, 15, 13, 11, 9, 7, 5, 3, 1, -1, -1 };
	}

	public void rotate(byte a) {
		super.rotate(a);
		edges[12] = (byte) (edges[13] % 3);
		edges[13] = (byte) (a / 3);
	}

	@Override
	protected void swapcorners2(CP d, CP c, CP b, CP a) {
	}

	@Override
	protected void swapcorners(CP d, CP c, CP b, CP a, boolean toporbottom) {
	}

}
