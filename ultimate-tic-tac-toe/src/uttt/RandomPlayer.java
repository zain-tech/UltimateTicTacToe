package uttt;

import java.util.Random;

/* 
 * A player designed for testing purposes that simply makes random 
 * moves. 
 */

public class RandomPlayer extends Player {
	
	public RandomPlayer(String identifier) {
		super(identifier);
	}

	Random random = new Random();
	
	@Override public void makeMove(Move move) throws InvalidMoveException, MultipleMovesException {
		int[] boards = new int[81];
		int[] spaces = new int[81];
		int n_moves = 0;

		// Compile a list of valid moves
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
		
		int moveIndex = random.nextInt(n_moves);
		move.makeMove(boards[moveIndex], spaces[moveIndex]);
	}
}
