package aaron.game.pokemonatb.gamestate;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import aaron.game.pokemonatb.main.GamePanel;
import aaron.game.pokemonatb.manager.GameStateManager;
import aaron.game.pokemonatb.manager.KeyManager;
import aaron.game.pokemonatb.manager.ResourceManager;
import aaron.game.pokemonatb.manager.SoundManager;

public class MainMenuGameState extends BaseGameState{
	
	 private SoundManager sManager;
	 private ResourceManager rManager;
	
	 private BufferedImage title;
	 private BufferedImage mainCharacter;
	 private BufferedImage selector;
	 private BufferedImage pokeball;
	 
	 private int currentOption;
	 private int startOption = 0;
	 
	 
	 private final int BALL_CYCLE = 60;
	 private final int BALL_START_Y = 150;
	 
	 private final int POKEMON_SIZE = 4;
	 private final int POKEMON_START_X = 75;
	 private final int POKEMON_CYCLE = 30;
	 private final int POKEMON_WAIT = 180;
	 
	 
	 private boolean existingSave = false;
	 private boolean enterPressed = false;
	 private final int NUM_OPTIONS = 4;
	 private String[] options = {
			 "Continue",
			 "New Game",
			 "Options",
			 "Quit"
	 };
	 
	 //Animation Stuff
	 private List<BufferedImage> scrollingPokemon = new ArrayList<BufferedImage>();
	 private int currentPokemon = 0;
	 boolean newPokemon = true;
	 private int pokemonX;
	 boolean top = false;
	 boolean ballTrigger = false;
	 private int ballY;
	 
	 int animationTimer = 0;
	 
	 

	public MainMenuGameState(GameStateManager mSm) {
		super(mSm);
	}

