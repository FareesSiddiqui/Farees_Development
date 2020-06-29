import processing.core.*;
public class Main extends PApplet{
	
	int dim = 3; // dimensions
	
	Cubbie[][][] Cube = new Cubbie[dim][dim][dim];

	public static void main(String[] args) {
		PApplet.main("Main");
	}
	
	public void settings() {
		size(900, 900, P3D);
	}
	
	public void setup() {
		
//		for(int i = 0; i < dim; i++) {
//			for(int j = 0; j < dim; j++) {
//				for(int k = 0; k < dim; k++) {
//					
//					float len = 10;
//					float x = len*i, y = len*j, z = len*k;
//					Cube[i][j][k] = new Cubbie(x, y, z, len, this);
//				}
//			}
//		}
	}
	
	public void draw() {
		background(255);
//		for(int i = 0; i < dim; i++) {
//			for(int j = 0; j < dim; j++) {
//				for(int k = 0; k < dim; k++) {
//					
//					float len = 10;
//					float x = len*i, y = len*j, z = len*k;
//					Cube[i][j][k].show();
//				}
//			}
//		}

	}

}
