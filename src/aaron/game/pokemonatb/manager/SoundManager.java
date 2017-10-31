package aaron.game.pokemonatb.manager;

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.Clip;

public class SoundManager {
	
	private static SoundManager manager;
	
	private Map<String, Clip> sounds = new HashMap<>();
	
	private SoundManager(){}
	
	public static final int LOOP = 0;
	public static final int ONCE = 1;
	
	public static SoundManager getInstance(){
		if(manager == null){
			manager = new SoundManager();
		}
		return manager;
	}
	
	public void addClip(String source, String alias){
		Clip sound = ResourceManager.getInstance().getClip(source);
		sounds.put(alias, sound);
	}
	
	public void playClip(String alias, int type){
		Clip sound = sounds.get(alias);
		if(sound == null){
			return;
		}
		sound.stop();
		sound.setFramePosition(0);		
		//sound.start();
		if(type == LOOP){
			//sound.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void stop(String alias){
		if(sounds.get(alias) == null){
			return;
		}
		sounds.get(alias).stop();
	}
	
	public boolean isPlaying(String alias){
		Clip sound = sounds.get(alias);
		if(!sound.isActive()){
			return false;
		}
		return true;
	}
}
