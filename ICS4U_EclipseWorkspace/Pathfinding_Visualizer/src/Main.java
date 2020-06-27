import processing.core.*;
import java.util.ArrayList;
public class Main extends PApplet{
	
	// An educated guess of how far it is between two points
	float heuristic(Node a, Node b) {
	  float d = dist(a.i, a.j, b.i, b.j);
	  return d;
	}

	// How many columns and rows?
	static int cols = 50;
	static int rows = 50;

	// This will be the 2D array
	Node[][] grid = new Node[cols][rows];

	// Open and closed set
	ArrayList<Node> openSet = new ArrayList<Node>();
	ArrayList<Node> closedSet = new ArrayList<Node>();

	// Start and end
	Node start;
	Node end;
	Node current;

	// Width and height of each cell of grid
	static float w, h;
	
	int count = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	}
	
	public void settings() {
		size(900,900);
	}
	
	public void setup() {
		  println("A*");
		  
		  frameRate(500);
		  
		  // Grid cell size
		  w = (float)(width) / cols;
		  h = (float)(height) / rows;

		  for (int i = 0; i < cols; i++) {
		    for (int j = 0; j < rows; j++) {
		      grid[i][j] = new Node(i, j, this);
		    }
		  }
		  
		  background(255);

		  for (int i = 0; i < cols; i++) {
			    for (int j = 0; j < rows; j++) {
			      grid[i][j].show(255, 255,255);
			    }
			  }
		  // All the neighbors
		  for (int i = 0; i < cols; i++) {
		    for (int j = 0; j < rows; j++) {
		      grid[i][j].addNeighbors(grid);
		    }
		  }

		  // Start and end
		  start = grid[0][0];
		  end = grid[rows-1][cols-1];
		  start.wall = false;
		  end.wall = false;

		  // openSet starts with beginning only
		  openSet.add(start);
		
	}
	boolean ready = false;
	
	public void draw() {
		// Am I still searching?
		  if (openSet.size() > 0 && ready) {

		    // Best next option
		    int winner = 0;
		    for (int i = 0; i < openSet.size(); i++) {
		      if (openSet.get(i).f < openSet.get(winner).f) {
		        winner = i;
		      }
		    }
		    current = openSet.get(winner);

		    // Did I finish?
		    if (current == end) {
		      noLoop();
		      println("DONE!");
		    }

		    // Best option moves from openSet to closedSet
		    //openSet = removeFromArray(openSet, current);
		    openSet.remove(current);
		    closedSet.add(current);

		    // Check all the neighbors
		    ArrayList<Node> neighbors = current.neighbors;
		    for (int i = 0; i < neighbors.size(); i++) {
		      Node neighbor = neighbors.get(i);

		      // Valid next spot?
		      if (!closedSet.contains(neighbor) && !neighbor.wall) {
		        float tempG = current.g + heuristic(neighbor, current);

		        // Is this a better path than before?
		        boolean newPath = false;
		        if (openSet.contains(neighbor)) {
		          if (tempG < neighbor.g) {
		            neighbor.g = tempG;
		            newPath = true;
		          }
		        } else {
		          neighbor.g = tempG;
		          newPath = true;
		          openSet.add(neighbor);
		        }

		        // Yes, it's a better path
		        if (newPath) {
		          neighbor.heuristic = heuristic(neighbor, end);
		          neighbor.f = neighbor.g + neighbor.heuristic;
		          neighbor.previous = current;
		        }
		      }
		    }
		  } else {
		    // Uh oh, no solution
		    println("no solution");
		    noLoop();
		    return;
		  }

		  // Draw current state of everything
		  background(255);

		  for (int i = 0; i < cols; i++) {
			    for (int j = 0; j < rows; j++) {
			      grid[i][j].show(255, 255,255);
			    }
			  }

		  for (int i = 0; i < closedSet.size(); i++) {
		    closedSet.get(i).show(255, 0, 0);
		  }

		  for (int i = 0; i < openSet.size(); i++) {
		    openSet.get(i).show(0, 255, 0);
		  }
		  
		  end.show(255, 165, 0);
		  
		  start.show(0, 255, 255);
		  // Find the path by working backwards
		  ArrayList<Node> path = new ArrayList<Node>();
		  Node temp = current;
		  path.add(temp);
		  while (temp.previous != null) {
		    path.add(temp.previous);
		    temp = temp.previous;
		  }

		   for (int i = 0; i < path.size(); i++) {
		     path.get(i).show(0, 0, 255);
		  }
	}
	
	public void mousePressed() {
		ready = true;
	}
	
	public void mouseReleased() {
		loop();
	}


}
