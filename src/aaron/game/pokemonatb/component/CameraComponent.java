package aaron.game.pokemonatb.component;

public class CameraComponent extends Component{
	//in pixels top left hand pixel
	public float xPos;
	public float yPos;
	public float xSize;
	public float ySize;
	
	public CameraComponent(float xPos, float yPos, float xSize, float ySize) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
	}
}
