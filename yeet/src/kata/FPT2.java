package kata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class FPT2 {
	public static void main(String[] args) {
		FPT2 test = new FPT2(new int[] { 37, 16, 57, 26 });
		List<Integer> sol = test.solve();
		System.out.println(sol);
		System.out.println(sol.size());
		String[][] board = new String[10][10];

//		List<Integer> obstacles = new LinkedList<Integer>();
////		System.out.println(aStarPath(0, 47, 1, obstacles));
//		obstacles.add(0);
//		obstacles.add(11);
//		System.out.println(aStarPath(0, 7, obstacles));
////		System.out.println(aStarPath(0, 47, 1, obstacles));
////		obstacles.add(1);
////		System.out.println(aStarPath(0, 7, 1, obstacles));
////		obstacles.add(20);
////		System.out.println(aStarPath(0, 7, 1, obstacles));
	}

	int[] stations;
	List<Integer> stations2;

	public FPT2(int[] stations) {
		this.stations = stations;
		stations2 = toList(stations);
		System.out.println(stations2);
		System.out.println(hCost(stations[0], 1));
	}

	public List<Integer> solve() {
		LinkedList<Integer> shortestSolution = new LinkedList<Integer>();
		for (int i = 0; i < 6; i++) {
			LinkedList<Integer> currentSol = (LinkedList<Integer>) tryPerm(i);
			if (currentSol.isEmpty())
				continue;
			currentSol.add(i);
			if (shortestSolution.size() > currentSol.size() || shortestSolution.isEmpty()) {
				shortestSolution = currentSol;
			}
		}
		LinkedList<Integer>[] rearrangedSol = (LinkedList<Integer>[]) new LinkedList[4];
		if (shortestSolution.isEmpty()) {
			return null;
		}
		int[] perm = getPerm(shortestSolution.pollLast());
//		System.out.println(Arrays.toString(perm));
//		System.out.println(shortestSolution);
		int id = 0;
		while (id < 3) {
			if (shortestSolution.peek() == -1) {
				shortestSolution.poll();
				LinkedList<Integer> paritalSolution = new LinkedList<Integer>();
				while (shortestSolution.peek() != -1) {
					paritalSolution.add(shortestSolution.poll());
				}
				rearrangedSol[perm[id] - 1] = paritalSolution;
			}
			id++;
		}
//		System.out.println(rearrangedSol);
		shortestSolution.poll();
		int[][] print = new int[10][10];
		for (int i = 0; i < perm.length; i++) {
			shortestSolution.add(stations[i]);
			shortestSolution.addAll(rearrangedSol[i]);
			for (int in : rearrangedSol[i]) {
				print[in / 10][in % 10] = i * 2 + 2;
			}
			print[stations[i] / 10][stations[i] % 10] = i * 2 + 1;
		}
		shortestSolution.add(stations[3]);
		for (int[] i : print) {
			System.out.println(Arrays.toString(i));
		}
		System.out.println(toList(perm));
		return shortestSolution;
	}

	public List<Integer> toList(int[] ints) {
		List<Integer> intList = new ArrayList<Integer>(ints.length);
		for (int i : ints) {
			intList.add(i);
		}
		return intList;
	}

	public List<Integer> tryPerm(int perm) {
		List<Integer> obstacles = new LinkedList<Integer>();
		obstacles.addAll(stations2);
		int[] order = getPerm(perm);

		return tryPerm(order, obstacles, 0);
	}

	public int[] getPerm(int perm) {
		int[] order;
		switch (perm) {
		case 0:
			order = new int[] { 1, 2, 3 };
			break;
		case 1:
			order = new int[] { 1, 3, 2 };
			break;
		case 2:
			order = new int[] { 2, 1, 3 };
			break;
		case 3:
			order = new int[] { 2, 3, 1 };
			break;
		case 4:
			order = new int[] { 3, 1, 2 };
			break;
		case 5:
			order = new int[] { 3, 2, 1 };
			break;
		default:
			throw new IllegalArgumentException();
		}
		return order;
	}

	public List<Integer> tryPerm(int[] perm, List<Integer> obstacles, int index) {
		LinkedList<Integer> solution = new LinkedList<Integer>();
		if (index != perm.length) {
			int start = stations[perm[index] - 1];
			int goal = stations[perm[index]];
			Queue<List<Integer>> partialSols = new PriorityQueue<List<Integer>>(
					(List<Integer> a, List<Integer> b) -> a.size() - b.size());
			Queue<List<Integer>> firstSols = new LinkedList<List<Integer>>();
			for (int i = 0; i < 3; i++) {
				List<Integer> a = generatePath(start, goal, obstacles, i);
				if (a != null) {
					firstSols.add(a);
				}
			}
			while (!firstSols.isEmpty()) {
				List<Integer> partialSol = firstSols.poll();
				obstacles.addAll(partialSol);
				List<Integer> next = tryPerm(perm, obstacles, index + 1);
				obstacles.removeAll(partialSol);
				if (!next.isEmpty()) {
					partialSol.addAll(next);
					partialSols.add(partialSol);
				}
			}
			if (partialSols.isEmpty()) {
				return solution;
			}
			solution = (LinkedList<Integer>) partialSols.poll();
		} else {
//			System.out.println();
		}
		solution.addFirst(-1);

		return solution;

	}

	public List<Integer> generatePath(int start, int goal, List<Integer> obstacles, int i) {
		switch (i) {
		case 0:
			return simplestPath(start, goal, true, obstacles);
		case 1:
			return simplestPath(start, goal, false, obstacles);
		case 2:
			return aStarPath(start, goal, obstacles);
		}
		throw new IllegalArgumentException();
	}

	public List<Integer> aStarPath(int start, int goal, List<Integer> obstacles) {
		int[] cameFrom = new int[100];
		int[] gScore = new int[100];
		int[] fScore = new int[100];
		for (int i = 0; i < 100; i++) {
			gScore[i] = 10000;
			fScore[i] = 10000;
			cameFrom[i] = -1;
		}
		gScore[start] = 0;
		fScore[start] = h(start, goal);
		Comparator<Integer> comp = (Integer a, Integer b) -> (fScore[a] - fScore[b]);
		Queue<Integer> openSet = new PriorityQueue<Integer>(comp);
		openSet.add(start);
		while (!openSet.isEmpty()) {
			int current = openSet.poll();
			if (current == goal) {

				return reconstruct_path(cameFrom, start, cameFrom[current]);
			}
			Queue<Integer> neighbors = new LinkedList<Integer>();
			if (current % 10 != 0) {
				neighbors.add(current - 1);
			}
			if (current % 10 != 9) {
				neighbors.add(current + 1);
			}
			if (current / 10 != 0) {
				neighbors.add(current - 10);
			}
			if (current / 10 != 9) {
				neighbors.add(current + 10);
			}

			if (current != start) {
				neighbors.remove(cameFrom[current]);
			}
			for (Integer neighbor : neighbors) {
				if (obstacles.contains(neighbor) && neighbor != start && neighbor != goal)
					continue;

				boolean sameDirection = neighbor + cameFrom[current] - current * 2 == 0 || current == start;

//				print(fScore);
				boolean adjacentWall = false;
				for (int i : generateCorners(neighbor)) {
					if (obstacles.contains(i) && !stations2.contains(i)) {
						adjacentWall = true;
						break;
					}
				}
				int tentative_gScore = gScore[current] + (adjacentWall ? 0 : 1) + 3;
				if (tentative_gScore < gScore[neighbor]) {
					cameFrom[neighbor] = current;
					gScore[neighbor] = tentative_gScore;
					fScore[neighbor] = gScore[neighbor] + h(neighbor, goal);
					if (neighbor == 7) {
					}
					if (!openSet.contains(neighbor)) {
						openSet.add(neighbor);
					}
				}
			}
		}
		return null;
	}

	public static void print(int[] board) {
		for (int i = 0; i < 10; i++) {
			for (int i2 = 0; i2 < 10; i2++) {
				System.out.print(Integer.toString(board[i * 10 + i2]).substring(0,
						(int) Math.min(1, Math.log10(board[i * 10 + i2])) + 1));
				System.out.print(", ");
			}
			System.out.println();
		}
		System.out.println();

	}

	public static List<Integer> simplestPath(int start, int goal, boolean verticalFirst, List<Integer> obstacles) {
		return simplestPath(start / 10, start % 10, goal / 10, goal % 10, verticalFirst, obstacles);
	}

	public static List<Integer> simplestPath(int x0, int y0, int x1, int y1, boolean verticalFirst,
			List<Integer> obstacles) {
		List<Integer> ans = new LinkedList<Integer>();
		if (verticalFirst) {
			if (x1 - x0 != 0) {
				for (int i = (int) (x0 + Math.signum(x1 - x0)); i != x1; i += Math.signum(x1 - x0)) {
					if (!obstacles.contains(i * 10 + y0)) {
						ans.add(i * 10 + y0);
					} else {
						return null;
					}
				}
			}
			if (y1 - y0 != 0) {
				for (int i = y0; i != y1; i += Math.signum(y1 - y0)) {
					if (!obstacles.contains(x1 * 10 + i)) {
						ans.add(x1 * 10 + i);
					} else {
						return null;
					}
				}
			}
		} else {
			if (y1 - y0 != 0) {
				for (int i = (int) (y0 + Math.signum(x1 - x0)); i != y1; i += Math.signum(y1 - y0)) {
					if (!obstacles.contains(x0 * 10 + i)) {
						ans.add(x0 * 10 + i);
					} else {
						return null;
					}
				}
			}
			if (x1 - x0 != 0) {
				for (int i = x0; i != x1; i += Math.signum(x1 - x0)) {
					if (!obstacles.contains(i * 10 + y1)) {
						ans.add(i * 10 + y1);
					} else {
						return null;
					}
				}
			}
		}
		ans.remove((Integer) (x0 * 10 + y0));
		return ans;
	}

	public static List<Integer> generateCorners(int a) {
		int x = a / 10, y = a % 10;
		List<Integer> corners = new LinkedList<Integer>();
		boolean lowx = x > 0;
		boolean lowy = y > 0;
		boolean highx = x < 9;
		boolean highy = y < 9;
		if (lowx && lowy) {
			corners.add(a - 11);
		}
		if (highx && lowy) {
			corners.add(a + 9);
		}
		if (lowx && highy) {
			corners.add(a - 9);
		}
		if (highx && highy) {
			corners.add(a + 11);
		}
		return corners;
	}

	public static List<Integer> reconstruct_path(int[] cameFrom, int start, int current) {
		LinkedList<Integer> total_path = new LinkedList<Integer>();
		total_path.add(current);
		while (cameFrom[current] >= 0) {
			current = cameFrom[current];
			total_path.addFirst(current);
		}
		total_path.remove();
		return total_path;
	}

	public static int h(int start, int end) {
		return Math.abs(start / 10 - end / 10) + Math.abs(start % 10 - end % 10);
	}
	public int hCost(int currentPos, int currentGoalInd) {
		if (currentGoalInd == 4) {
			return 0;
		}
		int cost = h(currentPos, stations[currentGoalInd]);
		for (int i = currentGoalInd; i < stations.length - 1; i++) {
			cost += h(stations[i], stations[i + 1]);
		}
		return cost;
	}
}