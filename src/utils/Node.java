package utils;

public class Node {
	AABB data;
	private Node parent;
	private Node[] children;
	int treeHeight;
	
	boolean childrenCrossed;
	
	public Node(){
		this.setParent(null);
		this.children = new Node[2];
		this.children[0] = null;
		this.children[1] = null;
		data = null;
		treeHeight = 0;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("<<<<<<<<<<<<<<<<<<<<<<<<<\n");
		sb.append("ID: " + data + "\n");
		if(parent == null){
			sb.append("Parent: None (ROOT)\n");
		}
		else{
			sb.append("Parent: " + parent.hashCode()+ "\n");
		}
		if(data != null){
			sb.append("Size: " + data.getPerimeter() + "\n");
		}
		if(children[0] == null){
			sb.append("No Children\n");
		}
		else{
			sb.append("Left: " + children[0].data + " ");
			if(children[1] == null){
				sb.append("Right: None \n");
			}
			else{
				sb.append("Right: " + children[1].data + "\n");
			}
		}
		sb.append("Height: " + treeHeight + "\n");
		sb.append("<<<<<<<<<<<<<<<<<<<<<<<<<\n");
		return sb.toString();
	}
	
	public void setBranch(Node n1, Node n2){
		this.children[0] = n1;
		this.children[1] = n2;
		n1.setParent(this);
		n2.setParent(this);
	}
	
	public Node getParent(){
		return parent;
	}
	
	public void setParent(Node parent){
		this.parent = parent;
	}
	
	public AABB getData(){
		return data;
	}
	
	public void setData(AABB aabb){
		data = aabb;
	}
	
	public boolean isLeaf(){
		return children[0] == null;
	}
	
	public boolean isRoot(){
		return parent == null;
	}
	
	public Node getLeft(){
		return children[0];
	}
	
	public Node getRight(){
		return children[1];
	}
	
	public void setLeft(Node left){
		children[0] = left;
	}
	
	public void setRight(Node right){
		 children[1] = right;
	}
}
