package aaron.game.pokemonatb.system;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utils.Tuple;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.manager.EntityManager;

public abstract class GameSystemBase implements GameSystemInterface {
	protected List<Tuple<SysRequirement, List<Class<? extends Component>>>> componentRequirements;
	protected int numberOfEntities = 0;
	
	//Requirement to Entities
	private Map<Integer, List<Integer>> entities;
	protected ECSEngine engine;
	
	public GameSystemBase(ECSEngine engine){
		entities = new HashMap<Integer, List<Integer>>();
		componentRequirements = new ArrayList<Tuple<SysRequirement, List<Class<? extends Component>>>>();
		this.engine = engine;
	}
	
	protected void addRequirements(SysRequirement req, List<Class<? extends Component>> cList){
		Tuple<SysRequirement, List<Class<? extends Component>>> requirements = new Tuple<SysRequirement, List<Class<? extends Component>>>(req, cList);
		componentRequirements.add(requirements);
		entities.put(componentRequirements.indexOf(requirements), new ArrayList<Integer>());
	}
	
	protected List<Integer> getEntities(){
		List<Integer> allEntities = new ArrayList<Integer>();
		for(Entry<Integer, List<Integer>> entity : entities.entrySet()){
			allEntities.addAll(entity.getValue());
		}
		return allEntities;
	}
	
	public int getNumberOfEntities(){
		return getEntities().size();
	}
	
	public int getNumberOfEntities(int requirement){
		return getEntities(requirement).size();
	}
	
	protected List<Integer> getEntities(int requirementsNo){
		return new ArrayList<Integer>(entities.get(requirementsNo));
	}
	
	protected Integer getFirstEntity(int requirementsNo){
		if(entities.get(requirementsNo).size() > 0)
			return entities.get(requirementsNo).get(0);
		return null;
	}
	
	public void registerEntity(int entityID, Map<Class<? extends Component>, Component> components){
		boolean flag;
		//System.out.println(entityID);
		//System.out.println(components.keySet());
		for(Tuple<SysRequirement, List<Class<? extends Component>>> requirements : componentRequirements){
			switch (requirements.getLeft()){
				default:
					flag = false;
					break;
				case AllOf:
					flag = allOfRequirements(components, requirements.getRight());
					break;
				case OneOf:
					flag = oneOfRequirements(components, requirements.getRight());
					break;
			}
			if(flag){
				flag = false;
				//System.out.println(entityID + " REGISTERED");
				entities.get(componentRequirements.indexOf(requirements)).add(entityID);
			}
			else{
				//System.out.println(entityID + " NOT REGISTERED");
			}
		}
	}
	
	public List<Tuple<SysRequirement, List<Class<? extends Component>>>> getCompRequirements(){
		return componentRequirements;
	}
	
	private boolean allOfRequirements(Map<Class<? extends Component>, Component> components, List<Class<? extends Component>> list){
		//System.out.println("Components: " + components.keySet());
		//System.out.println("Requirements: " + list);
		if(components.keySet().containsAll(list)){
			return true;
		}
		return false;
	}
	
	private boolean oneOfRequirements(Map<Class<? extends Component>, Component> components, List<Class<? extends Component>> list){
		if(list.contains(components.keySet())){
			return true;
		}
		return false;
	}
	
	public void removeEntity(int entityID){
		for(List<Integer> entityList : entities.values()){
			entityList.remove(entityID);
		}
	}
	
	public void addComponent(int entityID, EntityManager em){
		List<Integer> entities = getEntities();
		if(!entities.contains(entityID)){
			registerEntity(entityID, em.getEntity(entityID));
		}
	}
	
	public void removeComponent(Integer entityID, Class<? extends Component> classType){
		for(Tuple<SysRequirement, List<Class<? extends Component>>> requirement : componentRequirements){
			if(requirement.getRight().contains(classType)){
				if(requirement.getLeft() == SysRequirement.AllOf){
					entities.get(componentRequirements.indexOf(requirement)).remove(entityID);
				}
				else if(requirement.getLeft() == SysRequirement.OneOf){
					//Implement OneOf component update.
				}
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g){
		
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Requirements : ");
		for(Tuple<SysRequirement, List<Class<? extends Component>>> requirements : componentRequirements){
			sb.append(requirements.getLeft().toString() + "\n");
			for(Class<? extends Component> type : requirements.getRight()){
				sb.append(type.toString() + "\n");
			}
			sb.append("Registered Entities: \n");
			boolean first = true;
			sb.append("[");
			for(int e : entities.get(componentRequirements.indexOf(requirements))){
				if(first){
					sb.append(e);
					first = false;
				}
				else{
					sb.append(e + ", ");
				}
			}
			sb.append("]");
		}
		return sb.toString();
	}
}
