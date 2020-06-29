import processing.core.*;

public class Cubbie {
	PVector pos;
	float len;
	
	protected final PApplet p;
	
	Cubbie(float x, float y, float z, float _len, PApplet _p){
		pos = new PVector(x, y, z);
		p = _p;
		len = _len;
	}
	
	void show() {
		
		p.fill(255);
		p.stroke(0);
		p.strokeWeight(8);
		
		p.pushMatrix();
		
		p.translate(pos.x, pos.y, pos.z);
		p.box(len);
		
		p.popMatrix();
		
	}

}
