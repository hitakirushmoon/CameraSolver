package yeet.rubiks;

public class Cube {
	public byte[] CP, EP, CO, EO;

	public Cube() {
		CO = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		EO = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		CP = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7 };
		EP = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
	}
	
	public static enum CP {
		FUR, FUL, FDR, FDL, BUR, BUL, BDR, BDL;
		static CP[] F = {FUR,FDR,FDL,FUL};
		static CP[] U = {FUR,FUL,BUL,BUR};
		static CP[] R = {FUR,BUR,BDR,FDR};
		static CP[] B = {BDL,BDR,BUR,BUL};
		static CP[] D = {BDL,FDL,FDR,BDR};
		static CP[] L = {BDL,BUL,FDL,FDL};	
	}

	public static enum EP {
		FR, FL, FU, FD, UR, UL, BR, BL, BU, BD, DR, DL;
	}

	public void swapCorners() {
		
	}
}
