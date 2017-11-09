package utils;

public class Vector3 implements IVector<Vector3> {
	
	//X Component
	public float x = 0f;
	
	//Y Component
	public float y = 0f;
	
	//Z Component (Can be used as Layer in 2D games)
	public float z = 0f;
	
	public Vector3(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public float length() {
		return (float) Math.sqrt( x * x + y * y + z * z);
	}

	@Override
	public float dot(Vector3 vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}

	@Override
	public Vector3 scale(float scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
		return this;
	}

	@Override
	public Vector3 add(Vector3 vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
		return this;
	}

	@Override
	public Vector3 sub(Vector3 vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
		return this;
	}

	@Override
	public Vector3 add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	@Override
	public Vector3 sub(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

}
