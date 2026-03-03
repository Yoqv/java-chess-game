import javax.swing.Icon;
import java.awt.Color;
public interface Globals
{
	final int BOARD_SIZE = 8; // board size	
	final int BOTTOM_PAWNS_ROW = 6; // white pawns initial row index
	final int TOP_PAWNS_ROW = 1; // black pawns initial row index
	final int BUTTON_SIZE = 90; // GUI's buttons size
	final int KING_INDEX = 0; // the king's index in whitePiecesArray and blackPiecesArray
	final Color BACKGROUND_COLOR = new Color(26, 27, 29); // background color
	
	final int PAWN_WEIGHT = 100; // pawn weight
	final int KNIGHT_WEIGHT = 320; // knight weight
	final int BISHOP_WEIGHT = 330; // bishop weight
	final int ROOK_WEIGHT = 500; // rook weight
	final int QUEEN_WEIGHT = 900; // queen weight
	final int KING_WEIGHT = 20000;  // king weight
	
	final int TOP_TO_BOTTOM = 1;
	final int BOTTOM_TO_TOP = -1;
	
	final int AI_DIRECTION = TOP_TO_BOTTOM; // AI direction is always from top to bottom
	final int HUMAN_PLAYER_DIRECTION = BOTTOM_TO_TOP; // Human player direction is always from bottom to top
	
	final int LEFT_COLUMN_INDEX = 0; // left column index of currentGameBoard and buttonsMatrix
	final int RIGHT_COLUMN_INDEX = BOARD_SIZE - 1; // right column index of currentGameBoard and buttonsMatrix
	final int TOP_ROW_INDEX = 0; // top row index of currentGameBoard and buttonsMatrix
	final int BOTTOM_ROW_INDEX = BOARD_SIZE - 1; // bottom row index of currentGameBoard and buttonsMatrix
	
	final Icon WHITE_BISHOP_ICON = new NoneBackgroundImage("images/chess_white_bishop.png"); // white bishop icon
	final Icon WHITE_KNIGHT_ICON = new NoneBackgroundImage("images/chess_white_knight.png"); // white knight icon
	final Icon WHITE_ROOK_ICON = new NoneBackgroundImage("images/chess_white_rook.png"); // white rook icon
	final Icon WHITE_PAWN_ICON = new NoneBackgroundImage("images/chess_white_pawn.png"); // white pawn icon
	final Icon WHITE_QUEEN_ICON = new NoneBackgroundImage("images/chess_white_queen.png"); // white queen icon
	final Icon WHITE_KING_ICON = new NoneBackgroundImage("images/chess_white_king.png"); // white king icon
	
	final Icon BLACK_BISHOP_ICON = new NoneBackgroundImage("images/chess_black_bishop.png"); // black bishop icon
	final Icon BLACK_KNIGHT_ICON = new NoneBackgroundImage("images/chess_black_knight.png"); // black knight icon
	final Icon BLACK_ROOK_ICON = new NoneBackgroundImage("images/chess_black_rook.png"); // black rook icon
	final Icon BLACK_PAWN_ICON = new NoneBackgroundImage("images/chess_black_pawn.png"); // black pawn icon
	final Icon BLACK_QUEEN_ICON = new NoneBackgroundImage("images/chess_black_queen.png"); // black queen icon
	final Icon BLACK_KING_ICON = new NoneBackgroundImage("images/chess_black_king.png"); // black king icon
	
	enum PieceType
	{
		BISHOP, KNIGHT, ROOK, PAWN, QUEEN, KING
	}
	
	enum ChessColor
	{
		WHITE, BLACK
	}
	
	/*
	 * Map for each chess piece that the value is the evaluation
	 * for the position. The map is if the pieces start at the bottom
	 * (if player is white then white pieces are at the bottom,
	 * if player is black then black pieces start at the bottom)
	 */
	
	final int[][] PAWN_MAP = // Pawns Map
	{
		{ 0,  0,  0,  0,  0,  0,  0,  0 },
		{ 50, 50, 50, 50, 50, 50, 50, 50 },
		{ 10, 10, 20, 30, 30, 20, 10, 10 },
		{ 5,  5, 10, 25, 25, 10,  5,  5 },
		{ 0,  0,  0, 20, 20,  0,  0,  0 },
		{ 5, -5,-10,  0,  0,-10, -5,  5 },
		{ 5, 10, 10,-20,-20, 10, 10,  5 },
		{ 0,  0,  0,  0,  0,  0,  0,  0 }
	};
	