	@Override
	public void initialize() {
		System.out.println("MAIN MENU");
		checkSaveFile();
		sManager = SoundManager.getInstance();
		sManager.addClip("C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources\\Sounds\\title.wav", "title");
		sManager.playClip("title", SoundManager.LOOP);
		
		ballY = 160;
		pokemonX = 256;
		
		rManager = ResourceManager.getInstance();
		try{
			title = rManager.getImage("C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources\\Images\\logo.png");
			mainCharacter = rManager.getImage("C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources\\Images\\ash_mainMenu.png");
			pokeball = rManager.getImage("C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources\\Images\\pokeball.png");
			selector = pokeball;
			scrollingPokemon.add(rManager.getImage("C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources\\Images\\pikachu.png"));
			scrollingPokemon.add(rManager.getImage("C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources\\Images\\charmander.png"));
			scrollingPokemon.add(rManager.getImage("C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources\\Images\\squirtle.png"));
			scrollingPokemon.add(rManager.getImage("C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources\\Images\\bulbasaur.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(existingSave){
			currentOption = 0;
		}
		else{
			currentOption = 1;
			startOption = 1;
		}
	}
	
	private void checkSaveFile(){
		//TODO Check for Save Files
		existingSave = false;
	}

	@Override
	public void update() {
		inputHandler();
		animatePokemon();
		animateBall();
		animationTimer++;
	}
	
	/*private void drawCenteredString(Graphics2D g, Font font, String text, Rectangle rect){
		FontMetrics metrics = g.getFontMetrics(font);
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		int y = rect.y + (rect.height - metrics.stringWidth(text)) / 2;
		g.setFont(font);
		g.drawString(text, x, y);
	}
	
	private void drawVerticalCenteredString(Graphics2D g, Font font, String text, Rectangle rect, int x){
		FontMetrics metrics = g.getFontMetrics(font);
		int y = rect.y + (rect.height - metrics.stringWidth(text)) / 2;
		g.setFont(font);
		g.drawString(text, x, y);
	}*/
	
	private void drawHorizontalCenteredString(Graphics2D g, Font font, String text, Rectangle rect, int y){
		FontMetrics metrics = g.getFontMetrics(font);
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		g.setFont(font);
		g.drawString(text, x, y);
	}
	
	private void animatePokemon(){
		if(animationTimer > POKEMON_WAIT){
			pokemonX = pokemonX - 3*(POKEMON_START_X / POKEMON_CYCLE);
		}
		else if(newPokemon){
			pokemonX = pokemonX - 3*(POKEMON_START_X / POKEMON_CYCLE);
			if(pokemonX < POKEMON_START_X){
				pokemonX = POKEMON_START_X;
				newPokemon = !newPokemon;
			}
		}
		if(pokemonX < -32){
			if(currentPokemon == POKEMON_SIZE -1){
				currentPokemon = 0;
			}
			else{
				currentPokemon++;
			}
			animationTimer = 0;
			pokemonX = 256;
			newPokemon = !newPokemon;
			ballTrigger = !ballTrigger;
		}
	}
	
	private void animateBall(){
		if(ballTrigger){
			ballY = ballY - (int)(2.5*(BALL_START_Y / BALL_CYCLE));
			if(ballY < 130){
				ballY = 130;
				top = !top;
				ballTrigger = !ballTrigger;
			}
		}
		if(top){
			ballY = ballY + (int)(2.5*(BALL_START_Y / BALL_CYCLE));
			if(ballY > 160){
				ballY = 160;
				top = !top;
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setComposite(AlphaComposite.SrcOver.derive(1f));
		g.drawImage(title, 15, 5, GamePanel.WIDTH-30, GamePanel.HEIGHT/3, null);
		g.setColor(new Color(200, 200, 51, 255));
		drawHorizontalCenteredString(g, new Font("TimesRoman", Font.BOLD, 11), "Aaron Version", new Rectangle(0, 0, GamePanel.HEIGHT, GamePanel.WIDTH), GamePanel.HEIGHT/3 + 15);
		g.drawImage(scrollingPokemon.get(currentPokemon), pokemonX, 2*GamePanel.HEIGHT/3 - 24, 80, 80, null);
		g.drawImage(mainCharacter, GamePanel.WIDTH /2, GamePanel.HEIGHT/2 - 14, 75, (int)(75*1.5), null);
		g.drawImage(pokeball, GamePanel.WIDTH /2 - 2, ballY, 15, 15, null);
		drawHorizontalCenteredString(g, new Font("TimesRoman", Font.BOLD, 11), "©'17 AARON FREAK inc.", new Rectangle(0, 0, GamePanel.HEIGHT, GamePanel.WIDTH), GamePanel.HEIGHT-5);
		if(!enterPressed){
			g.setColor(new Color(0, 0, 0, 255));
			
		}
		else{
			int y = 140;
			g.setColor(new Color(0, 0, 0, 255));
			for(int i = startOption; i < NUM_OPTIONS; i++){
				if(currentOption == i){
					g.drawImage(selector, 10, y-15, 20, 20, null);
				}
				g.drawString(options[i], 30, y);
				y = y + 15;
			}
			
		}
	}

	@Override
	public void inputHandler() {
		if(KeyManager.isPressed(KeyManager.SPACE)) {
			if(!enterPressed){
				enterPressed = true;
			}
			else{
				selectOption();
			}
		}
		if(KeyManager.isPressed(KeyManager.W)){
			if(currentOption == startOption){
				currentOption = NUM_OPTIONS-1;
			}
			else{
				currentOption--;
			}
		}
		if(KeyManager.isPressed(KeyManager.S)){
			if(currentOption == NUM_OPTIONS-1){
				currentOption = startOption;
			}
			else{
				currentOption++;
			}
		}
	}
	
	private void selectOption(){
		switch(currentOption){
			case 0: loadGame(); break;
			case 1: newGame(); break;
			case 2: System.out.println("OPTIONS"); break;
			case 3: System.exit(1); break;
		}
	}
	
	private void loadGame(){
		System.out.println("Load Game");
	}
	
	private void newGame(){
		sm.replaceState(new MainGameState(sm, 0));
		sManager.stop("title");
	}

}
