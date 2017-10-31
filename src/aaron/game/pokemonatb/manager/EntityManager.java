package aaron.game.pokemonatb.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import aaron.game.pokemonatb.component.Component;

public class EntityManager{
	private static int entityNumber = 0;
	private Map<Class<? extends Component>, Map<Integer, Component>> entities = new HashMap<Class<? extends Component>, Map<Integer, Component>>();
	private List<Integer> activeEntities = new ArrayList<Integer>();
	
	public int addEntity(List<Component> components){
		int entityID = getNextID();
		activeEntities.add(entityID);
		addComponents(components, entityID);
		System.out.println("Added new Entity: " +  entityID);
		return entityID;
	}
	
	private void addComponents(List<Component> components, int entityID){
		for(Component component : components){
			addComponent(entityID, component);
		}
	}
	
	public void addComponent(int entityID, Component component){
		if(!entities.containsKey(component.getClass())){
			entities.put(component.getClass(), new HashMap<Integer, Component>());
		}
		//System.out.println(entities);
		entities.get(component.getClass()).put(entityID, component);
	}
	
	public void removeComponent(int entity, Class<? extends Component> type){
		entities.get(type).remove(entity);
	}
	
	public void deleteEntity(int entity){
		for(Map<Integer, Component> components : entities.values()){
			components.remove(entity);
		}
	}
	
	//Provides next available ID
	private Integer getNextID(){
		entityNumber++;
		return entityNumber;
	}
	
	public Map<Class<? extends Component>, Map<Integer, Component>> getComponents(){
		return entities;
	}
	
	public List<Integer> getEntities(){
		return activeEntities;
	}
	
	public Map<Class<? extends Component>, Component> getEntity(int entityid){
		Map<Class<? extends Component>, Component> components = new HashMap<Class<? extends Component>, Component>();
		for(Entry<Class<? extends Component>, Map<Integer, Component>> entity : entities.entrySet()){
			Component component = entity.getValue().get(entityid);
			if(component != null){
				components.put(component.getClass(), component);
			}
		}
		return components;
	}
	
	public <T extends Component> T getComponent(int entity, Class<? extends Component> classType){
		return entities.get(classType) != null ? (entities.get(classType).containsKey(entity) ? (T)entities.get(classType).get(entity) : null) : null;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Entity List\n");
		sb.append("ID \t\t\t\tComponents\n");
		
		for(int i : activeEntities){
			sb.append("[" + i + "]\t\t\t\t");
			for(Map<Integer, Component> entityComponents : entities.values()){
				if(entityComponents.get(i) != null){
					sb.append("[" + entityComponents.get(i).toString() + "]\t\t\t\t");
					boolean first = true;
					Component c = entityComponents.get(i);
					if(first){
						if(c != null){
							sb.append(c.getClass());
							first = false;
						}
					}
					else sb.append("\t\t\t\t" + c.getClass());
				}
				sb.append("\n");
			}
		}
		sb.append("\n");
		return sb.toString();
	}
}
