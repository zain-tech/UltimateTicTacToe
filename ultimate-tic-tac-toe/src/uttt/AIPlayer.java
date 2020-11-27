package uttt;

public class AIPlayer extends Player {
	
	private int intelligence = 100000; // Total number of simulations to run each iteration
	public AIPlayer(String identifier) {
		super(identifier);
	}
	
	public AIPlayer(String identifier, boolean verbose) {
		super(identifier);
	}
	
	public AIPlayer(String identifier, int intelligence) {
		super(identifier);
		this.intelligence = intelligence;
	}
	
	public AIPlayer(String identifier, int intelligence, boolean verbose) {
		super(identifier);
		this.intelligence = intelligence;
	}

	@Override public void makeMove(Move move) throws MultipleMovesException, InvalidMoveException {
		int[] boards = new int[81];
		int[] spaces = new int[81];
		int n_moves = 0;
		
		if(! move.hasRequiredBoard()) {
			for(int board = 0; board < 9; board++) {
				for(int space = 0; space < 9; space++) {
					if(move.isValidMove(board, space)) {
						boards[n_moves] = board;
						spaces[n_moves] = space;
						n_moves++;
					}
				}
			}
		} else {
			int board = move.getRequiredBoard();
			for(int space = 0; space < 9; space++) {
				if(move.isValidMove(board, space)) {
					boards[n_moves] = board;
					spaces[n_moves] = space;
					n_moves++;
				}
			}
		}
		
		if(n_moves == 1) {
			
			System.out.println("Current Player is : "+identifier);
			System.out.println("Selected Board : "+boards[0]);
			System.out.println("Please select a valid square on the selected board:");
			System.out.println("Selected Square : "+spaces[0]);
			move.makeMove(boards[0], spaces[0]);
			return;
		} else if(n_moves == 0) {
			throw new RuntimeException("makeMove called with no valid moves");
		}
		
		int bestScore = intelligence * (-2);
		int bestMove = -1;
		for(int i = 0; i < n_moves; i++) {
			int score = scoreMove(boards[i], spaces[i], move, intelligence/n_moves);
			if(score > bestScore) {
				bestScore = score;
				bestMove = i;
			}
		}
		
		
		//double confidence = (((double)bestScore)/(((double)intelligence)/((double)n_moves))) * 100.0;
		//if(verbose) System.out.println("[" + getIdentifier() + "] Making move with a score of "
				//+ bestScore + " / " + (intelligence/n_moves) + " (" + confidence + "%)");
		
		// Make the move with the highest score
		System.out.println("Current Player is : "+identifier);
		System.out.println("Selected Board : "+boards[bestMove]);
		System.out.println("Please select a valid square on the selected board:");
		System.out.println("Selected Square : "+spaces[bestMove]);
		move.makeMove(boards[bestMove], spaces[bestMove]);
	}
	
	private int scoreMove(int board, int space, Move move, int tries) throws InvalidMoveException, MultipleMovesException {
		Player me = new RandomPlayer(move.getActiveIdentifier());
		Player them = new RandomPlayer(move.getOtherIdentifier());
		int score = 0;
		for(int i = 0; i < tries; i++) {
			Game testGame = new Game(them, me, move.getBoardClone());
			Move myMove = new Move(testGame.getBoard(), me, them);
			myMove.makeMove(board, space);
			testGame.setLastMove(myMove);
			testGame.play();
			if(testGame.getBoard().hasWinner()) {
				if(testGame.getBoard().getWinner().belongsTo(me)) {
					score++;
				} else if(testGame.getBoard().getWinner().belongsTo(them)) {
					score--;
				} else {
					throw new RuntimeException("Winning piece doesn't belong to either player");
				}
			}
		}
		return score;
	}
}
