import javax.swing.JButton;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

@SuppressWarnings("serial")
public class UndoButton extends JButton implements ActionListener, Globals
{
	private Main main;
	
	public UndoButton(Main main) 
	{
		super();
		this.main = main;
		setText("Undo");
		setAlignmentX(Component.CENTER_ALIGNMENT); 
		setFocusable(false);
		setPreferredSize(new Dimension(50, 50));
		setBackground(Color.WHITE);
		
		addActionListener(this);
	}
	

	

	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(main.movesStack.isEmpty())
			return;
		
		if(main.selectedPieceButton != null) // If a button is selected
			main.selectedPieceButton.unselectButton(main.generateMoves(main.playerColor, false)); // Unselect the button
		
		
		// Unmake last 2 moves (One human move and one AI move)
		if(main.movesStack.size() >= 2) // If there is atleast 2 moves in the stack, unmake them
		{
			ChessColor moveMakerColor = main.playerColor;
			moveMakerColor = main.movesStack.peek().getMovingPiece().getColor();
			
			main.unmakeMove(main.movesStack.pop()); // AI move (Unless player won or made draw)
			
			if(moveMakerColor != main.playerColor) // If popped move was made by player then shouldn't unmake another move
				main.unmakeMove(main.movesStack.pop()); // Human move
		}
		
		main.isPlayerTurn = true; // Set player turn to true (it's false when game is over)
		main.gameInfoLabel.setText("Your turn."); // Set game info label's text to player turn
		
	}

	

	


}
