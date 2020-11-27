package uttt;


public abstract class Spectator {
	
	
	public abstract void gameStarted(BoardState board);
	public abstract void moveMade(Move move);
	public abstract void gameOver(BoardState board);
}
