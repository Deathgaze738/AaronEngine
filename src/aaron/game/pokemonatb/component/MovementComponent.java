package aaron.game.pokemonatb.component;

import utils.Vector3D;

public class MovementComponent extends Component {
	public Vector3D translate;
	public float rotate;
	public boolean worldAxis;
	
	public MovementComponent(boolean worldAxis){
		translate = new Vector3D(Vector3D.ZERO);
		rotate = 0f;
		this.worldAxis = worldAxis;
	}
}
