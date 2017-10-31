package aaron.game.pokemonatb.component;

public class CameraComponent extends Component{
	//in pixels top left hand pixel
	public int xPos;
	public int yPos;
	public int xSize;
	public int ySize;
	
	public CameraComponent(int xPos, int yPos, int xSize, int ySize) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
	}
}
