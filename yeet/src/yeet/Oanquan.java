package yeet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Oanquan {

	public static void main(String[] args) {
//		Game a = new Game(2, 5, 10, 6, new int[] { -1, 1 });
		Game a = new Game(3, 0, 10, 4, new int[] { -1, 1 });
		a.scores  = new int[]{3,3,3};
//		Game a = new Game(4, new int[] { 34, 0, 0, 1, 34, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, new int[] { -1, 1 });
		int turn = 0;
		a.fm(0, 1, 1);
		Minimax b = new Minimax(a, 1, 3);
		b.minimax();
		b.toFile();
//		while (!a.isOver) {
//			Minimax b;
//			if (turn % 3 == 0) {
//				b = new Minimax(a, turn % a.players, 9);
//			} else {
//				b = new Minimax(a, turn % a.players, 12);
//
//			}
//			Game res = b.getResult();
//			System.out.println(Arrays.toString(res.eval));
//			System.out.println(Arrays.toString(res.scores));
//			System.out.println(Arrays.toString(res.board));
//			System.out.println(Arrays.deepToString(res.moveHistory.toArray()));
//			System.out.println(b.getCount());
//			int[] firstMove = res.moveHistory.get(turn);
//			a.fm(turn % a.players, firstMove[0], firstMove[1]);
//			System.out.println(Arrays.toString(a.board));
//
////			b.toFile();
//			turn++;
//		}
	}
}

class Minimax {
	private int count = 0;
	private Game game;
	private int player;
	private final int maxDepth;
	private List<String> lines = new ArrayList<String>();
	private Game result;
	private final int maxPoint;

	public Minimax(Game game, int player, int maxDepth) {
		this.game = new Game(game);
		this.player = player;
		this.maxDepth = maxDepth;
		int total = 0;
		for (int i = 0; i < game.eval.length; i++) {
			total += game.eval[i];
		}
		maxPoint = total;
	}

	public int getCount() {
		return count;
	}

