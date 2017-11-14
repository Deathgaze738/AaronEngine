package utils;

public class Vector3D implements IVector<Vector3D> {
	
	public static final Vector3D ZERO = new Vector3D(0f, 0f, 0f);
	
	//X Component
	public float x = 0f;
	
	//Y Component
	public float y = 0f;
	
	//Z Component (Can be used as Layer in 2D games)
	public float z = 0f;
	
	public Vector3D(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public Vector3D set(Vector3D vector){
		return this.set(vector.x, vector.y, vector.z);
	}
	
	public Vector3D set(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	@Override
	public float length() {
		return (float) Math.sqrt( x * x + y * y + z * z);
	}

	@Override
	public float dot(Vector3D vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}

	@Override
	public Vector3D scale(float scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
		return this;
	}

	@Override
	public Vector3D add(Vector3D vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
		return this;
	}

	@Override
	public Vector3D sub(Vector3D vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
		return this;
	}

	public Vector3D add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public Vector3D sub(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

}
