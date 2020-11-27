package uttt;
public class Move implements BoardState {
	
	private Player activePlayer;
	private Player otherPlayer;
	private Board board;
	private Move lastMove;
	

	protected boolean isMade = false;
	protected int moveBoard;
	protected int moveSpace;
	
	public Move(Board board, Player activePlayer, Player otherPlayer) {
		this.board = board;
		this.activePlayer = activePlayer;
		this.otherPlayer = otherPlayer;
	}
	
	// lastMove is important when a player must move on a particular board
	public Move(Board board, Player activePlayer, Player otherPlayer, Move lastMove) {
		this(board, activePlayer, otherPlayer);
		this.lastMove = lastMove;
	}
	
	// Get the identifier of the active player
	public String getActiveIdentifier() {
		return activePlayer.getIdentifier();
	}
	
	// Get the other player's identifier
	public String getOtherIdentifier() {
		return otherPlayer.getIdentifier();
	}
	
	// Make a move, but not if this object has already been used to make a move
	public void makeMove(int board, int space) throws MultipleMovesException, InvalidMoveException {
		// Don't let the player make multiple moves
		if(isMade()) throw new MultipleMovesException();
		
		// Don't let the player move anywhere other than the required board
		if(hasRequiredBoard() && board != getRequiredBoard()) throw new InvalidMoveException(); 
		
		this.board.move(activePlayer.getPiece(), board, space);
		moveBoard = board;
		moveSpace = space;
		isMade = true;
	}
	

	public boolean hasRequiredBoard() {
		return (lastMove != null) && (! board.isFull(getRequiredBoard()));
	}
	

	public int getRequiredBoard() {
		if(lastMove == null) return -1;
		return lastMove.getSpace();
	}
	

	public boolean isMade() {
		return isMade;
	}
	

	public int getBoard() {
		if(! isMade()) throw new MoveNotMadeException();
		return moveBoard;
	}
	
	
	public int getSpace() {
		if(! isMade()) throw new MoveNotMadeException();
		return moveSpace;
	}
	
	public Move getLastMove() {
		return lastMove;
	}
	
	
	public boolean isValidMove(int board, int space) {
		if(isMade()) return false;
		if(hasRequiredBoard() && board != getRequiredBoard()) return false;
		return this.board.isValidMove(board, space);
	}
	
	
	public boolean hasWinner() {
		return this.board.hasWinner();
	}
	
	public Piece getWinner() {
		return this.board.getWinner();
	}
	
	public boolean hasWinner(int board) {
		return this.board.hasWinner(board);
	}
	
	public Piece getWinner(int board) {
		return this.board.getWinner(board);
	}
	
	public Piece getPiece(int board, int space) {
		return this.board.getPiece(board, space);
	}
	
	public boolean isFull(int board) {
		return this.board.isFull(board);
	}
	
	public boolean isOccupied(int board, int space) {
		return this.board.isOccupied(board, space);
	}

	@Override public int[] getWinningCondition(int board) {
		return this.board.getWinningCondition(board);
	}
	
	@Override public boolean isFull() {
		return board.isFull();
	}
	
	public Board getBoardClone() {
		return board.clone();
	}
}
