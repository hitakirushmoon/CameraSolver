package rubiks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class RBFS {
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
		Deque<Byte> path = new ArrayDeque<Byte>();
		FullCube r = new FullCube();
		Random rand = new Random();
		byte nextRotation = (byte) rand.nextInt(18);

		for (int i = 0; i < 13; i++) {
			while (r.lastMove == nextRotation / 3
					|| (r.secondToLastMove == (nextRotation / 3) % 3 && r.lastMove % 3 == r.secondToLastMove)) {
				nextRotation = (byte) rand.nextInt(18);
			}
			// byte nextRotation = (byte) ((i % 2) * 3);
			System.out.println(Rubiks.rotationToString(nextRotation));
			r.rotate(nextRotation);
		}
		r.last = r.secondToLastMove = r.lastMove = -1;
		System.out.println();
		r.F[0] = h(r);
		search(path, r, (byte) 21);
//		RBFSCR(path, r, Byte.MAX_VALUE, Byte.MAX_VALUE);
		while (!path.isEmpty()) {
			System.out.println(Rubiks.rotationToString(path.poll()));
		}
		System.out.println(System.currentTimeMillis() - timestamp);

	}

	public static byte search(Deque<Byte> path, FullCube r, byte bound) {
		byte f = (byte) (path.size() + h(r));
		if (f > bound) {
			return f;
		}
		if (isGoal(r)) {
			return -1;
		}
		Comparator<FullCube> cubeComparator = (r1, r2) -> {
			return r1.F[0] - r2.F[0];
		};
		Queue<FullCube> childnodes = new PriorityQueue<FullCube>(cubeComparator);
		for (byte i = 0; i < Rubiks.rotateMoves.length; i++) {
			if (r.lastMove == i / 3) {
				continue;
			}
			if (r.secondToLastMove == (i / 3) % 3 && r.lastMove % 3 == r.secondToLastMove) {
				continue;
			}
			FullCube rotated = new FullCube(r);
			rotated.rotate(i);
			byte fni = (byte) (path.size() + 1 + h(rotated));
			if (f < r.F[0]) {
				rotated.F[0] = (byte) Math.max(r.F[0], fni);
			} else {
				rotated.F[0] = fni;
			}
			childnodes.add(rotated);
		}

		while (childnodes.peek().F[0] <= bound && childnodes.peek().F[0] < 21) {
			FullCube child = childnodes.poll();
			path.add(child.last);
			byte result = search(path, child, (byte) Math.min(bound, childnodes.peek().F[0]));
			if (result == -1) {
				return -1;
			}
			path.removeLast();
			child.F[0] = result;
			childnodes.add(child);
		}
		return childnodes.peek().F[0];
	}


	public static byte h(FullCube r) {
		int cornerHash = r.cornerHasher();
		int edgeHash1 = r.edgeHasher1();
		int edgeHash2 = r.edgeHasher2();
		return (byte) Math.max(Math.max(edgedatabase1[edgeHash1], edgedatabase2[edgeHash2]),
				cornerdatabase[cornerHash]);
	}

	public static boolean isGoal(FullCube r) {
		return h(r) == 0;
	}
}
