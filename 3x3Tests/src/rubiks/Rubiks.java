package rubiks;

import java.util.Arrays;

public class Rubiks {
	final static int[] factorialconst = { 1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600 };
	protected byte[] corners;
	protected byte[] edges;

	public static enum CP {
		FUR, FUL, FDR, FDL, BUR, BUL, BDR, BDL;
	}

	public static enum EP {
		FR, FL, FU, FD, UR, UL, BR, BL, BU, BD, DR, DL;
	}

//	public Rubiks[] getNext() {
//		Rubiks[] a = new Rubiks[18];
//		for(int i = 0; i < rotateMoves.length; i++) {
//		
//		}
//		return a;
//	}

	public byte[] getCorners() {
		return corners;
	}

	public byte[] getEdges() {
		return edges;
	}

	public void rotate(byte a) {
		rotateMoves[a].rotate(this);
	}

	interface Rotate {
		void rotate(Rubiks r);
	}

	public static final Rotate[] rotateMoves = new Rotate[] { new Rotate() {
		public void rotate(Rubiks r) {
			F(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			F2(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			F3(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			U(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			U2(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			U3(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			R(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			R2(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			R3(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			B(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			B2(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			B3(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			D(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			D2(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			D3(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			L(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			L2(r);
		}
	}, new Rotate() {
		public void rotate(Rubiks r) {
			L3(r);
		}
	}, };

	private static void F(Rubiks r) {
		r.swapedges(EP.FR, EP.FD, EP.FL, EP.FU);
		r.swapcorners(CP.FDL, CP.FUL, CP.FUR, CP.FDR, false);
	}

	private static void F2(Rubiks r) {
		r.swapedges2(EP.FR, EP.FD, EP.FL, EP.FU);
		r.swapcorners2(CP.FDL, CP.FUL, CP.FUR, CP.FDR);
	}

	private static void F3(Rubiks r) {
		r.swapedges(EP.FR, EP.FU, EP.FL, EP.FD);
		r.swapcorners(CP.FDL, CP.FDR, CP.FUR, CP.FUL, false);
	}

	private static void U(Rubiks r) {
		r.swapedges(EP.UR, EP.FU, EP.UL, EP.BU);
		r.swapcorners(CP.FUL, CP.BUL, CP.BUR, CP.FUR, true);
	}

	private static void U2(Rubiks r) {
		r.swapedges2(EP.UR, EP.FU, EP.UL, EP.BU);
		r.swapcorners2(CP.FUL, CP.BUL, CP.BUR, CP.FUR);
	}

	private static void U3(Rubiks r) {
		r.swapedges(EP.UR, EP.BU, EP.UL, EP.FU);
		r.swapcorners(CP.FUL, CP.FUR, CP.BUR, CP.BUL, true);
	}

	private static void R(Rubiks r) {
		r.swapedges(EP.FR, EP.UR, EP.BR, EP.DR);
		r.swapcorners(CP.BUR, CP.BDR, CP.FDR, CP.FUR, false);
	}

	private static void R2(Rubiks r) {
		r.swapedges2(EP.FR, EP.UR, EP.BR, EP.DR);
		r.swapcorners2(CP.BUR, CP.BDR, CP.FDR, CP.FUR);
	}

	private static void R3(Rubiks r) {
		r.swapedges(EP.FR, EP.DR, EP.BR, EP.UR);
		r.swapcorners(CP.FDR, CP.BDR, CP.BUR, CP.FUR, false);
	}

	private static void B(Rubiks r) {
		r.swapedges(EP.BR, EP.BU, EP.BL, EP.BD);
		r.swapcorners(CP.BDL, CP.BDR, CP.BUR, CP.BUL, false);
	}

	private static void B2(Rubiks r) {
		r.swapedges2(EP.BR, EP.BU, EP.BL, EP.BD);
		r.swapcorners2(CP.BDL, CP.BDR, CP.BUR, CP.BUL);
	}

	private static void B3(Rubiks r) {
		r.swapedges(EP.BR, EP.BD, EP.BL, EP.BU);
		r.swapcorners(CP.BDL, CP.BUL, CP.BUR, CP.BDR, false);
	}

	private static void D(Rubiks r) {
		r.swapedges(EP.DR, EP.BD, EP.DL, EP.FD);
		r.swapcorners(CP.FDL, CP.FDR, CP.BDR, CP.BDL, true);
	}

	private static void D2(Rubiks r) {
		r.swapedges2(EP.DR, EP.BD, EP.DL, EP.FD);
		r.swapcorners2(CP.FDL, CP.FDR, CP.BDR, CP.BDL);
	}

	private static void D3(Rubiks r) {
		r.swapedges(EP.DR, EP.FD, EP.DL, EP.BD);
		r.swapcorners(CP.FDL, CP.BDL, CP.BDR, CP.FDR, true);
	}

	private static void L(Rubiks r) {
		r.swapedges(EP.FL, EP.DL, EP.BL, EP.UL);
		r.swapcorners(CP.FUL, CP.FDL, CP.BDL, CP.BUL, false);
	}

	private static void L2(Rubiks r) {
		r.swapedges2(EP.FL, EP.DL, EP.BL, EP.UL);
		r.swapcorners2(CP.FUL, CP.FDL, CP.BDL, CP.BUL);
	}

	private static void L3(Rubiks r) {
		r.swapedges(EP.FL, EP.UL, EP.BL, EP.DL);
		r.swapcorners(CP.FUL, CP.BUL, CP.BDL, CP.FDL, false);
	}

	public static String rotationToString(int a) {
		switch (a / 3) {
		case 0:
			return "F" + (a % 3 + 1);
		case 1:
			return "U" + (a % 3 + 1);
		case 2:
			return "R" + (a % 3 + 1);
		case 3:
			return "B" + (a % 3 + 1);
		case 4:
			return "D" + (a % 3 + 1);
		case 5:
			return "L" + (a % 3 + 1);
		}
		return "";
	}

	protected static int nonConsecutiveFHasher(byte[] arr) {
		byte[] clone = arr.clone();
		byte[] translatearr = arr.clone();
		Arrays.sort(clone);
		for (int i = 0; i < clone.length; i++) {
			translatearr[i] = (byte) Arrays.binarySearch(clone, translatearr[i]);
		}
//		System.out.println(Arrays.toString(translatearr));

		return fHasher(translatearr);
	}

	protected static int fHasher(byte arr[]) {
		int a = 0;
		byte[] translatearr = arr.clone();
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				if (translatearr[j] > translatearr[i]) {
					translatearr[j]--;
				}
			}
		}
		for (int i = 0; i < arr.length; i++) {
			a += translatearr[i] * factorialconst[arr.length - i - 1];
		}

		return a;
	}

	protected static int cHasher(byte[] arr) {
		int a = 0;
		byte[] clone = arr.clone();
		Arrays.sort(clone);
		for (int i = 0; i < clone.length; i++) {
			a += nCk(clone[i], i + 1);
		}
		return a;
	}

	private static int nCk(int n, int k) {
		if (n < k) {
			return 0;
		}
		return factorialconst[n] / (factorialconst[k] * factorialconst[n - k]);
	}

	protected static int bHasher(byte[] arr, int base, int offsetend) {
		int a = 0;
		for (int i = 0; i < arr.length - offsetend; i++) {
			a += Math.pow(base, i) * arr[i];
		}
		return a;
	}

	protected void swapcorners2(CP d, CP c, CP b, CP a) {
		byte temp = corners[a.ordinal()];
		corners[a.ordinal()] = corners[c.ordinal()];
		corners[c.ordinal()] = temp;
		temp = corners[b.ordinal()];
		corners[b.ordinal()] = corners[d.ordinal()];
		corners[d.ordinal()] = temp;
	}

	protected void swapcorners(CP d, CP c, CP b, CP a, boolean toporbottom) {
		if (toporbottom) {
			byte temp = corners[a.ordinal()];
			corners[a.ordinal()] = corners[b.ordinal()];
			corners[b.ordinal()] = corners[c.ordinal()];
			corners[c.ordinal()] = corners[d.ordinal()];
			corners[d.ordinal()] = temp;
		} else {
			byte temp = (byte) (corners[a.ordinal()] - corners[a.ordinal()] % 3 + (2 + corners[a.ordinal()] % 3) % 3);
			corners[a.ordinal()] = (byte) (corners[b.ordinal()] - corners[b.ordinal()] % 3
					+ (1 + corners[b.ordinal()] % 3) % 3);
			corners[b.ordinal()] = (byte) (corners[c.ordinal()] - corners[c.ordinal()] % 3
					+ (2 + corners[c.ordinal()] % 3) % 3);
			corners[c.ordinal()] = (byte) (corners[d.ordinal()] - corners[d.ordinal()] % 3
					+ (1 + corners[d.ordinal()] % 3) % 3);
			corners[d.ordinal()] = temp;
		}
	}

	protected void swapedges(EP d, EP c, EP b, EP a) {
		byte temp = (byte) (edges[a.ordinal()] + 1 - 2 * (edges[a.ordinal()] % 2));
		edges[a.ordinal()] = (byte) (edges[b.ordinal()] + 1 - 2 * (edges[b.ordinal()] % 2));
		edges[b.ordinal()] = (byte) (edges[c.ordinal()] + 1 - 2 * (edges[c.ordinal()] % 2));
		edges[c.ordinal()] = (byte) (edges[d.ordinal()] + 1 - 2 * (edges[d.ordinal()] % 2));
		edges[d.ordinal()] = temp;
	}

	protected void swapedges2(EP d, EP c, EP b, EP a) {
		byte temp = edges[a.ordinal()];
		edges[a.ordinal()] = edges[c.ordinal()];
		edges[c.ordinal()] = temp;
		temp = edges[b.ordinal()];
		edges[b.ordinal()] = edges[d.ordinal()];
		edges[d.ordinal()] = temp;
	}

	protected int cornerHasher() {
		byte[] perm = new byte[8];
		byte[] orien = new byte[8];
		for (int i = 0; i < perm.length; i++) {
			perm[i] = (byte) (corners[i] / 3);
			orien[i] = (byte) (corners[i] % 3);
		}
		int pHash = fHasher(perm);
		int oHash = bHasher(orien, 3, 1);
		// 2187 = 3 ^ 7
		return pHash * 2187 + oHash;
	}

	protected int edgeHasher1() {
		byte[] ofirst6edges = new byte[6];
		byte[] pfirst6edges = new byte[6];
		for (byte i = 0; i < 12; i++) {
			if (edges[i] < 12) {
				pfirst6edges[edges[i] / 2] = i;
				ofirst6edges[edges[i] / 2] = (byte) (edges[i] % 2);
			}
		}
		int pHash = cHasher(pfirst6edges) * factorialconst[6] + nonConsecutiveFHasher(pfirst6edges);
		int oHash = bHasher(ofirst6edges, 2, 0);
		// 64 = 2 ^ 6
		return pHash * 64 + oHash;
	}

	protected int edgeHasher2() {
		byte[] olast6edges = new byte[6];
		byte[] plast6edges = new byte[6];
		for (byte i = 0; i < 12; i++) {
			if (edges[i] > 11) {
				plast6edges[edges[i] / 2 - 6] = i;
				olast6edges[edges[i] / 2 - 6] = (byte) (edges[i] % 2);
			}
		}
		int pHash = cHasher(plast6edges) * factorialconst[6] + nonConsecutiveFHasher(plast6edges);
		int oHash = bHasher(olast6edges, 2, 0);
		// 64 = 2 ^ 6

		return pHash * 64 + oHash;
	}
}
