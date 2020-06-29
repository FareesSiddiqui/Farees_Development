import processing.core.PApplet;
import processing.core.PMatrix2D;
import processing.core.PMatrix3D;
import peasy.*;

public class Main extends PApplet{
	
	//limit is 69x69x69
	
	int dim = 3; // dimensions
	
	PeasyCam Cam;
	
	Cubbie[] Cube = new Cubbie[dim *dim *dim];

	String[] moves = { "f", "b", "u", "d", "l", "r"};
	
	String sequence = "";
	
	String solveSequence = "";
	
	boolean solve = false;
	
	int count = 0;
	
	
	public static void main(String[] args) {
		PApplet.main("Main");
	}
	
	public void generateCubbies() {
		
		int index = 0;
		
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				for(int z = -1; z <= 1; z++) {
					PMatrix3D matrix = new PMatrix3D();
					
					matrix.translate(x, y, z);
					
					Cube[index] = new Cubbie(matrix, x, y, z, this);
					index++;
				}
			}
		}
	}
	
	public void showCube() {
		scale(50);
		for(int i = 0; i < Cube.length; i++) {
			Cube[i].show();
		}
	}
	
	public void settings() {
		size(900, 900, P3D);
	}
	
	public String flipCase(char c) {
		String s = "" + c;
		
		if(s.equals(s.toLowerCase())) {
			return s.toUpperCase();
		}
		
		else {
			return s.toLowerCase();
		}
		
	}
	
	public void generateScramble(int numMoves) {
		for(int i = 0; i < numMoves; i++) {
			int r = (int) (random(moves.length));
			if(random(1) < 0.5) {
				sequence +=moves[r]; 
			}
			
			else {
				sequence +=moves[r].toUpperCase(); 
			}
		}
		
		for(int i = sequence.length()-1; i >= 0; i--) {
			String nextMove = ""+flipCase(sequence.charAt(i));
			solveSequence += nextMove;
		}
		
		println(sequence);
		
	}
	
	
	boolean started = false;
	public void setup() {
		frameRate(60);
		Cam = new PeasyCam(this, 400);
		generateCubbies();
		generateScramble(1000);
		
		
		
	}
	
	void turnZ(int index, int dir) {
		  for (int i = 0; i < Cube.length; i++) {
		    Cubbie qb = Cube[i];
		    if (qb.z == index) {
		      PMatrix2D matrix = new PMatrix2D();
		      matrix.rotate(dir*HALF_PI);
		      matrix.translate(qb.x, qb.y);
		      qb.update(round(matrix.m02), round(matrix.m12), round(qb.z));
		      qb.turnFacesZ(dir);
		    }
		  }
		}

		void turnY(int index, int dir) {
		  for (int i = 0; i < Cube.length; i++) {
		    Cubbie qb = Cube[i];
		    if (qb.y == index) {
		      PMatrix2D matrix = new PMatrix2D();
		      matrix.rotate(dir*HALF_PI);
		      matrix.translate(qb.x, qb.z);
		      qb.update(round(matrix.m02), qb.y, round(matrix.m12));
		      qb.turnFacesY(dir);
		    }
		  }
		}

		void turnX(int index, int dir) {
		  for (int i = 0; i < Cube.length; i++) {
		    Cubbie qb = Cube[i];
		    if (qb.x == index) {
		      PMatrix2D matrix = new PMatrix2D();
		      matrix.rotate(dir*HALF_PI);
		      matrix.translate(qb.y, qb.z);
		      qb.update(qb.x, round(matrix.m02), round(matrix.m12));
		      qb.turnFacesX(dir);
		    }
		  }
		}
	
	public void applyMove(char move) {
		
		switch (move) {
		  case 'f': 
		    turnZ(1, 1);
		    break;
		  case 'F': 
		    turnZ(1, -1);
		    break;  
		  case 'b': 
		    turnZ(-1, 1);
		    break;
		  case 'B': 
		    turnZ(-1, -1);
		    break;
		  case 'u': 
		    turnY(1, 1);
		    break;
		  case 'U': 
		    turnY(1, -1);
		    break;
		  case 'd': 
		    turnY(-1, 1);
		    break;
		  case 'D': 
		    turnY(-1, -1);
		    break;
		  case 'l': 
		    turnX(-1, 1);
		    break;
		  case 'L': 
		    turnX(-1, -1);
		    break;
		  case 'r': 
		    turnX(1, 1);
		    break;
		  case 'R': 
		    turnX(1, -1);
		    break;
		  }
		
	}
	
	public void keyPressed() {
		if(key == ' ') {
			started = true;
		}
		
		if(key == 'n') {
			solve = true;
		}
	}
	
	int solveCount;
	
	public void draw() {
		background(51);
		
		if(started) {
			if(frameCount % 1 == 0) {
				if(count < sequence.length()) {
					char move = sequence.charAt(count);
					applyMove(move);
					count++;
				}
			}
		}
		
		if(solve) {
			if(frameCount % 1 == 0) {
				if(solveCount < solveSequence.length()) {
					char move = solveSequence.charAt(solveCount);
					applyMove(move);
					solveCount++;
				}
			}
		}
		
		
		showCube();

	}

}
