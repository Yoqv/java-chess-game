import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

public class Main implements Globals
{
	public BoardButton[][] buttonsMatrix; // 8x8 matrix each cell contains the GUI's buttons
	public BoardButton selectedPieceButton; // Button selected by player (null if no button is selected)
	public ChessPiece[][] chessBoardMatrix; // 8x8 matrix each cell contains a Chess Piece (cell can be null if there is no piece in this row and column index)
	
	public boolean isPlayerTurn; // true when it is the player turn to make a move, else false
	public ChessColor playerColor; // player's color
	public ArrayList<ChessPiece> whitePiecesArray; // each cell contains a White ChessColor Chess Piece 
	public ArrayList<ChessPiece> blackPiecesArray; // each cell contains a Black ChessColor Chess Piece 

	public JLabel gameInfoLabel; // JLabel with info (The text says whose turn is it or if there is checkmate)
	public ChessAI chessAI; // Chess AI used to make the AI move
	Stack<Move> movesStack; // Stack contains all of the moves that were made in the game
	
	public boolean isValidPosition(int row, int col)
	{
		if(row < 0 || row >= BOARD_SIZE)
			return false;
		if(col < 0 || col >= BOARD_SIZE)
			return false;
		
		return true;
	}
	/**
	 * Return true if the row and column are in the board range
	 * Else returns false
	 */
	

	public boolean isPositionEmpty(int row, int col)
	{
		return chessBoardMatrix[row][col] == null;
	}
	/**
	 * Return true if there is no chess piece in this row and col
	 * Else returns false
	 */
	

