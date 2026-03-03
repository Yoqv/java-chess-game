import java.util.LinkedList;


public class ChessAI implements Globals, Runnable
{
	private Main main;
	private final static int MAX_DEPTH = 4;
	private Evaluate evaluation; // Evaluation refrence
	private Move currentMove; // AI's current move (updates after calling searchBestMove function)
	public ChessColor aiColor; // AI's color
	
	public ChessAI(Main main)
	{
		this.main = main;
		this.evaluation = new Evaluate(main);
		this.aiColor = (main.playerColor == ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.WHITE;
	}
	
	public void moveAI()
	{
		searchBestMove(MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true, aiColor); // Find AI best move

		// currentMove can't be null because the function moveAI will never be called if AI has no moves
		main.makeMove(currentMove);
		main.movesStack.push(currentMove);
		
		if(!main.checkAndDisplayGameOver(main.playerColor)) // If player has any moves they can do (game is not over) 
		{
			main.gameInfoLabel.setText("Your turn.");
			main.isPlayerTurn = true;
		}
		
	}
	/**
	 * Plays the AI's best move using minimax algorithm 
	 * If game is over sets the text on the gameInfoLabel on GUI
	 */
			
	
	public int searchBestMove(int depth, int alpha, int beta, boolean isMaximizing, ChessColor maximizingColor)
	{
		Move bestMove = null;
		
		if(depth == 0)
			return evaluation.evaluate(maximizingColor);
		
		ChessColor minimizingColor = (maximizingColor == ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.WHITE;
		ChessColor moveMakerColor = (isMaximizing) ? maximizingColor : minimizingColor;	
		
		LinkedList<Move> movesList = main.generateMoves(moveMakerColor, false);
		
		if(movesList.isEmpty())
		{
			if(!main.isKingInDanger(moveMakerColor)) // If it's a draw
				return 0; // Draw evaluation 
		}
		else
		{
			bestMove = movesList.element();
		}

		if(isMaximizing)
		{	
			int maxEval = Integer.MIN_VALUE;
			for(Move move : movesList)
			{
				int currentEval;

				main.makeMove(move);
				currentEval = searchBestMove(depth - 1, alpha, beta, false, maximizingColor);
				main.unmakeMove(move);
				
				if(currentEval > maxEval)
				{
					maxEval = currentEval;
					bestMove = move;
				}
				
				if(currentEval >= beta)
					break;
				
				alpha = maxEval;
				
			}
			
			this.currentMove = bestMove; // Save AI's best move as currentMove
			return maxEval;
		}
		else
		{
			int minEval = Integer.MAX_VALUE;
			for(Move move : movesList)
			{
				int currentEval;

				main.makeMove(move);
				currentEval = searchBestMove(depth - 1, alpha, beta, true, maximizingColor);
				main.unmakeMove(move);
				
				minEval = Math.min(minEval, currentEval);
				
				if(currentEval <= alpha)
					break;
				
				beta = minEval;
			}
			return minEval;
		}	
	}
	/**
	 * Recursive function returns the static evaluation when reaching to depth 0. 
	 * Function is searching for best move using minimax algorithm.
	 * Function also updates the AI's currentMove (Taking the move with the best evaluation), If AI can't 
	 * make any move (draw or checkmate) then currentMove will be null
	 */

	
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		moveAI();
	}



	
}
