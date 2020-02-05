package kata;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class MazeSolver {
	Cell start;
	Cell end;
	Cell[][][] fullMaze;
	public MazeSolver(int[][] maze) {
		fullMaze = new Cell[4][maze.length][maze[0].length];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {

				fullMaze[0][i][j] = new Cell(maze[i][j], i, j, 0);
				for (int k = 1; k < fullMaze.length; k++) {
					fullMaze[k][i][j] = fullMaze[k - 1][i][j].createNextInterveal();
				}
				fullMaze[fullMaze.length - 1][i][j].thisNextInterval = fullMaze[0][i][j];
				if (maze[i][j] == -1) {
					start = fullMaze[0][i][j];
				}
				if (maze[i][j] == -2) {
					end = fullMaze[0][i][j];
				}
			}
		}
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				for (int k = 0; k < fullMaze.length; k++) {
					if (i > 0) {
						if (!fullMaze[k][i - 1][j].walls[1] && !fullMaze[k][i][j].walls[3]) {
							fullMaze[k][i - 1][j].neighbors[1] = fullMaze[k][i][j];
							fullMaze[k][i][j].neighbors[3] = fullMaze[k][i - 1][j];
						}
					}
					if (j > 0) {
						if (!fullMaze[k][i][j - 1].walls[0] && !fullMaze[k][i][j].walls[2]) {
							fullMaze[k][i][j - 1].neighbors[0] = fullMaze[k][i][j];
							fullMaze[k][i][j].neighbors[2] = fullMaze[k][i][j - 1];
						}
					}
				}
			}
		}
	}

	public List<String> solve() {
		Queue<Cell> openSet = new PriorityQueue<Cell>();
		openSet.add(start);
		List<String> solution = null;
		while (!openSet.isEmpty()) {
			Cell current = openSet.poll();
			if (current.isGoal) {
				return reconstruct_path(current);
			}
			for (int i = 0; i < current.neighbors.length; i++) {
				Cell neighbor = current.neighbors[i];
				if (neighbor == null)
					continue;
				if (neighbor == current.last) {
					continue;
				}
				if (current.cost < neighbor.cost) {
					neighbor.last = current;
					neighbor.lastString = getLast(i);
					neighbor.cost = current.cost;

					if (!openSet.contains(neighbor)) {
						openSet.add(neighbor);
					}
				}
			}

			Cell thisNextInterval = current.thisNextInterval;
			if (current.cost + 1 < thisNextInterval.cost) {
				thisNextInterval.last = current;
				thisNextInterval.lastString = ",";
				thisNextInterval.cost = current.cost + 1;
				if (!openSet.contains(thisNextInterval)) {
					openSet.add(thisNextInterval);
				}
			}
		}
		return solution;
	}

//	int h(Cell current) {
//		return 
//	}

	List<String> reconstruct_path(Cell current) {
		String total_path = "";
		while (current != null) {
			total_path = current.lastString + total_path;
			current = current.last;
		}
		String[] solution = total_path.split(",");
		return Arrays.asList(solution);
	}

	String getLast(int a) {
		switch (a) {
		case 0:
			return "E";
		case 1:
			return "S";
		case 2:
			return "W";
		case 3:
			return "N";
		default:
			return ".";
		}
	}
}

class Cell implements Comparable<Cell> {
	boolean[] walls = new boolean[4];
	Cell thisNextInterval;
	Cell[] neighbors = new Cell[4];
	boolean isGoal;
	Cell last;
	String lastString = "";
	int cost = Integer.MAX_VALUE;
	int x, y, interval;

	public Cell(int index, int x, int y, int interval) {
		this.x = x;
		this.y = y;
		this.interval = interval;
		if (index == -2) {
			isGoal = true;
			return;
		}
		if (index == -1) {
			cost = 0;
			return;
		}
		for (int i = 0; i < walls.length; i++) {
			walls[i] = (index >> i & 1) == 1;
		}
	}

	public Cell(boolean rightWall, boolean bottomWall, boolean leftWall, boolean topWall, int x, int y, int interval) {
		this.x = x;
		this.y = y;
		this.interval = interval;
		walls[0] = rightWall;
		walls[1] = bottomWall;
		walls[2] = leftWall;
		walls[3] = topWall;
	}

	public Cell createNextInterveal() {
		Cell thisNextInterval = new Cell(walls[3], walls[0], walls[1], walls[2], x, y, interval + 1);
		this.thisNextInterval = thisNextInterval;
		return thisNextInterval;
	}

	@Override
	public int compareTo(Cell o) {
		return cost - o.cost;
	}

	public String toString() {
		return "x: " + x + " y: " + y + " interval: " + interval + " cost: " + cost;
	}
}