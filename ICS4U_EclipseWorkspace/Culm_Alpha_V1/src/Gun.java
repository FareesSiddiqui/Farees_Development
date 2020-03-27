
public class Gun {
	
	public static String gun;
	
	public static boolean machine = false, pistol = false, shotty = false;
		
	public static int ammo, damage, cartridge, cartridgeMax;
	
	Gun(String type){
		this.gun = type;
	}
	
	void select() {
		
		/*Machine gun has 3 reloads of 300 rounds each*/
		if(this.gun.equals("Machine Gun")) {
			this.machine = true;
			this.ammo = 900;
			this.cartridge = 300;
			this.cartridgeMax = 300;
			this.damage = 20;
		}
		
		/*Pistol is the beginner gun has infinite ammo and 10 round cartridges*/
		if(this.gun.equals("Pistol")) {
			this.pistol = true;
			this.ammo = 50;
			this.damage = 10;
		}
		
		/*Shotgun has 6 reloads of 7 rounds each*/
		if(this.gun.equals("Shotgun")) {
			this.shotty = true;
			this.ammo = 42;
			this.cartridge = 7;
			this.cartridgeMax = 7;
			this.damage = 60;
		}
	}
}
