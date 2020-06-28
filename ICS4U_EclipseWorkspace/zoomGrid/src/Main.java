import java.util.ArrayList;

import processing.core.*;

public class Main extends PApplet{
	
	static int rows = 25;
	static int cols = 25;
	
	Node[][] grid = new Node[rows][cols];
	
	public static void main(String[] args) {
		PApplet.main("Main");
	}
	
	public void settings() {
		size(900, 900);
	}
	
	public void setup() {
		background(255);
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				grid[i][j] = new Node(i, j, this);
			}
		}
		
	}
	
	public void draw() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				grid[i][j].show(255, 255, 255);
			}
		}
	}

}
