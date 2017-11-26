package utils;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class DynamicAABBTree implements IAABBTree{
	Node root = null;
	
	public DynamicAABBTree(){
		
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("-----------------------------\n");
		LinkedList<Node> nodes = new LinkedList<>();
		nodes.add(root);
		while(nodes.size() != 0){
			Node node = nodes.poll();
			sb.append(node.toString());
			if(node.getLeft() != null){
				nodes.add(node.getLeft());
			}
			if(node.getRight() != null){
				nodes.add(node.getRight());
			}
		}
		sb.append("^^^^^^^^^^^^^^^^^^^^^^^^^^^\n");
		return sb.toString();
	}
	
	@Override
	public Node insert(Node node){
		node.setParent(null);
		node.setLeft(null);
		node.setRight(null);
		if(root == null){
			//System.out.println("FIRST NODE");
			root = node;
			return node;
		}
		else{
			return insertNode(root, node);
		}	
	}
	
	private Node insertNode(Node parent, Node node){
		if(parent.isLeaf()){
			//System.out.println(parent.toString());
			Node newParent = new Node();
			newParent.setParent(parent.getParent());
			newParent.setBranch(parent, node);
			
			//System.out.println(newParent);
			if(parent != root){
				if(newParent.getParent().getLeft() == parent){
					newParent.getParent().setLeft(newParent);
				}
				else{
					newParent.getParent().setRight(newParent);
				}
			}
			else{
				root = newParent;
			}
		}
		else{
			parent = root;
			while(!parent.isLeaf()){
				AABB combinedAABB;
				combinedAABB = new AABB(node.getData(), parent.getLeft().data);
				float cost1 = combinedAABB.getPerimeter();
				
				combinedAABB = new AABB(node.getData(), parent.getRight().data);
				float cost2 = combinedAABB.getPerimeter();
				
				if(cost1 < cost2){
					//System.out.println(cost1 + " < " + cost2);
					parent = parent.getLeft();
				}
				else{
					//System.out.println(cost1 + " > " + cost2);
					parent = parent.getRight();
				}
			}
			insertNode(parent, node);
		}
		propagateUp(node);
		return node;
	}
	
	private void propagateUp(Node node){
		boolean isRoot = false;
		while(!isRoot){
			isRoot = node.isRoot();
			if(node.isLeaf()){
				//System.out.println("IS LEAF");
				node = node.getParent();
			}
			else{
				AABB totalBound = new AABB(node.getRight().data, node.getLeft().data);
				node.data = totalBound;
				node = node.getParent();
			}
		}
	}
	
	public List<AABB> getRects(){
		LinkedList<Node> nodes = new LinkedList<>();
		List<AABB> rects = new ArrayList<>();
		nodes.add(root);
		if(root == null){
			return new ArrayList<AABB>();
		}
		while(nodes.size() != 0){
			Node node = nodes.poll();
			rects.add(node.data);
			if(node.getLeft() != null){
				nodes.add(node.getLeft());
			}
			if(node.getRight() != null){
				nodes.add(node.getRight());
			}
		}
		return rects;
	}

	@Override
	public void remove(Node node) {
		//Ignore if node is null
		if(node == null){
			return;
		}
		Node parent = node.getParent();
		
		//If the parent is null, node is root. So we remove the root.
		if(parent == null){
			root = null;
			return;
		}
		
		//Check which side of the parent the node is connected to and move the other side up.
		Node child;
		if(parent.getLeft() == node){
			child = parent.getRight();
		}
		else{
			child = parent.getLeft();
		}
		
		//Adjust the parent value of child or if parent is root, replace node with child. 
		if(parent.getParent() != null){
			Node grandParent = parent.getParent();
			
			if(grandParent.getLeft() == parent){
				grandParent.setLeft(child);
			}
			else{
				grandParent.setRight(child);
			}
			child.setParent(grandParent);
		}
		else{
			root = child;
			child.setParent(null);
		}
		
		propagateUp(child);
	}

	@Override
	public void update() {
		if(root == null){
			return;
		}
		List<Node> nodes = getMovedNodes(root, null);
		for(Node node : nodes){
			remove(node);
			insert(node);
		}
	}
	
	private List<Node> getMovedNodes(Node parent, List<Node> moved){
		if(parent == null){
			return moved;
		}
		if(moved == null){
			List<Node> nodes = new ArrayList<>();
			moved = nodes;
		}
		//Traverse tree and create a list of moved nodes
		if(parent.isLeaf()){
			parent.data.updateAABB();
			if(!parent.data.faabb.contains(parent.data.aabb)){
				moved.add(parent);
				parent.data.updateFAABB();
			}
			else{
				parent.data.updateFAABB();
			}
		}
		else{
			getMovedNodes(parent.getLeft(), moved);
			getMovedNodes(parent.getRight(), moved);
		}
		return moved;
	}

	@Override
	public List<AABB> query(Node node, AABB aabb, List<AABB> intersections) {
		if(node == null){
			return null;
		}
		if(aabbIntersects(node.data, aabb)){
			if(node.isLeaf()){
				intersections.add(node.data);
			}
			else{
				query(node.getLeft(), aabb, intersections);
				query(node.getRight(), aabb, intersections);
			}
		}
		return intersections;
	}
	
	public Node getRoot(){
		return root;
	}
	
	private boolean aabbIntersects(AABB aabb1, AABB aabb2){
		if(aabb1 == null || aabb2 == null){
			return false;
		}
		return aabb1.aabb.intersects(aabb2.aabb);
	}
}
