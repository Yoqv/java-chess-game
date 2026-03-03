import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Container;


@SuppressWarnings("serial")
public class GameFrame extends JFrame implements Globals
{
	public GameFrame(Main main)
	{		
		super();

		Container pane = getContentPane();
		setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		setTitle("Chess");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Empty label to make empty space on top of GUI
		JLabel emptyLabel = new JLabel(); 
		emptyLabel.setPreferredSize(new Dimension(0, 100)); // Empty label's size
		pane.add(emptyLabel);
		
		pane.add(new GamePanel(main));
		pane.add(new UndoButton(main));
		pane.add(main.gameInfoLabel);
		pane.setBackground(BACKGROUND_COLOR);
		pack();
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
	}
	


}
