package aaron.game.pokemonatb.system;

import java.util.ArrayList;
import java.util.List;

import utils.Vector3D;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.InputComponent;
import aaron.game.pokemonatb.component.PhysicsComponent;
import aaron.game.pokemonatb.component.StateComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.main.ECSEngine;

public class PhysicsSystem extends GameSystemBase {

	public PhysicsSystem(ECSEngine engine) {
		super(engine);
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<>();
		cList.add(PhysicsComponent.class);
		cList.add(TransformComponent.class);
		addRequirements(req, cList);
	}

	@Override
	public void update() {
		for(int entity : getEntities(0)){
			//+y is default for forward.
			PhysicsComponent physics = engine.getComponent(entity, PhysicsComponent.class);
			TransformComponent transform = engine.getComponent(entity, TransformComponent.class);
			if(physics.isGravity){
				
			}
		}		
	}
	
}
