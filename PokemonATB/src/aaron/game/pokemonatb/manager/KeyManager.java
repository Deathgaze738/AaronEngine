package aaron.game.pokemonatb.manager;

import java.awt.event.KeyEvent;

public class KeyManager {

	private static int HELD = 10;
	
	public enum KeyStatus{
		INACTIVE,
		PRESSED,
		HELD
	}
	
	//List of viable keys
	public static final int NUM_KEYS = 7;
	public static int W = 0;
	public static int A = 1;
	public static int S = 2;
	public static int D = 3;
	public static int E = 4;
	public static int SPACE = 5;
	public static int CONSOLE_KEY = 6;
	
	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static KeyStatus[] keyStatus = new KeyStatus[NUM_KEYS];
	public static int[] framesHeld = new int[NUM_KEYS];
	
	
	//Sets a key as pressed or not pressed
	public static void setKey(int keyCode, boolean value){
		switch(keyCode){
			case KeyEvent.VK_W: keyState[W] = value; break;
			case KeyEvent.VK_A: keyState[A] = value; break;
			case KeyEvent.VK_S: keyState[S] = value; break;
			case KeyEvent.VK_D: keyState[D] = value; break;
			case KeyEvent.VK_E: keyState[E] = value; break;
			case KeyEvent.VK_SPACE: keyState[SPACE] = value; break;
			case KeyEvent.VK_F1: keyState[CONSOLE_KEY] = value; break;
		}
	}
	
	public static void updateKeys(){
		for(int i = 0; i < NUM_KEYS; i++){
			if(keyState[i]){
				framesHeld[i]++;
				keyStatus[i] = framesHeld[i] > HELD ? KeyStatus.HELD : KeyStatus.INACTIVE;
			}
			else{
				keyStatus[i] = (framesHeld[i] > 0 && framesHeld[i] < HELD) ? KeyStatus.PRESSED : KeyStatus.INACTIVE;
				framesHeld[i] = 0;
			}
		}
	}
	
	public static boolean isPressed(int i){
		if(keyStatus[i] == KeyStatus.PRESSED){
			return true;
		}
		return false;
	}
	
	public static boolean anyKeyPressed(){
		for(int i = 0; i < NUM_KEYS; i++){
			if(keyStatus[i] == KeyStatus.PRESSED){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isHeld(int i){
		if(keyStatus[i] == KeyStatus.HELD){
			return true;
		}
		return false;
	}
	
	public static void resetKey(int i){
		keyState[i] = false;
	}
	
	public static boolean anyKeyHeld(){
		for(int i = 0; i < NUM_KEYS; i++){
			if(keyStatus[i] == KeyStatus.HELD){
				return true;
			}
		}
		return false;
	}
}
