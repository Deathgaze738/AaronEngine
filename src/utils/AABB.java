package utils;

import java.awt.geom.Rectangle2D;

import aaron.game.pokemonatb.component.BoxColliderComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.TransformComponent;

public class AABB {
	int entity;
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
		this.aabb = new Rectangle2D.Float(position.position.x, position.position.y, position.position.x + width, position.position.y + height);
	}
}
