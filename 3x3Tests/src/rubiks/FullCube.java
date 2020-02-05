package rubiks;

import java.util.Arrays;

public class FullCube extends Rubiks {
	byte lastMove, secondToLastMove;
	byte last;
	byte[] F = new byte[2];
	int[] hashes = new int[3];
	boolean isTip;

	public FullCube() {
		corners = new byte[] { 0, 3, 6, 9, 12, 15, 18, 21 };
		edges = new byte[] { 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22 };
		lastMove = secondToLastMove = last = -1;
		hashes[0] = cornerHasher();
		hashes[1] = edgeHasher1();
		hashes[2] = edgeHasher2();
	}

	public FullCube(FullCube r) {
		corners = r.corners.clone();
		edges = r.edges.clone();
		lastMove = r.lastMove;
		secondToLastMove = r.secondToLastMove;
		hashes = r.hashes.clone();
	}

	public void rotate(byte a) {
		super.rotate(a);
		secondToLastMove = (byte) (lastMove % 3);
		lastMove = (byte) (a / 3);
		last = a;
		hashes[0] = cornerHasher();
		hashes[1] = edgeHasher1();
		hashes[2] = edgeHasher2();
	}

	public String toString() {
//		byte[] pcorner = new byte[8];
//		byte[] ocorner = new byte[8];
//		for (int i = 0; i < pcorner.length; i++) {
//			pcorner[i] = (byte) (corners[i] / 3);
//			ocorner[i] = (byte) (corners[i] % 3);
//		}
//		byte[] pedge = new byte[12];
//		byte[] oedge = new byte[12];
//		for (int i = 0; i < pedge.length; i++) {
//			pedge[i] = (byte) (edges[i] / 2);
//			oedge[i] = (byte) (edges[i] % 2);
//		}
//		String a = "";
//		a += Integer.toHexString(pcorner[CP.BUL.ordinal()]) + "   " + Integer.toHexString(pedge[EP.BU.ordinal()])
//				+ "   " + Integer.toHexString(pcorner[CP.BUR.ordinal()]) + " " + ocorner[CP.BUL.ordinal()] + "   "
//				+ oedge[EP.BU.ordinal()] + "   " + ocorner[CP.BUR.ordinal()] + "\r\n";
//		a += " " + Integer.toHexString(pedge[EP.UL.ordinal()]) + "     " + Integer.toHexString(pedge[EP.UR.ordinal()])
//				+ "  " + " " + oedge[EP.UL.ordinal()] + "     " + oedge[EP.UR.ordinal()] + "\r\n";
//		a += "  " + Integer.toHexString(pcorner[CP.FUL.ordinal()]) + " " + Integer.toHexString(pedge[EP.FU.ordinal()])
//				+ " " + Integer.toHexString(pcorner[CP.FUR.ordinal()]) + "   " + "  " + ocorner[CP.FUL.ordinal()] + " "
//				+ oedge[EP.FU.ordinal()] + " " + ocorner[CP.FUR.ordinal()] + "\r\n";
//		a += Integer.toHexString(pedge[EP.BL.ordinal()]) + " " + Integer.toHexString(pedge[EP.FL.ordinal()]) + "   "
//				+ Integer.toHexString(pedge[EP.FR.ordinal()]) + " " + Integer.toHexString(pedge[EP.BR.ordinal()]) + " "
//				+ oedge[EP.BL.ordinal()] + " " + oedge[EP.FL.ordinal()] + "   " + oedge[EP.FR.ordinal()] + " "
//				+ oedge[EP.BR.ordinal()] + "\r\n";
//		a += "  " + Integer.toHexString(pcorner[CP.FDL.ordinal()]) + " " + Integer.toHexString(pedge[EP.FD.ordinal()])
//				+ " " + Integer.toHexString(pcorner[CP.FDR.ordinal()]) + "   " + "  " + ocorner[CP.FDL.ordinal()] + " "
//				+ oedge[EP.FD.ordinal()] + " " + ocorner[CP.FDR.ordinal()] + "\r\n";
//		a += " " + Integer.toHexString(pedge[EP.DL.ordinal()]) + "     " + Integer.toHexString(pedge[EP.DR.ordinal()])
//				+ "  " + " " + oedge[EP.DL.ordinal()] + "     " + oedge[EP.DR.ordinal()] + "\r\n";
//		a += Integer.toHexString(pcorner[CP.BDL.ordinal()]) + "   " + Integer.toHexString(pedge[EP.BD.ordinal()])
//				+ "   " + Integer.toHexString(pcorner[CP.BDR.ordinal()]) + " " + ocorner[CP.BDL.ordinal()] + "   "
//				+ oedge[EP.BD.ordinal()] + "   " + ocorner[CP.BDR.ordinal()] + "\r\n";
//		a += cornerHasher() + " " + edgeHasher1() + " " + edgeHasher2() + "\r\n";
//		return a;
		return cornerHasher() + " " + edgeHasher1() + " " + edgeHasher2() + " " + (F[0] + F[1]) + "\r\n";
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof FullCube)) {
			return false;
		}
		FullCube r = (FullCube) o;
		return r.hashes[0] == this.hashes[0] && r.hashes[1] == this.hashes[1] && r.hashes[2] == this.hashes[2];
	}
}
