package aaron.game.pokemonatb.component;
import java.awt.geom.Rectangle2D;


public class BoxColliderComponent extends Component {
	public Rectangle2D.Float bounds;
	
	public BoxColliderComponent(Rectangle2D.Float bounds){
		this.bounds = bounds;
	}
	
	public BoxColliderComponent(float x1, float y1, float x2, float y2){
		this(new Rectangle2D.Float(x1, y1, x2, y2));
	}
	
	public BoxColliderComponent(float xSize, float ySize, TransformComponent transform){
		this(new Rectangle2D.Float(transform.position.x, transform.position.y, xSize, ySize));
	}
}
