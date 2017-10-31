package utils;

public class Triple<X, Y, Z> {
	private X first;
	private Y second;
	private Z third;
	
	public Triple(X mFirst, Y mSecond, Z mThird){
		first = mFirst;
		second = mSecond;
		third = mThird;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Triple)) return false;
		Triple<?, ?, ?> input = (Triple<?, ?, ?>) o;
		return this.first.equals(input.getFirst()) 
				&& this.second.equals(input.getSecond())
				&& this.third.equals(input.getThird());	
	}

	public X getFirst() {
		return first;
	}

	public Y getSecond() {
		return second;
	}

	public Z getThird() {
		return third;
	}
	
	public void setFirst(X newFirst){
		first = newFirst;
	}
	
	public void setSecond(Y newSecond){
		second = newSecond;
	}
	
	public void setThird(Z newThird){
		third = newThird;
	}
}
