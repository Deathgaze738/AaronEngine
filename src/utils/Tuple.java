package utils;

public class Tuple<X, Y> {
	private X left;
	private Y right;
	
	public Tuple(){
		
	}
	
	public Tuple(X mLeft, Y mRight){
		left = mLeft;
		right = mRight;
	}

	public X getLeft() {
		return left;
	}

	public void setLeft(X left) {
		this.left = left;
	}

	public Y getRight() {
		return right;
	}

	public void setRight(Y right) {
		this.right = right;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Tuple)) return false;
		Tuple<?, ?> input = (Tuple<?, ?>) o;
		return this.left.equals(input.getLeft()) && this.right.equals(input.getRight());	
	}
}
