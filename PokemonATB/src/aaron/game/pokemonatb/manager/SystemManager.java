package aaron.game.pokemonatb.manager;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import utils.Tuple;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.system.GameSystemBase;
import aaron.game.pokemonatb.system.SysRequirement;

public class SystemManager {
	private Map<Integer, GameSystemBase> systems;
	Map<Class<? extends Component>, List<GameSystemBase>> listeners;
	
	public SystemManager(){
		this.listeners = new HashMap<Class<? extends Component>, List<GameSystemBase>>();
		this.systems = new TreeMap<Integer, GameSystemBase>();
	}
	
	public void addSystem(GameSystemBase s, int priority, EntityManager em){
		for(Integer entity : em.getEntities()){
			s.registerEntity(entity, em.getEntity(entity));
		}
		systems.put(priority, s);
		addListeners(s);
	}
	
	private void addListeners(GameSystemBase s){
		for(Tuple<SysRequirement, List<Class<? extends Component>>> req : s.getCompRequirements()){
			for(Class<? extends Component> classType : req.getRight()){
				if(!listeners.containsKey(classType)){
					listeners.put(classType, new ArrayList<GameSystemBase>());
				}
				listeners.get(classType).add(s);
			}
		}
	}
	
	public void deleteSystem(GameSystemBase s){
		systems.remove(s);
	}
	
	public void removeEntity(int entityID){
		for(GameSystemBase s : systems.values()){
			s.removeEntity(entityID);
		}
	}
	
	public void addComponent(int entityID, Component comp, EntityManager em){
		List<GameSystemBase> toNotify = listeners.get(comp.getClass());
		if(toNotify == null){
			//System.out.println("No Systems to notify of " + comp.getClass());
			return;
		}
		for(GameSystemBase s : toNotify){
			s.addComponent(entityID, em);
		}
	}
	
	
	public void removeComponent(int entityID, Class<? extends Component> classType){
		List<GameSystemBase> toNotify = listeners.get(classType);
		if(toNotify == null){
			//System.out.println("No Systems to notify of " + classType);
			return;
		}
		for(GameSystemBase s : toNotify){
			s.removeComponent(entityID, classType);
		}
	}
	
	public void update(){
		for(GameSystemBase s : systems.values()){
			s.update();
		}
	}
	
	public void draw(Graphics2D g){
		for(GameSystemBase s : systems.values()){
			s.draw(g);
		}
	}
	
	//Register this entity with relevant systems.
	public void registerWithSystems(int entity, EntityManager em){
		for(GameSystemBase s : systems.values()){
			s.registerEntity(entity, em.getEntity(entity));
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		StringBuilder details = new StringBuilder();
		sb.append("Priority\t\t\tSystem\n");
		int lastPriority = -1;
		for(Map.Entry<Integer, GameSystemBase> systemPriority : systems.entrySet()){
			if(systemPriority.getKey() != lastPriority){
				lastPriority = systemPriority.getKey();
				sb.append("[" + systemPriority.getKey() + "]\t\t\t\t" + systemPriority.getValue().getClass());
			}
			else{
				sb.append("\t\t\t\t" + systemPriority.getValue().getClass());
			}
			details.append(systemPriority.getValue().getClass());
			details.append("\n");
			details.append(systemPriority.getValue().toString());
			details.append("\n");
			details.append("\n");
			sb.append("\n");
		}
		sb.append("\n");
		return sb.toString() + details.toString();
	}
}
