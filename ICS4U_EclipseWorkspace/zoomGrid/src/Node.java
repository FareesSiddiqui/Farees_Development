import processing.core.*;

public class Node {
	public static int i, j, w = 900/Main.rows, h = 900 / Main.cols;
	protected final PApplet p;
	Node(int _i, int _j, PApplet _p){
		p =_p;
		i = _i;
		j = _j;
	}
	
	void show(int r, int g, int b) {
		p.fill(r, g, b);
		p.stroke(0);
		p.rect(i*w-1, j*h-1, w, h);
	}
}
