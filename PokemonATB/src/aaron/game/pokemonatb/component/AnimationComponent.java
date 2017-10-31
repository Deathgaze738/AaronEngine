package aaron.game.pokemonatb.component;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationComponent extends Component {
	//State, Rotation
	public Map<String, List<BufferedImage>> animations;
	public int frameRate;
	public int currentTick;
	public int currentFrame;
	
	public AnimationComponent(int mFrameRate){
		frameRate = mFrameRate;
		currentTick = 0;
		currentFrame = 0;
		animations = new HashMap<String, List<BufferedImage>>();
	}
	
	public void addAnimationState(State state, int rotation, List<BufferedImage> sequence){
		animations.put(state+"#"+rotation, sequence);
	}
}
