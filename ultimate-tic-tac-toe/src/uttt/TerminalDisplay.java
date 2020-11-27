package uttt;

public class TerminalDisplay {
	public static String displayBoard(BoardState state) {
		StringBuilder builder = new StringBuilder();
		
		for(int row = 0; row <= 8; row++) {
			for(int col = 0; col <= 8; col++) {
				int board = ((row / 3) * 3) + (col / 3);
				int space = ((row % 3) * 3) + (col % 3);
				if(col==0)
				builder.append((col % 3 == 0) ? " Board#"+ board +" │" : '│');
				else
				builder.append((col % 3 == 0) ? "│ Board#"+ board +" │" : '│');
				
				if(state.isOccupied(board, space)) {
					boolean winningSpace = false;
					if(state.hasWinner(board)) {
						for(int i = 0; i < state.getWinningCondition(board).length; i++) {
							if(state.getWinningCondition(board)[i] == space) {
								winningSpace = true;
								break;
							}
						}
					}
					if(winningSpace) {
						builder.append('[');
						builder.append(state.getPiece(board, space).getIdentifier());
						builder.append(']');
					} else {
						builder.append(' ');
						builder.append(state.getPiece(board, space).getIdentifier());
						builder.append(' ');
					}
				} else {
					
					builder.append(" "+space+" ");
				}
			}
			builder.append("│\n");
			
		}
		for(int row = 0; row <= 8; row++) {
			
				if(state.hasWinner(row))
				{
					builder.append("The Board#"+row+" winner is "+state.getWinner(row)+"\n");
				}
			}	
		
		return builder.toString();
	}
}
