package aaron.game.pokemonatb.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.InputComponent;
import aaron.game.pokemonatb.component.InputState;
import aaron.game.pokemonatb.component.RotationComponent;
import aaron.game.pokemonatb.component.State;
import aaron.game.pokemonatb.component.StateComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.component.WalkingComponent;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.manager.SoundManager;

public class PlayerSystem extends GameSystemBase{
	SoundManager sm = SoundManager.getInstance();
	private static Map<InputState, Integer> stateToRotation;
	State lastState = null;
	int lastRotation = 360;

	public PlayerSystem(ECSEngine engine) {
		super(engine);
		stateToRotation = new HashMap<InputState, Integer>();
		stateToRotation.put(InputState.UP_HOLD, 0);
		stateToRotation.put(InputState.RIGHT_HOLD, 90);
		stateToRotation.put(InputState.DOWN_HOLD, 180);
		stateToRotation.put(InputState.LEFT_HOLD, 270);
		stateToRotation.put(InputState.UP_PRESS, 0);
		stateToRotation.put(InputState.RIGHT_PRESS, 90);
		stateToRotation.put(InputState.DOWN_PRESS, 180);
		stateToRotation.put(InputState.LEFT_PRESS, 270);
		
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<Class<? extends Component>>();
		cList.add(InputComponent.class);
		cList.add(StateComponent.class);
		cList.add(RotationComponent.class);
		cList.add(TransformComponent.class);
		addRequirements(req, cList);
	}

	@Override
	public void update() {
		StateComponent state;
		InputComponent input;
		RotationComponent rotation;
		TransformComponent transform;
		
		for(int entity : getEntities(0)){
			state = engine.getComponent(entity, StateComponent.class);
			input = engine.getComponent(entity, InputComponent.class);
			rotation = engine.getComponent(entity, RotationComponent.class);
			transform = engine.getComponent(entity, TransformComponent.class);
			if(state.state == State.IDLE){
				idleHandler(entity, input, rotation, state, transform);
			}
			else if(state.state == State.WALKING){
				walkingHandler(entity, input, rotation, state, transform);
			}
			if(lastState != state.state || rotation.rotation != lastRotation){
				//System.out.println("STATE CHANGE PLAYER");
				state.stateChange = true;
			}
			else{
				state.stateChange = false;
			}
			
			lastState = state.state;
			lastRotation = rotation.rotation;
			//System.out.println("Player: " + transform.xPixel + ", " + transform.yPixel);
			//System.out.println("Current State: " + state.state.toString());
			//System.out.println("Movement: " + transform.move);
		}
	}
	
	private void walkingHandler(int entity, InputComponent input, RotationComponent rotation, StateComponent state, TransformComponent transform){
		if(InputState.holdInputs.contains(input.state)){
			state.state = State.WALKING;
			if(!engine.hasComponent(entity, WalkingComponent.class)){
				transform.move = 1;
				//System.out.println("UPDATED");
				rotation.rotation = stateToRotation.get(input.state);
			}
			//System.out.println("WALKING");
		}
		else if(!engine.hasComponent(entity, WalkingComponent.class)){
			state.state = State.IDLE;
		}
	}
	
	private void idleHandler(int entity, InputComponent input, RotationComponent rotation, StateComponent state, TransformComponent transform){
		if(transform.move > 0){
			System.out.println("WALKING");
			state.state = State.WALKING;
		}	
		if(stateToRotation.get(input.state) == null)
			return;
		if(rotation.rotation == stateToRotation.get(input.state)){
			state.state = State.WALKING;
			rotation.rotation = stateToRotation.get(input.state);
			transform.move = 1;
			//System.out.println("WALKING");
		}
		else{
			if(InputState.pressInputs.contains(input.state) || InputState.holdInputs.contains(input.state)){
				rotation.rotation = stateToRotation.get(input.state);
				//System.out.println("ROTATE");
			}
		}
	}
}
