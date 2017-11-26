package utils;

import java.awt.geom.Rectangle2D;
import java.util.List;

public interface IAABBTree {

	public void insert(AABB aabb);
	public void remove(int entity);
	public void update(int entity);
	public List<AABB> query(Node root, AABB aabb, List<AABB> intersections);
}
