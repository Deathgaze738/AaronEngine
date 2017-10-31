package aaron.game.pokemonatb.gamestate;

import java.awt.Graphics2D;

import aaron.game.pokemonatb.manager.GameStateManager;

public abstract class BaseGameState {
	protected GameStateManager sm;
	
	public BaseGameState(GameStateManager mSm){
		sm = mSm;
	}
	
	public abstract void initialize();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void inputHandler();
}
