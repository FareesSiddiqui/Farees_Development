import processing.core.PApplet;
import processing.core.PMatrix;
import processing.core.PMatrix3D;
import processing.core.PVector;

public class Cubbie {
	PMatrix3D matrix;
	int x = 0;
	int y = 0;
	int z = 0;
	boolean highlight = false;
	int r, g, b;
	
	Face[] faces = new Face[6];
	
	protected final PApplet p;
	
	Cubbie(PMatrix3D m, int x, int y, int z, PApplet p){
		this.matrix = m;
		this.p = p;
		this.x = x;
		this.y = y;
		this.z = z;		
		this.r = this.g = this.b = 255;
		
		faces[0] = new Face(new PVector(0, 0, -1), 0, 0, 255, p);
		faces[1] = new Face(new PVector(0, 0, 1), 0, 255, 0, p);
		faces[2] = new Face(new PVector(0, 1, 0), 255, 255, 255, p);
		faces[3] = new Face(new PVector(0, -1, 0), 255, 255, 0, p);
		faces[4] = new Face(new PVector(1, 0, 0), 255, 165, 0, p);
		faces[5] = new Face(new PVector(-1, 0, 0), 255, 0, 0, p);
		
	}
	
	void turnFacesZ(int dir) {
		for (Face f : faces) {
			f.turnZ(dir * p.HALF_PI);
		}
	}
	
	void turnFacesX(int dir) {
		for (Face f : faces) {
			f.turnX(dir*p.HALF_PI);
		}
	}
	
	void turnFacesY(int dir) {
		for (Face f : faces) {
			f.turnY(dir*p.HALF_PI);
		}
	}
	
	void update(int x, int y, int z) {
		matrix.reset();
		matrix.translate(x, y, z);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	void show() {
				
		p.noFill();
		
		p.stroke(0);
		p.strokeWeight(0.1f);
		
		p.pushMatrix();
		
		p.applyMatrix(matrix);
		p.box(1);
		for(Face f: faces) {
			f.show();
		}
		
		p.popMatrix();
		
	}

}
