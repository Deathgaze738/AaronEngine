package aaron.game.pokemonatb.map;

import java.awt.image.BufferedImage;


public class StaticMapTile extends Tile {
	private BufferedImage tileImage;
	
	public StaticMapTile(TileType tileType, BufferedImage image){
		super(tileType);
		tileImage = image;
	}
	
	public void setImage(BufferedImage newImage){
		tileImage = newImage;
	}
	
	public BufferedImage getImage(){
		return tileImage;
	}

	@Override
	public void update() {
		return;
	}
}
