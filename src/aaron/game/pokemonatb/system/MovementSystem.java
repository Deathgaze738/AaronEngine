package aaron.game.pokemonatb.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Tuple;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.RotationComponent;
import aaron.game.pokemonatb.component.State;
import aaron.game.pokemonatb.component.StateComponent;
import aaron.game.pokemonatb.component.TilePositionComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.component.WalkingComponent;
import aaron.game.pokemonatb.main.ECSEngine;

public class MovementSystem extends GameSystemBase {
	
	//index is entity, number is pixels moved
	Map<Integer, Integer> activeMovements;
	private static final int WALK_CYCLE = 1;
	private static final int TILE_SIZE = 16;

	public MovementSystem(ECSEngine engine) {
		super(engine);
		activeMovements = new HashMap<Integer, Integer>();
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<Class<? extends Component>>();
		cList.add(RotationComponent.class);
		cList.add(TransformComponent.class);
		cList.add(TilePositionComponent.class);
		addRequirements(req, cList);
	}

	@Override
	public void update() {
		for(int entity : getEntities(0)){
			if(activeMovements.containsKey(entity)){
				updatePosition(entity);
			}
			else{
				TransformComponent transform = engine.getComponent(entity, TransformComponent.class);
				//System.out.println("MOVE: " + transform.move);
				if(transform.move > 0){
					transform.move = 0;
					activeMovements.put(entity, 0);
					engine.addComponent(entity, new WalkingComponent());
					updatePosition(entity);
				}
			}
		}
	}
	
	public void updatePosition(Integer entity){
		TransformComponent transform = engine.getComponent(entity, TransformComponent.class);
		RotationComponent rotation = engine.getComponent(entity, RotationComponent.class);
		TilePositionComponent tilePos = engine.getComponent(entity, TilePositionComponent.class);
		if(transform.walkingTick % WALK_CYCLE == 0){
			if(activeMovements.get(entity) < TILE_SIZE){
				int xOff = (int) Math.cos(Math.toRadians((double)rotation.rotation) + Math.toRadians(270.0));
				int yOff = (int) Math.sin(Math.toRadians((double)rotation.rotation) + Math.toRadians(270.0));
				//transform.xOffset += (rotation.rotation == 270) ? -xOff : xOff;
				//transform.yOffset += (rotation.rotation == 0) ? -yOff : yOff;
				transform.xPixel += xOff;
				transform.yPixel += yOff;
				activeMovements.put(entity, activeMovements.get(entity) + 1);
				transform.walkingTick = 0;
			}
			else{
				activeMovements.remove(entity);
				tilePos.xTile += (int) Math.cos(Math.toRadians((double)rotation.rotation) + Math.toRadians(270.0));
				tilePos.yTile += (int) Math.sin(Math.toRadians((double)rotation.rotation) + Math.toRadians(270.0));
				transform.walkingTick = 0;
				engine.removeComponent(entity, WalkingComponent.class);
			}
		}
		else{
			transform.walkingTick++;
		}
	}
	
	@Override
	public void removeComponent(Integer entityID, Class<? extends Component> classType){
		super.removeComponent(entityID, classType);
		activeMovements.remove(entityID);
	}
}