	public void addPawnMoves(PositionOnBoard pawnPosition, LinkedList<Move> movesList)
	{
		final int pawnRow = pawnPosition.getRow();
		final int pawnCol = pawnPosition.getCol();
		ChessPiece pawnPiece = chessBoardMatrix[pawnRow][pawnCol];
		int destRow, destCol;
		ChessColor moveMakerColor = chessBoardMatrix[pawnRow][pawnCol].getColor();
		
		// if player color is white, the white pawns go from bottom to top and the black pawns go from top to bottom
		// if player color is black, the black pawns go from bottom to top and the white pawns go from top to bottom
		final int WHITE_PAWNS_ROW = (playerColor == ChessColor.WHITE) ? BOTTOM_PAWNS_ROW : TOP_PAWNS_ROW;
		final int BLACK_PAWNS_ROW = (playerColor == ChessColor.WHITE) ? TOP_PAWNS_ROW : BOTTOM_PAWNS_ROW;
		int direction = (moveMakerColor == ChessColor.WHITE && playerColor == ChessColor.WHITE) || (moveMakerColor == ChessColor.BLACK && playerColor == ChessColor.BLACK) ? BOTTOM_TO_TOP : TOP_TO_BOTTOM;
		
		destRow = pawnRow + 1 * direction;
		destCol = pawnCol;
		if(isValidPosition(destRow, destCol) && isPositionEmpty(destRow, destCol))
		{
			movesList.add(new Move(pawnPiece, destRow, destCol, this));
		
			destRow = pawnRow + 2 * direction;
			if(isValidPosition(destRow, destCol) && isPositionEmpty(destRow, destCol))
			{
				if(moveMakerColor == ChessColor.WHITE && pawnRow == WHITE_PAWNS_ROW)
					movesList.add(new Move(pawnPiece, destRow, destCol, this));
				if(moveMakerColor == ChessColor.BLACK && pawnRow == BLACK_PAWNS_ROW)
					movesList.add(new Move(pawnPiece, destRow, destCol, this));
			}
		}
			
		destRow = pawnRow + 1 * direction;
		destCol = pawnCol + 1;
		if(isValidPosition(destRow, destCol) && !isPositionEmpty(destRow, destCol) && chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor)
			movesList.add(new Move(pawnPiece, destRow, destCol, this));
		
		destRow = pawnRow + 1 * direction;
		destCol = pawnCol - 1;
		if(isValidPosition(destRow, destCol) && !isPositionEmpty(destRow, destCol) && chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor)
			movesList.add(new Move(pawnPiece, destRow, destCol, this));
	}
	/**
	 * Gets a position of a pawn.
	 * The function adds the pawn moves to movesList
	 */
	
	
	public void addRookMoves(PositionOnBoard rookPosition, LinkedList<Move> movesList)
	{
		final int rookRow = rookPosition.getRow();
		final int rookCol = rookPosition.getCol();
		ChessPiece rookPiece = chessBoardMatrix[rookRow][rookCol];
		ChessColor moveMakerColor = chessBoardMatrix[rookRow][rookCol].getColor();
		int destRow, destCol;
		
		destCol = rookCol;
		for(destRow = rookRow - 1 ; isValidPosition(destRow, destCol) ; destRow--)
		{
			if(isPositionEmpty(destRow, destCol))
				movesList.add(new Move(rookPiece, destRow, destCol, this));
			else
			{
				if(chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor)
					movesList.add(new Move(rookPiece, destRow, destCol, this));
				
				break;
			}

		}
		
		for(destRow = rookRow + 1 ; isValidPosition(destRow, destCol) ; destRow++)
		{
			if(isPositionEmpty(destRow, destCol))
				movesList.add(new Move(rookPiece, destRow, destCol, this));
			else
			{
				if(chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor)
					movesList.add(new Move(rookPiece, destRow, destCol, this));
				
				break;
			}

		}
		
		destRow = rookRow;
		for(destCol = rookCol - 1 ; isValidPosition(destRow, destCol) ; destCol--)
		{
			if(isPositionEmpty(destRow, destCol))
				movesList.add(new Move(rookPiece, destRow, destCol, this));
			else
			{
				if(chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor)
					movesList.add(new Move(rookPiece, destRow, destCol, this));
				
				break;
			}
		}
		
		for(destCol = rookCol + 1 ; isValidPosition(destRow, destCol) ; destCol++)
		{
			if(isPositionEmpty(destRow, destCol))
				movesList.add(new Move(rookPiece, destRow, destCol, this));
			else
			{
				if(chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor)
					movesList.add(new Move(rookPiece, destRow, destCol, this));
				
				break;
			}
		}

	}
	/**
	 * Gets a position of a rook.
	 * The function adds the rook moves to movesList
	 */
	
	
	public void addKnightMoves(PositionOnBoard knightPosition, LinkedList<Move> movesList)
	{
		final int knightRow = knightPosition.getRow();
		final int knightCol = knightPosition.getCol();
		ChessPiece knightPiece = chessBoardMatrix[knightRow][knightCol];
		ChessColor moveMakerColor = chessBoardMatrix[knightRow][knightCol].getColor();
		int destRow, destCol;
		
		destRow = knightRow - 2;
		destCol = knightCol - 1;
		if(isValidPosition(destRow, destCol) && (isPositionEmpty(destRow, destCol)  || chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor))
			movesList.add(new Move(knightPiece, destRow, destCol, this));
		destRow = knightRow + 2;
		destCol = knightCol - 1;
		if(isValidPosition(destRow, destCol) && (isPositionEmpty(destRow, destCol) || chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor))
			movesList.add(new Move(knightPiece, destRow, destCol, this));
		destRow = knightRow - 2;
		destCol = knightCol + 1;
		if(isValidPosition(destRow, destCol) && (isPositionEmpty(destRow, destCol) || chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor))
			movesList.add(new Move(knightPiece, destRow, destCol, this));
		destRow = knightRow + 2;
		destCol = knightCol + 1;
		if(isValidPosition(destRow, destCol) && (isPositionEmpty(destRow, destCol) || chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor))
			movesList.add(new Move(knightPiece, destRow, destCol, this));
		
		destRow = knightRow - 1;
		destCol = knightCol - 2;
		if(isValidPosition(destRow, destCol) && (isPositionEmpty(destRow, destCol) || chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor))
			movesList.add(new Move(knightPiece, destRow, destCol, this));
		destRow = knightRow + 1;
		destCol = knightCol - 2;
		if(isValidPosition(destRow, destCol) && (isPositionEmpty(destRow, destCol) || chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor))
			movesList.add(new Move(knightPiece, destRow, destCol, this));
		destRow = knightRow - 1;
		destCol = knightCol + 2;
		if(isValidPosition(destRow, destCol) && (isPositionEmpty(destRow, destCol) || chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor))
			movesList.add(new Move(knightPiece, destRow, destCol, this));
		destRow = knightRow + 1;
		destCol = knightCol + 2;
		if(isValidPosition(destRow, destCol) && (isPositionEmpty(destRow, destCol) || chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor))
			movesList.add(new Move(knightPiece, destRow, destCol, this));
		
	}
	/**
	 * Gets a position of a knight.
	 * The function adds the knight moves to movesList
	 */
	
