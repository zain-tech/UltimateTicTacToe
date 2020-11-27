package uttt;

public class SmallBoard {
	// All combinations of spaces that can result in a win
	// This is duplicated between SmallBoard and Board because there's really no reason that they have 
	// to be the same, they just happen to be for this game
	public static final int[][] WIN_CONDITIONS = { 
		{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
		{0, 3, 6}, {1, 4, 7}, {2, 5, 8}, 
		{0, 4, 8}, {2, 4, 6}
	};
	
	// These are read-only because they can't meaningfully be modified later on
	private final int height;
	private final int width;
	
	// Space to store pieces as we receive them. See above for the coordinate system. 
	private Piece[] pieces;
	
	// Tracks any piece of the player who won first (all pieces with the same identifier 
	// are logically equivalent.)
	private Piece winner = null;
	
	// What condition this board was won by. Used to highlight winning spaces in display
	private int[] winningCondition;
	
	// Empty default constructor to initialize empty board
	public SmallBoard() {
		height = 3;
		width = 3;
		pieces = new Piece[height * width];
	}
	
	// Private constructor used when cloning the board
	private SmallBoard(int height, int width, Piece[] pieces) {
		this.height = height;
		this.width = width;
		this.pieces = pieces;
	}
	
	// Standard getters
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	// Get the piece at a given space, indexed 0 to 8
	public Piece getPiece(int space) {
		return pieces[space];
	}
	
	// True if the given space index is occupied, false otherwise
	public boolean isOccupied(int space) {
		return pieces[space] != null;
	}
	
	// Returns true if every square on this board is occupied, false otherwise
	public boolean isFull() {
		for(int i = 0; i < height * width; i++) {
			if(pieces[i] == null) {
				return false;
			}
		}
		return true;
	}
	
	// Called by Move when a player makes a move
	public void move(Piece piece, int space) throws InvalidMoveException {
		if(! isValidMove(space)) throw new InvalidMoveException();
		pieces[space] = piece;
		
		// Note that winner is recalculated each move, but only if there is no current 
		// winner. This is because once a player wins a board, they have won it forever. 
		// This is the only place that winner is set
		if(! hasWinner()) winner = calculateWinner();
	}
	
	// True if this board has a winner
	public boolean hasWinner() {
		return getWinner() != null;
	}
	
	// Get a piece belonging to the winner of this board. Note that in order to create safer 
	// code, the SmallBoard class is unable to access data about the Players playing on it. 
	// Instead, they are limited to interacting with the much simpler Pieces. 
	public Piece getWinner() {
		return winner;
	}
	
	// Returns true if the space given is available to be moved to
	public boolean isValidMove(int space) {
		// You cannot move to a space that isn't on the board, and you can't move to an 
		// occupied space. 
		return boundsCheck(space) && ! isOccupied(space);
	}
	
	// Check if the given space is actually on the board
	private boolean boundsCheck(int space) {
		return space >= 0 && space < getWidth() * getHeight();
	}
	
	// Calculate the winner and return one of their pieces. This method has not respect for 
	// who won first, and if more than one player has a winning condition, it will simply 
	// return whoever matched the earlier condition in the winConditions array. 
	private Piece calculateWinner() {
		// Check all conditions
		for(int[] condition : WIN_CONDITIONS) {
			// If there's no piece at the first index given by the condition, move on
			Piece checking = pieces[condition[0]];
			if(checking == null) continue;
			boolean winner = true;
			for(int i = 1; i < condition.length; i++) {
				// If the piece at any of the indices specified doesn't match the first
				if((pieces[condition[i]] == null) || (! pieces[condition[i]].equals(checking))) {
					winner = false;
					break;
				}
			}
			if(winner) {
				winningCondition = condition;
				return checking;
			}
		}
		return null;
	}

	// Return the condition that won this board
	public int[] getWinningCondition() {
		return winningCondition;
	}
	
	// Returns a deep clone of this board. Used by AIPlayer when running simulations. 
	@Override public SmallBoard clone() {
		Piece[] newPieces = new Piece[width * height];
		for(int i = 0; i < width * height; i++) {
			if(pieces[i] == null) {
				newPieces[i] = null;
			} else {
				newPieces[i] = pieces[i].clone();
			}
		}
		SmallBoard newBoard = new SmallBoard(height, width, newPieces);
		if(winner != null) newBoard.winner = winner.clone(); // Make sure that the winner remains the same
		// Note that cloning the pieces results in the cloned board having a winning piece that is not 
		// actually on the board. This is not a problem, though, because pieces are read-only and they 
		// all reference the same player. 
		
		newBoard.winningCondition = winningCondition;
		return newBoard;
	}
}
