package uttt;

import java.util.Scanner;


public class TerminalPlayer extends Player {
	Scanner scanner = new Scanner(System.in);
	
	public TerminalPlayer(String identifier) {
		super(identifier);
	}

	@Override public void makeMove(Move move) throws MultipleMovesException {
		System.out.print("Current Player is : ");
		System.out.println(identifier);
		
		if(move.hasRequiredBoard()) {
			System.out.print("AutoSelected board : ");
			System.out.println(move.getRequiredBoard());
			makeRestrictedMove(move);
		} else {
			System.out.println("Free to move anywhere on the board");
			makeFreeMove(move);
		}
	}
	
	// Make a move that must go on a specific board
	private void makeRestrictedMove(Move move) throws MultipleMovesException {
		System.out.println("Please select a valid square on the board(Press Enter to see valid moves): ");
		System.out.print("Selected Square: ");
		String line = scanner.nextLine();
		if(line.equals("")) { // Just pressing enter displays a list of valid moves
			displayRestrictedMoves(move);
			makeRestrictedMove(move);
			return;
		}
		try { // If they entered an invalid move, or didn't enter a number, try again
			move.makeMove(move.getRequiredBoard(), Integer.parseInt(line));
		} catch(InvalidMoveException e) {
			System.out.println("Invalid move! Please try again, or press enter for a list of valid moves. ");
			makeRestrictedMove(move);
		} catch(NumberFormatException e) {
			System.out.println("Please enter a valid move. You can press enter for a list. ");
			makeRestrictedMove(move);
		}
	}
	
	// Make a move that can go to any free space on the board
	private void makeFreeMove(Move move) throws MultipleMovesException {
		System.out.print("Enter your move (<board> <space>): ");
		String line = scanner.nextLine();
		if(line.equals("")) { // Just pressing enter displays a list of valid moves
			displayFreeMoves(move);
			makeFreeMove(move);
			return;
		}
		try { // Catch all manners of invalid input, as well as moves that go against the rules
			int board = Integer.parseInt(line.substring(0, 1));
			int space = Integer.parseInt(line.substring(2, 3));
			move.makeMove(board, space);
		} catch(InvalidMoveException e) {
			System.out.println("Invalid move! Please try again, or press enter for a list of valid moves. ");
			makeFreeMove(move);
		} catch(NumberFormatException e) {
			System.out.println("Please enter a valid move. You can press enter for a list. ");
			makeFreeMove(move);
		} catch(IndexOutOfBoundsException e) {
			System.out.println("Please enter a valid move. You can press enter for a list. ");
			makeFreeMove(move);
		}
	}
	
	// Display a list of available moves if the player must move to a certain board
	private void displayRestrictedMoves(Move move) {
		System.out.print("Valid moves are: ");
		for(int i = 0; i < 9; i++) {
			if(move.isValidMove(move.getRequiredBoard(), i)) {
				System.out.print(i);
				System.out.print("; ");
			}
		}
		System.out.println();
	}
	
	// Display a list of available moves if the player can move anywhere they like
	private void displayFreeMoves(Move move) {
		System.out.print("Valid moves are: ");
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(move.isValidMove(i, j)) {
					System.out.print(i);
					System.out.print(' ');
					System.out.print(j);
					System.out.print("; ");
				}
			}
		}
		System.out.println();
	}
}
