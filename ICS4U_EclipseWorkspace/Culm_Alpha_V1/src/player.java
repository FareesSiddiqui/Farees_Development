import processing.core.*;
public class Player {
	
	public static int x, y, speed, health;
    protected final PApplet pp;
    public static float angle;
    
    public static float segLength = 75, fx, fy;
        
    boolean[] keys = new boolean[5];
        
    static boolean reload = false;
    
    
        
        
        
        @SuppressWarnings("static-access")
		Player(int x1, int y1, int speed1, PApplet applet){
            this.pp = applet;
            this.health = 300;
            this.x = x1;
            this.y = y1;
            this.speed = speed1;
        }
        
        @SuppressWarnings("static-access")
		void update(){
            if(keys[1] == true && this.y > 25)
                this.y = y-speed;
            
            if(keys[2] == true && this.x > 25)
                this.x = x-speed;
                
            if(keys[3] == true && this.y < 675)
                this.y = y+speed;
            
            if(keys[4] == true && this.x < 1475)
                this.x = x+speed;
            
            
            pp.ellipse(this.x, this.y, 50, 50);
        }
        
      
        
        void keyp(){
            if(pp.key == 'w' || pp.key == 'W')
                keys[1] = true;
            
            if(pp.key == 'a' || pp.key == 'A')
                keys[2] = true;
            
            if(pp.key == 's' || pp.key == 'S')
                keys[3] = true;
            
            if(pp.key == 'd' || pp.key == 'D')
                keys[4] = true;
            
            if(pp.key == 'r' || pp.key == 'R')
            	this.reload = true;
           
        }
        
        void keyr(){
            if(pp.key == 'w' || pp.key == 'W')
                keys[1] = false;
            
            if(pp.key == 'a' || pp.key == 'A')
                keys[2] = false;
            
            if(pp.key == 's' || pp.key == 'S')
                keys[3] = false;
            
            if(pp.key == 'd' || pp.key == 'D')
                keys[4] = false;
            
            if(pp.key == 'r' || pp.key == 'R')
            	this.reload = false;
           
        }
        

}
