package utils;

import java.awt.geom.Rectangle2D;
import java.util.List;

public interface IAABBTree {

	public Node insert(AABB aabb);
	public void remove(Node node);
	public void update(int entity);
	public List<AABB> query(Node root, AABB aabb, List<AABB> intersections);
}
