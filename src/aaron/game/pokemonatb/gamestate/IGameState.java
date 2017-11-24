package aaron.game.pokemonatb.gamestate;

import java.awt.Graphics2D;

public interface IGameState {
	public abstract void initialize();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void inputHandler();
}
