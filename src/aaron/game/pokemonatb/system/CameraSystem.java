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
	}

	@Override
	public void update() {
		for(int entity : getEntities(0)){
			CameraComponent camera = engine.getComponent(entity, CameraComponent.class);
			TransformComponent transform = engine.getComponent(entity, TransformComponent.class);
			camera.xPos = transform.xPixel - (camera.xSize / 2) + transform.xSize/2;
			camera.yPos = transform.yPixel - (camera.ySize / 2) + transform.ySize/2;
			//System.out.println("X: " + camera.xPos);
			//System.out.println("Y: " + camera.yPos);
		}
	}

}
