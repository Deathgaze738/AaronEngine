package aaron.game.pokemonatb.gamestate;

import java.awt.Graphics2D;

import aaron.game.pokemonatb.manager.GameStateManager;

public abstract class BaseGameState implements IGameState{
	protected GameStateManager sm;
	
	public BaseGameState(GameStateManager mSm){
		sm = mSm;
	}
}
