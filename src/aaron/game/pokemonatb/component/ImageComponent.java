package aaron.game.pokemonatb.component;

import java.awt.image.BufferedImage;

public class ImageComponent extends Component {
	public BufferedImage sprite;
	public int xSize;
	public int ySize;
	public int layer;
	
	public ImageComponent(int layer, BufferedImage sprite){
		this.layer = layer;
		this.sprite = sprite;
		this.xSize = sprite.getWidth();
		this.ySize = sprite.getHeight();
	}

	public ImageComponent(int layer, BufferedImage sprite, int x, int y) {
		this.layer = layer;
		this.sprite = sprite;
		this.xSize = x;
		this.ySize = y;
	}
}
