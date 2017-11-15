package aaron.game.pokemonatb.main;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Map;

import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.manager.EntityManager;
import aaron.game.pokemonatb.manager.SystemManager;
import aaron.game.pokemonatb.system.GameSystemBase;

public class ECSEngine {
	
	public static final int WORLD_ENTITY = 0;
	
	private EntityManager em;
	private SystemManager sm;

	public ECSEngine() {
		this.em = new EntityManager();
		this.sm = new SystemManager();
	}
	
	public int addEntity(List<Component> components){
		int entityID = em.addEntity(components);
		sm.registerWithSystems(entityID, em);
		return entityID;
	}
	
	public void addSystem(GameSystemBase s, int priority){
		sm.addSystem(s, priority, em);
	}
	
	public void deleteSystem(GameSystemBase s){
		sm.deleteSystem(s);
	}
	
	public void removeComponent(int entity, Class<? extends Component> type){
		em.removeComponent(entity, type);
		sm.removeComponent(entity, type);
	}
	
	public void deleteEntity(int entity){
		em.deleteEntity(entity);
		sm.removeEntity(entity);
	}
	
	public void addComponent(int entity, Component comp){
		em.addComponent(entity, comp);
		sm.addComponent(entity, comp, em);
	}
	
	public Map<Class<? extends Component>, Component> getEntity(int entityid){
		return em.getEntity(entityid);
	}
	
	public boolean hasComponent(int entity, Class<? extends Component> class1){
		return em.getComponent(entity, class1) != null;
	}
	
	public <T extends Component> T getComponent(int entity, Class<? extends Component> type){
		return em.getComponent(entity, type);
	}

	public void update() {
		sm.update();
	}

	public void draw(Graphics2D g) {
		sm.draw(g);
	}
	
	public String toString(){
		return sm.toString();
	}
}
