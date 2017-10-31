package aaron.game.pokemonatb.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import aaron.game.pokemonatb.component.ActiveComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.InputComponent;
import aaron.game.pokemonatb.component.InteractibleComponent;
import aaron.game.pokemonatb.component.RenderComponent;
import aaron.game.pokemonatb.component.ImageComponent;
import aaron.game.pokemonatb.component.TextComponent;
import aaron.game.pokemonatb.component.TilePositionComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.main.GamePanel;
import aaron.game.pokemonatb.manager.GameStateManager;
import aaron.game.pokemonatb.manager.ResourceManager;
import aaron.game.pokemonatb.manager.SoundManager;
import aaron.game.pokemonatb.system.AnimationSystem;
import aaron.game.pokemonatb.system.EntityRenderSystem;
import aaron.game.pokemonatb.system.MovementSystem;
import aaron.game.pokemonatb.system.TextRenderSystem;

public class BattleGameState extends BaseGameState{
	
	//Need a way to sort out entities and systems between game states.
	ECSEngine engine;
	boolean introFlag = true;
	int playerid;
	int opponentid;

	public BattleGameState(GameStateManager sm) {
		super(sm);
	}

	@Override
	public void initialize() {
		System.out.println("BATTLE STATE");
		SoundManager.getInstance().addClip("Resources\\Sounds\\battle.wav", "battle");
		SoundManager.getInstance().playClip("battle", SoundManager.LOOP);
		
		//BE SURE TO IMPLEMENT A FUCKING BINARY STREAM FORMAT FOR LOADING YOUR SHIT IN BOIIIIIII
		engine =  new ECSEngine();
		EntityRenderSystem rs = new EntityRenderSystem(engine);
		AnimationSystem as = new AnimationSystem(engine);
		MovementSystem ms = new MovementSystem(engine);
		TextRenderSystem trs = new TextRenderSystem(engine);
		
		List<Component> sign = new ArrayList<Component>();
		List<String> text = new ArrayList<String>();
		text.add("A WILD POKÉMON APPEARED!");
		sign.add(new TextComponent(text, 2));
		sign.add(new ActiveComponent());
		sign.add(new InputComponent());
		engine.addEntity(sign);
		
		List<Component> opponent = new ArrayList<Component>();
		List<Component> player = new ArrayList<Component>();
		ImageComponent sOpponent = new ImageComponent(0, ResourceManager.getInstance().getImage("Resources\\Images\\charmander.png"), 100, 100);
		ImageComponent sPlayer = new ImageComponent(0, ResourceManager.getInstance().getImage("Resources\\Images\\typhlosion_back.png"), 100, 100);
		TransformComponent tOpponent = new TransformComponent(GamePanel.WIDTH, 0);
		TransformComponent tPlayer = new TransformComponent(-100, GamePanel.HEIGHT - 160);
		opponent.add(sOpponent);
		opponent.add(tOpponent);
		opponentid = engine.addEntity(opponent);
		player.add(sPlayer);
		player.add(tPlayer);
		playerid = engine.addEntity(player);
		engine.addSystem(rs, 998);
		engine.addSystem(trs, 999);
		engine.addSystem(as, 2);
		engine.addSystem(ms, 3);
	}

	@Override
	public void update() {
		if(introFlag){
			updateScroll();
		}
		
		engine.update();
	}
	
	private void updateScroll(){
		TransformComponent tOpponent = engine.getComponent(opponentid, TransformComponent.class);
		TransformComponent tPlayer = engine.getComponent(playerid, TransformComponent.class);
		System.out.println(tOpponent.xPixel);
		System.out.println(tPlayer.xPixel);
		if(tOpponent.xPixel > GamePanel.WIDTH - 100 && tPlayer.xPixel < 0){
				tOpponent.xPixel--;
				tPlayer.xPixel++;
		}		
		else{
			introFlag = !introFlag;
		}
		

	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		engine.draw(g);
	}

	@Override
	public void inputHandler() {
		// TODO Auto-generated method stub
		
	}

}
