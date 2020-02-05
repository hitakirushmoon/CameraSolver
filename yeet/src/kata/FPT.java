package kata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class FPT {
	int[] stations;
	List<Integer> stations2;
	static final int FOUND = -1;

	public FPT(int[] stations) {
		this.stations = stations;
		stations2 = toList(stations);
		System.out.println(stations2);
	}

	public List<Integer> solve() {
		LinkedList<Integer> solution = new LinkedList<Integer>();
		int intialBound = hCost(stations[0], 1, solution);
		int currentBound = intialBound;
		solution.add(stations[0]);
		while (true) {
			int t = search(solution, 1, currentBound);
			if (t == FOUND) {
				solution.add(stations[3]);
				return solution;
			}
			currentBound = t;
		}
//		return null;

	}

	public int search(LinkedList<Integer> path, int currentGoalInd, int bound) {
		int g = path.size();
		int node = path.peek();
		int h = hCost(node, currentGoalInd, path);
		if (h == -1) {
			return Integer.MAX_VALUE;
		}
		int f = g + h;
		if (f > bound)
			return f;
		if (currentGoalInd == 3) {
			List<Integer> shortestFinalPath = a_star(stations[2], stations[3], path);
			if (shortestFinalPath == null) {
				return Integer.MAX_VALUE;
			}
			Collections.reverse(path);
			path.addAll(shortestFinalPath);
			int t = search(path, currentGoalInd + 1, bound);
			if (t == FOUND)
				return FOUND;
			path.removeAll(shortestFinalPath);
			Collections.reverse(path);
			return t;
		}
		if (currentGoalInd == 4)
			return FOUND;
		int goal = stations[currentGoalInd];
		int min = Integer.MAX_VALUE;
		for (int succ : generateNeighbors(node)) {
			if (!path.contains(succ)) {
				path.push(succ);
				int t;
				if (succ == goal) {
					t = search(path, currentGoalInd + 1, bound);
				} else {
					t = search(path, currentGoalInd, bound);
				}
				if (t == FOUND)
					return FOUND;
				if (t < min)
					min = t;
				path.pop();
			}
		}
		return min;
	}

	public List<Integer> toList(int[] ints) {
		List<Integer> intList = new ArrayList<Integer>(ints.length);
		for (int i : ints) {
			intList.add(i);
		}
		return intList;
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

	public static int h(int start, int end) {
		return Math.abs(start / 10 - end / 10) + Math.abs(start % 10 - end % 10);
	}

	public static List<Integer> generateNeighbors(int a) {
		int x = a / 10, y = a % 10;
		List<Integer> neighbor = new LinkedList<Integer>();
		if (x > 0) {
			neighbor.add(a - 10);
		}
		if (x < 9) {
			neighbor.add(a + 10);
		}
		if (y > 0) {
			neighbor.add(a - 1);
		}
		if (y < 9) {
			neighbor.add(a + 1);
		}
		return neighbor;
	}

	public int hCost(int currentPos, int currentGoalInd, List<Integer> obstacles) {
		if (currentGoalInd == 4) {
			return 0;
		}
		List<Integer> lmao_a_star = a_star(currentPos, stations[currentGoalInd], obstacles);
		if (lmao_a_star == null) {
			return -1;
		}
		int cost = lmao_a_star.size();
		for (int i = currentGoalInd; i < stations.length - 1; i++) {
			lmao_a_star = a_star(stations[i], stations[i + 1], obstacles);
			if (lmao_a_star == null) {
				return -1;
			}
			cost += lmao_a_star.size();
		}
		return cost;
	}

	public List<Integer> a_star(int start, int goal, List<Integer> obstacles) {
		int[] cameFrom = new int[100];
		int[] gScore = new int[100];
		int[] fScore = new int[100];
		Queue<Integer> openSet = new PriorityQueue<Integer>((Integer a, Integer b) -> (fScore[a] - fScore[b]));
		for (int i = 0; i < 100; i++) {
			gScore[i] = Integer.MAX_VALUE;
			fScore[i] = Integer.MAX_VALUE;
			cameFrom[i] = -1;
		}
		gScore[start] = 0;
		fScore[start] = h(start, goal);
		openSet.add(start);
		while (!openSet.isEmpty()) {
			int current = openSet.poll();
			if (current == goal) {
				return reconstruct_path(cameFrom, cameFrom[current]);
			}
			List<Integer> neighbors = generateNeighbors(current);
			for (Integer neighbor : neighbors) {
				if (obstacles.contains(neighbor)) {
					continue;
				}
				int tentative_gScore = gScore[current] + 1;
				if (tentative_gScore < gScore[neighbor]) {
					// This path to neighbor is better than any previous one. Record it!
					cameFrom[neighbor] = current;
					gScore[neighbor] = tentative_gScore;
					fScore[neighbor] = gScore[neighbor] + h(neighbor, goal) * 2;
					if (!openSet.contains(neighbor)) {
						openSet.add(neighbor);
					}
				}
			}
		}
		return null;
	}

	public static List<Integer> reconstruct_path(int[] cameFrom, int current) {
		LinkedList<Integer> total_path = new LinkedList<Integer>();
		total_path.add(current);
		while (cameFrom[current] >= 0) {
			current = cameFrom[current];
			total_path.addFirst(current);
		}
		total_path.remove();
		return total_path;
	}
}