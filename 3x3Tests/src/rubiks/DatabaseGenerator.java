package rubiks;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class DatabaseGenerator {
	public static void main(String[] args) {
//		EdgesPermOnly a = new EdgesPermOnly();
//		System.out.println(Arrays.toString(a.edges));
//		a.rotate((byte) 14);
//		System.out.println(Arrays.toString(a.edges));
//		a = new EdgesPermOnly(a.toLong());
//		System.out.println(Arrays.toString(a.edges));
		DatabaseGenerator solver = new DatabaseGenerator();
//		solver.cbfs();
//		solver.e1bfs();
//		solver.e2bfs();

	}

	public void cbfs() {
		byte[] corners = new byte[88179840];

		CornersOnly r = new CornersOnly();
		Queue<byte[]> queue = new LinkedList<byte[]>();
		queue.add(r.corners);
		int hash;
		int oldHash;
		while (!queue.isEmpty()) {
			r = new CornersOnly(queue.remove());
			oldHash = r.cornerHasher();
			for (byte i = 0; i < CornersOnly.rotateMoves.length; i++) {
				if (r.corners[9] == i / 3) {
					continue;
				}
				if (r.corners[8] == (i / 3) % 3 && r.corners[9] % 3 == r.corners[8]) {
					continue;
				}
				CornersOnly rotated = new CornersOnly(r.corners);
				rotated.rotate(i);
				hash = rotated.cornerHasher();
				if (hash != 0 && corners[hash] == 0) {
					corners[hash] = (byte) (corners[oldHash] + 1);
					queue.add(rotated.corners);
				}
			}
		}
		try (FileOutputStream fos = new FileOutputStream("corners.dat")) {
			fos.write(corners);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void e1bfs() {
		byte[] firstedges = new byte[42577920];
		EdgesOnly r = new EdgesOnly();
		Queue<byte[]> queue = new LinkedList<byte[]>();
		queue.add(r.edges);
		int hash;
		int oldHash;
		while (!queue.isEmpty()) {
			r = new EdgesOnly(queue.remove());
			oldHash = r.edgeHasher1();
			for (byte i = 0; i < EdgesOnly.rotateMoves.length; i++) {
				if (r.edges[13] == i / 3) {
					continue;
				}
				if (r.edges[12] == (i / 3) % 3 && r.edges[13] % 3 == r.edges[12]) {
					continue;
				}
				EdgesOnly rotated = new EdgesOnly(r.edges);
				rotated.rotate(i);
				hash = rotated.edgeHasher1();
				if (hash != 0 && firstedges[hash] == 0) {
					firstedges[hash] = (byte) (firstedges[oldHash] + 1);
					queue.add(rotated.edges);
				}
			}
		}
		try (FileOutputStream fos = new FileOutputStream("firstedges.dat")) {
			fos.write(firstedges);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	int count = 0, cost = 0;

	public void e2bfs() {
		byte[] lastedges = new byte[42577920];
		EdgesOnly r = new EdgesOnly();
		int a = r.edgeHasher2();
		Queue<byte[]> queue = new LinkedList<byte[]>();
		queue.add(r.edges);
		int hash;
		int oldHash;
		while (!queue.isEmpty()) {
			r = new EdgesOnly(queue.remove());
			oldHash = r.edgeHasher2();
			for (byte i = 0; i < EdgesOnly.rotateMoves.length; i++) {
				if (r.edges[13] == i / 3) {
					continue;
				}
				if (r.edges[12] == (i / 3) % 3 && r.edges[13] % 3 == r.edges[12]) {
					continue;
				}
				EdgesOnly rotated = new EdgesOnly(r.edges);
				rotated.rotate(i);
				hash = rotated.edgeHasher2();
				if (hash != a && lastedges[hash] == 0) {
					lastedges[hash] = (byte) (lastedges[oldHash] + 1);
					queue.add(rotated.edges);
				}
			}
		}
		try (FileOutputStream fos = new FileOutputStream("lastedges.dat")) {
			fos.write(lastedges);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
