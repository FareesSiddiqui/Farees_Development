import processing.core.*;

public class Cell {
	int i, j;

	static int w = 900 / Main.rows;

	static int h = 900 / Main.cols;
		
	protected final PApplet p;
	
	boolean clicked = false;
	
	Cell(int i, int j, PApplet _p){
		this.i = i;
		this.j = j;
		p = _p;
	}	
	
	void show(int r, int g, int b){	
		p.fill(r,g,b);
		p.rect(this.i*w, this.j*h, w, h);
	}
	
}
