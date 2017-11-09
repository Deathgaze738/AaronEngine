package utils;

public class Vector2D implements IVector<Vector2D> {
	
	//X Component
	public float x = 0f;
	
	//Y Component
	public float y = 0f;
	
	public Vector2D(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public float length() {
		return (float) Math.sqrt( x * x + y * y);
	}

	@Override
	public float dot(Vector2D vector) {
		return x * vector.x + y * vector.y;
	}

	@Override
	public Vector2D scale(float scale) {
		this.x *= scale;
		this.y *= scale;
		return this;
	}

	@Override
	public Vector2D add(Vector2D vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	@Override
	public Vector2D sub(Vector2D vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}

	public Vector2D add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2D sub(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
}
