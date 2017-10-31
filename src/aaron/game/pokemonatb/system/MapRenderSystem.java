package aaron.game.pokemonatb.system;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import aaron.game.pokemonatb.component.CameraComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.main.GamePanel;
import aaron.game.pokemonatb.map.GameMap;
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
	
	GameMap gameMap;

	public MapRenderSystem(ECSEngine engine, GameMap map) {
		super(engine);
		gameMap = map;
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<Class<? extends Component>>();
		cList.add(CameraComponent.class);
		addRequirements(req, cList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		for(int entity : getEntities(0)){
			CameraComponent cameraComponent = engine.getComponent(entity, CameraComponent.class);
			xOff = cameraComponent.xPos % GameMap.TILE_SIZE;
			yOff = cameraComponent.yPos % GameMap.TILE_SIZE;
			startXTile = cameraComponent.xPos / GameMap.TILE_SIZE;
			startYTile = cameraComponent.yPos / GameMap.TILE_SIZE;
			endXTile = (int) Math.ceil((double)(cameraComponent.xPos + cameraComponent.xSize) / GameMap.TILE_SIZE);
			endYTile = (int) Math.ceil((double)(cameraComponent.yPos + cameraComponent.ySize) / GameMap.TILE_SIZE);
		}
		updateTiles();
	}
	
	private void drawMap(Graphics2D g){
		Tile[][] tiles = gameMap.getTileMap();
		int x = 0;
		int y = 0;
		//g.drawImage(tiles[0][0].getImage(), 0, 0, 16, 16, null);
		for(int i = startYTile; i < endYTile; i++){
			x = 0;
			for(int k = startXTile; k < endXTile; k++){
				try{
					g.drawImage(tiles[i][k].getImage(), x - xOff, y - yOff, GameMap.TILE_SIZE, GameMap.TILE_SIZE, null);
					if(tiles[i][k].getType() == TileType.BLOCKED){
						//g.drawOval(x - xOff, y - yOff, GameMap.TILE_SIZE, GameMap.TILE_SIZE);
					}
					//System.out.println("Current Tile ["+Integer.toString(i)+"]["+Integer.toString(k)+"]");
				}
				catch(Exception e){
					//System.out.println("No Tile data");
				}
				x = x + GameMap.TILE_SIZE;
			}
			y = y + GameMap.TILE_SIZE;
		}
	}
	
	private void updateTiles(){
		Tile[][] tiles = gameMap.getTileMap();
		for(int i = 0; i < gameMap.getWidth(); i++){
			for(int k = 0; k < gameMap.getHeight(); k++){
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
