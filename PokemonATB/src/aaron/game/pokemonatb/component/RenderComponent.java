package aaron.game.pokemonatb.component;

public abstract class RenderComponent extends Component {
	public int layer;
	
	public RenderComponent(int layer){
		this.layer = layer;
	}
}
