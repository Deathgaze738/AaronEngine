package aaron.game.pokemonatb.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import aaron.game.pokemonatb.manager.*;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

	//Panel Size 272
	public static final int WIDTH = 272;
	public static final int HEIGHT = 272;
	public static final int SCALE = 3;
	
	//Game Loop
	private Thread thread;
	private boolean running;
	
	private final int FPS = 60;
	private final int TARGET_TIME = 1000 / FPS;
	
	//Drawing
	private BufferedImage image;
	private Graphics2D g;
	private GameStateManager sm;
	
	private void initialize(){
		running = true;
		image = new BufferedImage(WIDTH, HEIGHT, 1);
		g = (Graphics2D) image.getGraphics();
		sm = new GameStateManager();
	}
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	@Override
	public void run() {
		initialize();
		
		long startTime;
		long timeElapsed;
		long wait;
		
		while(running){
			startTime = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			timeElapsed = System.nanoTime() - startTime;
			wait = TARGET_TIME - timeElapsed / 1000000;
			if(wait < 0) wait = TARGET_TIME;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			//System.out.println("Frame Time: " + timeElapsed);
			
		}
	}
	
	//Callback when ready
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			addKeyListener(this);
			thread = new Thread(this);
			thread.start();
		}
	}
	
	private void update(){
		sm.update();
		KeyManager.updateKeys();
	}
	
	private void draw(){
		sm.draw(g);
	}
	
	private void drawToScreen(){
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}
	@Override
	public void keyTyped(KeyEvent key) {}
	
	@Override
	public void keyPressed(KeyEvent key) {
		KeyManager.setKey(key.getKeyCode(), true);
		//System.out.println("Key Pressed: " + key.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent key) {
		KeyManager.setKey(key.getKeyCode(), false);
		//System.out.println("Key Released: " + key.getKeyCode());
	}
}
