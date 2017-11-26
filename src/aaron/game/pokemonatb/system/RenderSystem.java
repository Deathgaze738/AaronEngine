package aaron.game.pokemonatb.system;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import utils.Tuple;
import aaron.game.pokemonatb.component.CameraComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.ImageComponent;
import aaron.game.pokemonatb.component.RenderableComponent;
import aaron.game.pokemonatb.component.TileMapComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.map.Tile;
import aaron.game.pokemonatb.map.TileType;

public class RenderSystem extends GameSystemBase {
	CameraComponent mainCamera;
	
	public RenderSystem(ECSEngine engine) {
		super(engine);
		List<Class<? extends Component>> cList = new ArrayList<>();
		cList.add(RenderableComponent.class);
		cList.add(TileMapComponent.class);
		List<Class<? extends Component>> cList2 = new ArrayList<>();
		cList2.add(RenderableComponent.class);
		cList2.add(TransformComponent.class);
		cList2.add(ImageComponent.class);
		List<Class<? extends Component>> cList3 = new ArrayList<>();
		cList3.add(CameraComponent.class);
		addRequirements(SysRequirement.AllOf, cList);
		addRequirements(SysRequirement.AllOf, cList2);
		addRequirements(SysRequirement.AllOf, cList3);
	}

	@Override
	public void update() {
		//System.out.println("UPDATE");
		for(int entity : getEntities(2)){
			//System.out.println("DEFAULT CAMERA ADDED");
			mainCamera = engine.getComponent(entity, CameraComponent.class);
		}
		for(int entity: getEntities(1)){
			RenderableComponent render = engine.getComponent(entity, RenderableComponent.class);
			TransformComponent transform = engine.getComponent(entity, TransformComponent.class);
			ImageComponent image = engine.getComponent(entity, ImageComponent.class);
			updateImage(image, render, transform);
		}
	}
	
	@Override
	public void draw(Graphics2D g){
		render(g);
	}
	
	private void renderMap(TileMapComponent gameMap, Graphics2D g){
		Tile[][] tiles = gameMap.tileMap;
		int x = 0;
		int y = 0;
		
		int xOff = (int) mainCamera.xPos % gameMap.tileSize;
		int yOff = (int) mainCamera.yPos % gameMap.tileSize;
		int startXTile = (int) mainCamera.xPos / gameMap.tileSize;
		int startYTile = (int) mainCamera.yPos / gameMap.tileSize;
		int endXTile = (int) Math.ceil((double)(mainCamera.xPos + mainCamera.xSize) / gameMap.tileSize);
		int endYTile = (int) Math.ceil((double)(mainCamera.yPos + mainCamera.ySize) / gameMap.tileSize);
		
		//g.drawImage(tiles[0][0].getImage(), 0, 0, 16, 16, null);
		for(int i = startYTile ; i < endYTile; i++){
			x = 0;
			for(int k = startXTile; k < endXTile; k++){
				try{
					g.drawImage(tiles[i][k].getImage(), x - xOff, y - yOff, gameMap.tileSize, gameMap.tileSize, null);
					if(tiles[i][k].getType() == TileType.BLOCKED){
						g.drawOval(x - xOff, y - yOff, gameMap.tileSize, gameMap.tileSize);
					}
					//System.out.println("Current Tile ["+Integer.toString(i)+"]["+Integer.toString(k)+"]");
				}
				catch(Exception e){
					//System.out.println("No Tile data");
				}
				x = x + gameMap.tileSize;
			}
			y = y + gameMap.tileSize;
		}
	}
	
	private void updateImage(ImageComponent imageComp, RenderableComponent renderComp, TransformComponent transform){
		renderComp.x = (int) (transform.position.x - mainCamera.xPos);
		renderComp.y = (int) (transform.position.y - mainCamera.yPos);
	}
	
	private void drawImage(ImageComponent imageComp, RenderableComponent renderComp, Graphics2D g){
		if((renderComp.x) <= mainCamera.xSize && (mainCamera.xPos + imageComp.xSize) >= 0 && (renderComp.y) <= mainCamera.ySize && (mainCamera.yPos + imageComp.ySize) >= 0){
			if(renderComp.active){
				g.drawImage(imageComp.sprite, renderComp.x, renderComp.y, imageComp.xSize, imageComp.ySize, null);
			}
			//System.out.println("DRAWN AT");
		}
		//System.out.println("POSITION: " + renderComp.x + " " + renderComp.y);
	}
	
	private void render(Graphics2D g){
		List<Tuple<RenderableComponent, Integer>> components = new ArrayList<>();
		List<Integer> entities = getEntities(0);
		entities.addAll(getEntities(1));
		entities.forEach((component) -> components.add(new Tuple<RenderableComponent, Integer>(engine.getComponent(component, RenderableComponent.class), component)));
		Collections.sort(components, new Comparator<Tuple<RenderableComponent, Integer>>(){
			public int compare(Tuple<RenderableComponent, Integer> r1, Tuple<RenderableComponent, Integer> r2){
				if(r1.getLeft().layer < r2.getLeft().layer){
					return -1;
				}
				if(r1.getLeft().layer > r2.getLeft().layer){
					return 1;
				}
				return 0;
			}
		});
		
		ImageComponent imageComp;
		TileMapComponent tileComp;
		RenderableComponent renderComp;
		for(Tuple<RenderableComponent, Integer> i : components){
			renderComp = i.getLeft();
			imageComp = engine.getComponent(i.getRight(), ImageComponent.class);
			tileComp = engine.getComponent(i.getRight(), TileMapComponent.class);
			if(imageComp != null){
				drawImage(imageComp, renderComp, g);
			}
			if(tileComp != null){
				renderMap(tileComp, g);
			}
		}
	}
}
