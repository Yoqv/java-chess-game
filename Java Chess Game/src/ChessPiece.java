import javax.swing.Icon;

public class ChessPiece implements Globals
{

	private PieceType pieceType; // piece type
	private ChessColor pieceColor; // piece color
	private int currentRow; // piece's current row
	private int currentCol; // piece's current column
	private int movementsCount; // Count the amount of times this piece was moved

	public ChessPiece(PieceType pieceType, ChessColor pieceColor, int currentRow, int currentCol) 
	{
		this.pieceType = pieceType;
		this.pieceColor = pieceColor;
		this.currentRow = currentRow;
		this.currentCol = currentCol;
		this.movementsCount = 0;
	}
	
	public PositionOnBoard getPosition()
	{
		return new PositionOnBoard(currentRow, currentCol);
	}
	
	public void moveTo(int destRow, int destCol, ChessPiece[][] currentBoardMatrix, BoardButton[][] buttonsMatrix, boolean isUnmakingMove) 
	{	
		movementsCount = isUnmakingMove ? movementsCount - 1 : movementsCount + 1;
		currentBoardMatrix[destRow][destCol] = this; // Move the piece to destination
		buttonsMatrix[destRow][destCol].setIcon(buttonsMatrix[currentRow][currentCol].getIcon()); // Move the icon to destination
		buttonsMatrix[currentRow][currentCol].setIcon(null); // Remove the icon in start position
		currentBoardMatrix[currentRow][currentCol] = null; // Remove piece from start position
		currentRow = destRow; // Update row
		currentCol = destCol; // Update col
	}
	/**
	 * Moves this piece from the piece's currentRow and currentCol
	 * to destRow and destCol on currentBoardMatrix,
	 * moving the piece's icon on buttonsMatrix and updates
	 * the currentRow and currentCol of this piece. 
	 * If unmaking a move then decrease this piece's
	 * movementsCount by 1 else increase it by 1
	 */

	public boolean hasMoved()
	{
		return movementsCount != 0;
	}
	/**
	 * Return true if piece moved atleast one time
	 * else return false
	 */

	public ChessColor getColor() {
		return pieceColor;
	}

	public void promote(ChessPiece[][] currentBoardMatrix, BoardButton[][] buttonsMatrix)
	{
		ChessColor pawnColor = currentBoardMatrix[currentRow][currentCol].getColor();
		if(pawnColor == ChessColor.WHITE)
			buttonsMatrix[currentRow][currentCol].setIcon(WHITE_QUEEN_ICON);
		if(pawnColor == ChessColor.BLACK)
			buttonsMatrix[currentRow][currentCol].setIcon(BLACK_QUEEN_ICON);
		this.pieceType = PieceType.QUEEN;
	}
	/**
	 * Promoting the piece (it should be a pawn) by changing its
	 * type (can be queen, knight, rook or bishop)
	 */
	
	public void unpromote(ChessPiece[][] currentBoardMatrix, BoardButton[][] buttonsMatrix)
	{
		ChessColor pawnColor = currentBoardMatrix[currentRow][currentCol].getColor();
		Icon pawnIcon = (pawnColor == ChessColor.WHITE) ? WHITE_PAWN_ICON : BLACK_PAWN_ICON; 
		buttonsMatrix[currentRow][currentCol].setIcon(pawnIcon);
		this.pieceType = PieceType.PAWN;
	}
	/**
	 * Unpromoting the piece (it should be a promoted pawn) by changing its
	 * type to pawn. the function is being called when unmaking a move
	 */

	public int getWeight()
	{
		int weight = 0;
		switch (pieceType) 
		{
		case KING: 
			weight = KING_WEIGHT;
			break;
		case QUEEN: 
			weight = QUEEN_WEIGHT;
			break;
		case PAWN: 
			weight = PAWN_WEIGHT;
			break;
		case BISHOP: 
			weight = BISHOP_WEIGHT;
			break;
		case KNIGHT: 
			weight = KNIGHT_WEIGHT;
			break;
		case ROOK: 
			weight = ROOK_WEIGHT;
			break;
		}
		
		return weight;
	}
	/**
	 * @return weight of piece
	 */
	
	
	public PieceType getType() {
		return pieceType;
	}

}
