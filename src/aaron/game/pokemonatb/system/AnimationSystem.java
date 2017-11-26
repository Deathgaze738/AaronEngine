package aaron.game.pokemonatb.system;

import java.util.ArrayList;
import java.util.List;

import aaron.game.pokemonatb.component.AnimationComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.ImageComponent;
import aaron.game.pokemonatb.component.StateComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.main.ECSEngine;

public class AnimationSystem extends GameSystemBase{
	

	public AnimationSystem(ECSEngine engine) {
		super(engine);
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList = new ArrayList<Class<? extends Component>>();
		cList.add(StateComponent.class);
		cList.add(AnimationComponent.class);
		cList.add(ImageComponent.class);
		cList.add(TransformComponent.class);
		addRequirements(req, cList);
	}

	@Override
	public void update() {
		for(int entity : getEntities(0)){
			StateComponent state = (StateComponent) engine.getComponent(entity, StateComponent.class);
			AnimationComponent animation = (AnimationComponent) engine.getComponent(entity, AnimationComponent.class);
			ImageComponent sprite = (ImageComponent) engine.getComponent(entity, ImageComponent.class);
			TransformComponent rotation = (TransformComponent) engine.getComponent(entity, TransformComponent.class);
			if(state.stateChange){
				animation.currentFrame = 0;
				animation.currentTick = 0;
				sprite.sprite = animation.animations.get(state.state+"#"+(int)rotation.rotation).get(0);
				//animation.currentFrame++;
				//System.out.println("STATE CHANGE");
			}
			else{
				if(animation.currentTick == animation.frameRate){
					//System.out.println(animation.animations.get(state.state+"#"+rotation.rotation).size() - 1);
					if(animation.currentFrame > animation.animations.get(state.state+"#"+(int)rotation.rotation).size() - 1){
						animation.currentFrame = 0;
						animation.currentTick = 0;
						sprite.sprite = animation.animations.get(state.state+"#"+(int)rotation.rotation).get(animation.currentFrame);
						animation.currentFrame++;
					}
					else{
						
						sprite.sprite = animation.animations.get(state.state+"#"+(int)rotation.rotation).get(animation.currentFrame);
						animation.currentFrame++;
						animation.currentTick = 0;
					}
					//System.out.println(animation.currentFrame);
				}
			}
			animation.currentTick++;
		}
	}
}
