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
        //from ppt
        public float lenSqr(){
            return (x*x+y*y);
        }
        //from ppt
        public Vector2f norm(){
            return div(len());
        }
        //from ppt, purpendicular
        public Vector2f prep(){
            return new Vector2f(-y,x);
        }
        //from ppt, dot product
        public float dot(Vector2f v){
            return x*v.x + y*v.y;
        }
        // from ppt angle radians created by vector
        public float angle(){
            return (float) Math.atan2(y,x);
        }
        //from ppt, polar
        public static Vector2f polar(float angle, float radius){
            return new Vector2f(((float)radius*Math.cos(angle)),radius*(float)Math.sin(angle));
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