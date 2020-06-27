import java.util.*;
import processing.core.*;
// An object to describe a spot in the grid
class Node {
  int i;
  int j;
  // f, g, and h values for A*
  float f = 0;
  float g = 0;
  float heuristic = 0;
  
  // Neighbors
  ArrayList<Node> neighbors = new ArrayList<Node>();

  // Where did I come from?
  Node previous = null;

  boolean wall = false;
  
  protected final PApplet p;
  
  Node(int i_, int j_, PApplet p_) {

    // Location
    i = i_;
    j = j_;
    
    p = p_;

    // Am I a wall?
    wall = false;
    if (p.random(1) < 0.4) {
      wall = true;
    }
  }
  
  // Display me
  void show() {
    if (wall) {
      p.fill(0);
      p.rect(i*Main.w, j*Main.h, Main.w, Main.h);
//      p.ellipse((float)(i * Main.w + Main.w / 2.0), (float) (j * Main.h + Main.h / 2.0), (float)(Main.w / 2.0), (float)(Main.h / 2.0));
    }
  }
  
  void show(int r, int g, int b) {
    if (wall) {
      p.fill(0);
      p.rect(i*Main.w, j*Main.h, Main.w, Main.h);
//      p.ellipse((float)(i * Main.w + Main.w / 2.0), (float) (j * Main.h + Main.h / 2.0), (float)(Main.w / 2.0), (float)(Main.h / 2.0));
    } else {
      p.fill(r, g, b);
      p.rect(i*Main.w, j*Main.h, Main.w, Main.h);
    }
  }

  // Figure out who my neighbors are
  void addNeighbors(Node[][] grid) {
    if (i < Main.cols - 1) {
      neighbors.add(grid[i + 1][j]);
    }
    if (i > 0) {
      neighbors.add(grid[i - 1][j]);
    }
    if (j < Main.rows - 1) {
      neighbors.add(grid[i][j + 1]);
    }
    if (j > 0) {
      neighbors.add(grid[i][j - 1]);
    }
    if (i > 0 && j > 0) {
      neighbors.add(grid[i - 1][j - 1]);
    }
    if (i < Main.cols - 1 && j > 0) {
      neighbors.add(grid[i + 1][j - 1]);
    }
    if (i > 0 && j < Main.rows - 1) {
      neighbors.add(grid[i - 1][j + 1]);
    }
    if (i < Main.cols - 1 && j < Main.rows - 1) {
      neighbors.add(grid[i + 1][j + 1]);
    }
  }
};