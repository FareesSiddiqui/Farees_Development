import java.util.ArrayList;

import processing.core.*;

/**
 * @author Farees
 *
 */
public class Main extends PApplet{
	
	/*Instantiate player class*/
	Player player;
	
	Gun gun;
	Gun.Bullet bullet;
	
	boolean shoot = false;
	
	
	/*Screen variable to control what part of the game runs*/
	int screen = 0;
	
	/*Player image*/
	public static PImage playerImg;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	}

	@SuppressWarnings("static-access")
	@Override
	public void settings() {
		//     X    Y
		size(1500, 700);
		player = new Player(300, 300, 5, this);
		gun = new Gun("Pistol");
		bullet = new Gun.Bullet(player.x, player.y, 10, this);
			
	}
	
	@Override
	public void setup() {
		playerImg = loadImage("player.png");	
		gun.select();
		
		bullet.spawnBullets();
		ellipseMode(CENTER);

	}
	
	public void game() {
		background(60, 75, 114);
		
		textSize(52);
		text(gun.cartridge +"/"+ gun.ammo, 1273, 689);
		
		boolean debug = false;
		
		if(key == 'l')
			debug = true;
		
		
		
		player.update();
		updateBullets();
		
		bullet.update();
				
		
		
		
		if(debug) {
			System.out.println("ammo: "+gun.ammo);
			System.out.println("cartridge: "+gun.cartridge);
			System.out.println("Ammo_Till_Max: "+gun.ammo_till_max);
			System.out.println("                                     ");
			debug = !debug;
		}
		
				
		
	}
	
	void updateBullets() {
		
		if(shoot) {
			for(int i = 0; i < gun.cartridge; i++) {
				bullet.bullets.get(i).y -= player.y - bullet.bullets.get(i).speed;
				bullet.bullets.get(i).x = player.x; 
				ellipse(bullet.bullets.get(i).y, bullet.bullets.get(i).x, 10, 10);
			}
		}
		
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void mouseClicked() {
		shoot = !shoot;
		
		if(gun.weapon == Gun.Weapons.SHOTGUN) {
			bullet.reload();
			if(gun.cartridge > 0) {
				gun.cartridge --;
			}
		}
		
		if(gun.weapon == Gun.Weapons.PISTOL) {
			
			bullet.reload();
			if(gun.cartridge > 0) {
				gun.cartridge --;
			}
		}
		
	}
	
	@Override
	public void draw() {
		game();
		System.out.println("Bullets: "+ bullet.bullets.size());
		
	}
	
	@Override
    public void keyPressed() {
		player.keyp();
    	
    }
    
    @Override
    public void keyReleased() {
    	player.keyr();
    }
}
