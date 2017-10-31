package aaron.game.pokemonatb.component;

import utils.Tuple;

public class WarpableComponent extends Component{
	public boolean active = true;
	public Tuple<Integer, Integer> lastWarpPos = new Tuple<Integer, Integer>(0, 0);
	
	public WarpableComponent() {
		
	}
}
