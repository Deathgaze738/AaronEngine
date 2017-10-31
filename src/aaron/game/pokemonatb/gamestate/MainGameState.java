package aaron.game.pokemonatb.gamestate;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Pokemon;
import utils.Tuple;
import aaron.game.pokemonatb.component.ActiveComponent;
import aaron.game.pokemonatb.component.AnimationComponent;
import aaron.game.pokemonatb.component.BackpackComponent;
import aaron.game.pokemonatb.component.CameraComponent;
import aaron.game.pokemonatb.component.InputComponent;
import aaron.game.pokemonatb.component.InteractibleComponent;
import aaron.game.pokemonatb.component.RenderComponent;
import aaron.game.pokemonatb.component.RotationComponent;
import aaron.game.pokemonatb.component.State;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.TilePositionComponent;
import aaron.game.pokemonatb.component.TransformComponent;
import aaron.game.pokemonatb.component.ImageComponent;
import aaron.game.pokemonatb.component.StateComponent;
import aaron.game.pokemonatb.component.TextComponent;
import aaron.game.pokemonatb.component.TileMapComponent;
import aaron.game.pokemonatb.component.WarpComponent;
import aaron.game.pokemonatb.component.WarpableComponent;
import aaron.game.pokemonatb.factory.TileMapFactory;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.main.GamePanel;
import aaron.game.pokemonatb.manager.EntityManager;
import aaron.game.pokemonatb.manager.GameStateManager;
import aaron.game.pokemonatb.manager.KeyManager;
import aaron.game.pokemonatb.manager.ResourceManager;
import aaron.game.pokemonatb.manager.SoundManager;
import aaron.game.pokemonatb.manager.SystemManager;
import aaron.game.pokemonatb.map.GameMap;
import aaron.game.pokemonatb.system.AnimationSystem;
import aaron.game.pokemonatb.system.CameraSystem;
import aaron.game.pokemonatb.system.CollisionSystem;
import aaron.game.pokemonatb.system.InputSystem;
import aaron.game.pokemonatb.system.InteractionSystem;
import aaron.game.pokemonatb.system.MapRenderSystem;
import aaron.game.pokemonatb.system.MovementSystem;
import aaron.game.pokemonatb.system.PlayerSystem;
import aaron.game.pokemonatb.system.PlayerSystem;
import aaron.game.pokemonatb.system.EntityRenderSystem;
import aaron.game.pokemonatb.system.EntityRenderSystem;
import aaron.game.pokemonatb.system.TextRenderSystem;
import aaron.game.pokemonatb.system.WarpSystem;

public class MainGameState extends BaseGameState {
	
																																																																													//Map Info Her
	private ResourceManager rm = ResourceManager.getInstance();
	private ECSEngine engine;
	
	private int currentMap = 0;
	private int cameraX = 15;
	private int cameraY = 15;
	private int cameraSize = 17;
	
	private int xTile = (int) ((cameraX  * GameMap.TILE_SIZE) + (Math.ceil((cameraSize * GameMap.TILE_SIZE)/2))) - GameMap.TILE_SIZE/2;
	private int yTile = (int) ((cameraY  * GameMap.TILE_SIZE) + (Math.ceil((cameraSize * GameMap.TILE_SIZE)/2))) - GameMap.TILE_SIZE/2;
	
	
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
		engine.addEntity(getWorld());
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
		
		EntityRenderSystem rs = new EntityRenderSystem(engine);
		MapRenderSystem maps = new MapRenderSystem(engine);
		PlayerSystem ps = new PlayerSystem(engine);
		MovementSystem ms = new MovementSystem(engine);
		InputSystem ins = new InputSystem(engine);
		CameraSystem cs = new CameraSystem(engine);
		AnimationSystem as = new AnimationSystem(engine);
		WarpSystem ws = new WarpSystem(engine);
		TextRenderSystem trs = new TextRenderSystem(engine);
		InteractionSystem is = new InteractionSystem(engine);
		CollisionSystem cos = new CollisionSystem(engine);
		
