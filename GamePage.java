import javax.swing.JFrame;

public class GamePage {

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
		JFrame frame = new JFrame();
		MainGame g = new MainGame();
		
		frame.setBounds(0, 0, 700, 600);
		frame.setTitle("Breack-a-Brick");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(g);
	}

}
