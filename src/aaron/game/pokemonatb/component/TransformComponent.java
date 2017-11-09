package aaron.game.pokemonatb.component;

public class TransformComponent extends Component{
	public int xPixel;
	public int yPixel;
	public int xSize = 0;
	public int ySize = 0;
	public int move = 0;
	public int walkingTick = 0;
	
	public TransformComponent(int xPixel, int yPixel) {
		super();
		this.xPixel = xPixel;
		this.yPixel = yPixel;
	}
	
	public TransformComponent(int xPixel, int yPixel, int xSize, int ySize){
		super();
		this.xPixel = xPixel;
		this.yPixel = yPixel;
		this.xSize = xSize;
		this.ySize = ySize;
	}
}
