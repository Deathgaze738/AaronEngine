package aaron.game.pokemonatb.system;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import aaron.game.pokemonatb.component.CameraComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.RenderComponent;
import aaron.game.pokemonatb.component.TileMapComponent;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.manager.EntityManager;
import aaron.game.pokemonatb.map.Tile;
import aaron.game.pokemonatb.map.TileType;

public class RenderSystem extends GameSystemBase {
	
	//Camera Values
	private int xOff = 0;
	private int yOff = 0;
	private int startYTile;
	private int endYTile;
	private int startXTile;
	private int endXTile;
	
	public RenderSystem(ECSEngine engine) {
		super(engine);
		List<Class<? extends Component>> cList = new ArrayList<>();
		cList.add(RenderComponent.class);
		List<Class<? extends Component>> cList2 = new ArrayList<>();
		cList2.add(CameraComponent.class);
		addRequirements(SysRequirement.AllOf, cList);
		addRequirements(SysRequirement.AllOf, cList2);
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
	}
	
	@Override
	public void draw(Graphics2D g){
		List<RenderComponent> renderQueue = getRenderQueue();
		for(RenderComponent comp : renderQueue){
			if(comp.getClass().equals(TileMapComponent.class)){
				renderMap(comp, g);
			}
		}
	}
	
	private void renderMap(TileMapComponent gameMap, Graphics2D g){
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
	
	private List<RenderComponent> getRenderQueue(){
		List<RenderComponent> components = new ArrayList<RenderComponent>();
		getEntities(0).forEach((component) -> components.add(engine.getComponent(component, RenderComponent.class)));
		Collections.sort(components, new Comparator<RenderComponent>(){
			public int compare(RenderComponent r1, RenderComponent r2){
				if(r1.layer < r2.layer){
					return -1;
				}
				if(r1.layer > r2.layer){
					return 1;
				}
				return 0;
			}
		});
		return components;
	}
}