		engine.addSystem(maps, 996);
		engine.addSystem(rs, 997);
		engine.addSystem(trs, 998);
		engine.addSystem(ins, 1);
		engine.addSystem(ps, 2);
		engine.addSystem(as, 4);
		engine.addSystem(cos, 5);
		engine.addSystem(ms, 6);
		engine.addSystem(cs, 7);
		engine.addSystem(ws, 3);
		engine.addSystem(is, 8);
		SoundManager.getInstance().addClip("C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources\\Sounds\\pallet_town.wav", "music");
		SoundManager.getInstance().playClip("music", SoundManager.LOOP);
	}
	
	private List<Component> getWorld(){
		List<Component> world = new ArrayList<Component>();
		TileMapComponent map = TileMapFactory.tileMapFromFile(currentMap, 0, TileMapComponent.ORTHOGONAL, 16);
		world.add(map);
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
		characterEnt.add(new RotationComponent(180));
		characterEnt.add(new WarpableComponent());
		characterEnt.add(new CameraComponent(cameraX, cameraY, GamePanel.WIDTH, GamePanel.HEIGHT));
		characterEnt.add(new StateComponent(State.IDLE));
		characterEnt.add(new InputComponent());
		characterEnt.add(new TransformComponent(xTile, yTile));
		int animationTime = 8;
		String animationSheet = "C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources\\Images\\character.png";
		AnimationComponent animation = new AnimationComponent(animationTime);
		animation.addAnimationState(State.WALKING, 0, rm.getAnimation(animationSheet, new int[]{9, 8, 10, 8}, 1));
		animation.addAnimationState(State.WALKING, 90, rm.getAnimation(animationSheet, new int[]{2, 1}, 1));
		animation.addAnimationState(State.WALKING, 180, rm.getAnimation(animationSheet, new int[]{6, 5, 7, 5}, 1));
		animation.addAnimationState(State.WALKING, 270, rm.getAnimation(animationSheet, new int[]{4, 3}, 1));
		animation.addAnimationState(State.IDLE, 0, rm.getAnimation(animationSheet, new int[]{8}, 1));
		animation.addAnimationState(State.IDLE, 90, rm.getAnimation(animationSheet, new int[]{1}, 1));
		animation.addAnimationState(State.IDLE, 180, rm.getAnimation(animationSheet, new int[]{5}, 1));
		animation.addAnimationState(State.IDLE, 270, rm.getAnimation(animationSheet, new int[]{3}, 1));
		characterEnt.add(animation);
		characterEnt.add(new ImageComponent(0, rm.getSprite(animationSheet, 5, 0)));
		
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
	
	private List<Component> getAnimationTester(){
		List<Component> characterEnt = new ArrayList<Component>();
		characterEnt.add(new TransformComponent(5, 5));
		characterEnt.add(new RotationComponent(180));
		characterEnt.add(new StateComponent(State.WALKING));
		String animationSheet = "C:\\Users\\Aaron\\Desktop\\PokemonATB\\PokemonATB\\Resources\\Images\\character.png";
		int animationTime = 10;
		AnimationComponent animation = new AnimationComponent(animationTime);
		animation.addAnimationState(State.WALKING, 180, rm.getAnimation(animationSheet, new int[]{6, 5, 7, 5}, 1));
		characterEnt.add(animation);
		characterEnt.add(new ImageComponent(0, rm.getSprite(animationSheet, 5, 1)));
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
			//System.out.println(em.toString());
			//System.out.println(sm.toString());
			//PositionComponent pos = (PositionComponent) em.getComponent(charID, PositionComponent.class);
			//System.out.println("Current Player Location: " + pos.xTile + " " + pos.yTile);
			//System.out.println(gameMap.toString());
		}
		if(KeyManager.isPressed(KeyManager.E)){
			SoundManager.getInstance().stop("music");
			sm.pushState(new BattleGameState(sm));
		}
	}

}
