import processing.core.*;
public class Face {
	PVector normal;
	int r, g, b;
	
	protected final PApplet p;
	
	Face(PVector normal, int r, int g, int b, PApplet _p){
		this.normal = normal;
		this.r = r;
		this.g = g;
		this.b = b;
		p = _p;
	}
	
	
	@SuppressWarnings("static-access")
	void turnZ(float angle) {
	    PVector v2 = new PVector();
	    v2.x = p.round(normal.x * p.cos(angle) - normal.y * p.sin(angle));
	    v2.y = p.round(normal.x * p.sin(angle) + normal.y * p.cos(angle));
	    v2.z = p.round(normal.z);
	    normal = v2;
	  }
		
	
	@SuppressWarnings("static-access")
	  void turnY(float angle) {
	    PVector v2 = new PVector();
	    v2.x = p.round(normal.x * p.cos(angle) - normal.z * p.sin(angle));
	    v2.z = p.round(normal.x * p.sin(angle) + normal.z * p.cos(angle));
	    v2.y = p.round(normal.y);
	    normal = v2;
	  }

	
	@SuppressWarnings("static-access")
	  void turnX(float angle) {
	    PVector v2 = new PVector();
	    v2.y = p.round(normal.y * p.cos(angle) - normal.z * p.sin(angle));
	    v2.z = p.round(normal.y * p.sin(angle) + normal.z * p.cos(angle));
	    v2.x = p.round(normal.x);
	    normal = v2;
	  }
	
	@SuppressWarnings("static-access")
	void show() {
		p.pushMatrix();
		p.fill(r, g, b);
		p.noStroke();
		p.rectMode(p.CENTER);
		p.translate(0.5f*normal.x, 0.5f*normal.y, 0.5f*normal.z);
		
		if (p.abs(normal.x) > 0) {
			p.rotateY(p.HALF_PI);
		}
		
		else if(p.abs(normal.y) > 0) {
			p.rotateX(p.HALF_PI);
		}
		
		p.square(0, 0, 1);
		p.popMatrix();
	}

}
