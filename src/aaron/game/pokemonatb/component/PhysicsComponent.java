package aaron.game.pokemonatb.component;

import utils.Vector3D;

public class PhysicsComponent extends Component {

	public Vector3D velocity;
	public Vector3D angularVelocity;
	public Vector3D force;
	public Vector3D angularForce;
	public float mass;
	public float drag;
	public boolean isGravity;

	public PhysicsComponent() {
		velocity = Vector3D.ZERO;
		angularVelocity = Vector3D.ZERO;
		force = Vector3D.ZERO;
		angularForce = Vector3D.ZERO;
		mass = 1f;
		drag =0f;
		isGravity = true;
	}
}
