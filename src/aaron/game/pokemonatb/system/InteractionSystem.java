package aaron.game.pokemonatb.system;

import java.util.ArrayList;
import java.util.List;

import aaron.game.pokemonatb.component.ActiveComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.InputComponent;
import aaron.game.pokemonatb.component.InputState;
import aaron.game.pokemonatb.component.InteractibleComponent;
import aaron.game.pokemonatb.component.TilePositionComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.main.ECSEngine;

public class InteractionSystem extends GameSystemBase{

	public InteractionSystem(ECSEngine engine) {
		super(engine);
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<Class<? extends Component>>();
		cList.add(InputComponent.class);
		cList.add(TilePositionComponent.class);
		cList.add(TransformComponent.class);
		addRequirements(req, cList);
		cList = new ArrayList<Class<? extends Component>>();
		cList.add(TilePositionComponent.class);
		cList.add(InteractibleComponent.class);
		addRequirements(req, cList);
	}

	@Override
	public void update(){
		List<Integer> players = getEntities(0);
		List<Integer> interactibles = getEntities(1);
		for(int player : players){
			InputComponent pIn = engine.getComponent(player, InputComponent.class);
			TilePositionComponent pPos = engine.getComponent(player, TilePositionComponent.class);
			TransformComponent pRot = engine.getComponent(player, TransformComponent.class);
			if((pIn.state == InputState.INTERACT_HOLD || pIn.state == InputState.INTERACT_PRESS)){
				int x = (int) Math.cos(Math.toRadians((double)pRot.rotation) + Math.toRadians(270.0));
				int y = (int) Math.sin(Math.toRadians((double)pRot.rotation) + Math.toRadians(270.0));
				for(Integer interactible : interactibles){
					TilePositionComponent iPos = engine.getComponent(interactible, TilePositionComponent.class);
					if(iPos.xTile == pPos.xTile + x && iPos.yTile == pPos.yTile + y){
						engine.addComponent(interactible, new ActiveComponent());
						engine.removeComponent(interactible, InteractibleComponent.class);
						pIn.used = true;
						break;
					}
						//System.out.println("R: " + pRot.rotation);
						//System.out.println("Y: " + y);
						//System.out.println("X: " + x);
				}
			}
		}
	}
}