	public void addBishopMoves(PositionOnBoard bishopPosition, LinkedList<Move> movesList)
	{
		final int bishopRow = bishopPosition.getRow();
		final int bishopCol = bishopPosition.getCol();
		ChessPiece bishopPiece = chessBoardMatrix[bishopRow][bishopCol];
		ChessColor moveMakerColor = chessBoardMatrix[bishopRow][bishopCol].getColor();
		int destRow, destCol;
		destRow = bishopRow + 1;
		destCol = bishopCol + 1;
		while(isValidPosition(destRow, destCol))
		{
			if(isPositionEmpty(destRow, destCol))
				movesList.add(new Move(bishopPiece, destRow, destCol, this));
			else
			{
				if(chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor)
					movesList.add(new Move(bishopPiece, destRow, destCol, this));
				
				break;
			}
			destRow++;
			destCol++;
		}
		
		destRow = bishopRow - 1;
		destCol = bishopCol + 1;
		while(isValidPosition(destRow, destCol))
		{
			if(isPositionEmpty(destRow, destCol))
				movesList.add(new Move(bishopPiece, destRow, destCol, this));
			else
			{
				if(chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor)
					movesList.add(new Move(bishopPiece, destRow, destCol, this));
				
				break;
			}
			destRow--;
			destCol++;
		}
		
		destRow = bishopRow + 1;
		destCol = bishopCol - 1;
		while(isValidPosition(destRow, destCol))
		{
			if(isPositionEmpty(destRow, destCol))
				movesList.add(new Move(bishopPiece, destRow, destCol, this));
			else
			{
				if(chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor)
					movesList.add(new Move(bishopPiece, destRow, destCol, this));
				
				break;
			}
			destRow++;
			destCol--;
		}
		
		destRow = bishopRow - 1;
		destCol = bishopCol - 1;
		while(isValidPosition(destRow, destCol))
		{
			if(isPositionEmpty(destRow, destCol))
				movesList.add(new Move(bishopPiece, destRow, destCol, this));
			else
			{
				if(chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor)
					movesList.add(new Move(bishopPiece, destRow, destCol, this));
				
				break;
			}
			destRow--;
			destCol--;
		}
	}
	/**
	 * Gets a position of a bishop.
	 * The function adds the bishop moves to movesList
	 */
	
	
	public void addQueenMoves(PositionOnBoard queenPosition, LinkedList<Move> movesList)
	{
		// queen's movesList is made of combination of bishop and rook moves
		addBishopMoves(queenPosition, movesList);
		addRookMoves(queenPosition, movesList);
	}
	/**
	 * Gets a position of a queen.
	 * The function adds the queen moves to movesList
	 */
	
