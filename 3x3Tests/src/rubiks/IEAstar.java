package rubiks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class IEAstar {
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
		FullCube r = new FullCube();
		Random rand = new Random();
		byte nextRotation = (byte) rand.nextInt(18);

		for (int i = 0; i < 11; i++) {
			while (r.lastMove == nextRotation / 3
					|| (r.secondToLastMove == (nextRotation / 3) % 3 && r.lastMove % 3 == r.secondToLastMove)) {
				nextRotation = (byte) rand.nextInt(18);
			}
			// byte nextRotation = (byte) ((i % 2) * 3);
			System.out.println(Rubiks.rotationToString(nextRotation));
			r.rotate(nextRotation);
		}
		System.out.println();
		IEAstar search = new IEAstar();
		search.ieastar(r);

		System.out.println(System.currentTimeMillis() - timestamp);
	}

	final int cutoff = -1, failure = -2;
	Queue<FullCube> closed = new LinkedList<FullCube>();

	public void ieastar(FullCube r) {
		r.last = r.secondToLastMove = r.lastMove = -1;
		byte result = cutoff;
		r.F[0] = (byte) h(r);
		byte flimit = (byte) (r.F[0] + r.F[1]);
		Comparator<FullCube> cubeComparator = (r1, r2) -> {
			if(r1.F[0] + r1.F[1] - r2.F[0] - r2.F[1] == 0) {
				return r1.F[0] - r2.F[0]; 
			}
			return r1.F[0] + r1.F[1] - r2.F[0] - r2.F[1];
		};
		Queue<FullCube> fringe = new PriorityQueue<FullCube>(cubeComparator);
		fringe.add(r);
		closed.add(r);
		Deque<Byte> path = new ArrayDeque<Byte>();
		while (result == cutoff) {
			Queue<FullCube> newfringe = new PriorityQueue<FullCube>(cubeComparator);
			while (!fringe.isEmpty()) {
				FullCube best = fringe.poll();
				result = search(path, best, flimit);
				if (result != cutoff) {
					while (!path.isEmpty()) {
						System.out.println(Rubiks.rotationToString(path.poll()));
					}
					return;
				}
				boolean a = false;
				for (byte i = 0; i < Rubiks.rotateMoves.length; i++) {
					if (best.lastMove == i / 3) {
						continue;
					}
					if (best.secondToLastMove == (i / 3) % 3 && best.lastMove % 3 == best.secondToLastMove) {
						continue;
					}
					FullCube rotated = new FullCube(best);
					rotated.rotate(i);
					rotated.F[0] = (byte) h(rotated);
					rotated.F[1] = (byte) (best.F[1] + 1);
					if (!closed.contains(rotated)) {
						if (rotated.F[0] + rotated.F[1] <= flimit) {
							closed.add(rotated);
							newfringe.add(rotated);
						} else {
							a = true;
						}
					}
				}
				if (a) {
					if (!closed.contains(best)) {
						closed.add(best);
					}
					newfringe.add(best);
				}
			}
			flimit = (byte) Math.max((newfringe.peek().F[0] + newfringe.peek().F[1]), flimit + 1);
			fringe = newfringe;
		}
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

	public byte search(Deque<Byte> path, FullCube r, byte bound) {
		if (r.F[0] + r.F[1] > bound) {
			return cutoff;
		}
		if (isGoal(r)) {
			return 1;
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
			rotated.F[0] = (byte) h(rotated);
			rotated.F[1] = (byte) (r.F[1] + 1);
			if (!closed.contains(rotated)) {
				path.add(i);
				byte result = search(path, rotated, bound);
				if (result != cutoff) {
					return result;
				}
				path.removeLast();
			}
		}
		return cutoff;
	}
}
