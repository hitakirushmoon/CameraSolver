package rubiks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Solver {

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

		for (int i = 0; i < 12; i++) {
			while (a.lastMove == nextRotation / 3
					|| (a.secondToLastMove == (nextRotation / 3) % 3 && a.lastMove % 3 == a.secondToLastMove)) {
				nextRotation = (byte) rand.nextInt(18);
			}
			// byte nextRotation = (byte) ((i % 2) * 3);
			System.out.println(Rubiks.rotationToString(nextRotation));
			a.rotate(nextRotation);
		}
		snc(a);
		System.out.println(System.currentTimeMillis() - timestamp);
	}

	static int bound, boundprime;
	static Map<FullCube, Byte> cache = new HashMap<FullCube, Byte>();

	static final int maxCacheSize = 1000000000;
//	static final float p = 0.1f;
//	static Random rand = new Random();

	public static void snc(FullCube r) {
		bound = h(r);
		boundprime = Integer.MAX_VALUE;
		r.last = r.secondToLastMove = r.lastMove = -1;
		Deque<Byte> path = new ArrayDeque<Byte>();
		while (!found) {
			System.out.println("bound = " + bound);
			search(path, r);
			bound = boundprime;
			boundprime = Integer.MAX_VALUE;
		}
		while (!path.isEmpty()) {
			System.out.println(Rubiks.rotationToString(path.poll()));
		}
	}

	static boolean found = false;

	public static void search(Deque<Byte> path, FullCube r) {
		byte cutoff = Byte.MAX_VALUE;
		if (cache.size() < maxCacheSize) {
			cache.put(r, h(r));
		}
		for (byte i = 0; i < Rubiks.rotateMoves.length; i++) {
			if (r.lastMove == i / 3) {
				continue;
			}
			if (r.secondToLastMove == (i / 3) % 3 && r.lastMove % 3 == r.secondToLastMove) {
				continue;
			}
			FullCube rotated = new FullCube(r);
			rotated.rotate(i);
			int fni = path.size() + 1 + h(r);
			if (fni <= bound) {
				path.add(i);
				if (isGoal(rotated)) {
					found = true;
					return;
				} else {
					search(path, rotated);
					if (found) {
						return;
					}
				}
				path.removeLast();
			} else if (fni < boundprime) {
				boundprime = fni;
			}
			if (h(rotated) + 1 < cutoff) {
				cutoff = (byte) (h(rotated) + 1);
			}

		}
		cache.put(r, cutoff);

	}

	public static byte h(FullCube r) {
		if (cache.containsKey(r)) {
			return cache.get(r);
		}

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
