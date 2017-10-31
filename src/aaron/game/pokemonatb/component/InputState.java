package aaron.game.pokemonatb.component;

import java.util.Arrays;
import java.util.List;

public enum InputState {
	UP_PRESS,
	RIGHT_PRESS,
	DOWN_PRESS,
	LEFT_PRESS,
	UP_HOLD,
	RIGHT_HOLD,
	DOWN_HOLD,
	LEFT_HOLD,
	INTERACT_PRESS,
	INTERACT_HOLD;
	
	public static List<InputState> holdInputs = Arrays.asList(UP_HOLD, RIGHT_HOLD, LEFT_HOLD, DOWN_HOLD, INTERACT_HOLD);
	public static List<InputState> pressInputs = Arrays.asList(UP_PRESS, RIGHT_PRESS, LEFT_PRESS, DOWN_PRESS, INTERACT_PRESS);
}
