import processing.core.*;
import java.util.ArrayList;
public class Main extends PApplet{
	
	static int rows = 10;
	static int cols = 10;
	
	ArrayList<Node> path = new ArrayList<Node>();
	
	Node[][] grid = new Node[rows][cols];
	
	Node Start, End, current;
	
	ArrayList<Node> openSet = new ArrayList<Node>();
	
	ArrayList<Node> closedSet = new ArrayList<Node>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	}
	
	public void settings() {
		size(900,900);
	}
	
	
	void removeFromArray(ArrayList<Node> a, Node elt) {
		for(int i = a.size()-1; i >= 0; i--) {
			if(a.get(i) == elt) {
				a.remove(i); 
			}
		}
	}
	
	public void setup() {
		background(255);
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				grid[i][j] = new Node(i, j, this);
			}
		}
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				grid[i][j].addNeighbors(grid);
			}
		}
		
		Start = grid[0][0];
		End = grid[rows-1][cols-1];
		
		openSet.add(Start);
	}
	
	public double heuristic(Node a, Node b) {
		return dist(a.i, a.j, b.i, b.j);
	}
	
	public void draw() {	
		
		try {
			
			if(openSet.size() > 0) {
				// Keep going
				
				int winner = 0;
				for(int i = 0; i < openSet.size(); i++) {
					if(openSet.get(i).fCost < openSet.get(winner).fCost) {
						winner = i;
					}
				}
				
				current = openSet.get(winner);
				
				if(current == End) {
					Node temp = current;
					
					while(temp.previous != null) {
						path.add(temp.previous);
						temp = temp.previous;
					}
					println("DONE!");
					noLoop();
				}
				
				removeFromArray(openSet, current);
				closedSet.add(current);
				
				for(int i = 0; i < current.neighbors.size(); i++) {
					Node neighbor = current.neighbors.get(i);
					
					if(!closedSet.contains(neighbor)) {
						double tempG = current.gCost;
						
						if(openSet.contains(neighbor)) {
							if(tempG < neighbor.gCost) {
								neighbor.gCost = tempG;
							}
						} else {
							neighbor.gCost = tempG;
							openSet.add(neighbor);
						}
						
						neighbor.hCost = heuristic(neighbor, End);
						neighbor.fCost = neighbor.gCost + neighbor.fCost;
						neighbor.previous = current;
						
					}
					
				}
				
			} else {
				//No solution
				println("NO SOLUTION!");
				noLoop();
			}
			
			background(0);
			
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < cols; j++) {
					grid[i][j].show(255, 255, 255);
				}
			}
			
			for(int i = 0; i < openSet.size(); i++) {
				openSet.get(i).show(0, 255, 0);
			}
			
			for(int i = 0; i < closedSet.size(); i++) {
				closedSet.get(i).show(255, 0, 0);
			}
						
			for(int i = 0; i < path.size(); i++) {
				path.get(i).show(0, 0, 255);	
//				grid[End.i][End.j].show(0, 0, 255);
			}
			
			
		} catch(Exception e) {
			println("lol there was error somewhere have fun ignoring it for 6 months");
			
		}
		
		
	}
	
	public void mousePressed() {
		
	}

}
