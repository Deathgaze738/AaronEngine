package aaron.game.pokemonatb.system;

import java.util.ArrayList;
import java.util.List;

import aaron.game.pokemonatb.component.CameraComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.TileMapComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.main.ECSEngine;

public class CameraSystem extends GameSystemBase {

	public CameraSystem(ECSEngine engine) {
		super(engine);
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<Class<? extends Component>>();
		cList.add(TransformComponent.class);
		cList.add(CameraComponent.class);
		addRequirements(req, cList);

		List<Class<? extends Component>> cList2 = new ArrayList<Class<? extends Component>>();
		cList.add(TileMapComponent.class);
		addRequirements(req, cList2);
	}

	@Override
	public void update() {
		TileMapComponent map = engine.getComponent(ECSEngine.WORLD_ENTITY, TileMapComponent.class);
		if(map == null){
			return;
		}
		for(int entity : getEntities(0)){
			CameraComponent camera = engine.getComponent(entity, CameraComponent.class);
			TransformComponent transform = engine.getComponent(entity, TransformComponent.class);
			camera.xPos = transform.xPixel - (camera.xSize / 2) + (map.tileSize / 2);
			camera.yPos = transform.yPixel - (camera.ySize / 2) + (map.tileSize / 2);
		}
	}

}
