package aaron.game.pokemonatb.manager;

import java.awt.Graphics2D;
import java.util.Stack;

import aaron.game.pokemonatb.gamestate.BaseGameState;
import aaron.game.pokemonatb.gamestate.IntroGameState;
import aaron.game.pokemonatb.gamestate.MainGameState;

public class GameStateManager {
 
	//Possible Game States
	public static final int NUM_STATES = 8;
	public static final int INTRODUCTION = 0;
	public static final int MAIN_MENU = 1;
	public static final int SCRIPTED_SCENE = 2;
	public static final int PAUSE_MENU = 4;
	public static final int SINGLE_BATTLE = 5;
	public static final int MULTI_BATTLE = 6;
	public static final int GAME_OVER = 7;
	public static final int OVERWORLD = 8;
	
	public static int MAP = 0;
	
	public static final Stack<BaseGameState> stateStack = new Stack<BaseGameState>();
	
	public GameStateManager(){
		//Automatically play intro
		pushState(new IntroGameState(this));
		//stateStack.push(new MainGameState(this, 0));
	}
	
	public void clearStates(){
		while(!stateStack.isEmpty()){
			stateStack.pop();
		}
	}
	
	//For simple state transitions
	public void replaceState(BaseGameState s){
		stateStack.pop();
		pushState(s);
	}
	
	//For Transitioning Between States
	public void pushState(BaseGameState s){
		stateStack.push(s);
		stateStack.peek().initialize();
	}
	
	//To remove current state from stack
	public void endState(){
		stateStack.pop();
	}
	
	public void update() {
		stateStack.peek().update();
	}
	
	public void draw(Graphics2D g) {
		stateStack.peek().draw(g);
	}
}
