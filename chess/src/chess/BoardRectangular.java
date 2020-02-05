package chess;

import java.util.LinkedList;
import java.util.List;

public class BoardRectangular implements Board {
	private Cell[][] board;
	private final int files, ranks;
	private List<Player> players = new LinkedList<Player>();

	public BoardRectangular(int ranks, int files) {
		board = new Cell[ranks][files];
		this.files = files;
		this.ranks = ranks;
	}

	public Cell getCell(int rank, int file) {
		if (rank > ranks || file > files || rank < 0 || files < 0) {
			return null;
		}
		return board[rank][file];
	}

	public Cell[][] getBoard() {
		return board;
	}

	public int getFiles() {
		return files;
	}

	public int getRanks() {
		return ranks;
	}

}
