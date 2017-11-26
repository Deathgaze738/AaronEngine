package aaron.game.pokemonatb.system;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import utils.AABB;
import utils.DynamicAABBTree;
import utils.Node;
import aaron.game.pokemonatb.component.BoxColliderComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.main.GamePanel;

public class AABBTestSystem extends GameSystemBase {
	
	DynamicAABBTree aabbTree;
	int limit = 20;
	int counter = 0;
	int ticksPerAction = 30;
	int currentTick = 0;
	Node lastNode;
	boolean active = true;

	public AABBTestSystem(ECSEngine engine) {
		super(engine);
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<>();
		cList.add(TransformComponent.class);
		cList.add(BoxColliderComponent.class);
		addRequirements(req, cList);
		aabbTree = new DynamicAABBTree();
	}

	@Override
	public void update() {
		if(currentTick > ticksPerAction && active){
			if(counter < limit){
				float x = (float) Math.random() * (GamePanel.WIDTH - 16);
				float y = (float) Math.random() * (GamePanel.HEIGHT - 16);
				List<Component> components = new ArrayList<>();
				TransformComponent transform = new TransformComponent(x, y, 0, 0);
				BoxColliderComponent collider = new BoxColliderComponent(16, 16, transform);
				components.add(transform);
				components.add(collider);
				int entity = engine.addEntity(components);
				AABB aabb = new AABB(entity, transform, collider);
				Node node = new Node();
				node.setData(aabb);
				//System.out.println("X: " + x + " Y: " + y);
				if(counter % 3 == 0){
					//aabbTree.remove(lastNode);
				}
				lastNode = aabbTree.insert(node);
				System.out.println(aabbTree.toString());
				currentTick = 0;
				counter++;
			}
			else{
				TransformComponent position = lastNode.getData().getTransform();
				float x = (float) Math.random() * (GamePanel.WIDTH - 16);
				float y = (float) Math.random() * (GamePanel.HEIGHT - 16);
				position.position.x = x;
				position.position.y = y;
				aabbTree.update();
				currentTick = 0;
				//System.out.println(aabbTree.toString());
				TransformComponent transform = new TransformComponent(x, y, 0, 0);
				BoxColliderComponent collider = new BoxColliderComponent(16, 16, transform);
				AABB aabb = new AABB(100, transform, collider);
				//List<AABB> intersections = new ArrayList<>();
				Node node = new Node();
				node.setData(aabb);
				//System.out.println(aabbTree.query(aabbTree.getRoot(), aabb, intersections));
			}
		}
		else{
			currentTick++;
		}
	}
	
	@Override
	public void draw(Graphics2D g){
		for(AABB rect : aabbTree.getRects()){
			g.setPaint(Color.BLACK);
			if(rect.getEntity() == 0){
				g.setPaint(Color.RED);
				g.drawString("0", (int)rect.getRect().getX(), (int)rect.getRect().getY());
			}
			g.draw(rect.getRect());
			g.drawString(Integer.toString(rect.getEntity()), (int)rect.getRect().getX(), (int)rect.getRect().getY());
		}
	}

}
