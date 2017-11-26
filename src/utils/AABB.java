package utils;

import java.awt.geom.Rectangle2D;

import aaron.game.pokemonatb.component.BoxColliderComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.TransformComponent;

public class AABB {
	int entity = 0;
	TransformComponent position;
	Rectangle2D.Float aabb;
	
	public AABB(int entity, TransformComponent position){
		this.entity = entity;
		this.position = position;
	}
	
	public AABB(int entity, TransformComponent position, BoxColliderComponent collider){
		this(entity, position);
		float cHeight = collider.bounds.height;
		float cWidth = collider.bounds.width;
		float height = (float) ((cWidth * Math.sin(position.rotation)) + (cHeight * Math.cos(position.rotation)));
		float width = (float) ((cHeight * Math.sin(position.rotation)) + (cWidth * Math.cos(position.rotation)));
		this.aabb = new Rectangle2D.Float(position.position.x, position.position.y, width, height);
	}
	
	public float getPerimeter(){
		return (aabb.height * 2) + (aabb.width * 2);
	}
	
	public AABB(AABB aabb1, AABB aabb2){
		this.entity = 0;
		this.aabb = new Rectangle2D.Float(aabb1.aabb.x, aabb1.aabb.y, aabb1.aabb.width, aabb1.aabb.height);
		this.aabb.add(aabb2.aabb);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(entity);
		return sb.toString();
	}
	
	public int getEntity(){
		return entity;
	}
	
	public Rectangle2D.Float getRect(){
		return aabb;
	}
}
