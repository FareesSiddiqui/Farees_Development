import processing.core.*;
public class Pipe {
	
	protected final PApplet p;
	
	private int x, y, w, h;

	private boolean trasportable;
	
	Pipe(int _x, int _y, int _w, int _h, boolean _trans,PApplet _p){
		p = _p;
		x = _x;
		y = _y;
		w = _w;
		h = _h;
		trasportable = _trans;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public boolean isTrasportable() {
		return trasportable;
	}

	public void setTrasportable(boolean trasportable) {
		this.trasportable = trasportable;
	}
	
	public void draw() {
		p.noFill();
		p.strokeWeight(2);
		p.rect(x, y, w, h);
	}
	
	
	
}
