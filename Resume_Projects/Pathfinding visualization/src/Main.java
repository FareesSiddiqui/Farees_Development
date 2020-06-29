import processing.core.*;
import java.util.ArrayList;

public class Main extends PApplet {

	// An educated guess of how far it is between two points
	float heuristic(Node a, Node b) {
		float d = dist(a.i, a.j, b.i, b.j);
		return d;
	}

	String screen = "Start";

	// How many columns and rows?
	static int cols = 100;
	static int rows = 100;

	// This will be the 2D array
	Node[][] grid = new Node[cols][rows];

	boolean[] keys = new boolean[5];

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
	
	int walls = 0;

	boolean startSelected = false;
	boolean endSelected = false;
	boolean ready = false;
	boolean wallSelected = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	}

	public void settings() {
		size(900, 900);
	}

	public void setup() {
		println("A*");

		frameRate(500);

		// Grid cell size
		w = (float) (width) / cols;
		h = (float) (height) / rows;

		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				grid[i][j] = new Node(i, j, this);
			}
		}

		background(255);

		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				grid[i][j].show(255, 255, 255);
				if(grid[i][j].wall) {
					walls++;
				}
			}
		}

		// All the neighbors
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				grid[i][j].addNeighbors(grid);
			}
		}

	}

	public void draw() {
		if (startSelected) {
			start.show(50, 255, 165);
		}
		if (endSelected) {
			end.show(255, 165, 0);
		}

		if (keys[0]) {
			ready = true; // change to !ready to toggle between the animation being on and off
		}

		if (keys[1]) { 
			println("mouseCount: ", count);
			println("mouseX: ", mouseX, " mouseY: ", mouseY);
			println("Start X: ", start.i, "Start Y", start.j);
			println("End X: ", end.i, "End Y", end.j);
			println("Number of Walls: ", walls);
			println("--------------------------------------------");
			
				
		}

		if (startSelected && endSelected && ready) {

			if (openSet.size() > 0) {
				end.show(255, 165, 0);

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

				openSet.remove(current);
				closedSet.add(current);

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

			}

			if (!(openSet.size() > 0)) {
				println("no solution");
				noLoop();
				return;
			}

			background(255);

			for (int i = 0; i < cols; i++) {
				for (int j = 0; j < rows; j++) {
					grid[i][j].show(255, 255, 255);
				}
			}

			for (int i = 0; i < closedSet.size(); i++) {
				closedSet.get(i).show(255, 0, 0);
			}

			for (int i = 0; i < openSet.size(); i++) {
				openSet.get(i).show(0, 255, 0);
			}

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

	}

	public void mousePressed() {

		if (count % 2 == 0 && !startSelected) {
			start = grid[(int) (mouseX / w)][(int) (mouseY / h)];
			startSelected = true;
			openSet.add(start);
			count++;
		}

		else if (count % 2 != 0 && !endSelected) {
			end = grid[(int) (mouseX / w)][(int) (mouseY / h)];
			endSelected = true;
			count++;
		}

	}

	public void keyPressed() {
		if (key == ' ') {
			keys[0] = true;
		}

		if (key == 'd') {
			keys[1] = true;
		}


	}

	public void keyReleased() {
		if (key == ' ') {
			keys[0] = false;
		}

		if (key == 'd') {
			keys[1] = false;
		}

	}

}
