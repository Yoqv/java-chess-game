import javax.swing.JPanel;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Globals {


	public GamePanel(Main main) 
	{
		super();
		setBackground(BACKGROUND_COLOR);
		add(new ChessBoardPanel(main), BorderLayout.CENTER);
	}

}