package chess;

import java.util.List;

public abstract class Piece {
	Cell currentCell;
	public abstract List<Cell> getMoves();
	public abstract List<Cell> getCaptures();
	
	
}
