package aaron.game.pokemonatb.component;

import aaron.game.pokemonatb.map.Tile;

public class TileMapComponent extends RenderComponent {
	
	public static final int ORTHOGONAL = 1;
	public static final int ISOMETRIC = 2;
	
	public int mapid;
	public int mapType;
	public Tile[][] tileMap;
	public int height;
	public int width;
	public int tileSize;

	public TileMapComponent(int mapid, int layer, int mapType, int tileSize, Tile[][] tileMap, int height, int width){
		super(layer);
		this.mapid = mapid;
		this.mapType = mapType;
		this.tileMap = tileMap;
		this.tileSize = tileSize;
		this.height = height;
		this.width = width;
	}
}
