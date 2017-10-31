package aaron.game.pokemonatb.system;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import aaron.game.pokemonatb.component.CameraComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.TileMapComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.component.RenderComponent;
import aaron.game.pokemonatb.component.ImageComponent;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.main.GamePanel;
import aaron.game.pokemonatb.manager.EntityManager;
import aaron.game.pokemonatb.map.Tile;
import aaron.game.pokemonatb.map.TileType;

public class EntityRenderSystem extends GameSystemBase{
	
	//Camera Values
	private int yPos;
	private int xPos;
	private int xSize;
	private int ySize;
	
	public EntityRenderSystem(ECSEngine engine){
		super(engine);
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<Class<? extends Component>>();
		cList.add(CameraComponent.class);
		addRequirements(req, cList);
		cList = new ArrayList<Class<? extends Component>>();
		cList.add(TransformComponent.class);
		cList.add(ImageComponent.class);
		cList.add(RenderComponent.class);
		addRequirements(req, cList);
		cList = new ArrayList<Class<? extends Component>>();
		cList.add(TileMapComponent.class);
		addRequirements(req, cList);
	}

	public void draw(Graphics2D g){
		drawEntities(g);
	}
	
	private void drawEntities(Graphics2D g){
		for(int entity : getEntities(1)){
			TransformComponent transform = engine.getComponent(entity, TransformComponent.class);
			ImageComponent sprite = engine.getComponent(entity, ImageComponent.class);
			int xScreen = transform.xPixel - xPos;
			int yScreen = transform.yPixel - yPos;
			//System.out.println("xScreen: " + xScreen);
			//System.out.println("yScreen: " + yScreen);
			
			if((xScreen) <= xSize && (xScreen + sprite.xSize) >= 0 && (yScreen) <= ySize && (yScreen + sprite.ySize) >= 0){
				g.drawImage(sprite.sprite, xScreen, yScreen, sprite.xSize, sprite.ySize, null);
				//System.out.println("DRAWN " + entity + " AT POSITION: " + xScreen + " " + yScreen);
			}
		}
	}

	@Override
	public void update() {
		List<Integer> entities = getEntities(0);
		if(entities.size() > 0){
			for(int entity : entities){
				CameraComponent cameraComponent = engine.getComponent(entity, CameraComponent.class);
				yPos = cameraComponent.yPos;
				xPos = cameraComponent.xPos;
				ySize = cameraComponent.ySize;
				xSize = cameraComponent.xSize;
			}
		}
		else{
			//System.out.println("No Camera: Using Default Values");
			yPos = 0;
			xPos = 0;
			ySize = 15 * 16;
			xSize = 15 * 16;
		}
	}
}