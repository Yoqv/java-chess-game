import java.util.ArrayList;

public class Evaluate implements Globals 
{
	private Main main;
	private final static int END_GAME_WEIGHT = QUEEN_WEIGHT + BISHOP_WEIGHT;
	
	public Evaluate(Main main)
	{
		this.main = main;
	}

	public boolean isEndgame(int playerWeight)
	{
		// Player weight does not include the king's weight
		return playerWeight <= END_GAME_WEIGHT; 
	}
	/**
	 * Returns true if the player's total
	 * weight is less than a queen and bishop weight
	 */
	
	public int evaluate(ChessColor maximizingColor)
	{
		int whiteEval = 0;
		int blackEval = 0;
		
		ArrayList<ChessPiece> whitePiecesArray = main.whitePiecesArray;
		ArrayList<ChessPiece> blackPiecesArray = main.blackPiecesArray;
		int whiteSumWeight = 0;
		boolean isWhiteEndgame = false;
		for(int i = KING_INDEX + 1 ; i < whitePiecesArray.size() ; i++) // Loop does not count king
		{
			ChessPiece whitePiece = whitePiecesArray.get(i);
			whiteSumWeight += whitePiece.getWeight();
			whiteEval += whitePiece.getWeight(); // Evaluate piece weight
			whiteEval += evaluatePiecePosition(whitePiece, isWhiteEndgame); // Evaluate piece position
		}
		
		isWhiteEndgame = isEndgame(whiteSumWeight);
		ChessPiece whiteKing = whitePiecesArray.get(KING_INDEX);
		whiteEval += whiteKing.getWeight();
		whiteEval += evaluatePiecePosition(whiteKing, isWhiteEndgame);
		
		int blackSumWeight = 0;
		boolean isBlackEndgame = false;
		for(int i = KING_INDEX + 1 ; i < blackPiecesArray.size() ; i++)
		{
			ChessPiece blackPiece = blackPiecesArray.get(i);
			blackSumWeight += blackPiece.getWeight();
			blackEval += blackPiece.getWeight(); // Evaluate piece weight
			blackEval += evaluatePiecePosition(blackPiece, isBlackEndgame); // Evaluate piece position
		}		
		
		isBlackEndgame = isEndgame(blackSumWeight);
		ChessPiece blackKing = blackPiecesArray.get(KING_INDEX);
		blackEval += blackKing.getWeight();
		blackEval += evaluatePiecePosition(blackKing, isBlackEndgame);
		
		if(maximizingColor == ChessColor.WHITE)
			return whiteEval - blackEval;
		else
			return blackEval - whiteEval;
	}
	/**
	 * Returns the evaluation of the board for the maximizing player
	 * (the evaluation is calculated by subtracting the opponent's
	 * score from the maximizing player's score)
	 */
	
	
	
	public int evaluatePiecePosition(ChessPiece chessPiece, boolean isEndgame)
	{
		int eval = 0;
		
		// If the piece is the human player's color then it goes from bottom to top
		// If the piece is the AI's color then it goes from top to bottom
		int direction = (chessPiece.getColor() == main.playerColor) ? HUMAN_PLAYER_DIRECTION : AI_DIRECTION;
		int pieceRow = chessPiece.getPosition().getRow();
		int pieceCol = chessPiece.getPosition().getCol();
		
		// If direction is from bottom to top then the row and col of the piece are the same as the in the map
		// If direction is from top to bottom then the row and col of the piece should be mirrored to get the right location in the map
		int mapRow = (direction == BOTTOM_TO_TOP) ? pieceRow : (BOARD_SIZE - 1) - pieceRow;
		int mapCol = (direction == BOTTOM_TO_TOP) ? pieceCol : (BOARD_SIZE - 1) - pieceCol;
		
		
		switch (chessPiece.getType()) 
		{
		case KING:
			if(isEndgame)
				eval = KING_MAP_END_GAME[mapRow][mapCol];
			else
				eval = KING_MAP_MID_GAME[mapRow][mapCol];
			break;
		case QUEEN: 
			eval = QUEEN_MAP[mapRow][mapCol];
			break;
		case PAWN: 
			eval = PAWN_MAP[mapRow][mapCol];
			break;
		case BISHOP: 
			eval = BISHOP_MAP[mapRow][mapCol];
			break;
		case KNIGHT: 
			eval = KNIGHT_MAP[mapRow][mapCol];
			break;
		case ROOK: 
			eval = ROOK_MAP[mapRow][mapCol];
			break;
		}
		
		return eval;
	}
	/**
	 * Returns evaluation for the position of the chessPiece
	 */
	
	

}
