import processing.core.*;
 
public class Cell {
 
    private int x, y;
 
    public static int w = 900 / Main.cols, h = 900 / Main.rows;
 
    protected final PApplet p;
    
    private String Node;
 
    Cell(int _x, int _y, PApplet _p){
        p = _p;
        x = _x;
        y = _y;
 
    }
    
    void setNode(String _Node){
    	Node = _Node;
    }
    
    String getNode() {
    	return Node;
    }
    
    void endNode_info() {
    	
    }
    
    int getX() {
        return x;
    }
 
    int getY() {
        return y;
    }
 
    void setX(int _x) {
        x = _x;
    }
 
    void setY(int _y) {
        y = _y;
    }
    
 
    void colorNode(int r, int g, int b) {
        p.fill(r, g, b);
        p.rect(x*w, y*h, w, h);
        p.fill(255);
    }
 
    void show() {
    	
        p.rect(x*w, y*h, w, h);
    }
}