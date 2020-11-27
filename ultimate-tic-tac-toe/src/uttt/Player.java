package uttt;


public abstract class Player {
	protected String identifier; // A String, such as "X" or "O", that identifies this player
	
	public abstract void makeMove(Move move) throws InvalidMoveException, MultipleMovesException;
	
	public Player(String identifier) {
		this.identifier = identifier;
	}

	public final String getIdentifier() {
		return identifier;
	}
	
	
	public final Piece getPiece() {
		return new Piece(this);
	}
	
	
	@Override public final String toString() {
		return   getIdentifier(); 
	}
}
