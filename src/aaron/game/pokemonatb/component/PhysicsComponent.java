package aaron.game.pokemonatb.component;

import utils.Vector3D;

public class PhysicsComponent extends Component {

	public Vector3D velocity;
	
	public PhysicsComponent() {
		velocity = new Vector3D(0,0,0);
	}

}
