package aaron.game.pokemonatb.system;

import java.util.ArrayList;
import java.util.List;

import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.InputComponent;
import aaron.game.pokemonatb.component.InputState;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.manager.KeyManager;

public class InputSystem extends GameSystemBase {

	public InputSystem(ECSEngine engine) {
		super(engine);
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<Class<? extends Component>>();
		cList.add(InputComponent.class);
		addRequirements(req, cList);
	}

	@Override
	public void update() {
		for(int inputComp : getEntities(0)){
			InputComponent input = engine.getComponent(inputComp, InputComponent.class);
			input.state = null;
			input.used = false;
			if(KeyManager.anyKeyHeld()){
				if(KeyManager.isHeld(KeyManager.W)){
					input.state = InputState.UP_HOLD;
				}
				if(KeyManager.isHeld(KeyManager.A)){
					input.state = InputState.LEFT_HOLD;
				}
				if(KeyManager.isHeld(KeyManager.S)){
					input.state = InputState.DOWN_HOLD;
				}
				if(KeyManager.isHeld(KeyManager.D)){
					input.state = InputState.RIGHT_HOLD;
				}
				if(KeyManager.isHeld(KeyManager.SPACE)){
					input.state = InputState.INTERACT_HOLD;
				}
			}
			else if(KeyManager.anyKeyPressed()){
				if(KeyManager.isPressed(KeyManager.W)){
					input.state = InputState.UP_PRESS;
				}
				if(KeyManager.isPressed(KeyManager.A)){
					input.state = InputState.LEFT_PRESS;
				}
				if(KeyManager.isPressed(KeyManager.S)){
					input.state = InputState.DOWN_PRESS;
				}
				if(KeyManager.isPressed(KeyManager.D)){
					input.state = InputState.RIGHT_PRESS;
				}
				if(KeyManager.isPressed(KeyManager.SPACE)){
					input.state = InputState.INTERACT_PRESS ;
				}
			}
			//System.out.println(input.state);
		}
	}

}
