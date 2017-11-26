package aaron.game.pokemonatb.system;

import java.util.ArrayList;
import java.util.List;

import aaron.game.pokemonatb.component.CameraComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.main.ECSEngine;

public class CameraSystem extends GameSystemBase {
	//Temporary camera offsett, going to be replaced by a camera class.
	float xOffset = 8;
	float yOffset = 8;

	public CameraSystem(ECSEngine engine) {
		super(engine);
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<Class<? extends Component>>();
		cList.add(TransformComponent.class);
		cList.add(CameraComponent.class);
		addRequirements(req, cList);
	}

	@Override
	public void update() {
		for(int entity : getEntities(0)){
			CameraComponent camera = engine.getComponent(entity, CameraComponent.class);
			TransformComponent transform = engine.getComponent(entity, TransformComponent.class);
			camera.xPos = transform.position.x - (camera.xSize / 2) + xOffset;
			camera.yPos = transform.position.y - (camera.ySize / 2) + yOffset;
		}
	}

}
