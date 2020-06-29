import processing.core.PApplet;

public class Main extends PApplet{
	
	public static int rows = 3, cols = 3;

	Cell[][] grid = new Cell[rows][cols];
	
	int count = 1;
	
	String player = "X";
	
	public static void main(String[] args) {
		PApplet.main("Main");
	}	
	
	public void settings() {
		size(900, 900);
	}
	
	public void setup() {		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				grid[i][j] = new Cell(i, j, this);
			}
		}
	}
	
	public void draw() {
		background(255);
		
		
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				grid[i][j].show(255, 255, 255);
			}
		}
		
		if(count %2 == 0) {
			player = "O";
		} else {
			player = "X";
		}
	}
	
	public void mousePressed() {
		grid[mouseX/Cell.w][mouseY/Cell.h].clicked = true;
		count ++;

		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				if(grid[i][j].clicked && player.equals("O")) {
					
					text("O", (i*Cell.w)/2, (j*Cell.h) / 2);
					
					println("(", i, "," , j, ")" + " was clicked");
					break;
				}
			}
		}
//		grid[mouseX/Cell.w][mouseY/Cell.h].clicked = false;
	}
	
	public void mouseReleased() {
//		grid[mouseX/Cell.w][mouseY/Cell.h].clicked = false;
	}

}
