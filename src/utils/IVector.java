package utils;

public interface IVector<T extends IVector<T>> {
	
	//Returns euclidean length of the vector.
	float length();
	
	//Scales the fector by scale
	T scale(float scale);
	
	//Add vector to this vector
	T add(T vector);
	
	//Subtract vector from this vector
	T sub(T vector);
	
	//Returns dot product of this vector
	float dot(T vector);
	
	
}
