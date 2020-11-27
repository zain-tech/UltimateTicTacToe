package uttt;

public final class Piece { // Do not extend, especially to make it non-read-only as this will mess with SmallBoard.clone()
	
	private final Player owner;
	
	public Piece(Player owner) {
		this.owner = owner;
	}
	
	@Override public boolean equals(Object other) {
		if(!(other instanceof Piece)) return false;
		return this.owner.getIdentifier().equals(((Piece) other).owner.getIdentifier());
	}
	
	@Override public String toString() {
		return owner.getIdentifier(); 
	}
	
	public String getIdentifier() {
		return owner.getIdentifier();
	}
	
	public boolean belongsTo(Player player) {
		return owner.getIdentifier().equals(player.getIdentifier());
	}
	
	public Piece clone() {
		return new Piece(owner);
	}
}