	public Game getGame() {
		return game;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public Game getResult() {
		minimax();
		return result;
	}

	public void minimax() {
		long timeStamp = System.nanoTime();
		if (game.players > 2) {
			result = minimax2(game, player, maxDepth);
		}
		timeStamp = System.nanoTime() - timeStamp;
		lines.add(String.valueOf(timeStamp));

	}

	public void addHistory(int depth, Game actualVal) {
		StringBuilder a = new StringBuilder();
		a.append("chosen at depth ").append(depth).append(": \r\n").append(actualVal.toString()).append("\r\n");
		lines.add(a.toString());
	}

	public void toFile() {
		Path file = Paths.get("D:\\Users\\Minh\\Desktop\\test.txt");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Game minimax2(Game game, int player, int depth) {
		lines.add(game.toString());
		if (depth == 0 || game.isOver) {
			count++;
			return game;
		}
		int val = Integer.MIN_VALUE;
		Game actualVal = null;
		iter: for (int i = 1; i < game.squares_per_player; i++) {
			for (int dirIndex = 0; dirIndex < game.moveList.length; dirIndex++) {
				Game gameClone = new Game(game);
				if (gameClone.fm(player, i, dirIndex)) {
					if (val == Integer.MIN_VALUE) {
						gameClone.upperBound = Integer.MAX_VALUE;
					} else {
						gameClone.upperBound = maxPoint - val;
					}

					gameClone = minimax2(gameClone, (player + 1) % game.players, depth - 1);
					if (val == gameClone.eval[player]) {
						if (gameClone.scores[player] > actualVal.scores[player]) {
							actualVal = gameClone;
						}
					}

					if (val < gameClone.eval[player]) {
						val = gameClone.eval[player];
						actualVal = gameClone;
					}
					if (gameClone.eval[player] > game.upperBound) {
						lines.add("break " + gameClone.toString());
						break iter;
					}
//					if (gameClone.moveHistory.get(maxDepth - depth) == Game.skipped) {
//						break iter;
//					}
				}
			}
		}
		 addHistory(depth, actualVal);
		return actualVal;
	}

}

class Game {
	public static final int[] skipped = { -1, 0 };
	final int players;
	final int squares_per_player;
	int[] scores;
	int[] eval;
	int[] board;
	final int[] moveList;
	boolean isOver = false;
	List<int[]> moveHistory;
	int upperBound;

	public Game(Game game) {
		players = game.players;
		scores = game.scores.clone();
		eval = game.eval.clone();
		squares_per_player = game.squares_per_player;
		board = game.board.clone();
		moveList = game.moveList;
		moveHistory = new ArrayList<int[]>(game.moveHistory);
		upperBound = game.upperBound;
	}

	public Game(int players, int normal_start, int special_start, int squares_per_player, int[] moveList) {
		upperBound = Integer.MAX_VALUE;
		this.players = players;
		this.squares_per_player = squares_per_player;
		moveHistory = new ArrayList<int[]>();
		scores = new int[players];
		eval = new int[players];
		board = new int[players * squares_per_player];
		this.moveList = moveList;
		for (int i = 0; i < board.length; i++) {
			if (i % squares_per_player == 0) {
				board[i] = special_start;
			} else {
				board[i] = normal_start;
			}
		}
		for (int i = 0; i < players; i++) {
			eval[i] = ((squares_per_player - 1) * players * board[1] + players * board[0]);
		}
	}

	public Game(int players, int[] board, int[] moveList) {
		this.players = players;
		this.moveList = moveList;
		moveHistory = new ArrayList<int[]>();
		scores = new int[players];
		eval = new int[players];
		this.squares_per_player = board.length / players;
		this.board = board;
		double total = 0;
		for (int i1 = 0; i1 < board.length; i1++) {
			total += board[i1];
		}
		for (int i = 0; i < players; i++) {
			eval[i] += total;
		}
	}

	public void moveByArr(List<int[]> arr) {
		int player = 0;
		for (int i = 0; i < arr.size(); i++) {
			fm(player, arr.get(i)[0], arr.get(i)[1]);
			System.out.println(toString());
			player = (player + 1) % players;

		}
	}

	public String toString() {
		StringBuilder a = new StringBuilder(
				Arrays.toString(board) + " " + Arrays.toString(scores) + " " + Arrays.toString(eval) + " ");
		for (int i = 0; i < moveHistory.size(); i++) {
			a.append(Arrays.toString(moveHistory.get(i)));
		}

		return a.append(upperBound).toString();
	};

	public boolean fm(int player, int square, int dirIndex) {
		// player out of bounds
//		if (player < 0 || player > players) {
//			return false;
//		}
//		// move out of bounds
//		if (dirIndex < 0 || dirIndex > moveList.length) {
//			return false;
//		}
//		// square out of bounds
//		if (square < 1 || square > squares_per_player - 1) {
//			return false;
//		}
		// empty square
		if (board[player * squares_per_player + square] == 0) {
			for (int i = 1; i < squares_per_player; i++) {
				if (board[player * squares_per_player + i] != 0) {
					return false;
				}
			}
			if (scores[player] >= squares_per_player - 1) {
				int total = -squares_per_player + 1;
				scores[player] += total;
				for (int i = 1; i < squares_per_player; i++) {
					board[player * squares_per_player + i] = 1;
				}
				for (int i = 0; i < players; i++) {
					if (i == player) {
						eval[i] += total * (players - 1);
						continue;
					}
					eval[i] -= total;
				}

			} else {
				moveHistory.add(skipped);
				return true;
			}
		}
		// else actually allow the move
		move(player, player * squares_per_player + square, moveList[dirIndex]);

		moveHistory.add(new int[] { square, dirIndex });
//		System.out.println(this.toString());
		checkEnd();
		return true;
	}

	public void checkEnd() {
		for (int i = 0; i < players; i++) {
			if (board[(i * squares_per_player) % board.length] != 0) {
				return;
			}
		}
		isOver = true;
		for (int i = 0; i < players; i++) {
			int total = 0;
			for (int i1 = 1; i1 < squares_per_player; i1++) {
				total += board[i * squares_per_player + i1];
//				board[i * squares_per_player + i1] = 0;
			}
			scores[i] += total;
			for (int i3 = 0; i3 < players; i3++) {
				if (i3 == i) {
					eval[i3] += total * (players - 1);
					continue;
				}
				eval[i3] -= total;
			}
		}
	}

	public void move(int player, int square, int direction) {
		// basically next starting square w/ modding magic cause java sucks
		int next = (board.length + ((board[square] + Math.abs(direction)) * direction + square) % board.length)
				% board.length;

		// add pebbles to squares on path
		for (int i = board[square]; i > 0; i--) {
			board[square]--;
			board[(board.length + (square + direction * i) % board.length) % board.length]++;
//			System.out.println(this.toString() + " moving");
		}

		// capture condition
		if (board[next] == 0 && next % squares_per_player != 0) {
			capture(player, next, direction);
			return;
		}

		// blocked according to the rules
		if (next % squares_per_player == 0) {
//			System.out.println(this.toString() + " is blocked; done");
			return;
		}

		// recursive move
//		System.out.println(this.toString() + " moves again with square " + next);
		move(player, next, direction);
		return;
	}

	public void capture(int player, int square, int direction) {
		// the square that is being captured
		int index = (board.length + (square + direction) % board.length) % board.length;

		// the square right next to aforementioned captured square
		int next = (index + direction + board.length) % board.length;

		// if captured square is empty
		if (board[index] == 0) {
			// then no (more) captures
//			System.out.println(this.toString() + " no dice; done");
			return;
		}

		// add score accordingly
		scores[player] += board[index];
		for (int i = 0; i < players; i++) {
			if (i == player) {
				eval[i] += board[index] * (players - 1);
				continue;
			}
			eval[i] -= board[index];
		}

//		System.out.println(this.toString() + " capturing");
		// else empty it
		board[index] = 0;

		// if there's a recursive capture
		if (board[next] == 0 && next % squares_per_player != 0) {

			// then capture again but for the other square that is next to the captured one
			capture(player, next, direction);
			return;
		}

//		System.out.println(this.toString() + " done");
	}
}
