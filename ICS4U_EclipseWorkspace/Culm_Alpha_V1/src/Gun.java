import java.util.ArrayList;

import processing.core.PApplet;

public class Gun {
	/*ArrayList for bullets*/
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	public static String gun;
	
	public static boolean machine = false, pistol = false, shotty = false;
	
	Gun(String type){
		this.gun = type;
		
	}
	
	public static class Bullet{
		public static int x, y, speed;
		
		public static int ammo, damage, cartridge, cartridgeMax;
		
		protected final PApplet pp;
		
		@SuppressWarnings("static-access")
		Bullet(int x1, int y1, int speed1, PApplet applet){
			this.x = x1;
			this.y = y1;
			this.speed = speed1;
			this.pp = applet;
		}
		
		@SuppressWarnings("static-access")
		void spawnBullets() {
			/*Machine gun has 3 reloads of 300 rounds each*/
			if(Gun.gun.equals("Machine Gun")) {
				this.ammo = 900;
				this.cartridge = 300;
				this.cartridgeMax = 300;
				this.damage = 20;
			}
			
			/*Pistol is the beginner gun has infinite ammo and 10 round cartridges*/
			if(Gun.gun.equals("Pistol")) {
				this.ammo = 50;
				this.damage = 10;
			}
			
			/*Shotgun has 6 reloads of 7 rounds each*/
			if(Gun.gun.equals("Shotgun")) {
				this.ammo = 42;
				this.cartridge = 7;
				this.cartridgeMax = 7;
				this.damage = 60;
			}
		}
		
		void update() {
			
		}
		
	}
	
	void select() {
		if(Gun.gun.equals("Machine Gun")) {
			Gun.machine = true;
			
		}
		
		if(Gun.gun.equals("Pistol")) {
			Gun.pistol = true;
			
		}
		
		if(Gun.gun.equals("Shotgun")) {
			Gun.shotty = true;
			
		}
	}
}
