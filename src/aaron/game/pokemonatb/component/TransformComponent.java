package aaron.game.pokemonatb.component;

import utils.Vector3D;

public class TransformComponent extends Component{
	public Vector3D position;
	public float rotation;
	public Vector3D scale;
	
	public TransformComponent(float x, float y, float z, float rotation){
		super();
		this.position = new Vector3D(x, y, z);
		this.rotation = rotation;
		this.scale = new Vector3D(1f, 1f, 1f);
	}
}