	final int[][] KNIGHT_MAP = // Knights Map
	{
		{ -50, -40, -30, -30, -30, -30, -40, -50 },
		{ -40, -20, 0, 0, 0, 0, -20, -40 },
		{ -30, 0, 10, 15, 15, 10, 0, -30 },
		{ -30, 5, 15, 20, 20, 15, 5, -30 },
		{ -30, 0, 15, 20, 20, 15, 0, -30 },
		{ -30, 5, 10, 15, 15, 10, 5, -30 },
		{ -40, -20, 0, 5, 5, 0, -20, -40 }, 
		{ -50, -40, -30, -30, -30, -30, -40, -50 }
	};
	
	final int[][] BISHOP_MAP = // Bishops Map
	{
		{ -20, -10, -10, -10, -10, -10, -10, -20 },
		{ -10, 0, 0, 0, 0, 0, 0, -10 },
		{ -10, 0, 5, 10, 10, 5, 0, -10 },
		{ -10, 5, 5, 10, 10, 5, 5, -10 },
		{ -10, 0, 10, 10, 10, 10, 0, -10 },
		{ -10, 10, 10, 10, 10, 10, 10, -10 },
		{ -10, 5, 0, 0, 0, 0, 5, -10 },
		{ -20, -10, -10, -10, -10, -10, -10, -20 }	
	};
	
	
	final int[][] ROOK_MAP = // Rooks Map
	{
		{ 0, 0, 0, 0, 0, 0, 0, 0 },
		{ 5, 10, 10, 10, 10, 10, 10, 5 },
		{ -5, 0, 0, 0, 0, 0, 0, -5 },
		{ -5, 0, 0, 0, 0, 0, 0, -5 },
		{ -5, 0, 0, 0, 0, 0, 0, -5 },
		{ -5, 0, 0, 0, 0, 0, 0, -5 },
		{ -5, 0, 0, 0, 0, 0, 0, -5 },
		{ 0, 0, 0, 5, 5, 0, 0, 0 }			
	};
	
	
	final int[][] QUEEN_MAP = // Queens Map
	{
		{ -20, -10, -10, -5, -5, -10, -10, -20 },
		{ -10, 0, 0, 0, 0, 0, 0, -10 },
		{ -10, 0, 5, 5, 5, 5, 0, -10 },
		{ -5, 0, 5, 5, 5, 5, 0, -5 },
		{ 0, 0, 5, 5, 5, 5, 0, -5 },
		{ -10, 5, 5, 5, 5, 5, 0, -10 },
		{ -10, 0, 5, 0, 0, 0, 0, -10 },
		{ -20, -10, -10, -5, -5, -10, -10, -20 }
	};
	
	final int[][] KING_MAP_MID_GAME = // King Mid Game Map
	{
		{ -30, -40, -40, -50, -50, -40, -40, -30 },
		{ -30, -40, -40, -50, -50, -40, -40, -30 },
		{ -30, -40, -40, -50, -50, -40, -40, -30 },
		{ -30, -40, -40, -50, -50, -40, -40, -30 },
		{ -20, -30, -30, -40, -40, -30, -30, -20 },
		{ -10, -20, -20, -20, -20, -20, -20, -10 },
		{ 20, 20, 0, 0, 0, 0, 20, 20 },
		{ 20, 30, 10, 0, 0, 10, 30, 20 }
	};
	
	final int[][] KING_MAP_END_GAME = // King End Game Map
	{	
		{ -50, -40, -30, -20, -20, -30, -40, -50 },
		{ -30, -20, -10, 0, 0, -10, -20, -30 },
		{ -30, -10, 20, 30, 30, 20, -10, -30 },
		{ -30, -10, 30, 40, 40, 30, -10, -30 },
		{ -30, -10, 30, 40, 40, 30, -10, -30 },
		{ -30, -10, 20, 30, 30, 20, -10, -30 },
		{ -30, -30, 0, 0, 0, 0, -30, -30 },
		{ -50, -30, -30, -30, -30, -30, -30, -50 }
	};
	

	

}
