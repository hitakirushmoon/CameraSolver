package chess;

public class Cell {
	int x, y;
	Piece currentPiece;
	Board currentBoard;
	public Cell(int x, int y, Piece currentPiece, Board currentBoard) {
		this.x = x;
		this.y = y;
		this.currentPiece = currentPiece;
		this.currentBoard = currentBoard;
	}
}
