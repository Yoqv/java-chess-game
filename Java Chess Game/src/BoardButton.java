import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.UIManager;

@SuppressWarnings("serial")
public class BoardButton extends JButton implements ActionListener, Globals 
{

	private int buttonRow;
	private int buttonCol;
	private final Color BROWN = new Color(181, 154, 132);
	private Main main;
	
	public BoardButton(Main main, int buttonRow, int buttonCol) 
	{
		super();
		this.buttonRow = buttonRow;
		this.buttonCol = buttonCol;
		this.main = main;
	
		setFocusable(false);
		setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
		setButtonDefaultColor();
		addActionListener(this);
	}
	
	public void setButtonDefaultColor()
	{
		if (buttonRow % 2 == 0)
		{
			if (buttonCol % 2 == 0)
				setBackground(Color.WHITE);
			else
				setBackground(BROWN);
		}
		else
		{
			if (buttonCol % 2 == 0)
				setBackground(BROWN);
			else
				setBackground(Color.WHITE);
		}
		
	}
	/**
	 * Sets this button to the default color it has on the GUI
	 */
	

	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(!main.isPlayerTurn)
			return;

		
		BoardButton selectedPieceButton = main.selectedPieceButton;
		ChessPiece[][] chessBoardMatrix = main.chessBoardMatrix;
		boolean isSelectedPieceMoved = false;

		
		if(selectedPieceButton != null) // If player already selected a piece to move
		{
			
			LinkedList<Move> movesList = main.generateMoves(main.playerColor, false);
			
			Move move = getMove(movesList); 
			if(move != null) // If the selected piece can move to this button
			{
				main.makeMove(move);
				main.movesStack.push(move);
				main.isPlayerTurn = false;
				isSelectedPieceMoved = true;
				if(!main.checkAndDisplayGameOver(main.chessAI.aiColor)) // If AI has any moves it can do (game is not over)
				{
					main.gameInfoLabel.setText("AI is thinking...");
					SwingUtilities.invokeLater(main.chessAI);
				}
				
			}
			
			unselectButton(movesList);
		}
		
		if(chessBoardMatrix[buttonRow][buttonCol] != null && !isSelectedPieceMoved && selectedPieceButton != this)  // If there is a piece in this button's buttonRow and buttonCol and and player did not already make a move and this button is not already selected
		{
			if(chessBoardMatrix[buttonRow][buttonCol].getColor() == main.playerColor) // If the color of piece in this button's buttonRow and buttonCol is the color of player 
				selectButton();
				
		}
		
	}
	/**
	 * Function activates on button click.
	 * Player can select a piece to move by clicking on the button
	 * with the piece's icon. after a piece was selected player
	 * can select another button to move the piece there only if
	 * the move is legal
	 */
	

	
	public void unselectButton(LinkedList<Move> movesList)
	{
		BoardButton[][] buttonsMatrix = main.buttonsMatrix;
		BoardButton selectedPieceButton = main.selectedPieceButton;

		int destRow, destCol, startRow, startCol;
		PositionOnBoard destPosition, startPosition;
		for(Move move : movesList)
		{
			startPosition = move.getStartPosition();
			startRow = startPosition.getRow();
			startCol = startPosition.getCol();
			if(startRow == selectedPieceButton.buttonRow && startCol == selectedPieceButton.buttonCol)
			{
				destPosition = move.getDestPosition();
				destRow = destPosition.getRow();
				destCol = destPosition.getCol();
				buttonsMatrix[destRow][destCol].setButtonDefaultColor();
			}
		}
		
		selectedPieceButton.setBorder(UIManager.getBorder("Button.border")); // default border
		main.selectedPieceButton = null;
	}
	/**
	 * Sets the color of the selected piece's possible moves to default color
	 * and unselects the selected button by changing it to null
	 */	
	
	public void selectButton()
	{
		BoardButton[][] buttonsMatrix = main.buttonsMatrix;
		
		setBorder(new LineBorder(Color.BLUE, 2));
		main.selectedPieceButton = this;
		

		LinkedList<Move> movesList = main.generateMoves(main.playerColor, false);

		int destRow, destCol, startRow, startCol;
		PositionOnBoard destPosition, startPosition;
		for(Move move : movesList)
		{
			startPosition = move.getStartPosition();
			startRow = startPosition.getRow();
			startCol = startPosition.getCol();
			if(startRow == main.selectedPieceButton.buttonRow && startCol == main.selectedPieceButton.buttonCol)
			{
				destPosition = move.getDestPosition();
				destRow = destPosition.getRow();
				destCol = destPosition.getCol();
				buttonsMatrix[destRow][destCol].setBackground(Color.BLUE);
			}
		}
		
	}
	/**
	 * Selects this button as the selected chess piece and also
	 * sets the color of the piece's possible moves to blue
	 */	
	
	
	public Move getMove(LinkedList<Move> movesList)
	{
		BoardButton selectedPieceButton = main.selectedPieceButton;
		
		int destRow, destCol, startRow, startCol;
		PositionOnBoard destPosition, startPosition;

		for(Move move: movesList)
		{
			startPosition = move.getStartPosition();
			startRow = startPosition.getRow();
			startCol = startPosition.getCol();
			destPosition = move.getDestPosition();
			destRow = destPosition.getRow();
			destCol = destPosition.getCol();
			if(selectedPieceButton.buttonRow == startRow && selectedPieceButton.buttonCol == startCol)
			{
				if(destRow == this.buttonRow && destCol == this.buttonCol)
					return move;
			}
		}
	
		return null;
	}
	/**
	 * If the selected button's position (buttonRow and buttonCol) is found in movesList as
	 * startPosition and this button's position is found in movesList as destination
	 * returns the move from moveList. Returns null if the move wasn't found.
	 */	


}
