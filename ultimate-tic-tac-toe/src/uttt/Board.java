package uttt;


public class Board implements BoardState {
	// winConditions is here as well as SmallBoard to reduce dependency between the classes
	public static final int[][] WIN_CONDITIONS = {
			{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
			{0, 3, 6}, {1, 4, 7}, {2, 5, 8}, 
			{0, 4, 8}, {2, 4, 6}
	};
	
	private int width = 3;
	private int height = 3;
	private Piece winner;
	
	// Array of sub-boards
	private SmallBoard[] boards;
	
	// Populate the boards array, because they can't be initialized to null
	public Board() {
		boards = new SmallBoard[getWidth() * getHeight()];
		for(int i = 0; i < boards.length; i++) {
			boards[i] = new SmallBoard();
		}
	}
	
	private Board(SmallBoard[] boards) {
		this.boards = boards;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	// Whether or not this entire board has been won
	public boolean hasWinner() {
		return getWinner() != null;
	}

	// Whether or not the small board at the given index (0-8) has been won
	public boolean hasWinner(int board) {
		return boards[board].hasWinner();
	}

	// Get the winner of this entire board
	public Piece getWinner() {
		return winner;
	}

	// Get the winner of the board at the given index (0-8)
	public Piece getWinner(int board) {
		return boards[board].getWinner();
	}

	// Get the piece on a given space. See above for description of how spaces 
	// are referenced. 
	public Piece getPiece(int board, int space) {
		return boards[board].getPiece(space);
	}

	public boolean isOccupied(int board, int space) {
		return boards[board].isOccupied(space);
	}

	// Whether the entire board is full
	public boolean isFull() {
		for(SmallBoard board : boards) {
			if(! board.isFull()) return false;
		}
		return true;
	}

	// Whether the board at the given index is full
	public boolean isFull(int board) {
		return boards[board].isFull();
	}

	// Attempt to make a move. Throws InvalidMoveException if the move cannot be made. 
	// Also recalculates the winner after making the move
	public void move(Piece piece, int board, int space) throws InvalidMoveException {
		if(! isValidMove(board, space)) throw new InvalidMoveException();
		boards[board].move(piece, space);
		if(! hasWinner()) winner = calculateWinner();
	}

	// Returns true if the given move is valid, false otherwise
	public boolean isValidMove(int board, int space) {
		if(board < 0 || board >= (getWidth() * getHeight())) return false;
		return boards[board].isValidMove(space);
	}

	private Piece calculateWinner() {
		// Check each condition
		for(int[] condition : WIN_CONDITIONS) {
			// If there's no winner on the first board we are checking, move on
			if(! boards[condition[0]].hasWinner()) continue;
			// Save a piece of the player that may have won this condition
			Piece checking = boards[condition[0]].getWinner();
			boolean winner = true;
			// If even one space in the win condition isn't won by this player, move on
			for(int i = 1; i < condition.length; i++) {
				if((! boards[condition[i]].hasWinner()) || (! boards[condition[i]].getWinner().equals(checking))) {
					winner = false;
					break;
				}
			}
			if(winner) return checking;
		}
		// If no condition was satisfied, there's no winner
		return null;
	}

	@Override public int[] getWinningCondition(int board) {
		return boards[board].getWinningCondition();
	}
	
	@Override public Board clone() {
		SmallBoard[] newBoards = new SmallBoard[9];
		for(int i = 0; i < 9; i++) {
			newBoards[i] = boards[i].clone();
		}
		Board newBoard = new Board(newBoards);
		newBoard.width = width;
		newBoard.height = height;
		newBoard.winner = winner;
		return newBoard;
	}
}