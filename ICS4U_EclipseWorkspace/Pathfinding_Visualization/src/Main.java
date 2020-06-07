import processing.core.*;
 
public class Main extends PApplet{
 
    public static int rows = 50, cols = 50;
 
    public Cell[][] grid = new Cell[rows][cols];
    
    public boolean[] keys = new boolean[3];
    
    int mouseCounter = 0;
        
    int StartNodeX, StartNodeY, EndNodeX, EndNodeY;
    
    boolean sPlaced = false;
    boolean ePlaced = false;
    
    int startNode = 0; int endNode = 0; int wallNode = 0;
        
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        PApplet.main("Main");
    }
 
    public void settings() {
        size(1200, 900);
 
    }
 
    public void setup() {
        background(255);
 
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                grid[i][j] = new Cell(i, j, this);
            }
        }
        
        generateGrid();
    }
    
    public void generateGrid() {
    	 for(int i = 0; i < rows; i++) {
             for(int j = 0; j < cols; j++) {
             	 
                 grid[i][j].show(); 
                 grid[i][j].setNode("Empty Node");
             }
         }
    }
    
    public void resetGrid() {    	
    	if(keys[0]) {
    		mouseCounter = 0;
    		startNode = 0;
    		endNode = 0;
    		wallNode = 0;
    		for(int i = 0; i < rows; i++) {
                for(int j = 0; j < cols; j++) {
                    grid[i][j].show(); 
                }
            }
    	}
    }
    
    public void debug() {
    	if(keys[2] ) {
    		println("Walls: "+wallNode+" Start Nodes: "+startNode+" endNodes: "+endNode);
    	}
    }
  
    public void draw() {
    	resetGrid();
    	debug();
    	
    	for(int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if(grid[i][j].getNode().equals("Start Node") && grid[i][j].counted == false) {
					grid[i][j].counted = true;
					startNode++;
				}
				
				if(grid[i][j].getNode().equals("End Node") && grid[i][j].counted == false) {
					grid[i][j].counted = true;
					endNode++;
				}
				
				if(grid[i][j].getNode().equals("Wall") && grid[i][j].counted == false) {
					grid[i][j].counted = true;
					wallNode++;
				}
			}
		}
    	
    	//Only draw walls if start and end nodes have been selected
    	
    	try {
    		if(mousePressed && mouseCounter > 2) {
        		grid[mouseX/Cell.w][mouseY/Cell.h].colorNode(39, 58, 179);
        		grid[mouseX/Cell.w][mouseY/Cell.h].setNode("Wall");
        	}
    	} 
    	
    	catch (Exception e){
    		println("Get back on the board pwease uwu");
    	}
         
    }
 
    public void mousePressed() {
    	   	
    	//Start Node
    	if(mouseCounter == 0) {
    		grid[mouseX/Cell.w][mouseY/Cell.h].colorNode(0, 255, 0); 
    		StartNodeX = mouseX/Cell.w; StartNodeY = mouseY/Cell.h;
    		grid[mouseX/Cell.w][mouseY/Cell.h].setNode("Start Node");
    	}
    	
    	//End node
    	else if(mouseCounter == 1) {
    		grid[mouseX/Cell.w][mouseY/Cell.h].colorNode(255, 0, 0); 
    		EndNodeX = mouseX/Cell.w; EndNodeY = mouseY/Cell.h;
    		grid[mouseX/Cell.w][mouseY/Cell.h].setNode("End Node");
    	}
    	
    	
    	else if(mouseCounter > 1) {
    		mouseCounter = 2;
    	}
    	    	
    	mouseCounter++;
    }
    
    
    
    public void keyPressed() {
    	if(key == 'r') { //Reset board
    		keys[0] = true;
    	}
    	
    	else if(key == 'c') { //Clear last node (WIP)
    		keys[1] = true;
    	}
    	
    	else if(key == 'd') { //Debug
    		keys[2] = true;
    	}
    	
    }
    
    public void keyReleased() {
    	if(key == 'r') {
    		keys[0] = false;
    	}
    	
    	else if(key == 'c') {
    		keys[1] = false;
    	}
    	
    	else if(key == 'd') {
    		keys[2] = false;
    	}
    	
    }
 
}