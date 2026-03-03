import javax.swing.Icon;



public class Move implements Globals
{
	
	private ChessPiece movingPiece; // the piece that is being moved
	private PositionOnBoard startPosition; // the start position of the piece (where the piece is before it moved)
	private PositionOnBoard destPosition; // destination position (where the piece can move to)
	private ChessPiece capturedPiece; // which chess piece is in this move's destination before the move is done
	private Icon capturedPieceIcon; // icon of the piece in the move's destination before the move is done
	private boolean isCastling; // is this move a castling move
	private boolean isPromotion; // is a pawn being promoted after making this move

	public Move(ChessPiece movingPiece, int destRow, int destCol, Main main) 
	{
		this.movingPiece = movingPiece; 
		this.startPosition = new PositionOnBoard(movingPiece.getPosition()); // New copy (because the startPosition points to the piece's current position and it might change after making move)
		this.destPosition = new PositionOnBoard(destRow, destCol);
		this.capturedPiece = main.chessBoardMatrix[destPosition.getRow()][destPosition.getCol()];
		this.capturedPieceIcon = main.buttonsMatrix[destPosition.getRow()][destPosition.getCol()].getIcon();
		this.isCastling = false;
		this.isPromotion = false;

		if(movingPiece.getType() == PieceType.PAWN)
		{
			if(movingPiece.getColor() == main.playerColor && destPosition.getRow() == TOP_ROW_INDEX)
			{
				// If piece is human player's piece and dest is the top row index then it's a promotion
				this.isPromotion = true;
			}
			
			if(movingPiece.getColor() == main.chessAI.aiColor && destPosition.getRow() == BOTTOM_ROW_INDEX)
			{
				// If piece is AI's piece and dest is the bottom row index then it's a promotion
				this.isPromotion = true;
			}
		}
		
	}
		
	public boolean isPromotion()
	{
		return isPromotion;
	}
	
	public void setCastling()
	{
		isCastling = true;
	}
	
	public boolean isCastling()
	{
		return isCastling;
	}

	public ChessPiece getMovingPiece()
	{
		return movingPiece;
	}


	public ChessPiece getCapturedPiece() {
		return capturedPiece;
	}


	public Icon getCapturedPieceIcon() {
		return capturedPieceIcon;
	}


	public PositionOnBoard getStartPosition() 
	{
		return startPosition;
	}

	public PositionOnBoard getDestPosition() 
	{
		return destPosition;
	}
	
	public boolean isCaptured()
	{
		return this.capturedPiece != null;
	}
	
	
	
}
