package aaron.game.pokemonatb.map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class AnimatedMapTile extends Tile {
	private List<Integer> frameTimes = new ArrayList<Integer>();
	private List<BufferedImage> frames = new ArrayList<BufferedImage>();
	private int currentFrame = 0;
	private int currentAnimationTick = 0;

	public AnimatedMapTile(TileType mType, List<BufferedImage> mFrames, List<Integer> mTimes) {
		super(mType);
		frameTimes = mTimes;
		frames = mFrames;
	}
	
	private void updateAnimation(){
		if(currentAnimationTick == frameTimes.get(currentFrame)){
			nextFrame();
			currentAnimationTick = 0;
		}
		else{
			currentAnimationTick++;
		}
	}
	
	private void nextFrame(){
		int i = frames.size() - 1;
		if(currentFrame == i){
			currentFrame = 0;
		}
		else{
			currentFrame++;
		}
		//System.out.println(currentFrame);
		//System.out.println("NEW FRAME");
	}
	
	public BufferedImage getImage(){
		return frames.get(currentFrame);
	}

	@Override
	public void update() {
		updateAnimation();
	}
}
