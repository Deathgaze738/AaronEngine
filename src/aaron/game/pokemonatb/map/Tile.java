package aaron.game.pokemonatb.map;

import java.awt.image.BufferedImage;

public abstract class Tile{
	private TileType type;
	
	public Tile(TileType mType){
		type = mType;
	}
	
	public TileType getType(){
		return type;
	}
	
	public abstract BufferedImage getImage();
	
	public abstract void update();
}