	public void addKingMoves(PositionOnBoard kingPosition, LinkedList<Move> movesList) 
	{
		final int kingRow = kingPosition.getRow();
		final int kingCol = kingPosition.getCol();
		ChessPiece kingPiece = chessBoardMatrix[kingRow][kingCol];
		ChessColor moveMakerColor = chessBoardMatrix[kingRow][kingCol].getColor();
		int destRow, destCol;

		for (destRow = kingRow - 1; destRow <= kingRow + 1; destRow++) 
		{
			for (destCol = kingCol - 1; destCol <= kingCol + 1; destCol++) 
			{
				if (isValidPosition(destRow, destCol)) {
					if (isPositionEmpty(destRow, destCol))
						movesList.add(new Move(kingPiece, destRow, destCol, this));
					else if (chessBoardMatrix[destRow][destCol].getColor() != moveMakerColor)
						movesList.add(new Move(kingPiece, destRow, destCol, this));
				}
			}
		}
	}
	/**
	 * Gets a position of a king.
	 * The function adds the king moves to movesList
	 */
	
	
	public void addCastlingMoves(PositionOnBoard kingPosition, LinkedList<Move> movesList) 
	{
		final int kingRow = kingPosition.getRow();
		final int kingCol = kingPosition.getCol();
		ChessPiece kingPiece = chessBoardMatrix[kingRow][kingCol];
		ChessColor moveMakerColor = chessBoardMatrix[kingRow][kingCol].getColor();
		int destRow, destCol;
		
		if (!chessBoardMatrix[kingRow][kingCol].hasMoved()) // If king was never moved
		{
			ChessPiece rightPiece = chessBoardMatrix[kingRow][RIGHT_COLUMN_INDEX];
			ChessPiece leftPiece =  chessBoardMatrix[kingRow][LEFT_COLUMN_INDEX];
			
			boolean isRightCastlingPossible = true;
			boolean isLeftCastlingPossible = true;
			
			/*
			 * If the most right sided or left sided pieces is null
			 * or it was moved one time or more then castling is 
			 * not possible
			 */
			if(rightPiece == null || rightPiece.hasMoved()) 
				isRightCastlingPossible = false;
			if(leftPiece == null || leftPiece.hasMoved())
				isLeftCastlingPossible = false;
			
			
			for (int col = kingCol + 1; isRightCastlingPossible && col < RIGHT_COLUMN_INDEX ; col++) // if all the right cells of the king are empty except the rook
			{
				if (!isPositionEmpty(kingRow, col)) 
					isRightCastlingPossible = false;	
			}

			for (int col = kingCol - 1; isLeftCastlingPossible && col > LEFT_COLUMN_INDEX ; col--) // if all the left cells of the king are empty except the rook
			{
				if (!isPositionEmpty(kingRow, col))
					isLeftCastlingPossible = false;
			}

			if ((isRightCastlingPossible || isLeftCastlingPossible) && !isKingInDanger(moveMakerColor)) 
			{
				if(isRightCastlingPossible) // Right castling:
				{
					destRow = kingRow;
					destCol = kingCol + 1;
					Move tempRightMove = new Move(kingPiece, destRow, destCol, this);
					// Temporarily move king 1 step the right to check if it's still not in danger
					makeMove(tempRightMove);
					if (!isKingInDanger(moveMakerColor)) 
					{
						// Right castling
						// Unmake move because when making the new castling move the piece in chessBoardMatrix in kingPosition is null if move is not unmade
						unmakeMove(tempRightMove); 
						destRow = kingRow;
						destCol = kingCol + 2;
						Move castlingMove = new Move(kingPiece, destRow, destCol, this);
						castlingMove.setCastling();
						movesList.add(castlingMove);
					}
					else 
					{
						// Unmake the temporarily move if move wasn't unmade yet
						unmakeMove(tempRightMove);
					}
				}
				
				if(isLeftCastlingPossible) // Left castling:
				{
					destRow = kingRow;
					destCol = kingCol - 1;
					Move tempLeftMove = new Move(kingPiece, destRow, destCol, this);
					// Temporarily move king 1 step the left to check if it's still not in danger
					makeMove(tempLeftMove);
					if (!isKingInDanger(moveMakerColor)) 
					{
						// Left castling:
						// Unmake move because when making the new castling move the piece in chessBoardMatrix in kingPosition is null if move is not unmade
						unmakeMove(tempLeftMove);
						destRow = kingRow;
						destCol = kingCol - 2;
						Move castlingMove = new Move(kingPiece, destRow, destCol, this);
						castlingMove.setCastling();
						movesList.add(castlingMove);
					}
					else
					{
						// Unmake the temporarily move if move wasn't unmade yet
						unmakeMove(tempLeftMove);
					}
				}
			}
		}	
	}
	/**
	 * Gets a position of a king.
	 * The function adds the legal possible castling moves to movesList
	 */
	
