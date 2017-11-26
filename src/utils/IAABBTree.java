package utils;

import java.util.List;

public interface IAABBTree {

	public Node insert(Node node);
	public void remove(Node node);
	public void update();
	public List<AABB> query(Node root, AABB aabb, List<AABB> intersections);
}
