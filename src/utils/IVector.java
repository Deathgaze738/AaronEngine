package utils;

public interface IVector<T extends IVector<T>> {
	
	//Returns euclidean length of the vector.
	float length();
	
	//Scales the fector by scale
	T scale(float scale);
	
	//Add vector to this vector
	T add(T vector);
	
	//Add floats to this vector
	T add(float x, float y, float z);
	
	//Subtract vector from this vector
	T sub(T vector);
	
	//Subtract floats from this vector.
	T sub(float x, float y, float z);
	
	//Returns dot product of this vector
	float dot(T vector);
	
	
}
