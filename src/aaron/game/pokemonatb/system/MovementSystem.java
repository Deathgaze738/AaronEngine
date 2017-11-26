package aaron.game.pokemonatb.system;

import java.util.ArrayList;
import java.util.List;

import utils.Vector3D;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.MovementComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.main.ECSEngine;

public class MovementSystem extends GameSystemBase {

	public MovementSystem(ECSEngine engine) {
		super(engine);
		//Moving Entities
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<>();
		cList.add(TransformComponent.class);
		cList.add(MovementComponent.class);
		addRequirements(req, cList);
	}

	@Override
	public void update() {
		for(int entity : getEntities(0)){
			TransformComponent transform = engine.getComponent(entity, TransformComponent.class);
			MovementComponent movement = engine.getComponent(entity, MovementComponent.class);
			transform.rotation += movement.rotate;
			if(movement.worldAxis){
				transform.position.add(movement.translate);
			}
			else{
				transform.position.add(localToWorld(movement.translate, transform.rotation));
			}
			engine.removeComponent(entity, MovementComponent.class);
		}
	}
	
	public static Vector3D localToWorld(Vector3D local, float rotation){
		Vector3D world = new Vector3D(Vector3D.ZERO);
		//System.out.println("Local Movement: " + local.toString() + " bearing " + rotation + " degrees.");
		world.x = (float) ((local.x * Math.cos(Math.toRadians(rotation))) + (local.y * Math.sin(Math.toRadians(rotation))));
		world.y = (float) ((local.x * Math.sin(Math.toRadians(rotation))) - (local.y * Math.cos(Math.toRadians(rotation))));
		world.z = local.z;
		world.x = Math.round(world.x);
		world.y = Math.round(world.y);
		world.z = Math.round(world.z);
		//System.out.println("Global Movement: " + world.toString() + " bearing " + rotation + " degrees.");
		return world;
	}
}