	public void addPieceMoves(PositionOnBoard piecePosition, LinkedList<Move> movesList)
	{
		int pieceRow = piecePosition.getRow();
		int pieceCol = piecePosition.getCol();
		
		PieceType pieceType = chessBoardMatrix[pieceRow][pieceCol].getType();
		switch(pieceType)
		{
		case KING: 
			addKingMoves(piecePosition, movesList);
			break;
		case QUEEN: 
			addQueenMoves(piecePosition, movesList);
			break;
		case PAWN: 
			addPawnMoves(piecePosition, movesList);
			break;
		case BISHOP: 
			addBishopMoves(piecePosition, movesList);
			break;
		case KNIGHT:
			addKnightMoves(piecePosition, movesList);
			break;
		case ROOK: 
			addRookMoves(piecePosition, movesList);
			break;
		}
		
	}
	/**
	 * Gets a position of a chess piece.
	 * The function adds the piece moves to movesList
	 */
	
	
	public boolean isKingInDanger(ChessColor kingColor)
	{
		ChessColor opponentColor = (kingColor == ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.WHITE;
		ArrayList<ChessPiece> friendlyPiecesArray = (kingColor == ChessColor.WHITE) ? whitePiecesArray : blackPiecesArray;
		
		ChessPiece kingPiece = friendlyPiecesArray.get(KING_INDEX);
			
		LinkedList<Move> opponentMovesList = generateMoves(opponentColor, true);
		for(Move move: opponentMovesList)
		{
			if(move.getCapturedPiece() == kingPiece)
				return true;
		}
		
		return false;
	}
	/**
	 * Gets a color, returns true if the king in this color
	 * is in danger else returns false
	 */
	

	public LinkedList<Move> removeKingDangerMoves(ChessColor moveMakerColor, LinkedList<Move> movesList)
	{
		LinkedList<Move> legalMovesList = new LinkedList<>();
		
		for(Move move : movesList)
		{			
			makeMove(move);
			
			if(!isKingInDanger(moveMakerColor))
			{
				legalMovesList.add(move);
			}
			
			unmakeMove(move);
		}
		
		return legalMovesList;
	}
	/**
	 * Gets movesList with all possible moves and returns new list without
	 * all of the illegal moves that will put the king in danger
	 */
	

	public void makeMove(Move move)
	{	
		PositionOnBoard startPosition = move.getStartPosition();
		PositionOnBoard destPosition = move.getDestPosition();
		int startRow = startPosition.getRow();
		int destRow = destPosition.getRow();
		int destCol = destPosition.getCol();
		ChessPiece capturedPiece = move.getCapturedPiece();
		ChessPiece pieceToMove = move.getMovingPiece(); // The moving piece
		ChessColor moveMakerColor = pieceToMove.getColor();
		ArrayList<ChessPiece> opponentPiecesArray = (moveMakerColor == ChessColor.WHITE) ? blackPiecesArray : whitePiecesArray;
		
		pieceToMove.moveTo(destRow, destCol, chessBoardMatrix, buttonsMatrix, false); // Move piece	
		
		if(move.isPromotion())
		{
			pieceToMove.promote(chessBoardMatrix, buttonsMatrix);
		}
		
		if(move.isCastling()) // Move the castling rook too in case this is a castling move
		{
			boolean isRightCastling = (destPosition.getCol() > startPosition.getCol());
			int rookStartRow = startRow;
			int rookStartCol = isRightCastling ? RIGHT_COLUMN_INDEX : LEFT_COLUMN_INDEX;
			int rookDestRow = destRow;
			int rookDestCol = isRightCastling ? destCol - 1 : destCol + 1;
			ChessPiece castlingRook = chessBoardMatrix[rookStartRow][rookStartCol];
			castlingRook.moveTo(rookDestRow, rookDestCol, chessBoardMatrix, buttonsMatrix, false); // Move castling rook
		}		
		
		if(move.isCaptured())
		{
			opponentPiecesArray.remove(capturedPiece);
		}
	}
	/**
	 * Makes the piece move from startPosition to destPosition,
	 * If the move is a castling move also moving the castling rook 
	 * removing the piece that was in destPosition from
	 * opponent pieces array
	 */
	
	public void unmakeMove(Move move)
	{
		PositionOnBoard startPosition = move.getStartPosition();
		PositionOnBoard destPosition = move.getDestPosition();
		int startRow = startPosition.getRow();
		int startCol = startPosition.getCol();
		int destRow = destPosition.getRow();
		int destCol = destPosition.getCol();
		ChessPiece capturedPiece = move.getCapturedPiece();
		Icon capturedPieceIcon = move.getCapturedPieceIcon();
		ChessPiece pieceToMoveBack = move.getMovingPiece(); // The piece that was moved
		ChessColor moveMakerColor = pieceToMoveBack.getColor();
		ArrayList<ChessPiece> opponentPiecesArray = (moveMakerColor == ChessColor.WHITE) ? blackPiecesArray : whitePiecesArray;
		
		pieceToMoveBack.moveTo(startRow, startCol, chessBoardMatrix, buttonsMatrix, true); // Move piece back
		chessBoardMatrix[destRow][destCol] = capturedPiece; // Set captured piece back in destination before move was done
		buttonsMatrix[destRow][destCol].setIcon(capturedPieceIcon); // Set captured piece's icon in destination before move was done
		
		if(move.isPromotion())
		{
			pieceToMoveBack.unpromote(chessBoardMatrix, buttonsMatrix);	
		}
		
		if(move.isCastling()) // Move the castling rook back too in case this was a castling move
		{
			boolean isRightCastling = (destPosition.getCol() - startPosition.getCol() > 1);
			int rookStartRow = startRow;
			int rookStartCol = isRightCastling ? RIGHT_COLUMN_INDEX : LEFT_COLUMN_INDEX;
			int rookDestRow = destRow;
			int rookDestCol = isRightCastling ? destCol - 1 : destCol + 1;
			ChessPiece castlingRook = chessBoardMatrix[rookDestRow][rookDestCol];

			castlingRook.moveTo(rookStartRow, rookStartCol, chessBoardMatrix, buttonsMatrix, true);
		}	
		
		if(move.isCaptured()) // If there is a piece which was captured
		{	
			if(capturedPiece.getType() == PieceType.KING) // If the piece is king add it at KING_INDEX (index 0)
				opponentPiecesArray.add(KING_INDEX, capturedPiece);
			else
				opponentPiecesArray.add(capturedPiece);
		}
	}
	/**
	 * Unmakes the move (piece move back to startPosition from destPosition, 
	 * if the move is a castling move also moving the castling rook back,
	 * adding the captured piece that was in destPosition before move was done to
	 * opponent pieces array and chessBoardMatrix, adds the captured piece's
	 * icon in buttonsMatrix)
	 */
	
		
	
	public LinkedList<Move> generateMoves(ChessColor moveMakerColor, boolean isGeneratingOpponentMoves)
	{
		LinkedList<Move> movesList = new LinkedList<>();
		ArrayList<ChessPiece> friendlyPiecesArray = (moveMakerColor == ChessColor.WHITE) ? whitePiecesArray : blackPiecesArray;
		
		for(int i = 0 ; i < friendlyPiecesArray.size() ; i++)
		{
			ChessPiece currentPiece = friendlyPiecesArray.get(i);
			PositionOnBoard piecePosition = currentPiece.getPosition();
			addPieceMoves(piecePosition, movesList);
		}
		
		if(!isGeneratingOpponentMoves)
		{
			ChessPiece kingPiece = friendlyPiecesArray.get(KING_INDEX);
			PositionOnBoard kingPosition = kingPiece.getPosition();
			addCastlingMoves(kingPosition, movesList);
			movesList = removeKingDangerMoves(moveMakerColor, movesList);
		}

		return movesList;
	}
	/**
	 * Gets color of the move maker and a boolean value that is true if it should remove the king danger moves.
	 * (it shouldn't remove the king danger moves when calling this function in removeKingDangerMoves
	 * to get the opponent moves). returns all of the possible moves for the player that their color is the moveMakerColor
	 */
	

	
	public boolean checkAndDisplayGameOver(ChessColor moveMakerColor)
	{
		boolean isGameOver = false;
		
		if(generateMoves(moveMakerColor, false).size() == 0) // If the current move maker can't make any moves
		{
			if(isKingInDanger(moveMakerColor)) // If current move maker king is in danger then it's a check mate (move maker wins)
			{
				if(moveMakerColor == playerColor)
					gameInfoLabel.setText("Check mate! You lose.");
				else
					gameInfoLabel.setText("Check mate! You win.");
			}
			else // If current move maker king is not in danger then it's draw
			{
				gameInfoLabel.setText("Draw.");
			}
			
			isGameOver = true;
		}
		return isGameOver;
	}
	/**
	 * The function is being called after player or AI made their move.
	 * Function will check if the player who got their turn after the move
	 * has none possible moves and will display a message on gameInfoLabel text
	 * whether it was a draw or checkmate
	 * If the moveMakerColor cant make any moves returns true
	 * else returns false
	 */
		

	
	public Main() 
	{
		selectedPieceButton = null;
		chessBoardMatrix = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
		buttonsMatrix = new BoardButton[BOARD_SIZE][BOARD_SIZE];
		whitePiecesArray = new ArrayList<>();
		blackPiecesArray = new ArrayList<>();
		
		playerColor = ChessColor.WHITE; // Player's color
		
		gameInfoLabel = new JLabel();
		gameInfoLabel.setBackground(Color.WHITE);
		gameInfoLabel.setOpaque(true);
		gameInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
		gameInfoLabel.setFont(new Font("Arial", Font.PLAIN, 32)); // Text font and size
		
		movesStack = new Stack<>();
		chessAI = new ChessAI(this);
		
		new GameFrame(this); // create GUI and init game board
		
		if(playerColor == ChessColor.WHITE)
		{
			gameInfoLabel.setText("Your turn.");
			isPlayerTurn = true;
		}
		else
		{
			gameInfoLabel.setText("AI is thinking...");		
			SwingUtilities.invokeLater(chessAI);
		}
		
	}

	public static void main(String[] args) 
	{
		new Main();
	}
	

	

}
