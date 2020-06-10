import processing.core.*;
import java.util.ArrayList;
public class Node {

	public int i, j, w = 900/Main.rows, h = 900/Main.cols;
	
	public static double fCost, gCost, hCost;
		
	protected final PApplet p;
	
	Node previous = null;
	
	ArrayList<Node> neighbors = new ArrayList<Node>();
	
	public static boolean wall = false;
	
	Node(int _i, int _j, PApplet _p){
		p = _p;
		i = _i;
		j = _j;
		gCost = 0;
		fCost = 0;
		hCost = 0;
		
		if(p.random(1) < 0.1) {
			wall = true;
		}
	}
	
	
	
	void show(int r, int g, int b) {
		
		p.fill(r, g, b);
		p.stroke(0);
		p.rect(this.i*this.w-1, this.j*this.h-1, this.w, this.h);
				
	}
	
	
	void addNeighbors(Node[][] g) {
		
		if(i < Main.rows-1) {
			neighbors.add(g[i+1][j]);
		}
		
		if(i > 0) {
			neighbors.add(g[i-1][j]);
		}
		
		if(j < Main.cols-1) {
			neighbors.add(g[i][j+1]);
		}
		
		if(j > 0) {
			neighbors.add(g[i][j-1]);
		}
		
		if(i > 0 && j > 0) {
			neighbors.add(g[i-1][j-1]);//
		}
		
		if(i < Main.rows-1 && j > 0) {
			neighbors.add(g[i+1][j-1]);
		}
		
		if(i > 0 && j < Main.cols - 1) {
			neighbors.add(g[i-1][j+1]);
		}
		
		if(i < Main.rows - 1 && j < Main.cols - 1) {
			neighbors.add(g[i+1][j+1]);
		}
				
	}
	
	
	

}
