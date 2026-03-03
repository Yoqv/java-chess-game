import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.Icon;
@SuppressWarnings("serial")
public class ChessBoardPanel extends JPanel implements Globals 
{

	private Main main;

	public ChessBoardPanel(Main main) 
	{
		super();
		this.main = main;
		createBoard();
	}

	public void createBoard() 
	{
		BoardButton[][] buttonsMatrix = main.buttonsMatrix;

		setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

		for (int i = 0; i < BOARD_SIZE; i++) 
		{
			for (int j = 0; j < BOARD_SIZE; j++) 
			{
				BoardButton button = new BoardButton(main, i, j);
				buttonsMatrix[i][j] = button;
				add(button);
			}
		}

		initBoard();
	}
	/**
	 * Creates the GUI's board, adds each button of the GUI's board to the
	 * buttonsMatrix and inits the board
	 */

	
	public void initBoard() 
	{		
		for (int col = 0; col < BOARD_SIZE; col++) // Place pawns
		{
			createPieceOnBoard(TOP_PAWNS_ROW, col, PieceType.PAWN, main.chessAI.aiColor);
			createPieceOnBoard(BOTTOM_PAWNS_ROW, col, PieceType.PAWN, main.playerColor);
		}
	
		final int KING_COL = (main.playerColor == ChessColor.WHITE) ? 4 : 3;
		final int QUEEN_COL = (main.playerColor == ChessColor.WHITE) ? 3 : 4;
		
		createPieceOnBoard(TOP_ROW_INDEX, 0, PieceType.ROOK,  main.chessAI.aiColor);
		createPieceOnBoard(TOP_ROW_INDEX, 1, PieceType.KNIGHT,  main.chessAI.aiColor);
		createPieceOnBoard(TOP_ROW_INDEX, 2, PieceType.BISHOP,  main.chessAI.aiColor);
		createPieceOnBoard(TOP_ROW_INDEX, QUEEN_COL, PieceType.QUEEN,  main.chessAI.aiColor);
		createPieceOnBoard(TOP_ROW_INDEX, KING_COL, PieceType.KING,  main.chessAI.aiColor);
		createPieceOnBoard(TOP_ROW_INDEX, 5, PieceType.BISHOP,  main.chessAI.aiColor);
		createPieceOnBoard(TOP_ROW_INDEX, 6, PieceType.KNIGHT,  main.chessAI.aiColor);
		createPieceOnBoard(TOP_ROW_INDEX, 7, PieceType.ROOK,  main.chessAI.aiColor);
		
		createPieceOnBoard(BOTTOM_ROW_INDEX, 0, PieceType.ROOK,  main.playerColor);
		createPieceOnBoard(BOTTOM_ROW_INDEX, 1, PieceType.KNIGHT,  main.playerColor);
		createPieceOnBoard(BOTTOM_ROW_INDEX, 2, PieceType.BISHOP,  main.playerColor);
		createPieceOnBoard(BOTTOM_ROW_INDEX, QUEEN_COL, PieceType.QUEEN,  main.playerColor);
		createPieceOnBoard(BOTTOM_ROW_INDEX, KING_COL, PieceType.KING,  main.playerColor);
		createPieceOnBoard(BOTTOM_ROW_INDEX, 5, PieceType.BISHOP,  main.playerColor);
		createPieceOnBoard(BOTTOM_ROW_INDEX, 6, PieceType.KNIGHT,  main.playerColor);
		createPieceOnBoard(BOTTOM_ROW_INDEX, 7, PieceType.ROOK,  main.playerColor);
		
	}
	/**
	 * Placing all of the chess pieces in their initial positions
	 */
	
	public void createPieceOnBoard(int pieceRow, int pieceCol, PieceType pieceType,  ChessColor pieceColor)
	{
		BoardButton[][] buttonsMatrix = main.buttonsMatrix;
		ChessPiece[][] chessBoardMatrix = main.chessBoardMatrix;

		Icon pieceIcon = null;
		boolean isKing = false;
		
		switch (pieceType) 
		{
		case KING:
			if(pieceColor == ChessColor.WHITE)
				pieceIcon = WHITE_KING_ICON;
			else
				pieceIcon = BLACK_KING_ICON;
			isKing = true;
			break;
		case QUEEN: 
			if(pieceColor == ChessColor.WHITE)
				pieceIcon = WHITE_QUEEN_ICON;
			else
				pieceIcon = BLACK_QUEEN_ICON;
			break;
		case PAWN: 
			if(pieceColor == ChessColor.WHITE)
				pieceIcon = WHITE_PAWN_ICON;
			else
				pieceIcon = BLACK_PAWN_ICON;
			break;
		case BISHOP: 
			if(pieceColor == ChessColor.WHITE)
				pieceIcon = WHITE_BISHOP_ICON;
			else
				pieceIcon = BLACK_BISHOP_ICON;
			break;
		case KNIGHT: 
			if(pieceColor == ChessColor.WHITE)
				pieceIcon = WHITE_KNIGHT_ICON;
			else
				pieceIcon = BLACK_KNIGHT_ICON;
			break;
		case ROOK: 
			if(pieceColor == ChessColor.WHITE)
				pieceIcon = WHITE_ROOK_ICON;
			else
				pieceIcon = BLACK_ROOK_ICON;
			break;
		}
		
		ChessPiece chessPiece = new ChessPiece(pieceType, pieceColor, pieceRow, pieceCol);
		buttonsMatrix[pieceRow][pieceCol].setIcon(pieceIcon);
		chessBoardMatrix[pieceRow][pieceCol] = chessPiece;
		
		switch (pieceColor) 
		{
		case WHITE: 
			if(isKing)
				main.whitePiecesArray.add(KING_INDEX, chessPiece);
			else
				main.whitePiecesArray.add(chessPiece);
			break;
		case BLACK: 
			if(isKing)
				main.blackPiecesArray.add(KING_INDEX, chessPiece);
			else
				main.blackPiecesArray.add(chessPiece);
			break;
		}
		
		
	}
	/**
	 * Gets a type of chess piece, color, row and column as parameters. 
	 * setting the piece's image on this row and column in the GUI and,
	 * adds the piece to chessBoardMatrix. Also adds
	 * the pieces to ArrayList if piece is white then it's added to whitePiecesArray
	 * and if it's black added to blackPiecesArray if the piece is king it's added at KING_INDEX (index 0)
	 */
}
