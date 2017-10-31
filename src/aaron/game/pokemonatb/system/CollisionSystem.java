package aaron.game.pokemonatb.system;

import java.util.ArrayList;

import aaron.game.pokemonatb.component.ActiveComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.InputComponent;
import aaron.game.pokemonatb.component.RotationComponent;
import aaron.game.pokemonatb.component.TextComponent;
import aaron.game.pokemonatb.component.TilePositionComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.component.TileMapComponent;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.manager.SoundManager;
import aaron.game.pokemonatb.map.StaticMapTile;
import aaron.game.pokemonatb.map.Tile;
import aaron.game.pokemonatb.map.TileType;

public class CollisionSystem extends GameSystemBase {
	
	public CollisionSystem(ECSEngine engine) {
		super(engine);
		ArrayList<Class<? extends Component>> cList = new ArrayList<Class<? extends Component>>();
		cList.add(TilePositionComponent.class);
		cList.add(TransformComponent.class);
		cList.add(RotationComponent.class);
		addRequirements(SysRequirement.AllOf, cList);
		ArrayList<Class<? extends Component>> cList2 = new ArrayList<Class<? extends Component>>();
		cList.add(TileMapComponent.class);
		addRequirements(SysRequirement.AllOf, cList2);
		SoundManager.getInstance().addClip("Resources\\Sounds\\collision.wav", "collision");
	}

	@Override
	public void update() {
		//Just get the first tile map, as this is all we will be using for this.
		TileMapComponent map = engine.getComponent(ECSEngine.WORLD_ENTITY, TileMapComponent.class);
		for(int entity : getEntities(0)){
			TransformComponent transform = engine.getComponent(entity, TransformComponent.class);
			TilePositionComponent tilePos = engine.getComponent(entity, TilePositionComponent.class);
			if(transform.move > 0){
				RotationComponent rotation = engine.getComponent(entity, RotationComponent.class);
				//get next tile it will move to.
				int xTile = tilePos.xTile + (transform.move * (int) Math.cos(Math.toRadians((double)rotation.rotation) + Math.toRadians(270.0)));
				int yTile = tilePos.yTile + (transform.move * (int) Math.sin(Math.toRadians((double)rotation.rotation) + Math.toRadians(270.0)));
				//System.out.println("X: " + xTile + " Y: " + yTile);
				if(checkCollision(xTile, yTile, map)){
					transform.move = 0;
					if(!SoundManager.getInstance().isPlaying("collision"))
						SoundManager.getInstance().playClip("collision", SoundManager.ONCE);
				}
				else{
					//System.out.println("NO COLLISION");
				}
			}
		}
	}
	
	private Tile getTile(int x, int y, TileMapComponent map){
		if(x < map.width && y < map.height){
			return map.tileMap[x][y];
		}
		return new StaticMapTile(TileType.WALKING, null);
	}
	
	private boolean checkCollision(int x, int y, TileMapComponent map){
		try{
			Tile nextTile = getTile(x, y, map);
			if(nextTile.getType() == TileType.BLOCKED){
				return true;
			}
		}
		catch(Exception e){
			System.out.println("No Tile");
			return false;
		}
		return false;
	}
}
