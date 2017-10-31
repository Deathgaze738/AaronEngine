package aaron.game.pokemonatb.component;

public class WarpComponent extends Component{
	public int mapid;
	public int xTile;
	public int yTile;
	public int rotation;
	
	public WarpComponent(int mMapid, int mxTile, int myTile, int mRotation) {
		mapid = mMapid;
		xTile = mxTile;
		yTile = myTile;
		rotation = mRotation;
	}
}
