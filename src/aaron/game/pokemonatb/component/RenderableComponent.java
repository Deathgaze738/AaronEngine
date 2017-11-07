package aaron.game.pokemonatb.component;

public class RenderableComponent extends Component {
	public boolean active = true;
	public int x = 0;
	public int y = 0;
	public int layer = 0;
	public RenderableComponent(int layer){
		this.layer = layer;
	}
}
