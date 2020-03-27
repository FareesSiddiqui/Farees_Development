import processing.core.*;
public class Player {
	
	public static int x, y, speed, health;
    protected final PApplet pp;
        
        boolean[] keys = new boolean[5];
        
        boolean reload = false;
        
        
        
        Player(int x1, int y1, int speed1, PApplet applet){
            this.pp = applet;
            this.health = 300;
            this.x = x1;
            this.y = y1;
            this.speed = speed1;
        }
        
        void update(){
            if(keys[1] == true && this.y > 25)
                this.y = y-speed;
            
            if(keys[2] == true && this.x > 25)
                this.x = x-speed;
                
            if(keys[3] == true && this.y < 575)
                this.y = y+speed;
            
            if(keys[4] == true && this.x < 575)
                this.x = x+speed;
            
            pp.ellipse(this.x, this.y, 50, 50);
        }
        
        
        
        void keyp(){
            if(pp.key == 'w')
                keys[1] = true;
            
            if(pp.key == 'a')
                keys[2] = true;
            
            if(pp.key == 's')
                keys[3] = true;
            
            if(pp.key == 'd')
                keys[4] = true;
            if(pp.key == 'r')
            	this.reload = true;
        }
        
        void keyr(){
            if(pp.key == 'w')
                keys[1] = false;
            
            if(pp.key == 'a')
                keys[2] = false;
            
            if(pp.key == 's')
                keys[3] = false;
            
            if(pp.key == 'd')
                keys[4] = false;
            
            if(pp.key == 'r')
            	this.reload = false;
        }
        
        void reload() {
        	if(this.reload == true) {
        		Gun.Bullet.cartridge = Gun.Bullet.cartridgeMax;
        		this.reload = false;
        		System.out.println("reloaded");
        	}
        }

}
