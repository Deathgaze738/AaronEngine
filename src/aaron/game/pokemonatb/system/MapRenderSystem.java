package aaron.game.pokemonatb.system;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import aaron.game.pokemonatb.component.CameraComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.ImageComponent;
import aaron.game.pokemonatb.component.RenderComponent;
import aaron.game.pokemonatb.component.TileMapComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.main.GamePanel;
import aaron.game.pokemonatb.map.Tile;
import aaron.game.pokemonatb.map.TileType;

public class MapRenderSystem extends GameSystemBase {
	//Camera Values
	private int xOff = 0;
	private int yOff = 0;
	private int startYTile;
	private int endYTile;
	private int startXTile;
	private int endXTile;

	public MapRenderSystem(ECSEngine engine) {
		super(engine);
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<Class<? extends Component>>();
		cList.add(CameraComponent.class);
		addRequirements(req, cList);
		cList = new ArrayList<Class<? extends Component>>();
		cList.add(TileMapComponent.class);
		addRequirements(req, cList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		TileMapComponent map = engine.getComponent(getFirstEntity(1), TileMapComponent.class);
		if(map == null){
			return;
		}
		
		for(int entity : getEntities(0)){
			CameraComponent cameraComponent = engine.getComponent(entity, CameraComponent.class);
			xOff = cameraComponent.xPos % map.tileSize;
			yOff = cameraComponent.yPos % map.tileSize;
			startXTile = cameraComponent.xPos / map.tileSize;
			startYTile = cameraComponent.yPos / map.tileSize;
			endXTile = (int) Math.ceil((double)(cameraComponent.xPos + cameraComponent.xSize) / map.tileSize);
			endYTile = (int) Math.ceil((double)(cameraComponent.yPos + cameraComponent.ySize) / map.tileSize);
		}
		updateTiles(map);
	}
	
	private void drawMap(Graphics2D g){
		TileMapComponent map = engine.getComponent(getFirstEntity(1), TileMapComponent.class);
		Tile[][] tiles = map.tileMap;
		int x = 0;
		int y = 0;
		//g.drawImage(tiles[0][0].getImage(), 0, 0, 16, 16, null);
		for(int i = startYTile; i < endYTile; i++){
			x = 0;
			for(int k = startXTile; k < endXTile; k++){
				try{
					g.drawImage(tiles[i][k].getImage(), x - xOff, y - yOff, map.tileSize, map.tileSize, null);
					if(tiles[i][k].getType() == TileType.BLOCKED){
						//g.drawOval(x - xOff, y - yOff, GameMap.TILE_SIZE, GameMap.TILE_SIZE);
					}
					//System.out.println("Current Tile ["+Integer.toString(i)+"]["+Integer.toString(k)+"]");
				}
				catch(Exception e){
					//System.out.println("No Tile data");
				}
				x = x + map.tileSize;
			}
			y = y + map.tileSize;
		}
	}
	
	private void updateTiles(TileMapComponent gameMap){
		Tile[][] tiles = gameMap.tileMap;
		for(int i = 0; i < gameMap.width; i++){
			for(int k = 0; k < gameMap.height; k++){
				tiles[i][k].update();
			}
		}
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		drawMap(g);
	}
}
