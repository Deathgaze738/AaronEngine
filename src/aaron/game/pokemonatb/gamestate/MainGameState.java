package aaron.game.pokemonatb.gamestate;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.Pokemon;
import aaron.game.pokemonatb.component.*;
import aaron.game.pokemonatb.factory.TileMapFactory;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.manager.*;
import aaron.game.pokemonatb.system.*;

public class MainGameState extends BaseGameState {
	
																																																																													//Map Info Her
	private ResourceManager rm = ResourceManager.getInstance();
	private ECSEngine engine;
	
	private int currentMap = 0;
	private int cameraX = 15;
	private int cameraY = 15;
	private int cameraSize = 17;
	
	private int xTile = (int) ((cameraX  * 16) + (Math.ceil((cameraSize * 16)/2))) - 16/2;
	private int yTile = (int) ((cameraY  * 16) + (Math.ceil((cameraSize * 16)/2))) - 16/2;
	
	
	public MainGameState(GameStateManager mSm) {
		super(mSm);
	}
	
	public MainGameState(GameStateManager mSm, int mapid){
		super(mSm);
		currentMap = mapid;
	}

	@Override
	public void initialize() {
		engine = new ECSEngine();
		engine.addEntity(getWorld());
		engine.addEntity(getCharacter());
		engine.addEntity(getSign());
		engine.addEntity(getSign2());
		//em.addEntity(getAnimationTester(), sm);
		
		List<Component> warp = new ArrayList<Component>();
		warp.add(new WarpComponent(3, 4, 9, 0));
		warp.add(new TilePositionComponent(29, 24));
		engine.addEntity(warp);
		warp = new ArrayList<Component>();
		warp.add(new WarpComponent(0, 29, 24, 180));
		warp.add(new TilePositionComponent(4, 10));
		engine.addEntity(warp);
		warp = new ArrayList<Component>();
		warp.add(new WarpComponent(0, 29, 24, 180));
		warp.add(new TilePositionComponent(5, 10));
		engine.addEntity(warp);
		
		RenderSystem rs = new RenderSystem(engine);
		PlayerSystem ps = new PlayerSystem(engine);
		MovementSystem ms = new MovementSystem(engine);
		InputSystem ins = new InputSystem(engine);
		CameraSystem cs = new CameraSystem(engine);
		AnimationSystem as = new AnimationSystem(engine);
		WarpSystem ws = new WarpSystem(engine);
		TextRenderSystem trs = new TextRenderSystem(engine);
		InteractionSystem is = new InteractionSystem(engine);
		AABBTestSystem ats = new AABBTestSystem(engine);
		//CollisionSystem cos = new CollisionSystem(engine);
		
		engine.addSystem(rs, 997);
		engine.addSystem(trs, 998);
		engine.addSystem(ins, 1);
		engine.addSystem(ps, 2);
		engine.addSystem(as, 4);
		//engine.addSystem(cos, 5);
		engine.addSystem(ms, 6);
		engine.addSystem(cs, 7);
		engine.addSystem(ws, 3);
		engine.addSystem(is, 8);
		engine.addSystem(ats, 999);
		SoundManager.getInstance().addClip("Resources\\Sounds\\pallet_town.wav", "music");
		SoundManager.getInstance().playClip("music", SoundManager.LOOP);
	}
	
	private List<Component> getWorld(){
		List<Component> world = new ArrayList<Component>();
		TileMapComponent map = TileMapFactory.tileMapFromFile(currentMap, 0, TileMapComponent.ORTHOGONAL, 16);
		RenderableComponent render = new RenderableComponent(0);
		world.add(map);
		world.add(render);
		return world;
	}
	
	private List<Component> getSign(){
		List<Component> sign = new ArrayList<Component>();
		sign.add(new TilePositionComponent(30, 16));
		List<String> text = new ArrayList<String>();
		text.add("WE WILL NO LONGER SURRENDER");
		text.add("THIS COUNTRY OR ITS PEOPLE");
		text.add("TO THE FALSE SONG OF GLOBALISM.");
		text.add("HI EMMA!");
		text.add("HI EMMA!");
		sign.add(new TextComponent(text, 2));
		sign.add(new InteractibleComponent());
		return sign;
	}
	
	private List<Component> getSign2(){
		List<Component> sign = new ArrayList<Component>();
		sign.add(new TilePositionComponent(27, 24));
		List<String> text = new ArrayList<String>();
		text.add("EMMA'S HOUSE");
		text.add("\"ONLY POOKIES ALLOWED\"");
		sign.add(new TextComponent(text, 2));
		sign.add(new InteractibleComponent());
		return sign;
	}
	
