import java.util.ArrayList;

import processing.core.PApplet;

public class Gun {
	
	public static String gun;
	
	public static boolean machine = false, pistol = false, shotty = false;
	
	public static int ammo, cartridge, cartridgeMax, ammo_till_max;
	
	static boolean shoot = false;
	
	Gun(String type){
		this.gun = type;
		
	}
	
	public static enum Weapons {
		SHOTGUN,
		PISTOL,
		MACHINE,
		KNIFE,
		KARAMBIT,
		GRENADE,
		FLASHBANG,
		INCINDIERY_GRENADE
	}
	
	public static Weapons weapon;
	
	public static class Bullet {
		public static int x, y, speed;
		
		public static int damage;
				
		public static boolean[] keys = new boolean[1];
		
		protected static PApplet pp;
		
		/*ArrayList for  Machine gun bullets*/
		static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
		
		@SuppressWarnings("static-access")
		Bullet(int x1, int y1, int speed1, PApplet applet){
			this.x = Player.x;
			this.y = Player.y;
			this.speed = speed1;
			Bullet.pp = applet;
		}
		
		@SuppressWarnings("static-access")
		public static void spawnBullets() {
			//TODO make initial sizes for bullet ArrayLists
			
			
			/*Machine gun has 3 reloads of 300 rounds each*/
			if(Gun.machine) {
				Bullet.damage = 20;
				
				for(int i = 0; i < Gun.ammo; i++) {
					bullets.add(new Bullet(Bullet.x, Bullet.y, 10, pp));
				}
				
			}
			
			/*Pistol is the beginner gun has 500 reloads of 10 round cartridges each*/
			if(Gun.pistol) {
				
				Bullet.damage = 10;
				
				for(int i = 0; i < Gun.ammo; i++) {
					bullets.add(new Bullet(Bullet.x, Bullet.y, 10, pp));
				}
			}
			
			/*Shotgun has 6 reloads of 7 rounds each*/
			if(Gun.shotty) {
				
				Bullet.damage = 60;
				
				for(int i = 0; i < Gun.ammo; i++) {
					bullets.add(new Bullet(Bullet.x, Bullet.y, 10, pp));
				}
				
			}
			
			
		}
		
		public static void reload() {
			if(Gun.cartridge <= 0)
				Player.reload = true;
			
			if(Player.reload == true && Gun.ammo > 0) {
				
				Gun.ammo_till_max = (Gun.cartridgeMax-Gun.cartridge);
				Gun.ammo = Gun.ammo - Gun.ammo_till_max;
				Gun.cartridge = Gun.cartridge+Gun.ammo_till_max;
				        		
        		Player.reload = false;
        		System.out.println("reloaded");
        	}
		}
		
		public static void update() {
			if(Gun.machine) {
				reload();
				if(pp.mousePressed == true && Gun.cartridge > 0) {
					Bullet.y = Bullet.y - Bullet.speed;
					Gun.cartridge --;
				}
				
			}
			
		}		
		
		
	}
	
	@SuppressWarnings("static-access")
	public static void select() {
		if(Gun.gun.equals("Machine Gun")) {
			weapon = Weapons.MACHINE;
			Gun.machine = true;
			Gun.ammo = 900;
			Gun.cartridge = 300;
			Gun.cartridgeMax = 300;
			
		}
		
		if(Gun.gun.equals("Pistol")) {
			weapon = Weapons.PISTOL;
			Gun.pistol = true;
			Gun.ammo = 5000;
			Gun.cartridge = 10;
			Gun.cartridgeMax = 10;
			
		}
		
		if(Gun.gun.equals("Shotgun")) {
			weapon = Weapons.SHOTGUN;
			Gun.shotty = true;
			Gun.ammo = 42;
			Gun.cartridge = 7;
			Gun.cartridgeMax = 7;
			
		}
	}
}
