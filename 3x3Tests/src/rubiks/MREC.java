package rubiks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class MREC {
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
		MREC solver = new MREC();
		for (int i = 0; i < 12; i++) {
			while (a.lastMove == nextRotation / 3
					|| (a.secondToLastMove == (nextRotation / 3) % 3 && a.lastMove % 3 == a.secondToLastMove)) {
				nextRotation = (byte) rand.nextInt(18);
			}
			// byte nextRotation = (byte) ((i % 2) * 3);
			System.out.println(Rubiks.rotationToString(nextRotation));
			a.rotate(nextRotation);
		}
		solver.mrec(a);
		System.out.println(System.currentTimeMillis() - timestamp);
	}

	Map<FullCube, Byte> cache = new HashMap<FullCube, Byte>();
	final int maxCacheSize = 000000000;
	boolean found = false;

	public void mrec(FullCube r) {
		r.last = r.secondToLastMove = r.lastMove = -1;
		b(r, h(r));
		Deque<Byte> path = new ArrayDeque<Byte>();
		while (!found) {
			explore(r, b(r), path);
		}
		while (!path.isEmpty()) {
			System.out.println(Rubiks.rotationToString(path.pollLast()));
		}
	}

	public void explore(FullCube r, byte b, Deque<Byte> path) {
		if (isGoal(r)) {
			found = true;
			return;
		}
		Queue<FullCube> succ = successor(r);
		if (r.isTip && cache.size() < maxCacheSize) {
			succ.forEach((n) -> b(n, h(n)));
		}
		byte cutoff = Byte.MAX_VALUE;
		byte newb = 0;
		while (!succ.isEmpty()) {
			FullCube ri = succ.poll();
			if (cache.containsKey(ri)) {
				newb = b(ri);
			} else {
				newb = h(ri);
			}
			if (newb + 1 <= b) {
				newb = (byte) (b - 1);
				explore(ri, newb, path);
				if (found) {
					path.add(ri.last);
					return;
				}
			}
			if (newb + 1 < cutoff) {
				cutoff = (byte) (newb + 1);
			}
		}
		b = cutoff;
		b(r, b);
	}

	public static byte h(FullCube r) {
		int cornerHash = r.cornerHasher();
		int edgeHash1 = r.edgeHasher1();
		int edgeHash2 = r.edgeHasher2();
		return (byte) Math.max(Math.max(edgedatabase1[edgeHash1], edgedatabase2[edgeHash2]),
				cornerdatabase[cornerHash]);
	}

	public byte b(FullCube r) {
		return cache.get(r);
	}

	public void b(FullCube r, byte b) {
		cache.put(r, b);
	}

	public static boolean isGoal(FullCube r) {
		return h(r) == 0;
	}

	public Queue<FullCube> successor(FullCube r) {
		r.isTip = true;
		Queue<FullCube> childnodes = new LinkedList<FullCube>();
		for (byte i = 0; i < Rubiks.rotateMoves.length; i++) {
			if (r.lastMove == i / 3) {
				continue;
			}
			if (r.secondToLastMove == (i / 3) % 3 && r.lastMove % 3 == r.secondToLastMove) {
				continue;
			}
			FullCube rotated = new FullCube(r);
			rotated.rotate(i);
			if (r.isTip) {
				if (cache.containsKey(rotated)) {
					r.isTip = false;
				}
			}
			childnodes.add(rotated);
		}
		return childnodes;
	}

}
