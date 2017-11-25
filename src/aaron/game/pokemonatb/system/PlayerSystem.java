package aaron.game.pokemonatb.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.InputComponent;
import aaron.game.pokemonatb.component.InputState;
import aaron.game.pokemonatb.component.MovementComponent;
import aaron.game.pokemonatb.component.State;
import aaron.game.pokemonatb.component.StateComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.component.PhysicsComponent;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.manager.SoundManager;

public class PlayerSystem extends GameSystemBase{
	SoundManager sm = SoundManager.getInstance();
	private static Map<InputState, Float> stateToRotation;
	State lastState = null;
	float lastRotation = 360;

	public PlayerSystem(ECSEngine engine) {
		super(engine);
		stateToRotation = new HashMap<>();
		stateToRotation.put(InputState.UP_HOLD, 0f);
		stateToRotation.put(InputState.RIGHT_HOLD, 90f);
		stateToRotation.put(InputState.DOWN_HOLD, 180f);
		stateToRotation.put(InputState.LEFT_HOLD, 270f);
		stateToRotation.put(InputState.UP_PRESS, 0f);
		stateToRotation.put(InputState.RIGHT_PRESS, 90f);
		stateToRotation.put(InputState.DOWN_PRESS, 180f);
		stateToRotation.put(InputState.LEFT_PRESS, 270f);
		
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<>();
		cList.add(InputComponent.class);
		cList.add(StateComponent.class);
		cList.add(TransformComponent.class);
		addRequirements(req, cList);
	}

	@Override
	public void update() {
		StateComponent state;
		InputComponent input;
		TransformComponent transform;
		
		for(int entity : getEntities(0)){
			state = engine.getComponent(entity, StateComponent.class);
			input = engine.getComponent(entity, InputComponent.class);
			transform = engine.getComponent(entity, TransformComponent.class);
			if(state.state == State.IDLE){
				idleHandler(entity, input, state, transform);
			}
			else if(state.state == State.WALKING){
				walkingHandler(entity, input, state, transform);
			}
			if(lastState != state.state || transform.rotation != lastRotation){
				//System.out.println("STATE CHANGE PLAYER");
				state.stateChange = true;
			}
			else{
				state.stateChange = false;
			}
			
			lastState = state.state;
			lastRotation = transform.rotation;
			//System.out.println("Current State: " + state.state.toString());
		}
	}
	
	private void walkingHandler(int entity, InputComponent input, StateComponent state, TransformComponent transform){
		if(InputState.holdInputs.contains(input.state)){
			state.state = State.WALKING;
			MovementComponent movement = new MovementComponent(false);
			movement.translate.add(0f, 1f, 0f);
			engine.addComponent(entity, movement);
			//System.out.println("UPDATED");
		}
		else{
			state.state = State.IDLE;
		}
	}
	
	private void idleHandler(int entity, InputComponent input, StateComponent state, TransformComponent transform){
		if(stateToRotation.get(input.state) == null)
			return;
		if(transform.rotation == stateToRotation.get(input.state)){
			state.state = State.WALKING;
			transform.rotation = stateToRotation.get(input.state);
			MovementComponent movement = new MovementComponent(false);
			movement.translate.add(0f, 1f, 0f);
			engine.addComponent(entity, movement);
			//System.out.println("WALKING");
		}
		else{
			if(InputState.pressInputs.contains(input.state) || InputState.holdInputs.contains(input.state)){
				transform.rotation = stateToRotation.get(input.state);
				//System.out.println("ROTATE");
			}
		}
	}
}
