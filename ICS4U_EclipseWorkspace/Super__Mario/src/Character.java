import processing.core.PApplet;
public class Character{
	public static double x, y, velocity = 0, gravity = 0.8, lift = -20;
	private int speed;
	
	protected final PApplet applet;
	
	boolean hit = true;
	
	private boolean[] keys = new boolean[5];
	
	int jumpcount = 0;
	
	char last;
	
	boolean jumping = false, onObs = false;
	int obsH;
	
	private int health;
	
        @SuppressWarnings("static-access")
		Character(int x1, int y1, int speed1, int health, PApplet pp){
        	this.applet = pp;
        	setX(x1);
    		setY(y1); 
        	setSpeed(speed1);
        }
        
        public void setKeys(int i, boolean state) {
        	keys[i] = state;
        }
        
        public void setHealth(int h) {
        	health = h;
        }
        
        public boolean getKeys(int i) {
    		return keys[i];
    	}
        
        public double getVel() {
        	return velocity;
        }
        
        public void setVel(double _v) {
        	velocity = _v;
        }
        
        public double getGravity() {
        	return gravity;
        }
        
        public void setGravity(double _g) {
        	gravity = _g;
        }
        
        public int getHealth() {
        	return health;
        }
    	
    	public double getX() {
    		return x;
    	}
    	
    	public double getY() {
    		return y;
    	}
    	
    	public void setX(double _x) {
    		x = _x;
    	}
    	
    	public void setY(double _y) {
    		y = _y;
    	}
    	
    	public int getSpeed() {
    		return speed;
    	}
    	
    	public void setSpeed(int _s) {
    		speed = _s;
    	}
    	
    	public void zeroLast() {
    		last = ' ';
    	}
    	
    	public char getLastKey() {
    		return last;
    	}
    	
        
        void jump() {
        	this.velocity += this.lift;
          	keys[2] = !keys[2]; //will cause you to have a new press for each jump
        	hit = !hit;
        	jumping = true;
        }
       
		void show() {
        	applet.ellipse((int)this.x, (int)this.y, 50, 50);
        }

}
