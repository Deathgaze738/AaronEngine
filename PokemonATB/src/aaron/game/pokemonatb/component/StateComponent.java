package aaron.game.pokemonatb.component;

public class StateComponent extends Component{
	public State state;
	public boolean stateChange = false;
	
	public StateComponent(State mState){
		state = mState;
	}
}
