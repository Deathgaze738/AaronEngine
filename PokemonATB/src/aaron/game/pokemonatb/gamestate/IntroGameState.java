package aaron.game.pokemonatb.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import aaron.game.pokemonatb.main.GamePanel;
import aaron.game.pokemonatb.manager.GameStateManager;
import aaron.game.pokemonatb.manager.ResourceManager;
import aaron.game.pokemonatb.manager.SoundManager;

public class IntroGameState extends BaseGameState{
	
	private BufferedImage logo;
	
	private int alpha;
	private int ticks;
	

	public IntroGameState(GameStateManager mSm) {
		super(mSm);
	}

	@Override
	public void initialize() {
		System.out.println("Intro");
		ticks = 0;
		try {
			logo = ResourceManager.getInstance().getImage("C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources\\Images\\intro.jpg");
			SoundManager.getInstance().addClip("C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources\\Sounds\\start.wav", "intro");
			SoundManager.getInstance().playClip("intro", SoundManager.ONCE);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		inputHandler();
		ticks++;
		if(ticks > 90) {
			sm.replaceState(new MainMenuGameState(sm));
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.drawImage(logo, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
	}

	@Override
	public void inputHandler() {

	}

}
