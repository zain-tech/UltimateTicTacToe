package uttt;

public class Game {
	
	private Board board;
	private Player player1;
	private Player player2;
	private Spectator spectator;
	private boolean isPlayer1Turn = true;
	private Move lastMove;
	
	
	public Game(Player player1, Player player2) {
		this(player1, player2, null, new Board());
	}
	
	public Game(Player player1, Player player2, Spectator spectator) {
		this(player1, player2, spectator, new Board());
	}
	
	public Game(Player player1, Player player2, Board board) {
		this(player1, player2, null, board);
	}
	
	public Game(Player player1, Player player2, Spectator spectator, Board board) {
		this.player1 = player1;
		this.player2 = player2;
		this.spectator = spectator;
		this.board = board;
	}
	

	public void play() {
		if(spectator != null) spectator.gameStarted(board);
		
		while(!(board.hasWinner() || board.isFull())) {
			playTurn();
		}
		
		if(spectator != null) spectator.gameOver(board);
	}
	

	public Move playTurn() {

		Player activePlayer;
		Player otherPlayer;
		if(isPlayer1Turn) {
			activePlayer = player1;
			otherPlayer = player2;
		} else {
			activePlayer = player2;
			otherPlayer = player1;
		}
		

		Move move = new Move(board, activePlayer, otherPlayer, lastMove);
		lastMove = move;
		int invalidMoves = 0;
		boolean succeeded = false;
		
		while(! succeeded) {
			try {
				activePlayer.makeMove(move);
				succeeded = true;
			} catch(InvalidMoveException e) { // If the player makes an invalid move
				System.out.println("Warning: Player attempted to make an invalid move");
				System.out.println("While the game is not affected, this may indicate a bug in the player's code");
				invalidMoves++;
				if(invalidMoves > 2) {
					System.out.println("Player turn terminated due to 3 invalid moves");
					break;
				}
				succeeded = false;
			} catch (MultipleMovesException e) {
				System.out.println("Warning: Player attempted to make more than one move");
				System.out.println("While the game is not affected, this may indicate a bug in the player's code");
				succeeded = true;
			}
		}
		

		if(spectator != null) spectator.moveMade(move);
		isPlayer1Turn = ! isPlayer1Turn;
		
		return move;
	}
	
	public Board getBoard() {
		return board;
	}
	
	
	public void setLastMove(Move move) {
		lastMove = move;
	}
}
