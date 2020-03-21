import processing.core.*;

public class player {
	
	int x, y, health, speed;
		
	protected final PApplet pp;
	
	boolean[] keys = new boolean[5];
	
	player(int x1, int y1, int speed1, PApplet applet) {
		this.pp = applet;
		this.x = x1;
		this.y = y1;
		this.health = 300;
		this.speed = speed1;
		
	}
	
	void update() {
		if(keys[1] == true)
			this.y = y - speed;
		
		if(keys[2] == true)
			this.x = x - speed;
		
		if(keys[3] == true)
			this.y = y + speed;
		
		if(keys[4] == true)
			this.x = x + speed;
		pp.image(Main.playerImg, this.x, this.y);
	}
	
	void keyp() {
		if(pp.key == 'w')
			keys[1] = true;
         
        if(pp.key == 'a')
            keys[2] = true;
         
        if(pp.key == 's')
            keys[3] = true;
         
        if(pp.key == 'd')
            keys[4] = true;
	}
	
	void keyr() {
		if(pp.key == 'w')
			keys[1] = false;
         
        if(pp.key == 'a')
            keys[2] = false;
         
        if(pp.key == 's')
            keys[3] = false;
         
        if(pp.key == 'd')
            keys[4] = false;
	}


}
