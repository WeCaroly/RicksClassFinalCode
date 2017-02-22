package javagames.util;

public class Vector2f {
	public float x;
	public float y;
	public float w;

	public Vector2f() {
		this.x = 0.0f;
		this.y = 0.0f;
		this.w = 1.0f; // !?!
	}
        //inverse from ppt
        public Vector2f inv(){
            return new Vector2f(-x,-y);
        }
        //add from ppt, summation of two vectors 
        public Vector2f add(Vector2f v){
            return new Vector2f(x+v.x, y+v.y);
        }
        //subtract
        public Vector2f sub(Vector2f v){
            return new Vector2f(x-v.x, y-v.y);
        }
        // from ppt muli
        public Vector2f mul(float scalar){
            return new Vector2f(scalar * x, scalar * y);
        }
        // divide from ppt dont devide by zero 
        public Vector2f div(float scalar){
            return new Vector2f(x/scalar,y/scalar);
        }
        //from ppt get length by pythagrean theorem
        public float len(){
         return (float) Math.sqrt(x*x+y*y);   
        }
        
        
        
	public Vector2f(Vector2f v) {
		this.x = v.x;
		this.y = v.y;
		this.w = v.w; // !?!
	}

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
		this.w = 1.0f; // !?!
	}

	public Vector2f(float x, float y, float w) {
		this.x = x;
		this.y = y;
		this.w = w; // !?!
	}

	public void translate(float tx, float ty) {
		x += tx;
		y += ty;
	}

	public void scale(float sx, float sy) {
		x *= sx;
		y *= sy;
	}

	public void rotate(float rad) {
		float tmp = (float) (x * Math.cos(rad) - y * Math.sin(rad));
		y = (float) (x * Math.sin(rad) + y * Math.cos(rad));
		x = tmp;
	}

	public void shear(float sx, float sy) {
		float tmp = x + sx * y;
		y = y + sy * x;
		x = tmp;
	}
}