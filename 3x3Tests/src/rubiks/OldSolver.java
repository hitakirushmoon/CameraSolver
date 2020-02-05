package rubiks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class OldSolver {

	static byte[] cornerdatabase, edgedatabase1, edgedatabase2;

	public static void main(String[] args) {
		long timestamp = System.currentTimeMillis();
		try (FileInputStream corner = new FileInputStream("corners.dat");
				FileInputStream edge1 = new FileInputStream("firstedges.dat");
				FileInputStream edge2 = new FileInputStream("lastedges.dat")) {
			cornerdatabase = corner.readAllBytes();
			edgedatabase1 = edge1.readAllBytes();
			edgedatabase2 = edge2.readAllBytes();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FullCube a = new FullCube();
		Random rand = new Random();
		byte nextRotation = (byte) rand.nextInt(18);

		for (int i = 0; i < 13; i++) {
			while (a.lastMove == nextRotation / 3
					|| (a.secondToLastMove == (nextRotation / 3) % 3 && a.lastMove % 3 == a.secondToLastMove)) {
				nextRotation = (byte) rand.nextInt(18);
			}
			// byte nextRotation = (byte) ((i % 2) * 3);
			System.out.println(Rubiks.rotationToString(nextRotation));
			a.rotate(nextRotation);
		}
		idastar(a);
		System.out.println(System.currentTimeMillis() - timestamp);
	}

	public static void idastar(FullCube r) {
		r.last = r.secondToLastMove = r.lastMove = -1;
		int bound = h(r);
		Deque<Byte> path = new ArrayDeque<Byte>();
		while (bound > 0) {
			System.out.println("bound = " + bound);
			bound = search(path, r, bound);
		}
		System.out.println("done!");
		System.out.println(path.size());
		System.out.println(path.toString());
	}

	public static int h(FullCube r) {
		int cornerHash = r.cornerHasher();
		int edgeHash1 = r.edgeHasher1();
		int edgeHash2 = r.edgeHasher2();
		return Math.max(Math.max(edgedatabase1[edgeHash1], edgedatabase2[edgeHash2]), cornerdatabase[cornerHash]);
	}

	public static boolean isGoal(FullCube r) {
		return h(r) == 0;
	}

	public static int search(Deque<Byte> path, FullCube r, int bound) {
		int f = path.size() + h(r);
		if (f > bound) {

			return f;
		}
		if (isGoal(r)) {
			return -1;
		}
		int min = Integer.MAX_VALUE;
		for (byte i = 0; i < Rubiks.rotateMoves.length; i++) {
			if (r.lastMove == i / 3) {
				continue;
			}
			if (r.secondToLastMove == (i / 3) % 3 && r.lastMove % 3 == r.secondToLastMove) {
				continue;
			}
			FullCube rotated = new FullCube(r);
			rotated.rotate(i);
			path.addLast(i);
			int t = search(path, rotated, bound);
			if (t == -1) {
				return -1;
			}
			min = Math.min(min, t);
			path.removeLast();
		}
		return min;
	}
}
