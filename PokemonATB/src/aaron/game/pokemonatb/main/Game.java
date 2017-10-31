package aaron.game.pokemonatb.main;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args) {
		System.out.println("Total Memory Usage: " + Double.toString((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000.0));
		JFrame window = new JFrame("Pokemon ATB");
		
		window.add(new GamePanel());
		
		window.setResizable(false);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
