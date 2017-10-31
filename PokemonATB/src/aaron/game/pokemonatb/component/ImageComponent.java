package aaron.game.pokemonatb.component;

import java.awt.image.BufferedImage;

public class ImageComponent extends RenderComponent {
	public BufferedImage sprite;
	public int xSize;
	public int ySize;
	
	public ImageComponent(int layer, BufferedImage sprite){
		super(layer);
		this.sprite = sprite;
		this.xSize = sprite.getWidth();
		this.ySize = sprite.getHeight();
	}

	public ImageComponent(int layer, BufferedImage sprite, int x, int y) {
		super(layer);
		this.sprite = sprite;
		this.xSize = x;
		this.ySize = y;
	}
}
