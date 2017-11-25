package utils;

import java.awt.geom.Rectangle2D;

public interface IAABBTree {

	public void insert(int entity, Rectangle2D bounds);
	public void remove(int entity);
	public void update(int entity);
}