	private List<Component> getCharacter(){
		List<Component> characterEnt = new ArrayList<Component>();
		characterEnt.add(new TilePositionComponent((int)(cameraX + (Math.ceil(cameraSize/2))), (int)(cameraY + (Math.ceil(cameraSize/2)))));
		characterEnt.add(new WarpableComponent());
		characterEnt.add(new CameraComponent(cameraX, cameraY, cameraSize * 16, cameraSize * 16));
		characterEnt.add(new StateComponent(State.IDLE));
		characterEnt.add(new InputComponent());
		TransformComponent transform = new TransformComponent(xTile, yTile, 1f, 180f);
		characterEnt.add(transform);
		characterEnt.add(new BoxColliderComponent(16, 16, transform));
		int animationTime = 8;
		String animationSheet = "Resources\\Images\\character.png";
		AnimationComponent animation = new AnimationComponent(animationTime);
		animation.addAnimationState(State.WALKING, 0, rm.getAnimation(animationSheet, new int[]{9, 8, 10, 8}, 1, 16));
		animation.addAnimationState(State.WALKING, 90, rm.getAnimation(animationSheet, new int[]{2, 1}, 1, 16));
		animation.addAnimationState(State.WALKING, 180, rm.getAnimation(animationSheet, new int[]{6, 5, 7, 5}, 1, 16));
		animation.addAnimationState(State.WALKING, 270, rm.getAnimation(animationSheet, new int[]{4, 3}, 1, 16));
		animation.addAnimationState(State.IDLE, 0, rm.getAnimation(animationSheet, new int[]{8}, 1, 16));
		animation.addAnimationState(State.IDLE, 90, rm.getAnimation(animationSheet, new int[]{1}, 1, 16));
		animation.addAnimationState(State.IDLE, 180, rm.getAnimation(animationSheet, new int[]{5}, 1, 16));
		animation.addAnimationState(State.IDLE, 270, rm.getAnimation(animationSheet, new int[]{3}, 1, 16));
		characterEnt.add(animation);
		characterEnt.add(new ImageComponent(0, rm.getSprite(animationSheet, 5, 0, 16)));
		RenderableComponent render = new RenderableComponent(1);
		render.x = xTile;
		render.y = yTile;
		characterEnt.add(render);
		
		characterEnt.add(getBackpack());
		
		return characterEnt;
	}
	
	private BackpackComponent getBackpack(){
		BackpackComponent backpack = new BackpackComponent();
		int[] testStats = new int[7];
		Arrays.fill(testStats, 99);
		Pokemon test = new Pokemon(0, 99, 99, 99, testStats, testStats, testStats, testStats);
		backpack.team[0] = test;
		return backpack;
	}
	
	@SuppressWarnings("unused")
	private List<Component> getAnimationTester(){
		List<Component> characterEnt = new ArrayList<Component>();
		characterEnt.add(new TransformComponent(5f, 5f, 1f, 180f));
		characterEnt.add(new StateComponent(State.WALKING));
		String animationSheet = "Resources\\Images\\character.png";
		int animationTime = 10;
		AnimationComponent animation = new AnimationComponent(animationTime);
		animation.addAnimationState(State.WALKING, 180, rm.getAnimation(animationSheet, new int[]{6, 5, 7, 5}, 1, 16));
		characterEnt.add(animation);
		characterEnt.add(new ImageComponent(0, rm.getSprite(animationSheet, 5, 1, 16)));
		return characterEnt;
	}

	@Override
	public void update() {
		inputHandler();
		engine.update();
	}

	@Override
	public void draw(Graphics2D g) {
		engine.draw(g);
	}

	@Override
	public void inputHandler() {
		if(KeyManager.isPressed(KeyManager.CONSOLE_KEY)){
			System.out.println(engine.toString());
			//System.out.println(sm.toString());
			//PositionComponent pos = (PositionComponent) em.getComponent(charID, PositionComponent.class);
			//System.out.println("Current Player Location: " + pos.xTile + " " + pos.yTile);
			//System.out.println(gameMap.toString());
		}
		if(KeyManager.isPressed(KeyManager.E)){
			SoundManager.getInstance().stop("music");
			//sm.pushState(new BattleGameState(sm));
		}
	}

}
