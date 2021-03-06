package aaron.game.pokemonatb.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Tuple;
import aaron.game.pokemonatb.component.CameraComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.TilePositionComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.component.RotationComponent;
import aaron.game.pokemonatb.component.State;
import aaron.game.pokemonatb.component.StateComponent;
import aaron.game.pokemonatb.component.TileMapComponent;
import aaron.game.pokemonatb.component.WarpComponent;
import aaron.game.pokemonatb.component.WarpableComponent;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.manager.EntityManager;
import aaron.game.pokemonatb.manager.SoundManager;

public class WarpSystem extends GameSystemBase{
	
	private static final int TILE_SIZE = 16;
	
	public WarpSystem(ECSEngine engine) {
		super(engine);
		SysRequirement req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList1 = new ArrayList<Class<? extends Component>>();
		cList1.add(CameraComponent.class);
		cList1.add(TransformComponent.class);
		cList1.add(RotationComponent.class);
		cList1.add(WarpableComponent.class);
		cList1.add(TilePositionComponent.class);
		addRequirements(req, cList1);
		req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList2 = new ArrayList<Class<? extends Component>>();
		cList2.add(WarpComponent.class);
		cList2.add(TilePositionComponent.class);
		addRequirements(req, cList2);
		req = SysRequirement.AllOf;
		List<Class<? extends Component>> cList3 = new ArrayList<Class<? extends Component>>();
		cList2.add(TileMapComponent.class);
		addRequirements(req, cList3);
		SoundManager.getInstance().addClip("Resources\\Sounds\\door.wav", "door");
	}

	@Override
	public void update() {
		List<Integer> players = getEntities(0);
		List<Integer> warps = getEntities(1);
		
		//Implement Warp Fade Out Timer
		
		for(int player : players){
			TransformComponent positionComp = engine.getComponent(player, TransformComponent.class);
			RotationComponent rotationComp = engine.getComponent(player, RotationComponent.class);
			CameraComponent cameraComp = engine.getComponent(player, CameraComponent.class);
			WarpableComponent warpableComp = engine.getComponent(player, WarpableComponent.class);
			TilePositionComponent tilePosComp = engine.getComponent(player, TilePositionComponent.class);
			
			updateWarpable(tilePosComp, warpableComp);
			if (!warpableComp.active)
				continue;
			for(int warp : warps){
				TilePositionComponent warpTilePos = engine.getComponent(warp, TilePositionComponent.class);
				//System.out.println("WARP - X: " + warpTilePos.xTile + " Y: " + warpTilePos.yTile);
				if((warpTilePos.xTile == tilePosComp.xTile) && (warpTilePos.yTile == tilePosComp.yTile)){
					SoundManager.getInstance().playClip("door", SoundManager.ONCE);
					WarpComponent warpComp = engine.getComponent(warp, WarpComponent.class);
					
					tilePosComp.xTile = warpComp.xTile;
					tilePosComp.yTile = warpComp.yTile;
					positionComp.xPixel = (tilePosComp.xTile) * TILE_SIZE;
					positionComp.yPixel = (tilePosComp.yTile) * TILE_SIZE;
					cameraComp.xPos = positionComp.xPixel - (cameraComp.xSize / 2) + (TILE_SIZE / 2);
					cameraComp.yPos = positionComp.yPixel - (cameraComp.ySize / 2) + (TILE_SIZE / 2);
					rotationComp.rotation = warpComp.rotation;
					warpableComp.active = false;
					warpableComp.lastWarpPos.setLeft(warpComp.xTile);
					warpableComp.lastWarpPos.setRight(warpComp.yTile);
					positionComp.move = 1;
					break;
				}
			}
		}
	}
	
	private void updateWarpable(TilePositionComponent trans, WarpableComponent warp){
		if(!(trans.xTile == warp.lastWarpPos.getLeft() && trans.yTile == warp.lastWarpPos.getRight())){
			warp.active = true;
		}
	}
}
